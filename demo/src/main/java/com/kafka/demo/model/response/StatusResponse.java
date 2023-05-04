package com.kafka.demo.model.response;

import com.kafka.demo.service.internal.BankAccCrudService;
import com.kafka.demo.service.internal.BankAccSicCrudService;
import lombok.Data;

@Data
public class StatusResponse {


    private Boolean notPresent;
    private BankAccCrudService.GetBankAccSummaryFilter bankAccInfo;
    private BankAccSicCrudService.BankAccSicFilter bankAccSicInfo;

    public StatusResponse(Boolean notPresent) {
        this.notPresent = notPresent;
    }

    public StatusResponse(Boolean notPresent, BankAccCrudService.GetBankAccSummaryFilter bankAccInfo, BankAccSicCrudService.BankAccSicFilter bankAccSicInfo) {
        this.notPresent = notPresent;
        this.bankAccInfo = bankAccInfo;
        this.bankAccSicInfo = bankAccSicInfo;
    }
}
