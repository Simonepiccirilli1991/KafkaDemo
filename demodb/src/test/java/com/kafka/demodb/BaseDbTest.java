package com.kafka.demodb;

import com.kafka.demodb.repo.UserAccRepo;
import com.kafka.demodb.repo.UserFinancialRepo;
import com.kafka.demodb.service.GetUserService;
import com.kafka.demodb.service.RegisterUserService;
import com.kafka.demodb.service.internal.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;

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
    protected UserAccRepo userAccRepo;

}
