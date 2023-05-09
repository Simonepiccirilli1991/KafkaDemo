package com.kafka.orc;

import com.kafka.orc.client.BankAccWebClient;
import com.kafka.orc.client.SicWebClient;
import com.kafka.orc.client.UserWebClient;
import com.kafka.orc.fragment.bankacc.BankUserService;
import com.kafka.orc.fragment.usersic.UserService;
import com.kafka.orc.fragment.usersic.UserSicService;
import com.kafka.orc.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class BaseOrcTest {

    @Autowired
    protected RegistrationService registrationService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected UserSicService userSicService;
    @Autowired
    protected BankUserService bankUserService;
    @MockBean
    protected BankAccWebClient bankAccWebClient;
    @MockBean
    protected UserWebClient userWebClient;
    @MockBean
    protected SicWebClient sicWebClient;
}
