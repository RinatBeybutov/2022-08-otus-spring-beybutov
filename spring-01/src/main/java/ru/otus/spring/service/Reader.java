package ru.otus.spring.service;

import java.io.IOException;
import java.util.List;
import ru.otus.spring.domain.Question;

public interface Reader {

  List<Question> read() throws IOException;
}
