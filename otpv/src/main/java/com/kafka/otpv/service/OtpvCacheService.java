package com.kafka.otpv.service;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.kafka.otpv.configuration.OtpvHazelConfig;
import com.kafka.otpv.model.OtpvDto;
import org.springframework.stereotype.Service;

import static com.kafka.otpv.configuration.OtpvHazelConfig.OTPS;

@Service
public class OtpvCacheService {

    HazelcastInstance hazelcastInstance
            = Hazelcast.getHazelcastInstanceByName("otphaze");

    public OtpvDto put(String number, OtpvDto dto){
        IMap<String, OtpvDto> map = hazelcastInstance.getMap(OTPS);
        return map.put(number, dto);
    }

    public OtpvDto get(String key){
        IMap<String, OtpvDto> map = hazelcastInstance.getMap(OTPS);
        return map.get(key);
    }

    public String insert(String key, OtpvDto dto){
        IMap<String, OtpvDto> map = hazelcastInstance.getMap(OTPS);
        map.putIfAbsent(key, dto);
        return key;
    }
}
