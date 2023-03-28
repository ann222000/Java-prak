package ru.cmc.msu.webprak.entities;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "person")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Person implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "surname")
    @NonNull
    private String surname;

    @Column(nullable = false, name = "fathers_name")
    @NonNull
    private String fathers_name;

    @Column(name = "telephone_number")
    private String telephone_number;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person other = (Person) o;
        return Objects.equals(id, other.id)
                && name.equals(other.name)
                && surname.equals(other.surname)
                && fathers_name.equals(other.fathers_name)
                && telephone_number.equals(other.telephone_number)
                && email.equals(other.email)
                && address.equals(other.address);
    }
}