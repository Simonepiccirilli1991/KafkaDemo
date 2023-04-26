package com.kafka.demodb.service.internal;

import com.kafka.demodb.model.entity.SecurityCounter;
import com.kafka.demodb.repo.SecCounterRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SecCounterCrudService {

    private final SecCounterRepo secCounterRepo;

    public SecCounterCrudService(SecCounterRepo secCounterRepo) {
        this.secCounterRepo = secCounterRepo;
    }

    public void createSecuretyCounter(String userKey){

        SecurityCounter counter = new SecurityCounter();

        counter.setEmailCounter(0);
        counter.setPswCounter(0);
        counter.setUserKey(userKey);
        secCounterRepo.save(counter);
    }

    public SecurityCounter getCounter(String userKey){

        return secCounterRepo.findByUserKey(userKey);
    }

    public void updateCounterPsw(String userKey){
        var counter = secCounterRepo.findByUserKey(userKey);
        var increment = counter.getPswCounter() + 1;

        if(increment >= 3)
            counter.setLastPswBlock(LocalDateTime.now());

        counter.setPswCounter(increment);
        secCounterRepo.save(counter);
    }

    public void updateCounterMail(String userKey){

        var counter = secCounterRepo.findByUserKey(userKey);
        var increment = counter.getEmailCounter() + 1;

        if(increment >= 3)
            counter.setLastEmailBlock(LocalDateTime.now());

        counter.setEmailCounter(increment);
        secCounterRepo.save(counter);

    }

    public void resetCounterPsw(String userKey){
        var counter = secCounterRepo.findByUserKey(userKey);
        counter.setPswCounter(0);
        secCounterRepo.save(counter);
    }

    public void resetCounterEmail(String userKey){

        var counter = secCounterRepo.findByUserKey(userKey);
        counter.setEmailCounter(0);
        secCounterRepo.save(counter);
    }
}
