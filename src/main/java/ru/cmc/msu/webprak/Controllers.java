package ru.cmc.msu.webprak;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.cmc.msu.webprak.DAO.*;
import ru.cmc.msu.webprak.DAO.implementation.*;
import ru.cmc.msu.webprak.entities.*;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.mockito.internal.matchers.text.ValuePrinter.print;

@Controller
public class Controllers {
    @Autowired
    private final PersonDAO personDAO = new PersonDAOImpl();

    @Autowired
    private final BusLineDAO buslineDAO = new BusLineDAOImpl();

    @Autowired
    private final TicketTemplateDAO tickettemplateDAO = new TicketTemplateDAOImpl();

    @Autowired
    private final TicketDAO ticketDAO = new TicketDAOImpl();

    @Autowired
    private final StationBusLineDAO stationbuslineDAO = new StationBusLineDAOImpl();


    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/add_ticket")
    public String Order() {
        return "add_ticket";
    }

    @RequestMapping(value = {"/persons"})
    public String persons(Model model) {
        List<Person> person = (List<Person>) personDAO.getAll();
        model.addAttribute("person", person);
        model.addAttribute("personDAO", personDAO);
        return "persons";
    }

    @GetMapping("/searchperson")
    public String searchperson(@RequestParam(required = false) Long id,
                               @RequestParam(required = false) String surname,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String fathers_name,
                               Model model) {
        Map<String, String> findmap = new HashMap<>();
        if (id != null)
            return personPage(id, model);
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
        Map<String, Long> findmap = new HashMap<>();
        findmap.put("id_person", personId);
        List<Ticket> tickets = ticketDAO.searchByFilter(findmap);
        model.addAttribute("tickets", tickets);
        model.addAttribute("tickettemplateService", tickettemplateDAO);
        model.addAttribute("person", person);
        model.addAttribute("personService", personDAO);
        return "person";
    }

    @GetMapping("/editPerson")
    public String editPersonPage(@RequestParam(name = "personId", required = false) Long personId, Model model) {
        if (personId == null) {
            model.addAttribute("person", new Person());
            model.addAttribute("personService", personDAO);
            return "edit_person";
        }
        Person person = personDAO.getById(personId);
        if (person == null) {
            model.addAttribute("error_msg", "В базе нет клиента с ID = " + personId);
            return "errorPage";
        }
        model.addAttribute("person", person);
        model.addAttribute("personService", personDAO);
        return "edit_person";
    }

    @PostMapping("/savePerson")
    public String savePersonPage(@RequestParam(name = "id") String id,
                                 @RequestParam(name = "name", required = false) String name,
                                 @RequestParam(name = "surname", required = false) String surname,
                                 @RequestParam(name = "fathers_name", required = false) String fathers_name,
                                 @RequestParam(name = "telephone_number", required = false) String telephone_number,
                                 @RequestParam(name = "email", required = false) String email,
                                 @RequestParam(name = "address", required = false) String address,
                                 Model model) {
        if (id.equals("")) {
            if ((Objects.equals(name, "")) || (Objects.equals(surname, "")) || (Objects.equals(fathers_name, ""))) {
                model.addAttribute("error_msg", "Нельзя создать клиента без имени, фамилии или отчества!");
                return "errorPage";
            }
            id = "1";
            Person person = new Person(Long.parseLong(id), name, surname, fathers_name, telephone_number, email, address);
            personDAO.save(person);
        } else {
            Person person = personDAO.getById(Long.parseLong(id));
            if (!Objects.equals(name, "")) {
                person.setName(name);
            }
            if (!Objects.equals(surname, "")) {
                person.setSurname(surname);
            }
            if (!Objects.equals(fathers_name, "")) {
                person.setFathers_name(fathers_name);
            }
            if (!Objects.equals(telephone_number, "")) {
                person.setTelephone_number(telephone_number);
            }
            if (!Objects.equals(email, "")) {
                person.setEmail(email);
            }
            if (!Objects.equals(address, "")) {
                person.setAddress(address);
            }
            personDAO.update(person);
        }
        return "redirect:/persons";
    }

    @PostMapping("/removePerson")
    public String removePersonPage(@RequestParam(name = "personId") Long personId) {
        personDAO.deleteById(personId);
        return "redirect:/persons";
    }

    @RequestMapping(value = {"/bus_lines"})
    public String bus_linesPage(Model model) {
        List<BusLine> buslines = (List<BusLine>) buslineDAO.getAll();
        model.addAttribute("stationbuslineDAO", stationbuslineDAO);
        model.addAttribute("buslines", buslines);
        model.addAttribute("buslineDAO", buslineDAO);
        return "bus_lines";
    }

    @GetMapping("/bus_line")
    public String Bus_linePage(@RequestParam(name = "buslineId") Long bus_lineId, Model model) {
        BusLine busline = buslineDAO.getById(bus_lineId);
        if (busline == null) {
            model.addAttribute("error_msg", "В базе нет рейса с ID = " + bus_lineId);
            return "errorPage";
        }
        Map<String, Long> findmap = new HashMap<>();
        findmap.put("bus_line", bus_lineId);
        List<TicketTemplate> tickettemplates = tickettemplateDAO.searchByFilter(findmap);
        Set<String> set_dates = new HashSet<String>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        for (TicketTemplate el : tickettemplates) {
            set_dates.add(formatter.format(el.getDate_departure()));
        }
        model.addAttribute("stationbuslineDAO", stationbuslineDAO);
        model.addAttribute("stations", String.join(", ", stationbuslineDAO.getBusLineStaions(busline)));
        model.addAttribute("dates", String.join(", ", new ArrayList<>(set_dates)));
        model.addAttribute("busline", busline);
        model.addAttribute("buslineService", buslineDAO);
        return "bus_line";
    }

    @PostMapping("/removeBusline")
    public String removeBusline(@RequestParam(name = "BuslineId") Long buslineId) {
        buslineDAO.deleteById(buslineId);
        return "redirect:/bus_lines";
    }

    @GetMapping("/editBusline")
    public String editBuslinePage(@RequestParam(name = "BuslineId", required = false) Long buslineId, Model model) {
        if (buslineId == null) {
            model.addAttribute("busline", new BusLine());
            model.addAttribute("buslineService", buslineDAO);
            return "edit_bus_line";
        }
        BusLine busline = buslineDAO.getById(buslineId);
        if (busline == null) {
            model.addAttribute("error_msg", "В базе нет рейса с ID = " + buslineId);
            return "errorPage";
        }
        model.addAttribute("busline", busline);
        model.addAttribute("buslineService", buslineDAO);
        return "edit_bus_line";
    }

    @PostMapping("/saveBusline")
    public String saveBusline(@RequestParam(name = "id") String id,
                              @RequestParam(name = "company", required = false) String company,
                              @RequestParam(name = "places", required = false) String places,
                              Model model) {
        if (id.equals("")) {
            BusLine busline = new BusLine(null, company, Integer.parseInt(places));
            buslineDAO.save(busline);
        } else {
            BusLine busline = buslineDAO.getById(Long.parseLong(id));
            if (!Objects.equals(company, "")) {
                busline.setCompany(company);
            }
            if (!Objects.equals(places, "")) {
                busline.setNumber_of_places(Integer.parseInt(places));
            }
            buslineDAO.update(busline);
        }
        return "redirect:/bus_lines";
    }

    @PostMapping("/removeStation")
    public ModelAndView removeStation(@RequestParam(name = "Station") String station,
                                      @RequestParam(name = "Busline") Long busline,
                                      Model model) {
        List<StationBusLine> stations = stationbuslineDAO.getByBusLine(buslineDAO.getById(busline));
        for (StationBusLine st : stations) {
            if (Objects.equals(st.getStation_name(), station)) {
                stationbuslineDAO.delete(st);
                return new ModelAndView("redirect:/bus_line", "buslineId", busline);
            }
        }
        return new ModelAndView("redirect:/bus_line", "buslineId", busline);
    }

    @GetMapping("/addStation")
    public String addStationPage(@RequestParam(name = "BuslineId") Long buslineId, Model model) {
        if (buslineId == null) {
            model.addAttribute("error_msg", "В базе нет рейса с ID = " + buslineId);
            return "errorPage";
        }
        BusLine busline = buslineDAO.getById(buslineId);
        if (busline == null) {
            model.addAttribute("error_msg", "В базе нет рейса с ID = " + buslineId);
            return "errorPage";
        }
//        model.addAttribute("stationbuslineDAO", stationbuslineDAO);
        model.addAttribute("busline", busline);
        model.addAttribute("buslineService", buslineDAO);
        return "add_station";
    }

    @PostMapping("/saveStation")
    public ModelAndView saveStation(@RequestParam(name = "id") String id,
                                    @RequestParam(name = "station") String stationName,
                                    @RequestParam(name = "time_in", required = false) String time_in,
                                    @RequestParam(name = "time_out", required = false) String time_out,
                                    @RequestParam(name = "day") Long day,
                                    @RequestParam(name = "type") Long type,
                                    Model model) {
        if (id.equals("")) {
            model.addAttribute("error_msg", "В базе нет рейса с ID = " + id);
            return new ModelAndView("errorPage");
        }
        BusLine busline = buslineDAO.getById(Long.parseLong(id));
        StationBusLine station = new StationBusLine(busline, stationName, type.intValue(), day.intValue());
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        if (!time_in.equals("")) {
            try {
                station.setTime_in(new java.sql.Time(formatter.parse(time_in).getTime()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        if (!time_out.equals("")) {
            try {
                station.setTime_out(new java.sql.Time(formatter.parse(time_out).getTime()));

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        stationbuslineDAO.save(station);
        return new ModelAndView("redirect:/bus_line", "buslineId", id);
    }

    @GetMapping("/addPrice")
    public String addPricePage(@RequestParam(name = "BuslineId") Long buslineId, Model model) {
        if (buslineId == null) {
            model.addAttribute("error_msg", "В базе нет рейса с ID = " + buslineId);
            return "errorPage";
        }
        BusLine busline = buslineDAO.getById(buslineId);
        if (busline == null) {
            model.addAttribute("error_msg", "В базе нет рейса с ID = " + buslineId);
            return "errorPage";
        }
        model.addAttribute("busline", busline);
        model.addAttribute("buslineService", buslineDAO);
        return "add_price";
    }

    @PostMapping("/savePrice")
    public ModelAndView savePrice(@RequestParam(name = "id") String id,
                                  @RequestParam(name = "station_from") String station_from,
                                  @RequestParam(name = "station_to") String station_to,
                                  @RequestParam(name = "date") String date,
                                  @RequestParam(name = "price") Long price,
                                  Model model) {
        if (id.equals("")) {
            model.addAttribute("error_msg", "В базе нет рейса с ID = " + id);
            return new ModelAndView("errorPage");
        }
        List<String> stations_obj = stationbuslineDAO.getBusLineStaions(buslineDAO.getById(Long.parseLong(id)));
        int ok = 0;
        for (String st : stations_obj) {
            if ((Objects.equals(station_to, st)) || (Objects.equals(station_from, st))) {
                ok++;
            }
        }
        if (ok < 2) {
            model.addAttribute("error_msg", "В базе нет таких станций ");
            return new ModelAndView("errorPage");
        }
        BusLine busline = buslineDAO.getById(Long.parseLong(id));
        TicketTemplate ticket_template = new TicketTemplate(null, busline, Date.valueOf(date), price, station_from, station_to);
        tickettemplateDAO.save(ticket_template);
        return new ModelAndView("redirect:/bus_line", "buslineId", id);
    }

    @GetMapping("/addTicket")
    public String addTicketPage(@RequestParam(name = "BuslineId", required = false) Long buslineId,
                                @RequestParam(name = "PersonId", required = false) Long personId, Model model) {
        if (buslineId != null) {
            model.addAttribute("buslineId", buslineId);
        }
        if (personId != null) {
            model.addAttribute("personId", personId);
        }
        return "add_ticket";
    }

    @PostMapping("/saveTicket")
    public ModelAndView saveTicket(@RequestParam(name = "buslineid") Long buslineid,
                                   @RequestParam(name = "personid") Long personid,
                                   @RequestParam(name = "station_from") String station_from,
                                   @RequestParam(name = "station_to") String station_to,
                                   @RequestParam(name = "date") String date,
                                   Model model) {
        BusLine busline = buslineDAO.getById(buslineid);
        Person person = personDAO.getById(personid);
        if ((person == null) || (busline == null)) {
            model.addAttribute("error_msg", "В базе нет такого рейса или клиента" );
            return new ModelAndView("errorPage");
        }
        Map<String, String> findmap = new HashMap<>();
        findmap.put("from_station", station_from);
        findmap.put("to_station", station_to);
        List<TicketTemplate> ticket_template = tickettemplateDAO.searchByFilter(findmap);
        TicketTemplate ready_template = null;
        for (TicketTemplate temp: ticket_template) {
            if (Date.valueOf(date).equals(temp.getDate_departure()) && busline.equals(temp.getBus_line()))
                ready_template = temp;
        }
        if (ready_template == null) {
            model.addAttribute("error_msg", "Нельзя приобрести такой билет" );
            return new ModelAndView("errorPage");
        }
        List<Ticket> tickets = (List<Ticket>) ticketDAO.getAll();
        int place = 1;
        for (Ticket tick: tickets) {
            if ((tick.getPlace() == place) && (ready_template.equals(tick.getId_template()))){
                place++;
            }
        }
        if (place > busline.getNumber_of_places()) {
            model.addAttribute("error_msg", "Нет мест" );
            return new ModelAndView("errorPage");
        }
        Ticket ticket = new Ticket(null, ticket_template.get(0), place, person);
        ticketDAO.save(ticket);
        ModelAndView modelAndView = new ModelAndView("done");
        modelAndView.addObject("place", place);
        modelAndView.addObject("price", ticket_template.get(0).getPrice());
        return modelAndView;
    }

    @GetMapping("/searchbusline")
    public String searchbusline(@RequestParam(required = false) Long id,
                               @RequestParam(required = false) String from,
                               @RequestParam(required = false) String to,
                               Model model) {
        if (id != null)
            return Bus_linePage(id, model);
        List<BusLine> buslines = (List<BusLine>) buslineDAO.getAll();
        List<BusLine> filtered = new ArrayList<>();
        for (BusLine busline: buslines) {
            List<String> stations = stationbuslineDAO.getBusLineStaions(busline);
            if (stations.isEmpty())
                continue;
            if ((stations.get(0).equals(from) || from.equals("")) && ((to.equals("")) || stations.get(stations.size() - 1).equals(to))) {
                filtered.add(busline);
            }
        }
        model.addAttribute("stationbuslineDAO", stationbuslineDAO);
        model.addAttribute("buslines", filtered);
        model.addAttribute("buslineDAO", buslineDAO);
        return "bus_lines";
    }
}