package ru.cmc.msu.webprak.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Ticket implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_template", referencedColumnName="id")
    @ToString.Exclude
    @NonNull
    private TicketTemplate id_template;

    @Column(nullable = false, name = "place")
    @NonNull
    private Integer place;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_person", referencedColumnName="id")
    @ToString.Exclude
    @NonNull
    private Person id_person;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket other = (Ticket) o;
        return Objects.equals(id, other.id)
                && id_template.equals(other.id_template)
                && place.equals(other.place)
                && id_person.equals(other.id_person);
    }
}