package ru.otus.dao;

public class IdPointer {

  private static long bookId = 1;
  private static long personId = 1;
  private static long genreId = 1;

  public static long getNewBookId() {
    return ++bookId;
  }

  public static long getNewPersonId() {
    return ++personId;
  }

  public static long getNewGenreId() {
    return ++genreId;
  }
}
