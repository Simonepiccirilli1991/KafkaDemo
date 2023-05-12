package com.kafka.cache.service;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.kafka.cache.error.SessionError;
import com.kafka.cache.model.SicSession;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static com.kafka.cache.config.SessionHazelConfig.SESSIONS;

@Service
public class SessionCacheService {

    HazelcastInstance hazelcastInstance
            = Hazelcast.getHazelcastInstanceByName("sessionhaze");

    public SicSession put(String number, SicSession dto){
        IMap<String, SicSession> map = hazelcastInstance.getMap(SESSIONS);
        return map.put(number, dto);
    }

    public SicSession get(String key){
        IMap<String, SicSession> map = hazelcastInstance.getMap(SESSIONS);
        return map.get(key);
    }

    public String insert(String key, SicSession dto){

        if(ObjectUtils.isEmpty(dto.getUserKey()))
            throw new SessionError("Invalid_Request", "Body Request is empty");

        IMap<String, SicSession> map = hazelcastInstance.getMap(SESSIONS);
        map.putIfAbsent(key, dto);
        return key;
    }

    public void delete(String key, SicSession dto){
        IMap<String, SicSession> map = hazelcastInstance.getMap(SESSIONS);
        map.delete(dto);
    }
}
