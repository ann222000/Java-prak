package ru.cmc.msu.webprak.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.cmc.msu.webprak.entities.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.cmc.msu.webprak.entities.StationBusLine.Station_type.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class StationBusLineDAOTest {
    @Autowired
    private CommonDAO<BusLine, Long> busLineDAO;
    @Autowired
    private StationBusLineDAO stationBusLineDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testgetstations() {
        List<String> stations = stationBusLineDAO.getBusLineStaions(busLineDAO.getById(1L));
        assertEquals(5, stations.size());
        List<String> actual_stations = List.of("Anapa", "Rostov-on-Don", "Voronezh", "Moscow", "Vologda");
        assertEquals(actual_stations, stations);
    }

    @Test
    void testgetAll() {
        List<StationBusLine> stationbuslines = (List<StationBusLine>) stationBusLineDAO.getAll();
        assertEquals(7, stationbuslines.size());
        assertEquals("Anapa", stationbuslines.get(0).getStation_name());
        assertEquals("Rostov-on-Don", stationbuslines.get(1).getStation_name());
        assertEquals("Moscow", stationbuslines.get(3).getStation_name());
    }

    @BeforeEach
    void beforeEach() {
        List<BusLine> buslineList = new ArrayList<>();
        buslineList.add(new BusLine(1L, "Pobeda", 50));
        buslineList.add(new BusLine(2L, "Turin", 40));
        buslineList.add(new BusLine(3L, "Iria", 30));
        busLineDAO.saveCollection(buslineList);
        List<StationBusLine> stationbuslineList = new ArrayList<>();
        stationbuslineList.add(new StationBusLine(busLineDAO.getById(1L), "Anapa", null, null, START, 1));
        stationbuslineList.add(new StationBusLine(busLineDAO.getById(1L), "Rostov-on-Don", null, null, COMMON, 1));
        stationbuslineList.add(new StationBusLine(busLineDAO.getById(1L), "Voronezh", null, null, COMMON, 1));
        stationbuslineList.add(new StationBusLine(busLineDAO.getById(1L), "Moscow", null, null, COMMON, 2));
        stationbuslineList.add(new StationBusLine(busLineDAO.getById(1L), "Vologda", null, null, FINAL, 2));
        stationbuslineList.add(new StationBusLine(busLineDAO.getById(2L), "Moscow", null, null, START, 1));
        stationbuslineList.add(new StationBusLine(busLineDAO.getById(2L), "Vologda", null, null, FINAL, 1));
        stationBusLineDAO.saveCollection(stationbuslineList);
    }

    @BeforeAll
    @AfterEach
    void restart() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE bus_line RESTART IDENTITY CASCADE;").executeUpdate();
            session.createNativeQuery("ALTER SEQUENCE bus_line_id_seq RESTART WITH 1;").executeUpdate();
            session.createNativeQuery("TRUNCATE station_and_bus_line RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}