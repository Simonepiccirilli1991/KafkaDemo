package com.kafka.orc.model.response;

import com.kafka.orc.constants.Action;

public record LoginResponse (String userKey, Action action){}
