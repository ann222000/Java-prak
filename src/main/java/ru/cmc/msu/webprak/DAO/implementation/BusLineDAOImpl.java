package ru.cmc.msu.webprak.DAO.implementation;

import org.springframework.stereotype.Repository;
import ru.cmc.msu.webprak.DAO.BusLineDAO;
import ru.cmc.msu.webprak.entities.BusLine;


@Repository
public class BusLineDAOImpl extends CommonDAOImpl<BusLine, Long> implements BusLineDAO {
    public BusLineDAOImpl(){
        super(BusLine.class);
    }
}