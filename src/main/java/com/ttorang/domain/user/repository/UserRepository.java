package com.ttorang.domain.user.repository;

import com.ttorang.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("select u from User u where u.deleteYn = 'Y' and u.deleteTime < :deleteTime")
    List<User> getDeletedUser(@Param("deleteTime") LocalDateTime deleteTime);

    void deleteByIdIn(List<Long> userIds);
}
