package com.fabada.librayapi.controller;

import com.fabada.librayapi.controller.dto.UsuarioDTO;
import com.fabada.librayapi.controller.mappers.UsuarioMappear;
import com.fabada.librayapi.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMappear usuarioMappear;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto){
        var usuario = usuarioMappear.dtoToUsuario(dto);
        usuarioService.salvar(usuario);
    }

}
