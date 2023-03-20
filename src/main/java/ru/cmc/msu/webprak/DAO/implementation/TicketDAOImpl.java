package ru.cmc.msu.webprak.DAO.implementation;

import org.springframework.stereotype.Repository;
import ru.cmc.msu.webprak.DAO.TicketDAO;
import ru.cmc.msu.webprak.entities.Ticket;


@Repository
public class TicketDAOImpl extends CommonDAOImpl<Ticket, Long> implements TicketDAO {
    public TicketDAOImpl(){
        super(Ticket.class);
    }
}