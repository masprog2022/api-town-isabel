package com.masprogtechs.apitownisabel.repositories;

import com.masprogtechs.apitownisabel.models.Attached;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachedRepository extends JpaRepository<Attached, Long> {

    Optional<Attached> findByName(String fileName);
}
