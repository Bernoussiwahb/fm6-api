package com.fm6.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fm6.fm6_api.entity.Child;

public interface ChildRepository extends JpaRepository<Child, Long> {
    List<Child> findByUserId(Long userId);
}
