package ru.cmc.msu.webprak.DAO.implementation;

import org.springframework.stereotype.Repository;
import ru.cmc.msu.webprak.DAO.PersonDAO;
import ru.cmc.msu.webprak.entities.Person;


@Repository
public class PersonDAOImpl extends CommonDAOImpl<Person, Long> implements PersonDAO {
    public PersonDAOImpl(){
        super(Person.class);
    }
}