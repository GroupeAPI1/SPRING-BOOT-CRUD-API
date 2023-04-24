package com.codclient.ClientAPI.controllers;

import com.codclient.ClientAPI.models.Client;
import com.codclient.ClientAPI.models.Compte;
import com.codclient.ClientAPI.models.TypeCompte;
import com.codclient.ClientAPI.repository.CompteRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comptes")

// ...

public class CompteController {
    @Autowired
    private CompteRepository compteRepository;

    public CompteController(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    // ...

    private Compte creerCompte(Client client, TypeCompte typeCompte) {
        Compte compte = new Compte();
        compte.setType(typeCompte);
        compte.setDateCreation(LocalDate.now());
        compte.setSolde(0);
        compte.setProprietaire(client);
        String randomString = RandomStringUtils.randomAlphabetic(5).toUpperCase();
        String numero = randomString + LocalDate.now().getYear();
        compte.setNumero(numero);
        compteRepository.save(compte);
        return compte;
    }

    // ...




    @GetMapping
    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compte> getCompteById(@PathVariable Long id) {
        Optional<Compte> compte = compteRepository.findById(id);
        if (compte.isPresent()) {
            return ResponseEntity.ok(compte.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Compte createCompte(@RequestBody Compte compte) {
        return compteRepository.save(compte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compte> updateCompte(@PathVariable Long id, @RequestBody Compte compte) {
        Optional<Compte> existingCompte = compteRepository.findById(id);
        if (existingCompte.isPresent()) {
            compte.setId(id);
            compteRepository.save(compte);
            return ResponseEntity.ok(compte);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        Optional<Compte> compte = compteRepository.findById(id);
        if (compte.isPresent()) {
            compteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/versement")
    public ResponseEntity<Void> faireVersement(@PathVariable Long id, @RequestParam("montant") double montant) {
        Optional<Compte> compte = compteRepository.findById(id);
        if (compte.isPresent()) {
            Compte c = compte.get();
            c.setSolde(c.getSolde() + montant);
            compteRepository.save(c);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/retrait")
    public ResponseEntity<Void> faireRetrait(@PathVariable Long id, @RequestParam("montant") double montant) {
        Optional<Compte> compte = compteRepository.findById(id);
        if (compte.isPresent()) {
            Compte c = compte.get();
            if (c.getSolde() >= montant) {
                c.setSolde(c.getSolde() - montant);
                compteRepository.save(c);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

