package com.kafka.otpv.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtpvHazelConfig {

    public static final String OTPS = "otps";
    protected final HazelcastInstance hazelcastInstance
            = Hazelcast.newHazelcastInstance(createConfigOtp());

    public Config createConfigOtp() {
        Config config = new Config();
        config.addMapConfig(mapConfig());
        return config;
    }

    private MapConfig mapConfig() {
        MapConfig mapConfig = new MapConfig(OTPS);
        mapConfig.setTimeToLiveSeconds(360);
        mapConfig.setMaxIdleSeconds(120);
        return mapConfig;
    }
}
