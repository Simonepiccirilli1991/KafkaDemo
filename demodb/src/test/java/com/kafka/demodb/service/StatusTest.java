package com.kafka.demodb.service;

import com.kafka.demodb.BaseDbTest;
import com.kafka.demodb.exception.CustomError;
import com.kafka.demodb.model.entity.UserAccount;
import com.kafka.demodb.model.entity.UserSecurity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class StatusTest extends BaseDbTest {

    @Test
    void statusCertifiedTestOK(){

        UserSecurity userSec = new UserSecurity();
        userSec.setEmailCertified(true);
        userSec.setLastPsw("1234");
        userSec.setUserKey("userKeyStatus");

        userSecRepo.save(userSec);

        UserAccount user = new UserAccount();
        user.setPsw("1234");
        user.setUsername("usernameStatus");
        user.setUserKey("userKeyStatus");
        user.setEmail("mailStatus");
        userCrudService.insertUser(user);

        var resp = statusService.getUserStatus("mailStatus","userKeyStatus");
        assert Boolean.TRUE.equals(resp.certified());
        assert Boolean.TRUE.equals(resp.registered());
    }

    @Test
    void statusRegisteredTestOK(){

        UserAccount user = new UserAccount();
        user.setPsw("1234");
        user.setUsername("usernameStatusRegister");
        user.setUserKey("userKeyStatusRegister");
        user.setEmail("mailStatusRegister");

        userCrudService.insertUser(user);

        var resp = statusService.getUserStatus("mailStatusRegister","userKeyStatusRegister");
        assert Boolean.FALSE.equals(resp.certified());
        assert Boolean.TRUE.equals(resp.registered());
    }

    @Test
    void statusAbsentTestOK(){

        var resp = statusService.getUserStatus("mailAbsent","userAbsent");

        assert Boolean.FALSE.equals(resp.certified());
        assert Boolean.FALSE.equals(resp.registered());

    }

    @Test
    void statusTestKO() {

        CustomError error = assertThrows(CustomError.class, () -> {
            statusService.getUserStatus("", "");
        });

        assert  error.getErrTp().equals("Invalid_Request");
        assert  error.getErrMsg().equals("Invalid requst, missing parameter");
        assert  error.getStatus().is4xxClientError();

    }

}
