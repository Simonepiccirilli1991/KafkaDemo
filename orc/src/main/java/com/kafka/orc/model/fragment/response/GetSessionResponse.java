package com.kafka.orc.model.fragment.response;

import com.kafka.orc.model.fragment.SicSession;
import lombok.Data;

@Data
public class GetSessionResponse {

    private SicSession session;
}
