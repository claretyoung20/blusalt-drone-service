package com.blusalt.droneservice.models.pojos;

import com.blusalt.droneservice.models.Address;
import com.blusalt.droneservice.models.Delivery;
import com.blusalt.droneservice.models.PackageInfo;
import com.blusalt.droneservice.models.enums.DeliveryStatusDelivery;
import com.blusalt.droneservice.models.enums.PackageTypeConstant;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
public class PackageInfoPojo {
    private Long id;
    private String name;
    private Double weight;
    private String code;
    private String imageUrl;
    private PackageTypeConstant packageType;
    private DeliveryStatusDelivery packageStatus;
    private Instant timeDelivered;
    private Date dateCreated;
    private Date dateUpdated;
    private Address address;
    private Delivery delivery;

    public static PackageInfoPojo toPojo(PackageInfo packageInfo) {
        PackageInfoPojo pojo = new PackageInfoPojo();

        //todo set values

        return pojo;
    }
}
