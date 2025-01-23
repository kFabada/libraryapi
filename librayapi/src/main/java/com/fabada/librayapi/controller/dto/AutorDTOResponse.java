package com.fabada.librayapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTOResponse(UUID id,
                               String nome,
                               String date,
                               LocalDate nacionalidade) {
}
