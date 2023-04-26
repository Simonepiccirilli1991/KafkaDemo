package com.kafka.demodb.model.response;

import com.kafka.demodb.model.entity.User;
import lombok.Data;

@Data
public class GetUserResponse extends BaseDbResponse {

    private User user;
}
