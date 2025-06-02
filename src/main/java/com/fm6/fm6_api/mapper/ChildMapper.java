package com.fm6.fm6_api.mapper;

import com.fm6.fm6_api.dto.ChildDto;
import com.fm6.fm6_api.entity.Child;
import com.fm6.fm6_api.entity.User;

public class ChildMapper {

    public static Child toEntity(ChildDto dto, User user) {
        Child c = new Child();
        c.setId(dto.getId());
        c.setNom(dto.getNom());
        c.setDateNaissance(dto.getDateNaissance());
        c.setStatut(dto.getStatut());
        c.setUser(user);
        return c;
    }

    public static ChildDto toDto(Child entity) {
        ChildDto dto = new ChildDto();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setDateNaissance(entity.getDateNaissance());
        dto.setStatut(entity.getStatut());
        return dto;
    }
}
