package com.blusalt.droneservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
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

    @NotNull
    @Column(name = "drone_state", nullable = false)
    private String droneState;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "droneState", "droneModel" }, allowSetters = true)
    private Drone drone;

}
