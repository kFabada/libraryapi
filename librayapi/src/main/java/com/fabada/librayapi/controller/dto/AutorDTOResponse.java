package com.fabada.librayapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTOResponse(UUID uuid,
                               String nome,
                               LocalDate dataNascimento,
                               String nacionalidade) {
}
