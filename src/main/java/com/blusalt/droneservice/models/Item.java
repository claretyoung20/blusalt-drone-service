package com.blusalt.droneservice.models;

import com.blusalt.droneservice.models.enums.DeliveryStatus;
import com.blusalt.droneservice.models.enums.ItemTypeConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
public class Item implements Serializable {

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
    private String code;

    @NotNull
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private ItemTypeConstant itemType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "item_status", nullable = false)
    private DeliveryStatus itemStatus;

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
    @JsonIgnoreProperties(value = { "drone", "items" }, allowSetters = true)
    private Delivery delivery;

    public Long getId() {
        return this.id;
    }

    public Item id(Long id) {
        this.setId(id);
        return this;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Item name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return this.weight;
    }

    public Item weight(Double weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getCode() {
        return this.code;
    }

    public Item code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Item imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ItemTypeConstant getItemType() {
        return this.itemType;
    }

    public Item itemType(ItemTypeConstant itemType) {
        this.setItemType(itemType);
        return this;
    }

    public void setItemType(ItemTypeConstant itemType) {
        this.itemType = itemType;
    }

    public DeliveryStatus getItemStatus() {
        return this.itemStatus;
    }

    public Item itemStatus(DeliveryStatus itemStatus) {
        this.setItemStatus(itemStatus);
        return this;
    }

    public void setItemStatus(DeliveryStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Instant getTimeDelivered() {
        return this.timeDelivered;
    }

    public Item timeDelivered(Instant timeDelivered) {
        this.setTimeDelivered(timeDelivered);
        return this;
    }

    public void setTimeDelivered(Instant timeDelivered) {
        this.timeDelivered = timeDelivered;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public Item dateCreated(Date dateCreated) {
        this.setDateCreated(dateCreated);
        return this;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return this.dateUpdated;
    }

    public Item dateUpdated(Date dateUpdated) {
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

    public Item address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Delivery getDelivery() {
        return this.delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Item delivery(Delivery delivery) {
        this.setDelivery(delivery);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return id != null && id.equals(((Item) o).id);
    }

    @Override
    public int hashCode() {
     return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Item{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", weight=" + getWeight() +
                ", code='" + getCode() + "'" +
                ", imageUrl='" + getImageUrl() + "'" +
                ", itemType='" + getItemType() + "'" +
                ", itemStatus='" + getItemStatus() + "'" +
                ", timeDelivered='" + getTimeDelivered() + "'" +
                ", dateCreated='" + getDateCreated() + "'" +
                ", dateUpdated='" + getDateUpdated() + "'" +
                "}";
    }


}
