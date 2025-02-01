package com.fabada.librayapi.controller.mappers;

import com.fabada.librayapi.controller.dto.UsuarioDTO;
import com.fabada.librayapi.model.Usuario;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Mapper(componentModel = "spring")

public interface UsuarioMappear {

    Usuario dtoToUsuario(UsuarioDTO dto);
}
