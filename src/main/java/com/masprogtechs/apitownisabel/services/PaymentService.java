package com.masprogtechs.apitownisabel.services;

import com.masprogtechs.apitownisabel.dtos.PaymentCreateDto;
import com.masprogtechs.apitownisabel.exception.EntityRuntimeException;
import com.masprogtechs.apitownisabel.exception.IllegalArgumentException;
import com.masprogtechs.apitownisabel.models.Month;
import com.masprogtechs.apitownisabel.models.Payment;
import com.masprogtechs.apitownisabel.models.User;
import com.masprogtechs.apitownisabel.repositories.MonthRepository;
import com.masprogtechs.apitownisabel.repositories.PaymentRepository;
import com.masprogtechs.apitownisabel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final MonthRepository monthRepository;

    public Payment makePayment(PaymentCreateDto paymentCreateDto) {

        User user = userRepository.findById(paymentCreateDto.getUserId())
                .orElseThrow(() -> new EntityRuntimeException(String.format("Usuário com identificador %s não existe.", paymentCreateDto.getUserId())));

        List<Month> months = monthRepository.findAllById(paymentCreateDto.getMonthIds());
        if (months.isEmpty()) {
            throw new IllegalArgumentException("Mês não existe.");
        }



        BigDecimal fixedAmount = BigDecimal.valueOf(5000);
        BigDecimal amountPaid = calculateAmountPaid(fixedAmount, months.size());



       /* for (Month month : months) {
            String monthAlreadyPaid = paymentRepository.findFirstMonthAlreadyPaidByMonthAndYearAndUser(user.getId(), Collections.singletonList(month.getId()), paymentCreateDto.getYear());
            if (monthAlreadyPaid != null) {
                throw new IllegalArgumentException(String.format("O mês %s já foi pago pelo usuário.", monthAlreadyPaid));
            }
        }*/

        for (Month month : months) {
            List<String> monthsAlreadyPaid = paymentRepository.findAllMonthsAlreadyPaidByMonthAndYearAndUser(
                    user.getId(), paymentCreateDto.getMonthIds(), paymentCreateDto.getYear());
            if (!monthsAlreadyPaid.isEmpty()) {
                throw new IllegalArgumentException(String.format("O(s) mês(es) de %s já foram pagos pelo usuário.", String.join(", ", monthsAlreadyPaid)));
            }
        }

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setMonths(months);
        payment.setYear(paymentCreateDto.getYear());
        payment.setAmountPaid(amountPaid);
        payment.setPaid(true);

        return paymentRepository.save(payment);
    }

    private BigDecimal calculateAmountPaid(BigDecimal fixedAmount, int numberOfMonths) {
        if (numberOfMonths <= 0) {
            throw new IllegalArgumentException("O número de meses deve ser maior que zero");
        }
        return fixedAmount.multiply(BigDecimal.valueOf(numberOfMonths));
    }

}
