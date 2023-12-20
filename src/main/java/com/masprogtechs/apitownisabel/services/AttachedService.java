package com.masprogtechs.apitownisabel.services;

import com.masprogtechs.apitownisabel.exception.EntityRuntimeException;
import com.masprogtechs.apitownisabel.models.Attached;
import com.masprogtechs.apitownisabel.models.Payment;
import com.masprogtechs.apitownisabel.repositories.AttachedRepository;
import com.masprogtechs.apitownisabel.repositories.PaymentRepository;
import com.masprogtechs.apitownisabel.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class AttachedService {

    private final AttachedRepository attachedRepository;
    private final PaymentRepository paymentRepository;

    public String attachedInvoice(MultipartFile file, Long paymentId) throws IOException {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityRuntimeException(String.format("Pagamento %d não existe.", paymentId )));

        Attached attached = attachedRepository.save(Attached.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .payment(payment)
                .build());

        return "Ficheiro salvo com sucesso : " + file.getOriginalFilename();

    }
}
