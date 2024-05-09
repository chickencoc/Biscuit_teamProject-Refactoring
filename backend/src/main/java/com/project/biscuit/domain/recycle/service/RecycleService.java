package com.project.biscuit.domain.recycle.service;

import com.project.biscuit.domain.book.entity.Book;
import com.project.biscuit.domain.recycle.entity.Recycle;
import com.project.biscuit.domain.user.entity.User;
import com.project.biscuit.domain.recycle.dto.AddRecycleRequestDto;
import com.project.biscuit.domain.recycle.dto.CampaignRequestDto;
import com.project.biscuit.domain.recycle.dto.CampaignResponseDto;
import com.project.biscuit.domain.recycle.dto.SaleCampaignRequestDto;
import com.project.biscuit.domain.book.repository.BookRepository;
import com.project.biscuit.domain.recycle.repository.RecycleRepository;
import com.project.biscuit.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecycleService {
    private final RecycleRepository recycleRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    /** 중고 도서 접수 */
    public CampaignResponseDto apply(AddRecycleRequestDto requestDto) {
        Optional<Book> books = bookRepository.findByIsbn(requestDto.getIsbn());
        Optional<User> user = userRepository.findByUserId(requestDto.getUserId());
        Recycle recycle = requestDto.toEntity(user.get(), books.get());
        return CampaignResponseDto.of(recycleRepository.save(recycle));
    }

    /** 승인된 게시글 전체 조회 */
    public List<Recycle> findAllAcceptY(String y) {
        return recycleRepository.findAllByAcceptYn(y);
    }

    /** 전체 게시글 조회 (관리자만 보임)*/
    public List<Recycle> findAll() {
        return recycleRepository.findAll();
    }

    /** 상세페이지 */
    public Recycle recycleView(Long recycleNo) {
        recycleRepository.updateCount(recycleNo);
        return recycleRepository.findByRecycleNo(recycleNo).orElseThrow(
                () -> new IllegalArgumentException("not found :" + recycleNo)
        );
    }
    
    /** 구매여부 변경 */
    @Transactional
    public void updateSaleYn(Long recycleNo, SaleCampaignRequestDto dto){
        Recycle recycle = recycleRepository.findByRecycleNo(recycleNo).orElseThrow(() ->
                new IllegalArgumentException("해당 캠페인이 존재 하지 않습니다."));
        String sale = dto.getSaleYn();
        recycle.updateSaleYn(sale);
    }

    /** 관리자 허용여부 */
    @Transactional
    public void updateAccept(CampaignRequestDto dto){
        Recycle recycle = recycleRepository.findByRecycleNo(dto.getRecycleNo()).orElseThrow(() ->
                new IllegalArgumentException("해당 캠페인이 존재 하지 않습니다."));

        recycle.updateAccept(
                dto.getAcceptYn(),
                dto.getStatus(),
                dto.getDiscountRate(),
                dto.getSalePrice()
        );
    }

    /** 조회수 */
    @Transactional
    public int updateCnt(Long recycleNo){
        return recycleRepository.updateCount(recycleNo);
    }


}
