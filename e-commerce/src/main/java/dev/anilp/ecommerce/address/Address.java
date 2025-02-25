package dev.anilp.ecommerce.address;

import dev.anilp.ecommerce.common.BaseEntity;
import dev.anilp.ecommerce.user.User;
import dev.anilp.ecommerce.validation.PostalCode;
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
public class Address extends BaseEntity {

    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false)
    private AddressType addressType;
    @Column(name = "address_line", nullable = false)
    private String addressLine;
    private String street;
    private String district;
    private String city;
    @PostalCode
    @Column(name = "postal_code", nullable = false, length = 5)
    private String postalCode;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
