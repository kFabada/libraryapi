package com.fabada.librayapi.services;

import com.fabada.librayapi.controller.dto.CadastroLivroDTO;
import com.fabada.librayapi.controller.dto.ErroResposta;
import com.fabada.librayapi.exceptions.RegistroDuplicadoException;
import com.fabada.librayapi.model.GeneroLivro;
import com.fabada.librayapi.model.Livro;
import com.fabada.librayapi.repository.LivroRepository;
import com.fabada.librayapi.repository.specs.LivroSpecs;
import com.fabada.librayapi.validador.LivroValidator;
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

import static com.fabada.librayapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;


    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro){
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa(String isbn, String titulo, String nomeAutor, GeneroLivro generoLivro, Integer anoPublicacao){

//        Specification<Livro> specs = Specification
//                .where(LivroSpecs.isbnEqual(isbn))
//                .and(LivroSpecs.tituloLike(titulo))
//                .and(LivroSpecs.generoEqual(generoLivro))
//                ;

        Specification<Livro> specs = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.conjunction());
        if (isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }
        if (titulo != null){
            specs = specs.and(tituloLike(titulo));
        }
        if (generoLivro != null){
            specs = specs.and(generoEqual(generoLivro));
        }
        if (anoPublicacao != null){
            specs = specs.and(anoPublicacao(anoPublicacao));
        }
        if (nomeAutor != null){
            specs = specs.and(nomeAutorLike(nomeAutor));
        }


        return livroRepository.findAll(specs);

    }

    public void atualizar(Livro livro) {
        if (livro.getUuid() == null){
            throw new IllegalArgumentException("Para atualizar é necessario que o livro ja esteja no banco");

        }
        livroValidator.validar(livro);
        livroRepository.save(livro);
    }
}
