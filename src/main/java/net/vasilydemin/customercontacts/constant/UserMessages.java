package net.vasilydemin.customercontacts.constant;

import lombok.Getter;

@Getter
public enum UserMessages {

    CUSTOMER_WITH_SUCH_ID_NOT_FOUND("Error: customer with id %id% not found in the database"),
    CUSTOMER_WITH_SUCH_NAME_NOT_FOUND("Error: customer with name \"%name%\" not found in the database"),
    EMAIL_WITH_SUCH_ID_NOT_FOUND("Error: email with id %id% not found in the database"),
    SUCH_EMAIL_NOT_FOUND("Error: Such email %email% not found in the database"),
    CUSTOMER_ID_MUST_NOT_BE_NULL("Error: Customer id mustn't be null"),
    EMAIL_ADDRESS_IS_IN_THE_DATABASE_ALREADY1("Error: email %email% is in the database already and owned by another customer"),
    PHONE_NUMBER_IS_IN_THE_DATABASE_ALREADY1("Error: phone number %phone% is in the database already and owned by another customer"),
    PHONE_WITH_SUCH_ID_NOT_FOUND("Error: email with id %id% not found in the database"),
    CONTACT_TYPE_IS_WRONG("Error: Contact type %type% is wrong. Contact type should be email or phone");

    private final String userMessage;

    UserMessages(String userMessage) {
        this.userMessage = userMessage;
    }
}
