package com.fabada.librayapi.controller;

import com.fabada.librayapi.controller.dto.CadastroLivroDTO;
import com.fabada.librayapi.controller.dto.ErroResposta;
import com.fabada.librayapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.fabada.librayapi.controller.mappers.LivroMappear;
import com.fabada.librayapi.exceptions.RegistroDuplicadoException;
import com.fabada.librayapi.model.Livro;
import com.fabada.librayapi.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMappear mappear;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid CadastroLivroDTO cadastroLivroDTO) {

             Livro livro = mappear.toEntity(cadastroLivroDTO);
             livroService.salvar(livro);

             URI location = gerarHeadearLocation(livro.getUuid());
             return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterLivro(@PathVariable("id") String id){
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    ResultadoPesquisaLivroDTO dto = mappear.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarLivro(@PathVariable("id") String id){
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    livroService.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
