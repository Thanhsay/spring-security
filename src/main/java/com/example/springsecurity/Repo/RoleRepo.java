package com.example.springsecurity.Repo;

import com.example.springsecurity.Model.Role;
import com.example.springsecurity.Model.URole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(URole name);
}
