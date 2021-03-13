package com.example.demo.repository;

import com.example.demo.data.Address;
import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class InMemoryPersonRepository {

    private final List<Person> people = new ArrayList<>();

    private InMemoryPersonRepository() {
        initializePeople();
    }

    public List<Person> findAll() {
        return people;
    }

    public Optional<Person> findById(String id) {
        return people.stream()
                .filter(p -> p.getId().equals(id))
                .findAny();
    }

    public List<Person> findByFirstName(String firstName) {
        return people.stream()
                .filter(p -> p.getFirstName() != null)
                .filter(p -> p.getFirstName().equals(firstName))
                .collect(Collectors.toList());
    }

    public Person createPerson(Person person) {
        people.add(person);
        return person;
    }

    public Person updatePerson(String id, Person person) {
        return people.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .map(p -> {
                    p.setFirstName(person.getFirstName());
                    p.setLastName(person.getLastName());
                    p.setAddress(person.getAddress());
                    p.setAliases(person.getAliases());
                    return p;
                })
                .orElse(null);
    }

    public Person updatePersonAliases(String id, List<String> aliases) {
        return people.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .map(p -> {
                    p.setAliases(aliases);
                    return p;
                })
                .orElse(null);
    }

    public boolean deletePersonById(String id) {
        return people.removeIf(p -> p.getId().equals(id));
    }

    public void deleteAllPeople() {
        people.clear();
    }

    private void initializePeople() {
        people.add(createSamplePerson("JUAN", "DIAZ", "C/LA PARROCHA, 23", "GIJON", "ASTURIAS", "33305", Collections.singletonList("JUANELE")));
        people.add(createSamplePerson("PEDRO", "HERNANDEZ", "C/CALATAYUD, 10", "MADRID", "MADRID", "28080", Collections.singletonList("PEDRITO")));
        people.add(createSamplePerson("SEBASTIAN", "GARCIA", "C/PEDRERA, S/N", "BARCELONA", "BARCELONA", "08080", Collections.singletonList("SEBAS")));
    }

    private Person createSamplePerson(String firstName, String lastName, String street, String city,
                                      String state, String zip, List<String> aliases) {
        return Person.builder()
                .id(UUID.randomUUID().toString())
                .firstName(firstName)
                .lastName(lastName)
                .address(Address.builder()
                        .street(street)
                        .city(city)
                        .state(state)
                        .zip(zip)
                        .build())
                .aliases(aliases)
                .build();
    }
}
