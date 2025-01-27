package com.fabada.librayapi.repository;

import com.fabada.librayapi.model.Autor;
import com.fabada.librayapi.model.GeneroLivro;
import com.fabada.librayapi.model.Livro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


/*
* @see LivroRepositoryTest

 */
public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    // Query Metodo para busca os dados
    // em tempo de compilaçao ele criar uma consula como esta abaixo
    // select * from livro where id_autor = id



    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByIsbn(String titulo);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    List<Livro> findByTituloOrPrecoOrderByTitulo(String titulo, BigDecimal preco);

//    List<Livro> findBydata_publicacaoBetween(LocalDate inicio, LocalDate fim);

    //JPQL -> referencias as entidades e as propriedades
    // select l.* from livro as l order by l.titulo
    @Query("select l from Livro as l order by l.titulo, l.preco ")
    List<Livro> listarTodosOrdernadoTituloAndPreco();

    @Query("select a from Livro l join l.autor a ")
    List<Autor> listaAutoresDosLivros();

    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

//    @Query("""
//            select l.genero
//            from Livro l
//            join l.autor
//            where a.nacionalidade = 'USA'
//            order by l.genero
//            """)
//    List<String> listaGenerosAutoresBrasileiros();

    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomePropriedade);

    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro, String nomePropriedade);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate localDate);

    Boolean existsByAutor(Autor autor);

}