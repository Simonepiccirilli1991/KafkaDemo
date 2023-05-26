package com.kafka.demoorc.model.fragment.response;

import com.kafka.demoorc.model.fragment.Item;
import lombok.Data;

@Data
public class GetItemResponse {

    private String result;
    private Item item;
    private String errMsg;
}
