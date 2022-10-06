package ru.otus.domain;

import java.util.List;

public class Question {

  private final int id;
  private final String question;
  private final List<String> answers;
  private final String correctAnswer;

  public Question(int id, String question, List<String> answers, String correctAnswer) {
    this.id = id;
    this.question = question;
    this.answers = answers;
    this.correctAnswer = correctAnswer;
  }

  public int getId() {
    return id;
  }

  public String getQuestion() {
    return question;
  }

  public List<String> getAnswers() {
    return answers;
  }

  public void addAnswer(String answer) {
    answers.add(answer);
  }

  public String getCorrectAnswer() {
    return correctAnswer;
  }

  @Override
  public String toString() {
    return "#" + id + ": " + question + "\n" + String.join("\n", answers);
  }
}
