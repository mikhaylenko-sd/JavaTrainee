package ru.digitalchief.javatrainee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.digitalchief.javatrainee.entity.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
}
