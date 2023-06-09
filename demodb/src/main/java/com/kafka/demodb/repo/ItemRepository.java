package com.kafka.demodb.repo;

import com.kafka.demodb.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findItemByName(String name);
    Item findItemByDesctiption(String description);

}
