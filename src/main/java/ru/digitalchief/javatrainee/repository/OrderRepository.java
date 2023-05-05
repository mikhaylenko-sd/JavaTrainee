package ru.digitalchief.javatrainee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.digitalchief.javatrainee.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
