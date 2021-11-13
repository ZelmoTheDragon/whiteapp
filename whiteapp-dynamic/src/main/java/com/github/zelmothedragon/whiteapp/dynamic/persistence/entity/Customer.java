package com.github.zelmothedragon.whiteapp.dynamic.persistence.entity;

import com.github.zelmothedragon.whiteapp.dynamic.persistence.UUIDConverter;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data

@Entity
@Table(name = "customer")
public class Customer implements Identifiable<UUID>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, unique = true, columnDefinition = UUIDConverter.COLUMN_DEFINITION)
    UUID id;

    @Column(name = "given_name", nullable = false, length = 255)
    String givenName;

    @Column(name = "family_name", nullable = false, length = 255)
    String familyName;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    String email;

}
