package ru.otus.spring.domain;

public class Question {

  private final int id;
  private final String question;
  private final String firstAnswer;
  private final String secondAnswer;

  public Question(int id, String question, String firstAnswer, String secondAnswer) {
    this.id = id;
    this.question = question;
    this.firstAnswer = firstAnswer;
    this.secondAnswer = secondAnswer;
  }

  public int getId() {
    return id;
  }

  public String getQuestion() {
    return question;
  }

  public String getFirstAnswer() {
    return firstAnswer;
  }

  public String getSecondAnswer() {
    return secondAnswer;
  }

  @Override
  public String toString() {
    return "Question #" + id + ": " + question + "\n" + firstAnswer + " or " + secondAnswer;
  }
}
