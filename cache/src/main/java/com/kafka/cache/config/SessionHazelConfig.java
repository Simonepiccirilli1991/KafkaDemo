package com.kafka.cache.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionHazelConfig {

    public static final String SESSIONS = "sessions";
    protected final HazelcastInstance hazelcastInstance
            = Hazelcast.newHazelcastInstance(createConfigSession());

    public Config createConfigSession() {
        Config config = new Config();
        config.setInstanceName("sessionhaze");
        config.addMapConfig(mapConfig());
        return config;
    }

    private MapConfig mapConfig() {
        MapConfig mapConfig = new MapConfig(SESSIONS);
        mapConfig.setTimeToLiveSeconds(600);
        mapConfig.setMaxIdleSeconds(590);
        mapConfig.setPerEntryStatsEnabled(true);
        return mapConfig;
    }
}
