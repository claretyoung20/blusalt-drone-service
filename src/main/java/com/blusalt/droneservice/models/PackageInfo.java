package com.blusalt.droneservice.models;

import com.blusalt.droneservice.models.enums.PackageStatusConstant;
import com.blusalt.droneservice.models.enums.PackageTypeConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * A PackageInfo.
 */
@Entity
@Table(name = "package_info")
@Data
public class PackageInfo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_\\-]*$")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Double weight;

    @NotNull
    @Pattern(regexp = "^[A-Z0-9_]*$")
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "package_type", nullable = false)
    private PackageTypeConstant packageType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "package_status", nullable = false)
    private PackageStatusConstant packageStatus;

    @Column(name = "time_delivered")
    private Instant timeDelivered;

    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @Column(name = "date_updated")
    private Date dateUpdated;

    @ManyToOne
    private Address address;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "drone", "packageInfos" }, allowSetters = true)
    private Delivery delivery;

}
