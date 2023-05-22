package com.kafka.orc.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Action {

    REGISTER("register"),
    CONSET("consent"),
    CERTIFY("certify"),
    SENDOTP("sendotp"),
    CHECKOTP("checkotp"),
    PERFORM("perform"),
    BANKCERTIFY("bankcertify"), // usata per fare sendOtp per certifica account
    BANKCERTIFIED("bankcerfied"); // usata per fare checkOtp per certifica account

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
