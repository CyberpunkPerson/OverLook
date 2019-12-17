package com.overlook.security.domain;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "user_profiles") //FIXME Refactor this
public class UserProfile extends User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID userId;

    public UserProfile(String username, String password, Set<ProfileRole> authorities) {
        super(username, password, authorities.stream()
                .map(profileRole -> new SimpleGrantedAuthority(profileRole.name()))
                .collect(Collectors.toList()));
    }
}
