package ru.otus.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class PersonDto {
    private final long id;
    private final String name;
}
