package ru.otus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.config.AppProps;
import ru.otus.domain.Question;
import ru.otus.service.CsvReader;

public class CSVTest {

  AppProps props = new AppProps();
  private CsvReader reader;

  @BeforeEach
  void setUp() {
    props.setLocalizationFile("/questionsEn.csv");
    reader = new CsvReader(props);
  }

  @Test
  public void readQuestions() {
    List<Question> expected = new ArrayList<>();
    expected.add(new Question(1,"Planet where people live",new ArrayList<>(Arrays.asList("Earth", "Mars")),"Earth"));
    expected.add(new Question(2, "Framework for web development", new ArrayList<>(Arrays.asList("Spring", "Kafka")),"Spring"));
    expected.add(new Question(3, "Whether progreaamer know english", new ArrayList<>(Arrays.asList("Yes", "No")),"Yes"));
    expected.add(new Question(4, "What do people eat in the morning", new ArrayList<>(Arrays.asList("Breakfast", "Supper")),"Breakfast"));
    expected.add(new Question(5, "Author of Java philosophy", new ArrayList<>(Arrays.asList("Bruce", "Boris")),"Bruce"));
    List<Question> actual = reader.read();
    Assertions.assertEquals(expected.get(0),actual.get(0));
    Assertions.assertEquals(expected.get(1),actual.get(1));
    Assertions.assertEquals(expected.get(2),actual.get(2));
    Assertions.assertEquals(expected.get(3),actual.get(3));
    Assertions.assertEquals(expected.get(4),actual.get(4));
  }
}
