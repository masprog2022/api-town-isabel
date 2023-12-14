package com.masprogtechs.apitownisabel.repositories;

import com.masprogtechs.apitownisabel.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
