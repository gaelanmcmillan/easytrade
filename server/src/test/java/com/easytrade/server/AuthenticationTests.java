package com.easytrade.server;

import lombok.RequiredArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void signupShouldReturnHttpOk() throws Exception {
        String signupBodyJson = TestConfig.dummyUserSignupJson;

        var signupRequest = MockMvcRequestBuilders.post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(signupBodyJson);

        mockMvc.perform(signupRequest)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void duplicateUserNameShouldReturnBadRequest() throws Exception {
        String firstSignupBodyJson = """
                {
                    "firstName":"Jane",
                    "lastName":"Doe",
                    "username":"jdoe",
                    "password":"12345678"
                }
                """;

        String duplicateSignupBodyJson = """
                {
                    "firstName":"John",
                    "lastName":"Does",
                    "username":"jdoe",
                    "password":"password"
                }
                """;

        var initialRequest = MockMvcRequestBuilders.post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(firstSignupBodyJson);

        var duplicateRequest = MockMvcRequestBuilders.post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(duplicateSignupBodyJson);

        mockMvc.perform(initialRequest)
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(duplicateRequest)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void signupThenLoginReturnsOkAndToken() throws Exception {
        String signupBodyJson = """
                {
                    "firstName":"William",
                    "lastName":"Tell",
                    "username":"william_tell",
                    "password":"lalala"
                }
                """;
        String loginBodyJson = """
                {
                    "username":"william_tell",
                    "password":"lalala"
                }
                """;

        var signupRequest = MockMvcRequestBuilders.post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(signupBodyJson);

        var loginRequest = MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBodyJson);

        mockMvc.perform(signupRequest)
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(loginRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("william_tell"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(Matchers.notNullValue()));
    }
}
