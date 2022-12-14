package ru.otus.service;

import ru.otus.domain.Question;

public class ConverterQuestionToString {

  public static String convert(Question question) {
    return "#" + question.getId() + ": " +
        question.getQuestion() + "\n" +
        String.join("\n", question.getAnswers());
  }
}
