package com.fabada.librayapi.controller.mappers;

import com.fabada.librayapi.controller.dto.CadastroLivroDTO;
import com.fabada.librayapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.fabada.librayapi.model.Livro;
import com.fabada.librayapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMappear.class)
public abstract class LivroMappear {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public  abstract Livro toEntity(CadastroLivroDTO dto);

     public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
