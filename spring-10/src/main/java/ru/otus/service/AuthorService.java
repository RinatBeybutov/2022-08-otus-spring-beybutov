package ru.otus.service;

import ru.otus.domain.Author;

public interface AuthorService {

  Author getOrCreatePerson(String name);

}
