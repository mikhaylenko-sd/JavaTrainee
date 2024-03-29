DROP TABLE IF EXISTS drivers, orders, clients, payments;

CREATE TABLE IF NOT EXISTS drivers (
    id INT4 GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(30) NOT NULL,
    middle_name VARCHAR(30),
    surname VARCHAR(50) NOT NULL,
    driving_experience INT NOT NULL,
    car_number VARCHAR(10) NOT NULL,
    CONSTRAINT drivers_pk PRIMARY KEY (id),
    CONSTRAINT unique_car_number UNIQUE (car_number)
);

CREATE TABLE IF NOT EXISTS clients(
    id INT4 GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(30) NOT NULL,
    middle_name VARCHAR(30),
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    CONSTRAINT clients_pk PRIMARY KEY (id),
    CONSTRAINT unique_email UNIQUE (email),
    CONSTRAINT unique_phone_number UNIQUE (phone_number)
);

CREATE TABLE IF NOT EXISTS payments(
    id INT4 NOT NULL,
    description VARCHAR(11) NOT NULL,
    CONSTRAINT payments_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS orders(
    id INT4 GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    driver_id INT4 NOT NULL,
    client_id INT4 NOT NULL,
    start_date TIMESTAMP WITHOUT TIME ZONE,
    end_date TIMESTAMP WITHOUT TIME ZONE,
    price INT4 NOT NULL,
    payment_method INT4 NOT NULL,
    CONSTRAINT orders_pk PRIMARY KEY (id),
    CONSTRAINT drivers_fk FOREIGN KEY (driver_id) REFERENCES drivers(id) ON DELETE CASCADE,
    CONSTRAINT clients_fk FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
    CONSTRAINT payments_fk FOREIGN KEY (payment_method) REFERENCES payments(id) ON DELETE CASCADE
);
