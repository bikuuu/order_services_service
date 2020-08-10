package com.pranienawezwanie.orderservicesservice.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
}
