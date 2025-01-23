package com.fabada.librayapi.validador;

import com.fabada.librayapi.controller.dto.AutorDTO;
import com.fabada.librayapi.controller.dto.AutorDTOResponse;
import com.fabada.librayapi.exceptions.RegistroDuplicadoException;
import com.fabada.librayapi.model.Autor;
import com.fabada.librayapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor){
        if (existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Autor ja cadastrado");
        }
    }

    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado = autorRepository
                .findByNomeAndDataNascimentoAndNacionalidade(
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade());

        if (autor.getUuid() == null){
            return autorEncontrado.isPresent();
        }

        return !autor.getUuid().equals(autorEncontrado.get().getUuid()) && autorEncontrado.isPresent();
    }
}
