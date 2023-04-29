package com.kafka.demodb.service.internal;

import com.kafka.demodb.model.entity.UserAccount;
import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.model.response.GetUserResponse;
import com.kafka.demodb.repo.UserAccRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class UserCrudService {

    @Autowired
    UserAccRepo userRepo;


    public BaseDbResponse insertUser(UserAccount user){

        var checkIfPresent = getUser(user.getEmail(), user.getUsername());

        if(!ObjectUtils.isEmpty(checkIfPresent.getUser()))
            return new BaseDbResponse("ERKO-00","User already registered", "Already_registered");

        try{
            userRepo.save(user);
        }catch(Exception e){
            return new BaseDbResponse("ERKO-01","Error in saving user", "Generic_Error");
        }

        return new BaseDbResponse("OK-00",user.getUserKey());
    }

    public BaseDbResponse updateUserEmail(String username, String email){

        var user = userRepo.findByUsername(username);

        if(user.isEmpty())
            return new BaseDbResponse("ERKO-02","Missing this usernameon db", "Generic_Error");
        try{

            user.get().setEmail(email);
            userRepo.save(user.get());
        }catch(Exception e){
            return new BaseDbResponse("ERKO-01","Error in updating mail user", "Generic_Error");
        }
        return new BaseDbResponse("OK-00");
    }

    public BaseDbResponse updateUserUsername(String email, String username){

        var user = userRepo.findByUsername(email);

        if(user.isEmpty())
            return new BaseDbResponse("ERKO-02","Missing this email on  db", "Generic_Error");
        try{

            user.get().setUsername(username);
            userRepo.save(user.get());
        }catch(Exception e){
            return new BaseDbResponse("ERKO-01","Error in updating username user", "Generic_Error");
        }
        return new BaseDbResponse("OK-00");
    }

    public BaseDbResponse updateUserPsw(String email, String username, String psw){

        var user = userRepo.findByEmail(email);

        if(user.isEmpty())
            return new BaseDbResponse("ERKO-02","Missing this email on  db", "Generic_Error");
        try{

            var upUser = user.get();
            upUser.setPsw(psw);

            userRepo.save(upUser);
        }catch(Exception e){
            return new BaseDbResponse("ERKO-01","Error in updating username user", "Generic_Error");
        }
        return new BaseDbResponse("OK-00");
    }

    public GetUserResponse getUser(String email, String userKey){

        GetUserResponse response = new GetUserResponse();

        var emailUser = userRepo.findByEmail(email);

        if(emailUser.isPresent()){
            response.setUser(emailUser.get());
            response.setResult("OK-00");
            return response;
        }

        var nameUser = userRepo.findByUserKey(userKey);

        if(ObjectUtils.isEmpty(nameUser)){
            response.setResult("ERKO-01");
            response.setErrMsg("Missing user on DB");
            response.setErrType("User not found");
            return response;
        }
        response.setUser(nameUser); response.setResult("OK-00");
        return response;
    }

    public GetUserResponse getUserByUsername(String userName){

        GetUserResponse response = new GetUserResponse();

        var nameUser = userRepo.findByUsername(userName);

        if(ObjectUtils.isEmpty(nameUser)){
            response.setResult("ERKO-01");
            response.setErrMsg("Missing user on DB");
            response.setErrType("User not found");
            return response;
        }
        response.setUser(nameUser.get()); response.setResult("OK-00");
        return response;
    }
}
