package com.project.biscuit.domain.bookclass.controller;

import com.project.biscuit.domain.bookclass.dto.BookclassEditReponseDto;
import com.project.biscuit.domain.bookclass.dto.BookclassReponseDto;
import com.project.biscuit.domain.bookclass.dto.BookclassRequestDto;
import com.project.biscuit.domain.bookclass.entity.Bookclass;
import com.project.biscuit.domain.bookclass.service.BookclassService;
import com.project.biscuit.global.model.EntityNoListRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/bookclass")
@RestController
public class BookclassController {
    private final BookclassService bookclassService;

    // 클래스 개설 신청
    @PostMapping("/create")
    public ResponseEntity<BookclassReponseDto> createBookclass(@RequestBody BookclassRequestDto req) {
        return ResponseEntity.ok(bookclassService.createBookclass(req));
    }

    // 북클래스 목록 조회 (사용자 참여 여부 포함)
    @GetMapping("/list")
    public ResponseEntity<List<BookclassReponseDto>> findAllBookclass(@RequestParam long userNo) {
        return ResponseEntity.ok(bookclassService.findAllBookclass(userNo));
    }

    // 북클래스 상세 조회 (사용자 참여 여부 포함)
    @GetMapping("/view/{classNo}")
    public ResponseEntity<BookclassReponseDto> findBookclass(@PathVariable Long classNo,
                                                             @RequestParam Long userNo) {
        return ResponseEntity.ok(bookclassService.findBookclass(classNo, userNo));
    }

    // 북클래스 수정용 정보 조회
    @GetMapping("/view/edit/{classNo}")
    public ResponseEntity<BookclassEditReponseDto> findBookclassForEdit(@PathVariable Long classNo) {
        Bookclass bookclass = bookclassService.findBookclassForEdit(classNo);
        return ResponseEntity.ok(BookclassEditReponseDto.of(bookclass));
    }

    // 북클래스 정보 수정
    @PatchMapping("/update/{classNo}")
    public ResponseEntity<Bookclass> updateBookclass(@PathVariable Long classNo,
                                                     @RequestBody BookclassRequestDto requestDto) {
        Bookclass updatedBookclass = bookclassService.updateBookclass(classNo, requestDto);
        return ResponseEntity.ok(updatedBookclass);
    }

    // 북클래스 검색
    @GetMapping("/search")
    public ResponseEntity<List<BookclassReponseDto>> searchBookclass(@RequestParam String keyword,
                                                                     @RequestParam Long userNo) {
        return ResponseEntity.ok(bookclassService.searchBookclass(keyword, userNo));
    }


    // 사용자 기능 -------------------

    // 북클래스 참여하기
    @PostMapping("/join/{classNo}/user/{userNo}")
    public ResponseEntity<String> userJoinClass(@PathVariable long classNo,
                                                @PathVariable long userNo) {
        return ResponseEntity.ok(bookclassService.userJoinBookclass(classNo, userNo));
    }

    // 북클래스 참여 취소하기
    @DeleteMapping("/leave/{classNo}/user/{userNo}")
    public ResponseEntity<String> userLeaveClass(@PathVariable long classNo,
                                                 @PathVariable long userNo) {
        return ResponseEntity.ok(bookclassService.userLeaveBookclass(classNo, userNo));
    }


    // 관리자 기능 -----------------

    // 북클래스 삭제
    @DeleteMapping("/admin/delete/{classNo}")
    public ResponseEntity<Void> deleteBookclass(@PathVariable long classNo) {
        bookclassService.deleteBookclass(classNo);
        return ResponseEntity.ok().build();
    }

    // 북클래스 전체 목록 또는 승인 대기 목록 조회
    @GetMapping("/admin/list")
    public ResponseEntity<List<BookclassReponseDto>> awaitBookclass(@RequestParam int sort) {
        return ResponseEntity.ok().body(bookclassService.awaitBookclass(sort));
    }

    // 북클래스 개설 상태 변경
    @PatchMapping("/admin/change/status/{status}")
    public ResponseEntity<Void> changeBookclassStatus(@PathVariable String status, @RequestBody EntityNoListRequestDto requestDto) {
        bookclassService.changeBookclassStatus(requestDto.getEntityNoList(), status);
        return ResponseEntity.ok().build();
    }

}
