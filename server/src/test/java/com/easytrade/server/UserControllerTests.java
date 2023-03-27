package com.easytrade.server;

import com.easytrade.server.config.EasyTradeProperties;
import com.jayway.jsonpath.JsonPath;
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
public class UserControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    EasyTradeProperties easyTradeProperties;

    private final String API_PREFIX = "/api/v1/user";

    @Test
    public void getPortfolioShouldReturnUsernameAndListOfHoldings() throws Exception {
        String signupBodyJson = """
                {
                    "firstName":"A",
                    "lastName":"Buyer",
                    "username":"ab",
                    "password":"123"
                }
                """;

        String dummyUsername = "ab";

        var signupRequest = MockMvcRequestBuilders.post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(signupBodyJson);

        var signupJson = mockMvc.perform(signupRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        String token = JsonPath.read(signupJson, "$.token");

        String buyRequestBodyJson = """
        {
            "symbol": "META",
            "quantity": 5
        }
        """;

        var buyStockRequest = MockMvcRequestBuilders.patch("/api/v1/stock/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(buyRequestBodyJson);

        mockMvc.perform(buyStockRequest)
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        var portfolioRequest = MockMvcRequestBuilders.get(API_PREFIX + "/portfolio/" + dummyUsername);

        mockMvc.perform(portfolioRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(dummyUsername))
                .andExpect(MockMvcResultMatchers.jsonPath("$.investments", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.investments[0].stock.symbol").value("META"));
    }
}