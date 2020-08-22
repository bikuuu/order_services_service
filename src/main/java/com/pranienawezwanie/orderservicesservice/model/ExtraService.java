package com.pranienawezwanie.orderservicesservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ExtraService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double additionalCost;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Service service;

    @ManyToMany(mappedBy = "extraServices")
    private Set<ServiceOrder> serviceOrders = new HashSet<>();
}
