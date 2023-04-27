package com.kafka.otpv.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GenerateKey {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private static final String DEFAULT_ABI = "99999";


    public String generateTrxid(String userKey){

        LocalDateTime timestampDate = LocalDateTime.now();
        String timestampFormatted = timestampDate.format(DATE_FORMATTER);

        String chiave =  DEFAULT_ABI  + userKey + timestampFormatted;

        return String.valueOf(chiave.hashCode());
    }
}
