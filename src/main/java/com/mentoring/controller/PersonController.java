package com.mentoring.controller;

import com.mentoring.model.Person;
import com.mentoring.service.PersonService;
import com.mentoring.model.dto.Winner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Class responsible for handling user requests related to Person object.
 */
@RestController
public class PersonController {
    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public void savePerson(@RequestBody Person person){
        LOG.info("/person post endpoint. user input: {}", person.getName());
        PersonService service = new PersonService();
            service.savePerson(person);
    }

    @RequestMapping(value = "/persons", method = RequestMethod.POST)
    public void savePersons( @RequestBody List<Person> persons){
        LOG.info("/persons post endpoint. user input:");
        for(Person p : persons) {
            LOG.info(p.getName());
        }
        PersonService service = new PersonService();
        service.savePersons(persons);
    }

    @RequestMapping(value = "/person/{name}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("name") String name) {
        LOG.info("/person delete endpoint. user input: {}", name);
        PersonService service = new PersonService();
        service.delete(name);
    }

    @RequestMapping(value = "/persons", method = RequestMethod.DELETE)
    public void delete() {
        LOG.info("/persons delete endpoint. Try tor remove all persons");
        PersonService service = new PersonService();
        service.delete();
    }

    @RequestMapping(value = "/winner", method = RequestMethod.GET)
    public Winner getWinner() {
        LOG.info("/winner endpoint. Looking for winner");
        PersonService service = new PersonService();
        return service.getWinner();
    }
}
