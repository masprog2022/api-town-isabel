package com.masprogtechs.apitownisabel.services;

import com.masprogtechs.apitownisabel.dtos.PaymentCreateDto;
import com.masprogtechs.apitownisabel.exception.EntityRuntimeException;
import com.masprogtechs.apitownisabel.models.Month;
import com.masprogtechs.apitownisabel.models.Payment;
import com.masprogtechs.apitownisabel.models.User;
import com.masprogtechs.apitownisabel.repositories.MonthRepository;
import com.masprogtechs.apitownisabel.repositories.PaymentRepository;
import com.masprogtechs.apitownisabel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final MonthRepository monthRepository;

    public Payment makePayment(PaymentCreateDto paymentCreateDto) {
        // Validação do usuário
        User user = userRepository.findById(paymentCreateDto.getUserId())
                .orElseThrow(() -> new EntityRuntimeException("Usuário não encontrado"));

        // Validação dos meses
        List<Month> months = monthRepository.findAllById(paymentCreateDto.getMonthIds());
        if (months.isEmpty()) {
            throw new IllegalArgumentException("Nenhum mês válido encontrado");
        }

        // Cálculo do valor pago
        BigDecimal fixedAmount = BigDecimal.valueOf(5000);
        BigDecimal amountPaid = calculateAmountPaid(fixedAmount, months.size());

        // Criação e persistência do pagamento
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setMonths(months);
        payment.setYear(paymentCreateDto.getYear());
        payment.setAmountPaid(amountPaid);
        payment.setPaid(true);

         paymentRepository.save(payment);

         return payment;
    }

    private BigDecimal calculateAmountPaid(BigDecimal fixedAmount, int numberOfMonths) {
        if (numberOfMonths <= 0) {
            throw new IllegalArgumentException("O número de meses deve ser maior que zero");
        }
        return fixedAmount.multiply(BigDecimal.valueOf(numberOfMonths));
    }
}
