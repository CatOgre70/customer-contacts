package net.vasilydemin.customercontacts.constant;

import lombok.Getter;

@Getter
public enum ContactType {

    PHONE("phone"), EMAIL("email");

    private final String name;

    ContactType(String name) {
        this.name = name;
    }

}
