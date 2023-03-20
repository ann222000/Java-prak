package ru.cmc.msu.webprak.DAO.implementation;

import org.springframework.stereotype.Repository;
import ru.cmc.msu.webprak.DAO.TicketTemplateDAO;
import ru.cmc.msu.webprak.entities.TicketTemplate;


@Repository
public class TicketTemplateDAOImpl extends CommonDAOImpl<TicketTemplate, Long> implements TicketTemplateDAO {
    public TicketTemplateDAOImpl(){
        super(TicketTemplate.class);
    }
}