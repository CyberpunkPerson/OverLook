package com.overlook.core.domain.user;

import com.overlook.core.domain.company.PhoneNumber;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "contact_info")
public class ContactInfo {

    @Id
    private UUID contactInfoId;

    @NotEmpty
    @PrimaryKeyJoinColumn
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    private List<PhoneNumber> phoneNumbers;

    @NotBlank
    private String email;

}
