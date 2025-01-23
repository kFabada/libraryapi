package com.fabada.librayapi.services;

import com.fabada.librayapi.controller.dto.AutorDTO;
import com.fabada.librayapi.exceptions.OperacaoNaoPermitidaException;
import com.fabada.librayapi.model.Autor;
import com.fabada.librayapi.model.Livro;
import com.fabada.librayapi.repository.AutorRepository;
import com.fabada.librayapi.repository.LivroRepository;
import com.fabada.librayapi.validador.AutorValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;

    public AutorService(AutorRepository autorRepository, AutorValidator autorValidator, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.autorValidator = autorValidator;
        this.livroRepository = livroRepository;
    }

    public Autor salvar(Autor autor){
        autorValidator.validar(autor);
        return  autorRepository.save(autor);
    }

    public void atualizar(Autor autor){

        if (autor.getUuid() == null){
            throw new IllegalArgumentException("id não localizado");
        }

        autorValidator.validar(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return  autorRepository.findById(id);
    }

    public List<AutorDTO> obterTodosAutores(){
        List<Autor> autores = autorRepository.findAll();

        List<AutorDTO> autorDTOS = new ArrayList<>();

        for (Autor all: autores){
            AutorDTO dto = new AutorDTO(all.getUuid(), all.getNome(), all.getDataNascimento(),all.getNacionalidade());
            autorDTOS.add(dto);
        }

        return autorDTOS;
    }

    public void deletarAutor(Autor autor){
        if (possuLivro(autor)){
            throw new OperacaoNaoPermitidaException("Não é Permitido excluir autor com livro");
        }
        autorRepository.deleteById(autor.getUuid());
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if (nome != null && nacionalidade != null){
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if (nome != null){
            return autorRepository.findByNome(nome);
        }

        if (nacionalidade != null){
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();
    }

    public boolean possuLivro(Autor autor){
       return livroRepository.existsByAutor(autor);
    }
}
