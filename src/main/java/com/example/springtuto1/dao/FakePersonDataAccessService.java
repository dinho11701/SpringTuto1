package com.example.springtuto1.dao;


import com.example.springtuto1.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao{

    
    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {

        DB.add(new Person(id,person.getName()));

        return 1;
    }

    public List<Person>selectAllPeople(){
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        //select la personne avec ce id
        //la delete

        Optional<Person> personMayBe = selectPersonById(id);

        if(personMayBe.isEmpty()){
            return 0;
        }

        DB.remove(personMayBe);

        return 1;

    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        //select la personne avec ce id
        //la update

        Optional<Person> personMayBe = selectPersonById(id);

        return personMayBe.map(p -> {
            int indexPersonToDelet = DB.indexOf(person);

            if(indexPersonToDelet >= 0){
                DB.set(indexPersonToDelet,new Person(id,person.getName()));
                return 1;
            }
            return 0;
        }).orElse(0);

    }


}
