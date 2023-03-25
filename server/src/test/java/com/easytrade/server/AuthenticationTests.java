package com.easytrade.server;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void signupShouldReturnHttpOk() throws Exception {
        String signupBodyJson = """
                {
                    "firstName":"John",
                    "lastName":"Smith",
                    "username":"johnsmith",
                    "password":"1234"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(signupBodyJson))
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

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(firstSignupBodyJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(duplicateSignupBodyJson))
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

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(signupBodyJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(loginBodyJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("william_tell"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(Matchers.notNullValue()));
    }
}
