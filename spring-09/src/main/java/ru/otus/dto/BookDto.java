package ru.otus.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class BookDto {
  private final long id;
  private final String name;
  private final long id_author ;
  private final long id_genre;
}
