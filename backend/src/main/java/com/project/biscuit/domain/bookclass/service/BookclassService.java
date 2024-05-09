package com.project.biscuit.domain.bookclass.service;

import com.project.biscuit.domain.book.entity.Book;
import com.project.biscuit.domain.bookclass.model.ClassStatus;
import com.project.biscuit.domain.bookclass.entity.Bookclass;
import com.project.biscuit.domain.bookclass.entity.BookclassMember;
import com.project.biscuit.domain.bookclass.repository.BookclassMemberRepository;
import com.project.biscuit.domain.bookclass.repository.BookclassRepository;
import com.project.biscuit.domain.user.entity.User;
import com.project.biscuit.domain.bookclass.dto.BkclassMemRequestDto;
import com.project.biscuit.domain.bookclass.dto.BookclassReponseDto;
import com.project.biscuit.domain.bookclass.dto.BookclassRequestDto;
import com.project.biscuit.domain.book.repository.BookRepository;
import com.project.biscuit.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookclassService {
    private final BookclassRepository bookclassRepository;
    private final BookclassMemberRepository bkclassMemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    // 참여 여부, 클래스 참여 인원 수 response에 반영
    private List<BookclassReponseDto> chkJoinAndNumOfMem(List<Bookclass> bkcList, boolean partyChk, Long userNo) {

        List<Long> cntList = bkcList.stream().map(item -> {
            Long classNo = item.getNo();
            return bkclassMemRepository.countByBookclassNo(classNo);
        }).toList();

        AtomicInteger index = new AtomicInteger(); // index 값을 사용하기 위한 변수 선언
        List<BookclassReponseDto> BkcResDtoList;


        if(partyChk) { // 참여 했는지 안했는지 확인을 할지 말지 결정
            List<Boolean> partyList = bkcList.stream().map(item -> {
                Long classNo = item.getNo();
                return bkclassMemRepository.existsByBookclass_NoAndUser_No(classNo, userNo);
            }).toList();

            BkcResDtoList = bkcList.stream().map(item -> {
                int idx = index.getAndIncrement();
                return new BookclassReponseDto(item, partyList.get(idx), cntList.get(idx));
            }).toList();
        }
        else {
            BkcResDtoList = bkcList.stream().map(item -> {
                int idx = index.getAndIncrement();
                return new BookclassReponseDto(item, false, cntList.get(idx));
            }).toList();
        }

        return BkcResDtoList;
    }

    // --------------------

    // Create Bookclass
    @Transactional
    public BookclassReponseDto createBookclass(BookclassRequestDto req) {
        Optional<Book> optBooks = bookRepository.findByIsbn(req.getIsbn());
        Optional<User> optUser = userRepository.findByUserId(req.getUserId());
        Bookclass bookclass = req.toEntity(optUser.get(), optBooks.get());

        Bookclass savedBkc = bookclassRepository.save(bookclass);
        bkclassMemRepository.save(BookclassMember.toEntity(savedBkc, optUser.get()));

        return new BookclassReponseDto(savedBkc, true, 1L);
    }

    // Read : All Bookclass with status Y
    public List<BookclassReponseDto> findAllBookclass(Long userNo) {
        List<Bookclass> bkcList = bookclassRepository.findByStatusOrderByUpdatedAtDesc(ClassStatus.Y);
        if(userNo == null) return chkJoinAndNumOfMem(bkcList, false, null);
        else return chkJoinAndNumOfMem(bkcList, true, userNo);
    }

    // Read : Bookclass Detail
    public BookclassReponseDto findBookclass(long classNo, Long userNo) {
        Bookclass bkc = bookclassRepository.findById(classNo).orElseThrow(() -> new IllegalArgumentException("not found : " + classNo));

        if(userNo != 0L) {
            boolean partyChk = bkclassMemRepository.existsByBookclass_NoAndUser_No(classNo, userNo);
            Long cnt = bkclassMemRepository.countByBookclassNo(classNo);

            return new BookclassReponseDto(bkc, partyChk, cnt);
        } else {
            Long cnt = bkclassMemRepository.countByBookclassNo(classNo);

            return new BookclassReponseDto(bkc, false, cnt);
        }
    }

    // Read : Bookclass for edit
    public Bookclass findBookclassForEdit(long bookclassId) {
        return bookclassRepository.findById(bookclassId).orElseThrow(
                () -> new IllegalArgumentException("not found : " + bookclassId)
        );
    }

    // Update : Bookclass
    @Transactional // 트랜잭션 메서드 : DB에서 데이터를 바꾸기 위한 작업 단위 (ex: 계좌이체(AtoB) => A계좌 출금 후, B계좌 입금)
    public Bookclass updateBookclass(Long bookclassId, BookclassRequestDto req) {
        Bookclass bookclass = bookclassRepository.findById(bookclassId).orElseThrow(
                () -> new IllegalArgumentException("not found : " + bookclassId)
        );

        Optional<Book> book = bookRepository.findByIsbn(req.getIsbn());
        return bookclassRepository.save(bookclass.update(req, book));
    }

    // 북클래스 제목, 작성자 이름으로 검색
    public List<BookclassReponseDto> searchBookclass(String keyword, Long userNo) {
        List<Bookclass> byTitle = bookclassRepository.findByTitleContaining(keyword);
        List<Bookclass> byName = bookclassRepository.findByUserNo_NicknameContaining(keyword);
        List<Bookclass> bckList = new ArrayList<>();
        bckList.addAll(byTitle);
        bckList.addAll(byName);

        byName.forEach(ia -> {
            byTitle.forEach(ib -> {
                if(ib.getNo().equals(ia.getNo())) {
                    bckList.remove(ia);
                }
            });
        });
        if(userNo == null) return chkJoinAndNumOfMem(byTitle, false, null);
        return chkJoinAndNumOfMem(byTitle, true, userNo);
    }


    // 사용자 action ----
    // 클래스 참여
    public String userJoinBookclass(long classNo, long userNo) {
        boolean inChk = bkclassMemRepository.existsByBookclass_NoAndUser_No(classNo, userNo);
        if(inChk) {
            return "already in";
        } else {
            Bookclass bkc = bookclassRepository.findById(classNo).orElseThrow(() -> new IllegalArgumentException("Not Exist Class"));
            Long nowCnt = bkclassMemRepository.countByBookclassNo(classNo);

            if(nowCnt < bkc.getMemberCnt()) {
                Optional<User> optUser = userRepository.findById(userNo);

                BookclassMember bckmember = BookclassMember.toEntity(bkc, optUser.get());
                bkclassMemRepository.save(bckmember);

                return "success";
            } else {
                return "max";
            }

        }
    }

    // 클래스 참여 취소
    public String userLeaveBookclass(long classNo, long userNo) {
        boolean inChk = bkclassMemRepository.existsByBookclass_NoAndUser_No(classNo, userNo);
        if(!inChk) {
            return "not in";
        } else {
            Optional<BookclassMember> optBkcMem = bkclassMemRepository.findByBookclass_NoAndUser_No(classNo, userNo);
            BookclassMember bkcMem = optBkcMem.get();

            bkclassMemRepository.deleteById(bkcMem.getNo());

            return "Success";
        }
    }

    // 사용자의 북클래스 개설 목록 가져오기
    public List<BookclassReponseDto> getOpenedClass(BkclassMemRequestDto req) {
        List<Bookclass> bkcList = switch (req.getSortNum()) {
            case 0 -> bookclassRepository.findByUserNo_NoOrderByCreatedAtDesc(req.getUserNo()); // 전체 (정렬 기준)
            case 1 -> bookclassRepository.findByUserNo_NoOrderByCreatedAtDesc(req.getUserNo())
                    .stream().filter(item -> item.getStatus().equals(ClassStatus.Y))
                    .collect(Collectors.toList()); // 모집중 상태
            case 2 -> bookclassRepository.findByUserNo_NoOrderByCreatedAtDesc(req.getUserNo())
                    .stream().filter(item -> item.getStatus().equals(ClassStatus.A))
                    .collect(Collectors.toList()); // 신청중 상태
            default -> throw new IllegalStateException("Unexpected value: " + req.getSortNum());
        };
        return chkJoinAndNumOfMem(bkcList, false, null);
    }

    // 사용자의 북클래스 참여 목록 가져오기
    public List<BookclassReponseDto> getParticipatedClass(BkclassMemRequestDto req) {
        List<BookclassMember> bkmcList = switch (req.getSortNum()) {
            case 0 -> bkclassMemRepository.findByUser_NoOrderByCreatedAtDesc(req.getUserNo()); // 전체 (정렬 기준)
            case 1 -> bkclassMemRepository.findByUser_NoOrderByCreatedAtDesc(req.getUserNo())
                    .stream().filter(item -> item.getBookclass().getStatus().equals(ClassStatus.Y))
                    .collect(Collectors.toList()); // 모집중 상태
            case 2 -> bkclassMemRepository.findByUser_NoOrderByCreatedAtDesc(req.getUserNo())
                    .stream().filter(item -> item.getBookclass().getStatus().equals(ClassStatus.E))
                    .collect(Collectors.toList()); // 종료됨 상태
            default -> throw new IllegalStateException("Unexpected value: " + req.getSortNum());
        };

        List<Long> cntList = bkmcList.stream().map(item -> {
            Long classNo = item.getBookclass().getNo();
            return bkclassMemRepository.countByBookclassNo(classNo);
        }).toList();

        AtomicInteger index = new AtomicInteger();
        List<BookclassReponseDto> BkcResDtoList = bkmcList.stream().map(item -> {
            int idx = index.getAndIncrement();
            return new BookclassReponseDto(item, false, cntList.get(idx));
        }).toList();

        return BkcResDtoList;
    }


    // 관리자 기능 =====
    // Delete : Bookclass
    public void deleteBookclass(Long classNo) {
        bookclassRepository.deleteById(classNo);
    };

    // 전체 목록 및 승인해야할 목록 가져오기
    public List<BookclassReponseDto> awaitBookclass(int sortNum) {
        if(sortNum == 1) { // 승인해야할 목록
            List<Bookclass> bkcList = bookclassRepository.findByStatusOrderByUpdatedAtDesc(ClassStatus.A);
            return chkJoinAndNumOfMem(bkcList, false, null);
        }
        else { // 전체 목록
            List<Bookclass> bkcList = bookclassRepository.findAll();
            return chkJoinAndNumOfMem(bkcList, false, null);
        }
    };

    // 북클래스 상태 변경
    @Transactional
    public void changeBookclassStatus(List<Long> classNoList, String status) {
        classNoList.forEach(classNo -> {
            Bookclass bkc = bookclassRepository.findById(classNo).orElseThrow(() -> new IllegalArgumentException("not found class"));
            bkc.setStatus(ClassStatus.valueOf(status));
        });
    }

}
