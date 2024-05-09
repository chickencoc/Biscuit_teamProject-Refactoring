package com.project.biscuit.domain.admin.controller;

import com.project.biscuit.domain.admin.service.AdminService;
import com.project.biscuit.global.model.EntityNoListRequestDto;
import com.project.biscuit.domain.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admins/user")
public class AdminUserController {

    private final AdminService adminService;

    // 사용자 전체 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDto>> getListAll(@RequestParam String type) {
        return ResponseEntity.ok(adminService.getUserAll(type));
    }

    // 사용자를 관리자로 변경
    @PatchMapping("/authority")
    public ResponseEntity<Void> changeAuthority(@RequestBody EntityNoListRequestDto request) {
        adminService.changeAuthority(request.getEntityNoList());
        return ResponseEntity.noContent().build();
    }

    // 사용자의 활성화 상태 변경
    @DeleteMapping("/out/{userNoList}")
    public ResponseEntity<Void> outUser(@PathVariable List<Long> userNoList) {
        adminService.changeUserStatus(userNoList);
        return ResponseEntity.noContent().build();
    }

}
