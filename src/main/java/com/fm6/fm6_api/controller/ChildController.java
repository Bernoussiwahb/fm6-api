package com.fm6.fm6_api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fm6.fm6_api.dto.ChildDto;
import com.fm6.fm6_api.service.ChildService;

@RestController
@RequestMapping("/children")
public class ChildController {

    private final ChildService service;

    public ChildController(ChildService service) { this.service = service; }

    /* ---------- POST /children ---------- */
    @PostMapping
    public ResponseEntity<ChildDto> create(@RequestBody @Validated ChildDto dto) {

        // récupère le username contenu dans le JWT
        String username = SecurityContextHolder.getContext()
                                               .getAuthentication()
                                               .getName();

        ChildDto saved = service.createForUser(username, dto);

        return ResponseEntity.created(URI.create("/children/" + saved.getId()))
                             .body(saved);
    }

    /* ---------- GET /children ---------- */
    @GetMapping
    public List<ChildDto> list() {
        String username = SecurityContextHolder.getContext()
                                               .getAuthentication()
                                               .getName();
        return service.listForUser(username);
    }

    /* ---------- GET /children/{id} ---------- */
    @GetMapping("/{id}")
    public ChildDto get(@PathVariable Long id) {
        return service.getById(id);
    }

    /* ---------- PUT /children/{id} ---------- */
    @PutMapping("/{id}")
    public ChildDto update(@PathVariable Long id,
                           @RequestBody @Validated ChildDto dto) {
        return service.update(id, dto);
    }

    /* ---------- DELETE /children/{id} ---------- */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
