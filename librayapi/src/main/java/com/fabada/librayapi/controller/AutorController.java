package com.fabada.librayapi.controller;


import com.fabada.librayapi.controller.dto.AutorDTO;
import com.fabada.librayapi.controller.dto.AutorDTOResponse;
import com.fabada.librayapi.controller.dto.ErroResposta;
import com.fabada.librayapi.exceptions.OperacaoNaoPermitidaException;
import com.fabada.librayapi.exceptions.RegistroDuplicadoException;
import com.fabada.librayapi.model.Autor;
import com.fabada.librayapi.services.AutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
public class AutorController {


    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid AutorDTO autorDTO){

        try {
            Autor autorEntidade = autorDTO.mapearParaAutor();

            autorService.salvar(autorEntidade);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("{/id}")
                    .buildAndExpand(autorEntidade.getUuid())
                    .toUri();

//        ErroResposta erroResposta = ErroResposta.conflito("Autor já cadastrado!");
//        return ResponseEntity.status(erroResposta.status()).body(erroResposta);

            return ResponseEntity.created(location).build();

        }catch (RegistroDuplicadoException e){
            ErroResposta erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletaAutor(@PathVariable("id") String id){
       try{
           UUID idbody = UUID.fromString(id);
           Optional<Autor> autorOptional = autorService.obterPorId(idbody);

           if (autorOptional.isEmpty()){
               return ResponseEntity.notFound().build();
           }

           autorService.deletarAutor(autorOptional.get());
           return ResponseEntity.noContent().build();
       }catch (OperacaoNaoPermitidaException e){
           var erro = ErroResposta.respostaPadrao(e.getMessage());
           return ResponseEntity.status(erro.status()).body(erro);
       }


    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTOResponse> obterDetalher(@PathVariable("id") String id){
        UUID idAutor = UUID.fromString(id);

        Optional<Autor> autor = autorService.obterPorId(idAutor);

        if (autor.isPresent()){
            Autor entidade = autor.get();
            AutorDTOResponse dto = new AutorDTOResponse(
                    entidade.getUuid(),
                    entidade.getNome(),
                    entidade.getNacionalidade(),
                    entidade.getDataNascimento());
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

//    @GetMapping(name = "allAutores")
//    public ResponseEntity<List<AutorDTO>>todosAutores(){
//        List<AutorDTO> autores = autorService.obterTodosAutores();
//        return ResponseEntity.ok(autores);
//    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisa(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){

        List<Autor> result = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = result
                .stream()
                .map(autor -> new AutorDTO(
                autor.getUuid(),
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }


    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") String id, @RequestBody AutorDTO dto){
       try{
           UUID idbody = UUID.fromString(id);
           Optional<Autor> autorOptional = autorService.obterPorId(idbody);

           if (autorOptional.isEmpty()){
               return ResponseEntity.notFound().build();
           }

           var autor = autorOptional.get();
           autor.setNome(dto.nome());
           autor.setNacionalidade(dto.nacionalidade());
           autor.setDataNascimento(dto.date());

           autorService.atualizar(autor);

           return ResponseEntity.noContent().build();
       }catch (RegistroDuplicadoException e){
           var erroDTO = ErroResposta.conflito(e.getMessage());
           return ResponseEntity.status(erroDTO.status()).body(erroDTO);
       }


    }


}
