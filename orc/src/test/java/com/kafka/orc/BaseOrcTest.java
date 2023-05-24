package com.kafka.orc;

import com.kafka.orc.client.*;
import com.kafka.orc.fragment.bankacc.BankUserService;
import com.kafka.orc.fragment.usersic.UserService;
import com.kafka.orc.fragment.usersic.UserSicService;
import com.kafka.orc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class BaseOrcTest {

    @Autowired
    protected GetBalanceService getBalanceService;
    @Autowired
    protected BankAccStatusService bankAccStatusService;
    @Autowired
    protected ValidationService validationService;
    @Autowired
    protected GenerateOtpService generateOtpService;
    @Autowired
    protected StatusService statusService;
    @Autowired
    protected LoginService loginService;
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
    @MockBean
    protected CacheWebClient cacheWebClient;
    @MockBean
    protected OtpvWebClient otpvWebClient;
}
