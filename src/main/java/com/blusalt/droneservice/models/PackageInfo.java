package com.blusalt.droneservice.models;

import com.blusalt.droneservice.models.enums.DeliveryStatusDelivery;
import com.blusalt.droneservice.models.enums.PackageTypeConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class PackageInfo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
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
    private String code; // todo auto generate or not ?

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
    private DeliveryStatusDelivery packageStatus;

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

    public Long getId() {
        return this.id;
    }

    public PackageInfo id(Long id) {
        this.setId(id);
        return this;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public PackageInfo name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return this.weight;
    }

    public PackageInfo weight(Double weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getCode() {
        return this.code;
    }

    public PackageInfo code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public PackageInfo imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PackageTypeConstant getPackageType() {
        return this.packageType;
    }

    public PackageInfo packageType(PackageTypeConstant packageType) {
        this.setPackageType(packageType);
        return this;
    }

    public void setPackageType(PackageTypeConstant packageType) {
        this.packageType = packageType;
    }

    public DeliveryStatusDelivery getPackageStatus() {
        return this.packageStatus;
    }

    public PackageInfo packageStatus(DeliveryStatusDelivery packageStatus) {
        this.setPackageStatus(packageStatus);
        return this;
    }

    public void setPackageStatus(DeliveryStatusDelivery packageStatus) {
        this.packageStatus = packageStatus;
    }

    public Instant getTimeDelivered() {
        return this.timeDelivered;
    }

    public PackageInfo timeDelivered(Instant timeDelivered) {
        this.setTimeDelivered(timeDelivered);
        return this;
    }

    public void setTimeDelivered(Instant timeDelivered) {
        this.timeDelivered = timeDelivered;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public PackageInfo dateCreated(Date dateCreated) {
        this.setDateCreated(dateCreated);
        return this;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return this.dateUpdated;
    }

    public PackageInfo dateUpdated(Date dateUpdated) {
        this.setDateUpdated(dateUpdated);
        return this;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PackageInfo address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Delivery getDelivery() {
        return this.delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public PackageInfo delivery(Delivery delivery) {
        this.setDelivery(delivery);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PackageInfo)) {
            return false;
        }
        return id != null && id.equals(((PackageInfo) o).id);
    }

    @Override
    public int hashCode() {
     return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PackageInfo{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", weight=" + getWeight() +
                ", code='" + getCode() + "'" +
                ", imageUrl='" + getImageUrl() + "'" +
                ", packageType='" + getPackageType() + "'" +
                ", packageStatus='" + getPackageStatus() + "'" +
                ", timeDelivered='" + getTimeDelivered() + "'" +
                ", dateCreated='" + getDateCreated() + "'" +
                ", dateUpdated='" + getDateUpdated() + "'" +
                "}";
    }


}
