package ru.cmc.msu.webprak.entities;

import lombok.*;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "station_and_bus_line")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class StationBusLine implements CommonEntity<String> {
    @Override
    public String getId() {
        return station_name;
    }

    @Override
    public void setId(String name) {
        station_name = name;
    }

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bus_line", referencedColumnName="id")
    @ToString.Exclude
    @NonNull
    private BusLine bus_line;

    @Id
    @Column(nullable = false, name = "station_name")
    @NonNull
    private String station_name;

    @Column(name = "time_in")
    private Time time_in;

    @Column(name = "time_out")
    private Time time_out;

    @Column(nullable = false, name = "type")
    @NonNull
    private Integer type;

    @Column(nullable = false, name = "day")
    @NonNull
    private Integer day;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationBusLine other = (StationBusLine) o;
        return Objects.equals(bus_line, other.bus_line)
                && Objects.equals(station_name, other.station_name)
                && Objects.equals(time_in, other.time_in)
                && Objects.equals(type, other.type)
                && Objects.equals(time_out, other.time_out)
                && Objects.equals(day, other.day);
    }
}