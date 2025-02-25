package dev.anilp.ecommerce.phone;

import dev.anilp.ecommerce.common.BaseEntity;
import dev.anilp.ecommerce.user.User;
import dev.anilp.ecommerce.validation.PhoneNumber;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Phone extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "phone_type", nullable = false)
    private PhoneType phoneType;
    @PhoneNumber
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;
}
