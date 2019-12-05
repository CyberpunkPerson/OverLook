package com.overlook.core.domain.company;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "provider")
public class Provider {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID providerId;

    @NotBlank
    private String name;

    @OneToMany
    @JoinColumn(name = "provider_id")
    private List<PhoneNumber> phoneNumberList;
}
