package com.depaul.edu.se491.service;

import com.depaul.edu.se491.service.email.EmailService;
import com.socrata.exceptions.LongRunningQueryException;
import com.socrata.exceptions.SodaError;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/applicationContext-Test.xml")
public class EmailServiceTest {

    @Autowired
    EmailService emailService;

    @Test
    public void testQueryService() throws LongRunningQueryException, SodaError, InterruptedException {
        try {
            emailService.sendEmail("testuser@gmail.com", "testing");
        } catch(Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }


}
