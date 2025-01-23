//package com.fabada.librayapi.repository;
//
//import com.fabada.librayapi.transacao.TransacaoService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//public class TransacoesTest {
//
//    @Autowired
//    TransacaoService transacaoService;
//
//
//    /**
//     *
//     * Commit -> Fazer a transação
//     * Rollback -> Restaura as alterações
//     *
//     */
//    @Test
//    void transacaoSimples(){
//        // salvar um livro
//        // salvar o autor
//        // alugar o livro
//        // enviar email pro locatario
//        // notificar que o livro saiu da livraria
//        transacaoService.executar();
//    }
//}
