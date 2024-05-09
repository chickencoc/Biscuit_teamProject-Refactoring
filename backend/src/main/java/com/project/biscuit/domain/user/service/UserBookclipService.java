package com.project.biscuit.domain.user.service;

import com.project.biscuit.domain.bookclip.entity.Bookclip;
import com.project.biscuit.domain.bookclip.dto.BookClipRequestDto;
import com.project.biscuit.domain.bookclip.dto.BookClipResponseDto;
import com.project.biscuit.domain.book.view.BookclipAndBookcnt;
import com.project.biscuit.domain.book.view.BookclipAndBookcntRepository;
import com.project.biscuit.domain.bookclip.repository.BookclipRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserBookclipService {

    private final BookclipRepository bookclipRepository;
    private final BookclipAndBookcntRepository bkcabRepository;
    private final EntityManager em;

    // 사용자의 북클립 목록 가져오기

    public List<BookClipResponseDto> getMyClips(BookClipRequestDto req) {
        switch (req.getSortNum()) {
            case 0 -> {
                return bookclipRepository.findByDelYnAndUser_NoOrderByUpdatedAtDesc("N", req.getUserNo())
                        .stream().map(BookClipResponseDto::new).toList();
            }
            case 1 -> {
                return bookclipRepository.findByDelYnAndUser_NoOrderByBook_Price("N", req.getUserNo())
                        .stream().map(BookClipResponseDto::new).toList();
            }
            case 2 -> {
                List<Bookclip> listClip = bookclipRepository.findByDelYnAndUser_No("N", req.getUserNo());
                List<BookclipAndBookcnt> listCnt = bkcabRepository.findByUserNoOrderByBcntDescUpdatedAtDesc(req.getUserNo());

                return listCnt.stream().map(item -> {
                    List<Bookclip> oneClip = listClip.stream().filter(clip -> item.getBookNo() == clip.getBook().getNo()).toList();

                    return new BookClipResponseDto(oneClip.get(0), item);
                }).toList();
            }
        }
        return null;
    }

    // 사용자의 북클립 삭제 처리
    public String delMyClip(BookClipRequestDto req) {
        Optional<Bookclip> optBookClip = bookclipRepository.findByBook_NoAndUser_No(req.getBookNo(), req.getUserNo());

        if(optBookClip.isPresent()) {
            Bookclip bookClip = optBookClip.get();
            bookClip.setDelYn("Y");

            bookclipRepository.save(bookClip);
            return "{\"result\": \"success\"}";
        }
        return "error";
    }
}
