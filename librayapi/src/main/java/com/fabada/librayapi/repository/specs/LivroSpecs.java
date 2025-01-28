package com.fabada.librayapi.repository.specs;

import com.fabada.librayapi.model.GeneroLivro;
import com.fabada.librayapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn){
        return ((root, query, cb) -> cb.equal(root.get("isbn"), isbn ) );
    }

    public static Specification<Livro> tituloLike(String titulo){
        return (root, query, cb) -> cb.like( cb.upper(root.get("titulo")),"%" +  titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro generoLivro){
        return (root, query, cb) -> cb.equal(root.get("genero"), generoLivro);
    }
    public static Specification<Livro> anoPublicacao(Integer anoPublicacao){
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class, root.get("dataPublicacao"), cb.literal("YYYY")), anoPublicacao.toString());
    }
    //maneira simples de busca com join
    /*
    public static Specification<Livro> nomeAutorLike(String nome){
        return (root, query, cb) ->
             cb.like(cb.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%" );

    }
     */

    //maneira com join avançado
    public static Specification<Livro> nomeAutorLike(String nome){
        return (root, query, cb) -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
            return cb.like(cb.upper(joinAutor.get("nome")), "%" + nome.toUpperCase() + "%");
        };
    }
}
