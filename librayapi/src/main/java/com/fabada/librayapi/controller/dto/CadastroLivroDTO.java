package com.fabada.librayapi.controller.dto;

import com.fabada.librayapi.model.GeneroLivro;
import com.fabada.librayapi.model.Livro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
         @NotBlank(message = "Campo Obrigatorio")
         String isbn,
         @NotBlank(message = "Campo Obrigatorio")
         String titulo,
         @NotNull(message = "precisa de uma data")
         @Past(message = "Não pode ser uma data futura")
         LocalDate dataPublicacao,
         GeneroLivro genero,
         BigDecimal preco,
         @NotNull(message = "o livro precisa pertencer a um autor")
         UUID idAutor) {

//    public Livro mapearLivro(){
//        Livro livro = new Livro();
//        livro.setIsbn(this.isbn);
//        livro.setTitulo(this.titulo);
//        livro.setDataPublicacao(dataPublicacao);
//        livro.setGenero(genero);
//        livro.setPreco(preco);
//        livro.setIdUsuario(idAutor);
//        return livro;
//    }
}
