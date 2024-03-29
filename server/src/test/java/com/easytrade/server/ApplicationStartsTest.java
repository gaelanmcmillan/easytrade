package com.easytrade.server;

import com.easytrade.server.controller.AuthenticationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationStartsTest {

    @Autowired
    private AuthenticationController authenticationController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(authenticationController).isNotNull();
    }
}
