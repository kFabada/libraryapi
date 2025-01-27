package com.fabada.librayapi.services;

import com.fabada.librayapi.controller.dto.CadastroLivroDTO;
import com.fabada.librayapi.controller.dto.ErroResposta;
import com.fabada.librayapi.exceptions.RegistroDuplicadoException;
import com.fabada.librayapi.model.GeneroLivro;
import com.fabada.librayapi.model.Livro;
import com.fabada.librayapi.repository.LivroRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;


    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro){
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa(String isbn, String nomeAutor, GeneroLivro generoLivro, Integer anoPublicacao){

        Specification<Livro> specs = null;

        return livroRepository.findAll(specs);

    }
}
