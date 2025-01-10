package com.fabada.librayapi.repository;

import com.fabada.librayapi.model.Autor;
import com.fabada.librayapi.model.GeneroLivro;
import com.fabada.librayapi.model.Livro;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;
    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Fabsa");
        autor.setNacionalidade("USA");
        autor.setDataNascimento(LocalDate.of(2004, 10, 15));

        Autor resul = repository.save(autor);
        System.out.println("Autor salvo" + resul);
    }

    @Test
    public void atualizarTest(){
       UUID uuid = UUID.fromString("2b614fe0-7cdf-4a98-a2ad-b2fe78fc8b4d");

        Optional<Autor> byId = repository.findById(uuid);

        if (byId.isPresent()){
            System.out.println("Dados Encontrado");

            Autor autorEncontrado = byId.get();
            System.out.println(autorEncontrado);

            autorEncontrado.setNome("Kfabada");
            autorEncontrado.setDataNascimento(LocalDate.of(2024, 10,30));

            repository.save(autorEncontrado);
            System.out.println("autor salvo");

        }
    }

    @Test
    public void ListaTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autor " + repository.count());
    }

    @Test
    public void deleteIdTest(){
        UUID id = UUID.fromString("2b614fe0-7cdf-4a98-a2ad-b2fe78fc8b4d");
        repository.deleteById(id);
    }

    @Test
    public void deleteObjetoTest(){
        UUID id = UUID.fromString("9404135a-d116-4487-b2ed-ce23801363a9");

        Autor autor = repository.findById(id).get();
        repository.delete(autor);


        System.out.println(autor);
    }

    @Test
    public void salvarAutorComLivros(){
        Autor autor = new Autor();
        autor.setNome("Marcao");
        autor.setNacionalidade("EUA");
        autor.setDataNascimento(LocalDate.of(2004, 10, 15));

        Livro livro = new Livro();
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("dsadasdas");
        livro.setPreco(BigDecimal.valueOf(5000));
        livro.setDataPublicacao(LocalDate.of(1995, 8, 30));
        livro.setIsbn("dasdasda");
        livro.setAutor(autor);


        Livro livro1 = new Livro();
        livro1.setGenero(GeneroLivro.CIENCIA);
        livro1.setTitulo("macaco");
        livro1.setPreco(BigDecimal.valueOf(10000));
        livro1.setDataPublicacao(LocalDate.of(1995, 8, 30));
        livro1.setIsbn("trtrt");
        livro1.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro1);

        repository.save(autor);

    }

    @Test
    void listaLivrosAutor(){

        UUID id  = UUID.fromString("8c9d4bc5-0f12-46d8-bf0d-573ab99b478e");
        var autor = repository.findById(id).get();
            System.out.println("Aqui");

            List<Livro> livroList = livroRepository.findByAutor(autor);
            autor.setLivros(livroList);

            autor.getLivros().forEach(System.out::println);

    }
}
