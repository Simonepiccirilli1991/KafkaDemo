package com.kafka.orc.model.fragment.response;

import lombok.Data;

@Data
public class GenerateOtpvResponse {

    private String trxId;
    private String otp; // e integrato java mail sender su otpv, ma non ho voglia di fare tutto il giro e
    // riprendermi la mail quindi me lo hardcodo qua in response tanto sticazzi al momento
}
