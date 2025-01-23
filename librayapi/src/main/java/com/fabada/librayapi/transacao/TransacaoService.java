//package com.fabada.librayapi.transacao;
//
//import com.fabada.librayapi.model.Autor;
//import com.fabada.librayapi.model.GeneroLivro;
//import com.fabada.librayapi.model.Livro;
//import com.fabada.librayapi.repository.AutorRepository;
//import com.fabada.librayapi.repository.LivroRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.UUID;
//
//public class TransacaoService {
//
//    @Autowired
//    private AutorRepository autorRepository;
//    @Autowired
//    private LivroRepository livroRepository;
////
////    @Transactional
////    public void  atualizarSemAtualizar(){
////        var livro = livroRepository.findById(UUID.fromString("")).orElse(null);
////
////        livro.setDataPublicacao(LocalDate.of(2323, 4,10));
////
////    }
//
//    @Transactional
//    public void executar(){
//
//        Autor autor = new Autor();
//        autor.setNome("dasda");
//        autor.setNacionalidade("EUA");
//        autor.setDataNascimento(LocalDate.of(2004, 10, 15));
//
//        autorRepository.save(autor);
//
//
//        Livro livro = new Livro();
//        livro.setGenero(GeneroLivro.FICCAO);
//        livro.setTitulo("dsadasdas");
//        livro.setPreco(BigDecimal.valueOf(5000));
//        livro.setDataPublicacao(LocalDate.of(1995, 8, 30));
//        livro.setIsbn("dasdasda");
//        livro.setAutor(autor);
//
//        Livro livro1 = new Livro();
//        livro1.setGenero(GeneroLivro.CIENCIA);
//        livro1.setTitulo("macaco");
//        livro1.setPreco(BigDecimal.valueOf(10000));
//        livro1.setDataPublicacao(LocalDate.of(1995, 8, 30));
//        livro1.setIsbn("trtrt");
//        livro1.setAutor(autor);
//
//        livroRepository.saveAndFlush(livro1);
//        livroRepository.saveAndFlush(livro);
//
//        if (autor.getNome().equals("Marcao")){
//            throw  new RuntimeException("RollBack");
//        }
//
//    }
//}
