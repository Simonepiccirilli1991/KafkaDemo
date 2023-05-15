package com.kafka.orc.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Action {

    CONSET("consent"),
    CERTIFY("certify"),
    SENDOTP("sendotp"),
    CHECKOTP("checkotp");

    private final String value;

    Action(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @JsonValue
    public String value() {
        return value;
    }

}
