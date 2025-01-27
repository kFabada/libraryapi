package com.fabada.librayapi.controller.mappers;

import com.fabada.librayapi.controller.dto.CadastroLivroDTO;
import com.fabada.librayapi.model.Livro;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-26T22:25:00-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Azul Systems, Inc.)"
)
@Component
public class LivroMappearImpl extends LivroMappear {

    @Override
    public Livro toEntity(CadastroLivroDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Livro livro = new Livro();

        livro.setIsbn( dto.isbn() );
        livro.setTitulo( dto.titulo() );
        livro.setDataPublicacao( dto.dataPublicacao() );
        livro.setGenero( dto.genero() );
        livro.setPreco( dto.preco() );

        livro.setAutor( autorRepository.findById(dto.idAutor()).orElse(null) );

        return livro;
    }
}
