package com.overlook.core.domain.user;

import com.overlook.core.domain.company.PhoneNumber;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "contact_info")
public class ContactInfo {

    @Id
    private UUID contactInfoId;

    @PrimaryKeyJoinColumn
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    private List<PhoneNumber> phoneNumbers;

    private String email;

}
