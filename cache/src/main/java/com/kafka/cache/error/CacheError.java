package com.kafka.cache.error;


import java.time.LocalDateTime;

public record CacheError(String errFrom, String errMsg, String errCause, LocalDateTime time) {}
