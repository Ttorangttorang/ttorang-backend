package com.ttorang.domain.user.service;

import com.ttorang.domain.qna.repository.QnaRepository;
import com.ttorang.domain.script.model.entity.Script;
import com.ttorang.domain.script.repository.ScriptRepository;
import com.ttorang.domain.user.model.dto.response.DeleteUserResponse;
import com.ttorang.domain.user.model.entity.User;
import com.ttorang.domain.user.repository.UserRepository;
import com.ttorang.global.code.ErrorCode;
import com.ttorang.global.error.exception.BusinessException;
import com.ttorang.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.ttorang.global.code.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final ScriptRepository scriptRepository;
    private final QnaRepository qnaRepository;

    /**
     * 이메일로 회원 찾기
     */
    public User findUserByEmail(String email) {
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
     * 탈퇴한 회원 재로그인
     */
    @Transactional
    public User updateUser(User user) {
        User savedUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_USER));
        savedUser.updateDeleteYn("N");
        return savedUser;
    }

    /**
     * 회원 중복 검사
     */
    private void validateDuplicateUser(User user) {
        User savedUser = userRepository.findByEmail(user.getEmail());
        if (savedUser != null) {
            throw new BusinessException(ALREADY_REGISTERED_USER);
        }
    }

    /**
     * 회원 탈퇴 (deleteYN update)
     */
    @Transactional
    public DeleteUserResponse deleteUserUpdateYn(Long userId) {
        User savedUser = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_USER));
        savedUser.withDraw("Y");
        return DeleteUserResponse.of(savedUser.getId());
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void deleteUser() {
        LocalDateTime delDate = LocalDateTime.now().minusMonths(1);
        List<User> deletedUsers = userRepository.getDeletedUser(delDate);

        if (deletedUsers.isEmpty()) {
            return;
        }

        List<Script> scriptsToDelete = scriptRepository.findByUserIn(deletedUsers);

        qnaRepository.deleteByScriptIn(scriptsToDelete);

        scriptRepository.deleteByUserIn(deletedUsers);

        List<Long> userIds = deletedUsers.stream()
                .map(user -> user.getId())
                .collect(Collectors.toList());

        userRepository.deleteByIdIn(userIds);

    }
}
