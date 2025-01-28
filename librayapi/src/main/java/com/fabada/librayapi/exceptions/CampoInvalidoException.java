package com.fabada.librayapi.exceptions;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

public class CampoInvalidoException extends RuntimeException{

    @Getter
    private String campo;

    public CampoInvalidoException(String campo, String mensagem){
        super(mensagem);
        this.campo = campo;
    }
}
