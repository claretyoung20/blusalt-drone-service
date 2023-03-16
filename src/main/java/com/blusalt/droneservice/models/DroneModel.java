package com.blusalt.droneservice.models;

import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * A DroneModel.
 */
@Entity
@Table(name = "drone_model")
@Data
public class DroneModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "model_name", nullable = false, unique = true)
    private String modelName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GenericStatusConstant status;

    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @Column(name = "date_updated")
    private Date dateUpdated;

}
