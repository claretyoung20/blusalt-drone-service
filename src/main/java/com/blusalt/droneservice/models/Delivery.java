package com.blusalt.droneservice.models;

import com.blusalt.droneservice.models.enums.DeliveryStatusDelivery;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
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

    public Long getId() {
        return this.id;
    }

    public Delivery id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeliveryStatusDelivery getDeliveryStatus() {
        return this.deliveryStatus;
    }

    public Delivery deliveryStatus(DeliveryStatusDelivery deliveryStatus) {
        this.setDeliveryStatus(deliveryStatus);
        return this;
    }

    public void setDeliveryStatus(DeliveryStatusDelivery deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public Delivery startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public Delivery endTime(Instant endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public Delivery dateCreated(Date dateCreated) {
        this.setDateCreated(dateCreated);
        return this;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return this.dateUpdated;
    }

    public Delivery dateUpdated(Date dateUpdated) {
        this.setDateUpdated(dateUpdated);
        return this;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public Delivery duration(Duration duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Drone getDrone() {
        return this.drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public Delivery drone(Drone drone) {
        this.setDrone(drone);
        return this;
    }

    public Set<PackageInfo> getPackageInfos() {
        return this.packageInfos;
    }

    public void setPackageInfos(Set<PackageInfo> packageInfos) {
        if (this.packageInfos != null) {
            this.packageInfos.forEach(i -> i.setDelivery(null));
        }
        if (packageInfos != null) {
            packageInfos.forEach(i -> i.setDelivery(this));
        }
        this.packageInfos = packageInfos;
    }

    public Delivery packageInfos(Set<PackageInfo> packageInfos) {
        this.setPackageInfos(packageInfos);
        return this;
    }

    public Delivery addPackageInfo(PackageInfo packageInfo) {
        this.packageInfos.add(packageInfo);
        packageInfo.setDelivery(this);
        return this;
    }

    public Delivery removePackageInfo(PackageInfo packageInfo) {
        this.packageInfos.remove(packageInfo);
        packageInfo.setDelivery(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Delivery)) {
            return false;
        }
        return id != null && id.equals(((Delivery) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + getId() +
                ", status='" + getDeliveryStatus() + "'" +
                ", startTime='" + getStartTime() + "'" +
                ", endTime='" + getEndTime() + "'" +
                ", dateCreated='" + getDateCreated() + "'" +
                ", dateUpdated='" + getDateUpdated() + "'" +
                ", duration='" + getDuration() + "'" +
                "}";
    }
}
