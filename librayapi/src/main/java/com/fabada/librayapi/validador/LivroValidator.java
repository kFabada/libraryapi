package com.fabada.librayapi.validador;

import com.fabada.librayapi.exceptions.CampoInvalidoException;
import com.fabada.librayapi.exceptions.RegistroDuplicadoException;
import com.fabada.librayapi.model.Livro;
import com.fabada.librayapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {
    private final LivroRepository repository;
    private static final  int ANO_EXIGENCIA_PRECO = 2020;

    public void validar(Livro livro){
        if (existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("ISBN Já cadastrado");
        }

        if (isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preco", "Para livros com ano apartir de 2020 o preço e obrigatorio");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeLivroComIsbn(Livro livro){
        Optional<Livro> byIsbn = repository.findByIsbn(livro.getIsbn());

        if (livro.getUuid() == null){
            return byIsbn.isPresent();
        }

        return byIsbn
                .map(Livro::getUuid)
                .stream()
                .anyMatch(uuid -> !uuid.equals(livro.getUuid()));
    }
}
