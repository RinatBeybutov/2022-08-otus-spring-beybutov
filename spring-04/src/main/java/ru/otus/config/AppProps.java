package ru.otus.config;

import java.util.Locale;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class AppProps {

  private int countQuestionForPass;
  private Locale locale;
  private String localizationFile;

  public String getLocalizationFile() {
    return localizationFile;
  }

  public void setLocalizationFile(String localizationFile) {
    this.localizationFile = localizationFile;
  }

  public static final int COUNT_QUESTIONS = 5;

  public int getCountQuestionForPass() {
    return countQuestionForPass;
  }

  public void setCountQuestionForPass(int countQuestionForPass) {
    this.countQuestionForPass = countQuestionForPass;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }
}
