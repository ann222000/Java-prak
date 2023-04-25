package ru.cmc.msu.webprak;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.cmc.msu.webprak.DAO.*;
import ru.cmc.msu.webprak.DAO.implementation.*;
import ru.cmc.msu.webprak.entities.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Controllers {
    @Autowired
    private final PersonDAO personDAO = new PersonDAOImpl();

    @RequestMapping(value = { "/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/bus_lines")
    public String BusLines() {
        return "bus_lines";
    }

    @RequestMapping(value = "/order")
    public String Orders() {
        return "order";
    }

    @RequestMapping(value = { "/persons"})
    public String persons(Model model) {
        List<Person> person = (List<Person>) personDAO.getAll();
        model.addAttribute("person", person);
        model.addAttribute("personDAO", personDAO);
        return "persons";
    }

    @GetMapping("/add_person")
    public String showAddPersonPage() {
        return "add_person";
    }

    @GetMapping("/searchperson")
    public String searchperson(@RequestParam(required = false) Long id,
                                 @RequestParam(required = false) String surname,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String fathers_name,
                                 Model model) {
        Map<String, String> findmap = new HashMap<>();
        if (name != null && !name.isEmpty())
            findmap.put("name", name);
        if (surname != null && !surname.isEmpty())
            findmap.put("surname", surname);
        if (fathers_name != null && !fathers_name.isEmpty())
            findmap.put("fathers_name", fathers_name);
        List<Person> persons = personDAO.searchByFilter(findmap);
        model.addAttribute("person", persons);
        model.addAttribute("personDAO", personDAO);
        return "persons";
    }


    @GetMapping("/person")
    public String personPage(@RequestParam(name = "personId") Long personId, Model model) {
        Person person = personDAO.getById(personId);
        if (person == null) {
            model.addAttribute("error_msg", "В базе нет человека с ID = " + personId);
            return "errorPage";
        }
        model.addAttribute("person", person);
        model.addAttribute("personService", personDAO);
        return "person";
    }

}