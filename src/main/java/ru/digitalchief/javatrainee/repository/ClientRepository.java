package ru.digitalchief.javatrainee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.digitalchief.javatrainee.entity.Client;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findClientByEmail(String email);

    Client findClientByPhoneNumber(String phoneNumber);
}
