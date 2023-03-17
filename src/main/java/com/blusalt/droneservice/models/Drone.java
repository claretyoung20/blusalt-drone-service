package com.blusalt.droneservice.models;

import com.blusalt.droneservice.models.enums.DroneStateConstant;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "drone")
@Data
public class Drone  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "serial_number", length = 100, nullable = false, unique = true)
    private String serialNumber;

    @NotNull
    @Column(name = "weight_limit", nullable = false)
    private Double weightLimit;

    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    @Column(name = "battery_level", nullable = false)
    private Integer batteryLevel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GenericStatusConstant status;

    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @Column(name = "date_updated")
    private Date dateUpdated;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "drone_state")
    private DroneStateConstant droneState;

    @ManyToOne(optional = false)
    @NotNull
    private DroneModel droneModel;

}
