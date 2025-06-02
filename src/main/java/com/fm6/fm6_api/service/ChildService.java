package com.fm6.fm6_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fm6.fm6_api.dto.ChildDto;
import com.fm6.fm6_api.mapper.ChildMapper;
import com.fm6.fm6_api.entity.Child;
import com.fm6.fm6_api.entity.User;
import com.fm6.repository.ChildRepository;
import com.fm6.repository.UserRepository;

@Service
public class ChildService {

    private final ChildRepository childRepo;
    private final UserRepository  userRepo;

    /* ---- CONSTRUCTEUR ---- */
    public ChildService(ChildRepository childRepo, UserRepository userRepo) {
        this.childRepo = childRepo;
        this.userRepo  = userRepo;
    }

    @Transactional
    public ChildDto createForUser(String username, ChildDto dto) {
        User user = userRepo.findByUsername(username)
                            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Child saved = childRepo.save(ChildMapper.toEntity(dto, user));
        return ChildMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<ChildDto> listForUser(String username) {
        User user = userRepo.findByUsername(username)
                            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return childRepo.findByUserId(user.getId())
                        .stream()
                        .map(ChildMapper::toDto)
                        .toList();
    }
}
