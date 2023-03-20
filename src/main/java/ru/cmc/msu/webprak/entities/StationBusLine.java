package ru.cmc.msu.webprak.entities;

import lombok.*;

import javax.persistence.*;
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

    public enum Station_type {FINAL,START, COMMON}
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bus_line", referencedColumnName="id")
    @ToString.Exclude
    @NonNull
    private BusLine bus_line;

    @Id
    @Column(nullable = false, name = "station_name")
    private String station_name;

    @Column(nullable = false, name = "time_in")
    private Time time_in;

    @Column(nullable = false, name = "time_out")
    private Time time_out;

    @Column(nullable = false, name = "type")
    private Station_type type;

    @Column(nullable = false, name = "day")
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