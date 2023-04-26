package com.kafka.demodb.service;

import com.kafka.demodb.BaseDbTest;
import com.kafka.demodb.model.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase
public class CheckPinTest extends BaseDbTest {

    @Test
    void checkPinTestOK(){

        UserRequest request = new UserRequest();
        request.setEmail("pin@mail.it");
        request.setPsw("psw123");
        request.setUsername("userPin");

        var resp = registerUser.registerUser(request);

        assert resp.getResult().equals("OK-00");

        var checkPin = checkPinService.checkPinUser(request);

        assert checkPin.getResult().equals("OK-00");
    }

    @Test
    void checkPinTestKO(){

        UserRequest request = new UserRequest();
        request.setEmail("pinKo@mail.it");
        request.setPsw("psw123");
        request.setUsername("userPinKo");

        var resp = registerUser.registerUser(request);

        var userKey = userCrudService.getUser("pinKo@mail.it","").getUser().getUserKey();
        assert resp.getResult().equals("OK-00");

        request.setPsw("ajeje");

        var checkPin = checkPinService.checkPinUser(request);

        assert checkPin.getResult().equals("ERKO-05");
        assert secCounterCrudService.getCounter(userKey).getPswCounter() == 1;
    }
}
