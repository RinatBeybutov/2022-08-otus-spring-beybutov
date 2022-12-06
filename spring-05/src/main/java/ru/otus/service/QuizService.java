package ru.otus.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.AppProps;
import ru.otus.domain.Question;
import ru.otus.domain.Student;

@Service
@AllArgsConstructor
public class QuizService {

  private final TextPrinter textPrinter;

  private final AnswerReader answerReader;

  private final MessageSource messageSource;

  private final AppProps properties;

  public int askQuestions(List<Question> questions) {
    int correctAnswers = 0;
    for (Question q : questions) {
      textPrinter.print(ConverterQuestionToString.convert(q));
      String answer = answerReader.readAnswer();
      if(answer.equals(q.getCorrectAnswer())) {
        correctAnswers++;
      }
    }
    return correctAnswers;
  }

  public void printResult(int countCorrectAnswers, Student student) {
    String you = messageSource.getMessage("you", null, properties.getLocale());
    String correctAnswers = messageSource.getMessage("correctAnswers", null,properties.getLocale());
    String passed = messageSource.getMessage("passed", null,properties.getLocale());
    String failed = messageSource.getMessage("failed", null,properties.getLocale());
    textPrinter.print(student.getName() + " " + student.getSurname() + ", " + you + " " + countCorrectAnswers + " " + correctAnswers);
    textPrinter.print(countCorrectAnswers >= properties.getCountQuestionForPass() ? passed : failed);
  }
}
