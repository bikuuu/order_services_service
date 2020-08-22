package com.pranienawezwanie.orderservicesservice.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime orderDate;

    private LocalDateTime completedDate;
    private Double duration;
    private Double price;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Service service;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private  AppUser appUser;

    @ManyToMany
    private Set<ExtraService> extraServices;

    @ManyToMany
    private Set<Address> address;

    @OneToOne
    private Schedule schedule;
}
