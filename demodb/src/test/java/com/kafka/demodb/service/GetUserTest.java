package com.kafka.demodb.service;

import com.kafka.demodb.BaseDbTest;
import com.kafka.demodb.model.entity.UserAccount;
import com.kafka.demodb.model.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase
public class GetUserTest extends BaseDbTest {

    @Test
    void getUserTestOK(){

        UserAccount userAcc = new UserAccount();
        userAcc.setEmail("getemail@mail.it");
        userAcc.setPsw("getpsw123");
        userAcc.setUsername("getUserProva");
        userAcc.setUserKey("getUserKey");

        UserRequest request = new UserRequest();
        request.setUserKey("getUserKey");

        userAccRepo.save(userAcc);

        var resp = getUserService.getUser("","getUserKey");

        assert resp.getUser().getPsw().equals("getpsw123");

        var resp2 = getUserService.getUserFilter(request);

        assert  resp2.email().equals("getemail@mail.it");
        assert  resp2.username().equals("getUserProva");


    }
}
