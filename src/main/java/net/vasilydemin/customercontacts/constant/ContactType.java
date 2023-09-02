package net.vasilydemin.customercontacts.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ContactType {

    PHONE("phone"), EMAIL("email");

    private final String name;

    ContactType(String name) {
        this.name = name;
    }

    private static final Map<String, ContactType> CONTACT_TYPE_BY_NAME = new HashMap<>();

    static {
        for(ContactType ct : ContactType.values()) {
            CONTACT_TYPE_BY_NAME.put(ct.name, ct);
        }
    }

    public static ContactType getContactTypeByName(String name) {
        return CONTACT_TYPE_BY_NAME.get(name);
    }

}
