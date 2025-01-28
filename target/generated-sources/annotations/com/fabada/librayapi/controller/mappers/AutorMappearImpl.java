package com.fabada.librayapi.controller.mappers;

import com.fabada.librayapi.controller.dto.AutorDTO;
import com.fabada.librayapi.controller.dto.AutorDTOResponse;
import com.fabada.librayapi.model.Autor;
import java.time.LocalDate;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-27T22:12:06-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Azul Systems, Inc.)"
)
@Component
public class AutorMappearImpl implements AutorMappear {

    @Override
    public Autor toEntity(AutorDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Autor autor = new Autor();

        autor.setNome( dto.nome() );
        autor.setNacionalidade( dto.nacionalidade() );

        return autor;
    }

    @Override
    public AutorDTO toDto(Autor autor) {
        if ( autor == null ) {
            return null;
        }

        String nome = null;
        String nacionalidade = null;

        nome = autor.getNome();
        nacionalidade = autor.getNacionalidade();

        UUID id = null;
        LocalDate date = null;

        AutorDTO autorDTO = new AutorDTO( id, nome, date, nacionalidade );

        return autorDTO;
    }

    @Override
    public AutorDTOResponse toAutorDTOResponse(Autor autor) {
        if ( autor == null ) {
            return null;
        }

        UUID uuid = null;
        String nome = null;
        LocalDate dataNascimento = null;
        String nacionalidade = null;

        uuid = autor.getUuid();
        nome = autor.getNome();
        dataNascimento = autor.getDataNascimento();
        nacionalidade = autor.getNacionalidade();

        AutorDTOResponse autorDTOResponse = new AutorDTOResponse( uuid, nome, dataNascimento, nacionalidade );

        return autorDTOResponse;
    }
}
