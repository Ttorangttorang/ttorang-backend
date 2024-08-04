package com.ttorang.domain.user.service;

import com.ttorang.domain.user.model.entity.User;
import com.ttorang.domain.user.repository.UserRepository;
import com.ttorang.global.code.ErrorCode;
import com.ttorang.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    /**
     * 이메일로 회원 찾기
     */
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * 회원 가입 (회원저장)
     */
    public User registUser(User user) {
        validateDuplicateUser(user);
        return userRepository.save(user);
    }

    /**
     * 회원 중복 검사
     */
    private void validateDuplicateUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_USER);
        }
    }


}
