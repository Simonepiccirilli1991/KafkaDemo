package com.kafka.orc.service;

import com.kafka.orc.BaseOrcTest;
import com.kafka.orc.constants.Action;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.response.StatusSicResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StatusServiceTest extends BaseOrcTest {


    @Test
    void statusServiceTestOK(){


        StatusSicResponse statusSicResponse = new StatusSicResponse();
        statusSicResponse.setResponse("OK-00");
        statusSicResponse.setRegistered(true);
        statusSicResponse.setCertified(false);

        Mockito.when(userWebClient.statusSic(Mockito.any())).thenReturn(statusSicResponse);

        var resp = statusService.checkStatus("user");

        assert resp.status().equals("not certified");
        assert resp.action().equals(Action.CERTIFY);
    }

    @Test
    void statusServiceTestKO(){

        StatusSicResponse statusSicResponse = new StatusSicResponse();
        statusSicResponse.setResponse("KO");
        statusSicResponse.setRegistered(true);
        statusSicResponse.setCertified(false);

        Mockito.when(userWebClient.statusSic(Mockito.any())).thenReturn(statusSicResponse);

        OrcError ex = Assertions.assertThrows(OrcError.class, () -> {
            statusService.checkStatus("user");
        });

        assert ex.getCaused().equals("Error_StatusUser");
        assert ex.getMsg().equals("Erro on get status user");
        assert ex.getErrId().equals("UserCheckStatusKO-02");
    }
}
