package com.fabada.librayapi.repository;

import com.fabada.librayapi.model.Autor;
import com.fabada.librayapi.model.GeneroLivro;
import com.fabada.librayapi.model.Livro;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

        @Autowired
        private LivroRepository repository;
        @Autowired
        private AutorRepository autorRepository;

        @Test
        void salvarTest(){
            Livro livro = new Livro();
            livro.setGenero(GeneroLivro.FICCAO);
            livro.setTitulo("aaa");
            livro.setPreco(BigDecimal.valueOf(100));
            livro.setDataPublicacao(LocalDate.of(2015, 8, 30));
            livro.setIsbn("aaaa");

            Autor autor = autorRepository
                    .findById(UUID.fromString("c669f353-27a6-4ad1-9d83-84eb8d5b170a"))
                    .orElse(null);



            livro.setAutor(autor);

            repository.save(livro);
        }

    @Test
    void salvarAutorLivroTest(){
        Livro livro = new Livro();
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("aaa");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setDataPublicacao(LocalDate.of(2015, 8, 30));
        livro.setIsbn("aaaa");

        Autor autor = new Autor();
        autor.setNome("ameba");
        autor.setNacionalidade("USA");
        autor.setDataNascimento(LocalDate.of(2004, 10, 15));


        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }


    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("aaa");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setDataPublicacao(LocalDate.of(2015, 8, 30));
        livro.setIsbn("aaaa");

        Autor autor = new Autor();
        autor.setNome("ameba");
        autor.setNacionalidade("USA");
        autor.setDataNascimento(LocalDate.of(2004, 10, 15));


        livro.setAutor(autor);


        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro(){
         Livro livroAtualizar = repository
                 .findById(UUID.fromString("6e80edf0-e959-4b7f-b351-bbeb3770bd17"))
                 .orElse(null);
         Autor autor =  autorRepository
               .findById(UUID.fromString("61bed3d8-f991-428e-b924-eed4728ac6c0"))
               .orElse(null);



       livroAtualizar.setAutor(autor);

       repository.save(livroAtualizar);

    }

    @Test
    void deletar(){
        Livro livroAtualizar = repository
                .findById(UUID.fromString("6e80edf0-e959-4b7f-b351-bbeb3770bd17"))
                .orElse(null);

        UUID uuid = UUID.fromString("6e80edf0-e959-4b7f-b351-bbeb3770bd17");

        repository.deleteById(uuid);
    }

    @Test
    void deletarCascade(){
        Livro livroAtualizar = repository
                .findById(UUID.fromString("6e80edf0-e959-4b7f-b351-bbeb3770bd17"))
                .orElse(null);

        UUID uuid = UUID.fromString("6e80edf0-e959-4b7f-b351-bbeb3770bd17");

        repository.deleteById(uuid);
    }

    @Test
    @Transactional
    void buscaLivroTest(){
            UUID id = UUID.fromString("87ed9a9b-4c95-41cf-8501-0a4b7215f571");
            Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro");
        System.out.println(livro.getTitulo());
        System.out.println("Autor");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void  pesquisaPorTituloTest(){
        List<Livro> livros = repository.findByTitulo("ABUBLE");
        livros.forEach(System.out::println);
    }

    @Test
    void  pesquisaPorIsbnTest(){
        List<Livro> livros = repository.findByIsbn("qualquer");
        livros.forEach(System.out::println);
    }

    @Test
    void  pesquisaPorTituloAndPrecoTest(){
        List<Livro> livros = repository.findByTituloAndPreco("etbilu", BigDecimal.valueOf(100.00));
        livros.forEach(System.out::println);
    }

    @Test
    void  listaLivrosComQueryJPQL(){
            List<Livro> resul = repository.listarTodosOrdernadoTituloAndPreco();
            resul.forEach(System.out::println);
    }

    @Test
    void  listaAutorDosLivroQueryJPQL(){
            List<Autor> resul = repository.listaAutoresDosLivros();
            resul.forEach(System.out::println);
    }


    @Test
    void  listaTituloLivrosDistinctQueryJPQL(){
        List<String> resul = repository.listarNomesDiferentesLivros();
        resul.forEach(System.out::println);
    }
//
//    @Test
//    void  listaGeneroLivrosAutoresBrasileirosQueryJPQL(){
//        List<String> resul = repository.listaGenerosAutoresBrasileiros();
//        resul.forEach(System.out::println);
//    }

//    @Test
//    void  listaGeneroQueryParamQueryJPQL(){
//        List<Livro> resul = repository.findByGenero(GeneroLivro.FICCAO, "dataPublicacao");
//        resul.forEach(System.out::println);
//    }

    @Test
    void deletePorGenero(){
        repository.deleteByGenero(GeneroLivro.FICCAO);
    }

    @Test
    void updateData(){
        repository.updateDataPublicacao(LocalDate.of(2015, 10,15));
    }


}