package com.fabada.librayapi.controller.mappers;

import com.fabada.librayapi.controller.dto.AutorDTO;
import com.fabada.librayapi.controller.dto.AutorDTOResponse;
import com.fabada.librayapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMappear {

    Autor toEntity(AutorDTO dto);
    AutorDTO toDto(Autor autor);
    AutorDTOResponse toAutorDTOResponse(Autor autor);
}
