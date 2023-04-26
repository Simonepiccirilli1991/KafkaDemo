package com.kafka.demodb.model.response;

import com.kafka.demodb.model.entity.UserAccount;
import lombok.Data;

@Data
public class GetUserResponse extends BaseDbResponse {

    private UserAccount user;
}
