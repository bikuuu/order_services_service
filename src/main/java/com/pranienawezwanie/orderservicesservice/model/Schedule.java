package com.pranienawezwanie.orderservicesservice.model;

import lombok.*;
import org.hibernate.criterion.Order;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private Integer slotNumber;
    private Integer duration;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "schedule")
    private ServiceOrder serviceOrder;
}
