package com.project.biscuit.domain.admin.service;

import com.project.biscuit.domain.user.dto.UserResponseDto;
import com.project.biscuit.domain.user.entity.User;
import com.project.biscuit.domain.user.model.Authority;
import com.project.biscuit.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    // 사용자 전체 목록 조회
    public List<UserResponseDto> getUserAll(String type) { // type 설정
        return switch (type) {
            case "All" -> {
                List<User> userList = userRepository.findAll();
                yield  userList.stream().map(UserResponseDto::toAdmin).toList();
            }
            case "User" -> {
                List<User> userList = userRepository.findByAuthorityAndQuitYn(Authority.U, "N");
                yield  userList.stream().map(UserResponseDto::toAdmin).toList();
            }
            case "Out" -> {
                List<User> userList = userRepository.findByQuitYn("Y");
                yield  userList.stream().map(UserResponseDto::toAdmin).toList();
            }
            case "Admin" -> {
                List<User> userList = userRepository.findByAuthority(Authority.A);
                yield  userList.stream().map(UserResponseDto::toAdmin).toList();
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    // 사용자를 관리자로 변경
    @Transactional
    public void changeAuthority(List<Long> userNoList) {
        if(userNoList.isEmpty())
            throw new  IllegalArgumentException();
        else {
            userNoList.forEach(item -> {
                User user = userRepository.findById(item).orElseThrow(() -> new IllegalArgumentException("Not found User"));

                if (user.getAuthority().equals(Authority.U)) user.setAuthority(Authority.A);
                else user.setAuthority(Authority.U);
            });
        }
    }

    // 사용자의 활성화 상태 변경
    @Transactional
    public void changeUserStatus(List<Long> userNoList) {
        if(userNoList.isEmpty())
            throw new  IllegalArgumentException();
        else {
            userNoList.forEach(item -> {
                User user = userRepository.findById(item).orElseThrow(() -> new IllegalArgumentException("Not found User"));

                if (user.getQuitYn().equals("N")) user.setQuitYn("Y");
                else user.setQuitYn("N");
            });
        }
    }
}
