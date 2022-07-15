package com.example.springsecurity.Repo;

import com.example.springsecurity.Model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    @Query(value = "INSERT INTO user_role (user_id, role_id) VALUES(:userId, :roleId)", nativeQuery = true)
    void saveUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
