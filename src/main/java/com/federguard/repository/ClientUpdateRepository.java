package com.federguard.repository;

import com.federguard.model.ClientUpdate;
import com.federguard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientUpdateRepository extends JpaRepository<ClientUpdate, Long> {

    List<ClientUpdate> findByUser(User user);
}
