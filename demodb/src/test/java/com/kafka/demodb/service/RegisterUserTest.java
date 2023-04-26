package com.kafka.demodb.service;

import com.kafka.demodb.BaseDbTest;
import com.kafka.demodb.model.entity.UserFinalncial;
import com.kafka.demodb.model.request.UserRequest;
import com.kafka.demodb.repo.UserFinancialRepo;
import com.kafka.demodb.service.internal.UserCrudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase
public class RegisterUserTest extends BaseDbTest {

    @Test
    void registerUserTestOK() {

        UserRequest request = new UserRequest();
        request.setEmail("email@mail.it");
        request.setPsw("psw123");
        request.setUsername("userProva");

        var resp = registerUser.registerUser(request);

        assert resp.getResult().equals("OK-00");

        assert userCrudService.getUser("email@mail.it","").getUser().getPsw().equals("psw123");

    }

    @Test
    void repoTEstOK(){
        UserFinalncial finalncial = new UserFinalncial();
        finalncial.setUserKey("123454");
        finalncial.setAccountBalance(1444.00);
        finalncial.setUsername("username");
        finalncial.setEmail("email");

        var resp = userFinancialRepo.save(finalncial);

        System.out.println(resp);

    }
}
