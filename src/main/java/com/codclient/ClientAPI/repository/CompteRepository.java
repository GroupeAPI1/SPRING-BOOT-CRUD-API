package com.codclient.ClientAPI.repository;

import com.codclient.ClientAPI.models.Client;
import com.codclient.ClientAPI.models.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    List<Compte> findByProprietaire(Client client);

}

