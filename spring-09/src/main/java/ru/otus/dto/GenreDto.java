package ru.otus.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GenreDto {
  private final long id;
  private final String name;
}
