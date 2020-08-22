package com.pranienawezwanie.orderservicesservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String street;
    private String houseNum;
    private String postalCode;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AppUser user;

    @ManyToMany(mappedBy = "address")
    private Set<ServiceOrder> serviceOrders = new HashSet<>();
}
