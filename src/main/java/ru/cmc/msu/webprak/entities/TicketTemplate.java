package ru.cmc.msu.webprak.entities;

import lombok.*;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "ticket_template")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class TicketTemplate implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bus_line", referencedColumnName="id")
    @ToString.Exclude
    @NonNull
    private BusLine bus_line;

    @Column(nullable = false, name = "date_departure")
    private Date date_departure;

    @Column(name = "price")
    private Integer price;

    @Column(nullable = false, name = "from_station")
    @NonNull
    private String from_station;

    @Column(nullable = false, name = "to_station")
    @NonNull
    private String to_station;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketTemplate other = (TicketTemplate) o;
        return Objects.equals(id, other.id)
                && bus_line.equals(other.bus_line)
                && date_departure.equals(other.date_departure)
                && price.equals(other.price)
                && from_station.equals(other.from_station)
                && to_station.equals(other.to_station);
    }
}