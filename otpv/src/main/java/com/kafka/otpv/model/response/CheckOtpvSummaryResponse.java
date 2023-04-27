package com.kafka.otpv.model.response;

import lombok.Data;

@Data
public record CheckOtpvSummaryResponse(String result, String responseMsg) {

}
