package ru.cmc.msu.webprak.DAO;

import ru.cmc.msu.webprak.entities.BusLine;
import ru.cmc.msu.webprak.entities.StationBusLine;

import java.util.List;

public interface StationBusLineDAO extends CommonDAO<StationBusLine, String> {
    List<String> getBusLineStaions(BusLine obj);

    void save(StationBusLine entity);
}