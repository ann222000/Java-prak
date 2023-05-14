package ru.cmc.msu.webprak.DAO.implementation;

import org.springframework.stereotype.Repository;
import ru.cmc.msu.webprak.DAO.BusLineDAO;
import ru.cmc.msu.webprak.entities.BusLine;

import java.util.Collection;
import java.util.List;


@Repository
public class BusLineDAOImpl extends CommonDAOImpl<BusLine, Long> implements BusLineDAO {
    public BusLineDAOImpl() {
        super(BusLine.class);
    }

//    @Override
//    public List<String> getStations(ID id) {
//
//
//    }
}