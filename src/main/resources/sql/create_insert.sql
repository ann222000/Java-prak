DROP TYPE IF EXISTS station_type CASCADE;

DROP TABLE IF EXISTS person CASCADE;
CREATE TABLE person(
    id SERIAL PRIMARY KEY,
    surname text NOT NULL,
    name text NOT NULL,
    fathers_name text NOT NULL,
    telephone_number text,
    email text,
    address text
);

-- ALTER SEQUENCE bus_line_id_seq RESTART WITH 1;
DROP TABLE IF EXISTS bus_line CASCADE;
CREATE TABLE bus_line (
                          id SERIAL PRIMARY KEY,
                          company text NOT NULL,
                          number_of_places integer NOT NULL
);

DROP TABLE IF EXISTS ticket_template CASCADE;
CREATE TABLE ticket_template (
    id SERIAL PRIMARY KEY,
    bus_line integer NOT NULL REFERENCES bus_line(id) ON DELETE CASCADE,
    date_departure date,
    price float CHECK (price > 0),
    from_station text NOT NULL,
    to_station text NOT NULL
);

DROP TABLE IF EXISTS ticket;
CREATE TABLE ticket (
    id          SERIAL PRIMARY KEY,
    id_template integer NOT NULL REFERENCES ticket_template(id) ON DELETE CASCADE,
    place       integer NOT NULL CHECK ((place > 0) and (place <= 60)),
    id_person   integer NOT NULL REFERENCES person(id) ON DELETE CASCADE
);


DROP TABLE IF EXISTS station_and_bus_line;
CREATE TABLE station_and_bus_line (
    bus_line integer NOT NULL REFERENCES bus_line(id) ON DELETE CASCADE,
    station_name text,
    time_in time,
    time_out time,
    type int,
    day integer NOT NULL CHECK (day > 0),
    PRIMARY KEY(bus_line, station_name)
);
-- type is number of this station from start from 0

INSERT INTO person
VALUES
    (1, 'Путилов', 'Евгений', 'Борисович', '89281342567', 'asd@icloud.com', 'Moscow, Leninski 8'),
    (2, 'Федотов', 'Александр', 'Игоревич', '89281122567', 'serun@icloud.com', 'Moscow, Leninski 58'),
    (3, 'Муратова', 'Елена', 'Константиновна', '89281462567', 'elena1345@icloud.com', 'Moscow, Nikolskaya 10'),
    (4, 'Царева', 'Вера', 'Ивановна', '89282582567', 'segio98@icloud.com', 'Moscow, Svetlanova 12'),
    (5, 'Сенюкова', 'Мария', 'Игнатьевна', '89281349067', 'winterbeauty@icloud.com', 'Moscow, Svetlanova 25');

INSERT INTO bus_line
VALUES
    (1, 'Pobeda', 53),
    (2, 'Roman_tower', 25),
    (3, 'Snowman', 35),
    (4, 'Rosa Khutor', 30);

INSERT INTO ticket_template
VALUES
    (1, 1, '2023-06-05', 800, 'Salarievo', 'Rostov-Main'),
    (2, 1, '2023-04-23', 700,'Salarievo', 'Rostov-Main'),
    (3, 2, '2023-03-30', 1000, 'Rostov-Main', 'Salarievo'),
    (4, 3, '2023-03-23', 900, 'Rostov-Main', 'Anapa'),
    (5, 4, '2023-07-30', 590, 'Salarievo', 'Sochi');

INSERT INTO ticket
VALUES
    (1, 1, 32, 2),
    (2, 3, 20, 3),
    (3, 4, 29, 5),
    (4, 2, 12, 4),
    (5, 3, 1, 1);

INSERT INTO station_and_bus_line
VALUES
    (1, 'Serpuhov','20:25', '20:30', 2, 1),
    (3, 'Anapa','09:34', NULL, 0, 2),
    (1, 'Rostov-Main','14:40', NULL, 0, 2),
    (2, 'Salarievo','10:30',NULL, 1, 2),
    (4, 'Tuapse','19:20', '19:30', 2, 2);


-- SET ROLE postgres; password admin
-- GRANT USAGE ON SCHEMA public to anna;
-- grant all on database postgres to anna;
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO anna;
--
-- SET ROLE anna;
-- SELECT * from person;
--
-- ALTER USER anna with SUPERUSER;
-- SELECT * FROM public.StationBusLine WHERE bus_line = :bus_line_param
ALTER SEQUENCE bus_line_id_seq RESTART WITH 5;
ALTER SEQUENCE ticket_template_id_seq RESTART WITH 6;
ALTER SEQUENCE ticket_id_seq RESTART WITH 6;
ALTER SEQUENCE person_id_seq RESTART WITH 6;

