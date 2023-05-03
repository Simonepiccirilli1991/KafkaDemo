package com.kafka.demodb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kafka.demodb.client.OtpWebClient;
import com.kafka.demodb.repo.*;
import com.kafka.demodb.service.*;
import com.kafka.demodb.service.internal.SecCounterCrudService;
import com.kafka.demodb.service.internal.UserCrudService;
import com.kafka.demodb.service.internal.UserSecCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

public class BaseDbTest {

    @Autowired
    protected GetUserService getUserService;

    @Autowired
    protected RegisterUserService registerUser;

    @Autowired
    protected UserCrudService userCrudService;

    @Autowired
    protected UserFinancialRepo userFinancialRepo;
    @Autowired
    protected SecCounterRepo secCounterRepo;
    @Autowired
    protected UserSecRepo userSecRepo;
    @Autowired
    protected UserAccRepo userAccRepo;
    @Autowired
    protected UserSecCrudService userSecCrudService;
    @Autowired
    protected SecCounterCrudService secCounterCrudService;
    @Autowired
    protected CheckPinService checkPinService;
    @Autowired
    protected UpdateSecuretuService updateSecuretuService;
    @Autowired
    protected StatusService statusService;
    @Autowired
    protected ItemService itemService;
    @Autowired
    protected ItemRepository itemRepository;
    @MockBean
    protected OtpWebClient otpWebClient;
    protected ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

}
