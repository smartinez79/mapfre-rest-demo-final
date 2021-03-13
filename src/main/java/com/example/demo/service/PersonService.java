package com.example.demo.service;

import com.example.demo.data.PersonDto;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.model.Person;
import com.example.demo.repository.InMemoryPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final InMemoryPersonRepository personRepository;
    private final PersonMapper personMapper;

    @Autowired
    public PersonService(InMemoryPersonRepository personRepository,
                         PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public List<PersonDto> findAll() {
        List<Person> models = personRepository.findAll();
        return models
                .stream()
                .map(personMapper::toDto)
                .collect(Collectors.toList());
    }

    public PersonDto findById(String id) {
        Optional<Person> model = personRepository.findById(id);
        return model
                .map(personMapper::toDto)
                .orElse(null);
    }

    public List<PersonDto> findByFirstName(String firstName) {
        List<Person> models = personRepository.findByFirstName(firstName);
        return models.stream()
                .map(personMapper::toDto)
                .collect(Collectors.toList());
    }

    public PersonDto createPerson(PersonDto personDto) {
        Person model = personMapper.toModel(personDto);
        return personMapper.toDto(personRepository.createPerson(model));
    }

    public PersonDto updatePerson(String id, PersonDto personDto) {
        Person model = personMapper.toModel(personDto);
        return Optional.ofNullable(personRepository.updatePerson(id, model))
                .map(personMapper::toDto)
                .orElse(null);
    }

    public PersonDto updatePersonAliases(String id, List<String> aliases) {
        return Optional.ofNullable(personRepository.updatePersonAliases(id, aliases))
                .map(personMapper::toDto)
                .orElse(null);
    }

    public boolean deletePersonById(String id) {
        return personRepository.deletePersonById(id);
    }

    public void deleteAllPeople() {
        personRepository.deleteAllPeople();
    }
}
