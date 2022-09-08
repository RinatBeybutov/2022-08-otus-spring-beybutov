package ru.otus.spring.dao;

import ru.otus.spring.domain.Person;

public class PersonDaoSimple implements PersonDao {

    int defaultAge = 0;

    public Person findByName(String name) {
        return new Person(name, defaultAge);
    }

    @Override
    public void setDefaultAge(int defaultAge) {
        this.defaultAge = defaultAge;
    }


}
