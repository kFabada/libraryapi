package com.fabada.librayapi.controller;


import com.fabada.librayapi.controller.dto.AutorDTO;
import com.fabada.librayapi.controller.dto.AutorDTOResponse;
import com.fabada.librayapi.controller.dto.ErroResposta;
import com.fabada.librayapi.controller.mappers.AutorMappear;
import com.fabada.librayapi.exceptions.OperacaoNaoPermitidaException;
import com.fabada.librayapi.exceptions.RegistroDuplicadoException;
import com.fabada.librayapi.model.Autor;
import com.fabada.librayapi.services.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AutorController implements GenericController {


    private final AutorService autorService;
    private final AutorMappear autorMappear;



    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid AutorDTO autorDTO){


            Autor autorEntidade = autorMappear.toEntity(autorDTO);

            autorService.salvar(autorEntidade);

            URI location = gerarHeadearLocation(autorEntidade.getUuid());
//        ErroResposta erroResposta = ErroResposta.conflito("Autor já cadastrado!");
//        return ResponseEntity.status(erroResposta.status()).body(erroResposta);

            return ResponseEntity.created(location).build();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletaAutor(@PathVariable("id") String id){

           UUID idbody = UUID.fromString(id);
           Optional<Autor> autorOptional = autorService.obterPorId(idbody);

           if (autorOptional.isEmpty()){
               return ResponseEntity.notFound().build();
           }

           autorService.deletarAutor(autorOptional.get());
           return ResponseEntity.noContent().build();

    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTOResponse> obterDetalher(@PathVariable("id") String id){
        UUID idAutor = UUID.fromString(id);

        return autorService
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTOResponse autorDTO = autorMappear.toAutorDTOResponse(autor);
                    return ResponseEntity.ok(autorDTO);
                }).orElseGet( () -> ResponseEntity.notFound().build());

//        if (autor.isPresent()){
//            Autor entidade = autor.get();
//            AutorDTOResponse dto = autorMappear.toAutorDTOAutorDtoResponse(entidade);
//            return ResponseEntity.ok(dto);
//        }
//
//        return ResponseEntity.notFound().build();
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
    }
}
