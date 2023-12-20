package com.masprogtechs.apitownisabel.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "tb_attached")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attached {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    @Lob
    @Column(name = "image_data",length = 1000)
    private byte[] imageData;

    @OneToOne(fetch = FetchType.LAZY) // Use FetchType.LAZY for efficiency
    @JoinColumn(name = "payment_id")
    private Payment payment;



}
