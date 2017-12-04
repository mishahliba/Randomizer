package com.mentoring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentoring.model.Person;
import com.mentoring.model.dto.Winner;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.File;
import java.io.IOException;

import com.mentoring.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);
    private static final String FILE_NAME = "inmemoryStorage.json";
    private static List<Person> inmemoryStorage;
    private static Winner winner;
    private static FileUtil filer;
    private ObjectMapper mapper;
    private File file;

    public PersonService() {
        inmemoryStorage = new ArrayList<>();
        mapper = new ObjectMapper();
        filer = new FileUtil();
        file = filer.getFile(FILE_NAME);
    }

    public void savePersons(List<Person> persons) {
        for (Person person : persons) {
            savePerson(person);
        }
    }

    public void savePerson(Person person) {
        try {
            loadInmemoryIfEmpty();
            if (isUnique(person)) {
                inmemoryStorage.add(person);
                mapper.writeValue(file, inmemoryStorage);
            }
        } catch (IOException e) {
            LOG.error("error occurred while saving person: {} ", e.getMessage());
        }
    }

    public void delete(String name) {
        loadInmemoryIfEmpty();
        inmemoryStorage.removeIf(person -> person.getName().equals(name));
        try {
            mapper.writeValue(file, inmemoryStorage);
        } catch (IOException e) {
            LOG.error("error occurred while trying to remove object. {} ", e.getMessage());
        }
    }

    public void delete() {
        inmemoryStorage = new ArrayList<>();
        try {
            mapper.writeValue(file, inmemoryStorage);
        } catch (IOException e) {
            LOG.error("error occured while trying to clear file. {} ", e.getMessage());
        }
    }

    public Winner getWinner() {
        if (winner != null) {
            return winner;
        }

        loadInmemoryIfEmpty();
        for (int i = 0; i < inmemoryStorage.size(); i++) {
            if (inmemoryStorage.get(i).getWinner() == true) {
                String name = inmemoryStorage.get(i).getName();
                winner = new Winner(i + 1, name);
                return winner;
            }
        }
        selectWinner();
        return winner;
    }

    private void selectWinner() {
        int winnerIndex = ThreadLocalRandom.current().nextInt(0, inmemoryStorage.size());
        inmemoryStorage.get(winnerIndex).setWinner(true);
        String winnerName = inmemoryStorage.get(winnerIndex).getName();
        winner = new Winner(winnerIndex + 1, winnerName);
        try {
            mapper.writeValue(file, inmemoryStorage);
        } catch (IOException e) {
            LOG.error("error occurred while saving person: {} ", e.getMessage());
        }
    }

    private boolean isUnique(Person person) {
        Optional<Person> value = inmemoryStorage.stream()
                .filter(x -> x.getName().equals(person.getName()))
                .findFirst();
        return !value.isPresent();
    }

    private void loadInmemoryIfEmpty() {
        if (inmemoryStorage.isEmpty() && file.length() != 0) {
            try {
                inmemoryStorage.addAll(Arrays.asList(mapper.readValue(file, Person[].class)));
            } catch (IOException e) {
                LOG.error("error occured while trying to load file {} ", e.getMessage());
            }
        }
    }
}
