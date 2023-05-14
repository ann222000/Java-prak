package ru.cmc.msu.webprak.DAO.implementation;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.cmc.msu.webprak.DAO.StationBusLineDAO;
import ru.cmc.msu.webprak.entities.BusLine;
import ru.cmc.msu.webprak.entities.StationBusLine;

import java.util.ArrayList;
import java.util.List;


@Repository
public class StationBusLineDAOImpl extends CommonDAOImpl<StationBusLine, String> implements StationBusLineDAO {
    public StationBusLineDAOImpl(){
        super(StationBusLine.class);
    }


    @Override
    public List<StationBusLine> getByBusLine(BusLine obj) {
        try (Session session = sessionFactory.openSession()) {
            Query<StationBusLine> query = session.createQuery("FROM StationBusLine WHERE bus_line.id = :bus_line_param order by type asc", StationBusLine.class)
                    .setParameter("bus_line_param", obj.getId());
            return query.getResultList();
        }
    }

    @Override
    public List<String> getBusLineStaions(BusLine obj) {
        try (Session session = sessionFactory.openSession()) {
            Query<StationBusLine> query = session.createQuery("FROM StationBusLine WHERE bus_line.id = :bus_line_param order by type asc", StationBusLine.class)
                    .setParameter("bus_line_param", obj.getId());
            List<StationBusLine> res = query.getResultList();
            List<String> res_string = new ArrayList<>();
            for (StationBusLine re : res) {
                res_string.add(re.getStation_name());
            }
            return res_string;
        }
    }

    @Override
    public void save(StationBusLine entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
    }
}

