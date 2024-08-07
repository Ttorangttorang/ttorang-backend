package com.ttorang.batch.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserBatchScheduler {

    private final UserDailyJob userDailyJob;

    /**
     * 매일 자정 delDate 1달 지난 사용자 삭제
     */
    @Scheduled(cron = "0 0 12 * * ?")
//    @Scheduled(fixedDelay = 60000) //test용
    public void runDailyJobForUser() {
        log.info("userDailyJob Update batch execute");
        userDailyJob.run();
    }

}
