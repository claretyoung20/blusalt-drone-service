package com.blusalt.droneservice.models;

import com.blusalt.droneservice.models.enums.DeliveryStatusDelivery;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;

@Entity
@Table(name = "delivery")
@Data
public class Delivery  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status", nullable = false)
    private DeliveryStatusDelivery deliveryStatus;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @Column(name = "date_updated")
    private Date dateUpdated;

    @Column(name = "duration")
    private Duration duration;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "droneState", "droneModel" }, allowSetters = true)
    private Drone drone;

    @OneToMany(mappedBy = "delivery")
    @JsonIgnoreProperties(value = { "address", "delivery" }, allowSetters = true)
    private Set<PackageInfo> packageInfos = new HashSet<>();

//    private double droneWeightAvailable; // todo is it needed?

}
