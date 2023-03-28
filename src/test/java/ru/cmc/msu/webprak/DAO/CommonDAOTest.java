package ru.cmc.msu.webprak.DAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.cmc.msu.webprak.entities.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class CommonDAOTest {
    @Autowired
    private CommonDAO<Person, Long> personDAO;

    @Autowired
    private CommonDAO<Ticket, Long> ticketDAO;

    @Autowired
    private CommonDAO<BusLine, Long> busLineDAO;

    @Autowired
    private CommonDAO<TicketTemplate, Long> ticketTemplateDAO;

    @Autowired
    private CommonDAO<StationBusLine, String> stationBusLineDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testUpdate() {
        String sur = "Маркова";
        Long id = 3L;
        Person updatePerson = personDAO.getById(id);
        updatePerson.setSurname(sur);
        personDAO.update(updatePerson);
        Person pers = personDAO.getById(id);
        assertEquals(sur, pers.getSurname());
    }

    @Test
    void testgetAll() {
        List<BusLine> buslines = (List<BusLine>) busLineDAO.getAll();
        assertEquals(3, buslines.size());
        assertEquals("Pobeda", buslines.get(0).getCompany());
        assertEquals("Turin", buslines.get(1).getCompany());
        assertEquals("Iria", buslines.get(2).getCompany());
    }

    @Test
    void testdelete() {
        TicketTemplate tickettemp = ticketTemplateDAO.getById(2L);
        BusLine busline = busLineDAO.getById(tickettemp.getBus_line().getId());
        ticketTemplateDAO.delete(tickettemp);
        assertNull(ticketTemplateDAO.getById(3L));
    }

    @Test
    void testsaveCollection() {
        List<Person> personList = new ArrayList<>();
        Long current_size = (long) ((List<Person>) personDAO.getAll()).size();
        personList.add(new Person(null, "Василий", "Петров", "Петрович", "89281572335", null, null));
        personList.add(new Person(null, "Ирина", "Роднина", "Львовна", "89281123456", null, null));
        personList.add(new Person(null, "Константин", "Марков", "Львович", null, "as@icloud.com", null));
        personList.add(new Person(null, "Андрей", "Карпов", "Игнатьевич", null, "qwert@gse.cs.msu.ru", null));
        personDAO.saveCollection(personList);
        assertNotNull(personDAO.getById(current_size + 3L));
        assertEquals(current_size + 4, ((List<Person>) personDAO.getAll()).size());
        assertEquals("Константин", personDAO.getById(current_size + 3L).getName());
    }

    @Test
    void testdeletebyid() {
        int current_size = personDAO.getAll().size();
        personDAO.deleteById(3L);
        assertNull(personDAO.getById(3L));
        assertEquals(current_size - 1, personDAO.getAll().size());
    }

    @Test
    void testsearchbyFilter() {
        Map<String, Long> testplace = new HashMap<>();
        testplace.put("place", 20L);
        List<Ticket> tickets = ticketDAO.searchByFilter(testplace);
        assertEquals(2, tickets.size());
        assertEquals(20, tickets.get(0).getPlace());
        assertEquals(20, tickets.get(1).getPlace());

    }

    @BeforeEach
    void beforeEach() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1L, "Леонид", "Капустин", "Петрович", "89281572335", null, null));
        personList.add(new Person(2L, "Ирина", "Роднина", "Александровна", "89298123456", null, null));
        personList.add(new Person(3L, "Петр", "Марков", "Валерьевич", null, "as@icloud.com", null));
        personList.add(new Person(4L, "Андрей", "Картошкин", "Андреевич", "89281682898", "qwert@gse.cs.msu.ru", null));
        personDAO.saveCollection(personList);
        List<BusLine> buslineList = new ArrayList<>();
        buslineList.add(new BusLine(null, "Pobeda", 50));
        buslineList.add(new BusLine(null, "Turin", 40));
        buslineList.add(new BusLine(null, "Iria", 30));
        busLineDAO.saveCollection(buslineList);
        List<TicketTemplate> tickettemplate = new ArrayList<>();
        tickettemplate.add (new TicketTemplate(null, busLineDAO.getById(1L), null, 890, "Anapa", "Salarievo"));
        tickettemplate.add (new TicketTemplate(null, busLineDAO.getById(2L), null, 920, "Rostov", "Salarievo"));
        ticketTemplateDAO.saveCollection(tickettemplate);
        List<Ticket> ticket = new ArrayList<>();
        ticket.add(new Ticket(null, ticketTemplateDAO.getById(1L), 20, personDAO.getById(2L)));
        ticket.add(new Ticket(null, ticketTemplateDAO.getById(1L), 12, personDAO.getById(1L)));
        ticket.add(new Ticket(null, ticketTemplateDAO.getById(1L), 13, personDAO.getById(4L)));
        ticket.add(new Ticket(null, ticketTemplateDAO.getById(2L), 20, personDAO.getById(3L)));
        ticketDAO.saveCollection(ticket);
    }

    @BeforeAll
    @AfterEach
    void restart() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE person RESTART IDENTITY CASCADE;").executeUpdate();
            session.createNativeQuery("ALTER SEQUENCE person_id_seq RESTART WITH 1;").executeUpdate();
            session.createNativeQuery("TRUNCATE bus_line RESTART IDENTITY CASCADE;").executeUpdate();
            session.createNativeQuery("ALTER SEQUENCE bus_line_id_seq RESTART WITH 1;").executeUpdate();
            session.createNativeQuery("TRUNCATE ticket RESTART IDENTITY CASCADE;").executeUpdate();
            session.createNativeQuery("ALTER SEQUENCE ticket_id_seq RESTART WITH 1;").executeUpdate();
            session.createNativeQuery("TRUNCATE ticket_template RESTART IDENTITY CASCADE;").executeUpdate();
            session.createNativeQuery("ALTER SEQUENCE ticket_template_id_seq RESTART WITH 1;").executeUpdate();
            session.createNativeQuery("TRUNCATE station_and_bus_line RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
