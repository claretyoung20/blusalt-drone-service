package com.blusalt.droneservice.models.pojos;

import com.blusalt.droneservice.models.Address;
import com.blusalt.droneservice.models.Delivery;
import com.blusalt.droneservice.models.Item;
import com.blusalt.droneservice.models.enums.DeliveryStatus;
import com.blusalt.droneservice.models.enums.ItemTypeConstant;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
public class ItemPojo {
    private Long id;
    private String name;
    private Double weight;
    private String code;
    private String imageUrl;
    private ItemTypeConstant itemTypeConstant;
    private DeliveryStatus itemStatus;
    private Instant timeDelivered;
    private Date dateCreated;
    private Date dateUpdated;
    private Address address;
    private Delivery delivery;

    public static ItemPojo toPojo(Item item) {
        ItemPojo pojo = new ItemPojo();

        //todo set values

        return pojo;
    }
}
