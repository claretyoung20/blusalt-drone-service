package com.blusalt.droneservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * A DroneAudit.
 */
@Entity
@Table(name = "drone_audit")
@Data
public class DroneAudit  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    @Column(name = "battery_level", nullable = false)
    private Integer batteryLevel;

    @Column(name = "drone_activity")
    private String droneActivity;

    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @Column(name = "date_updated")
    private Date dateUpdated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "droneState", "droneModel" }, allowSetters = true)
    private Drone drone;

    @ManyToOne(optional = false)
    @NotNull
    private DroneState droneState;

}
