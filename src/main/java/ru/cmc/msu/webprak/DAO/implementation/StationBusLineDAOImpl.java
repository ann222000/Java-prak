package ru.cmc.msu.webprak.DAO.implementation;

import org.springframework.stereotype.Repository;
import ru.cmc.msu.webprak.DAO.StationBusLineDAO;
import ru.cmc.msu.webprak.entities.StationBusLine;


@Repository
public class StationBusLineDAOImpl extends CommonDAOImpl<StationBusLine, String> implements StationBusLineDAO {
    public StationBusLineDAOImpl(){
        super(StationBusLine.class);
    }
}