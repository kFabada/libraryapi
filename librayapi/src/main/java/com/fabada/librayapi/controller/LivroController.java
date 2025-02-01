package com.fabada.librayapi.controller;

import com.fabada.librayapi.controller.dto.CadastroLivroDTO;
import com.fabada.librayapi.controller.dto.ErroResposta;
import com.fabada.librayapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.fabada.librayapi.controller.mappers.LivroMappear;
import com.fabada.librayapi.exceptions.RegistroDuplicadoException;
import com.fabada.librayapi.model.GeneroLivro;
import com.fabada.librayapi.model.Livro;
import com.fabada.librayapi.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMappear mappear;


    @PostMapping
    @PreAuthorize("hasAnRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<?> salvar(@RequestBody @Valid CadastroLivroDTO cadastroLivroDTO) {
                System.out.println("Entrou aqui");
             Livro livro = mappear.toEntity(cadastroLivroDTO);
             livroService.salvar(livro);

             //erro URI
//             URI location = gerarHeadearLocation(livro.getUuid());
             return ResponseEntity.ok(cadastroLivroDTO);
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

    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero-livro", required = false)
            GeneroLivro generoLivro,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao

    ){
        var resultado = livroService.pesquisa(isbn, titulo, nomeAutor, generoLivro, anoPublicacao);
        var lista = resultado
                .stream()
                .map(mappear::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") String id, @RequestBody @Valid CadastroLivroDTO dto ){
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidadeAuxiliar = mappear.toEntity(dto);
                    livro.setDataPublicacao(entidadeAuxiliar.getDataPublicacao());
                    livro.setIsbn(entidadeAuxiliar.getIsbn());
                    livro.setTitulo(String.valueOf(entidadeAuxiliar.getPreco()));
                    livro.setGenero(entidadeAuxiliar.getGenero());
                    livro.setTitulo(entidadeAuxiliar.getTitulo());
                    livro.setAutor(entidadeAuxiliar.getAutor());

                    livroService.atualizar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
