package com.steppedua.securityproject.repository;


import com.steppedua.securityproject.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
}
