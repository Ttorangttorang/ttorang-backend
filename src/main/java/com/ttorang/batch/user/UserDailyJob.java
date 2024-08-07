package com.ttorang.batch.user;

import com.ttorang.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDailyJob {

    private final UserService userService;

    public void run() {
        userService.deleteUser();
    }

}
