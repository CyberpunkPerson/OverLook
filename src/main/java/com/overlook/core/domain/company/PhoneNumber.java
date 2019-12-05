package com.overlook.core.domain.company;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Entity
@Table(name = "phone_number")
public class PhoneNumber {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID phoneNumberId;

    @NotBlank
    private String phoneNumber;

    //TODO Perhaps would be better to rid of this link
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "provider_id")
    private Provider provider;
}
