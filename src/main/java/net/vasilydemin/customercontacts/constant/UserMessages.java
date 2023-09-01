package net.vasilydemin.customercontacts.constant;

import lombok.Getter;

@Getter
public enum UserMessages {

    CUSTOMER_WITH_SUCH_ID_NOT_FOUND("Error: customer with id %id% not found in the database"),
    CUSTOMER_WITH_SUCH_NAME_NOT_FOUND("Error: customer with name %name% not found in the database");

    private final String userMessage;

    UserMessages(String userMessage) {
        this.userMessage = userMessage;
    }
}
