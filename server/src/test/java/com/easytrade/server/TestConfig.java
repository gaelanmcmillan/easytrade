package com.easytrade.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * This class allows our unit tests to use a separate database,
 * meaning we can destroy all test data from the test database each time we run our tests,
 * resulting in a clean slate for every run of the test suite.
 * */
@Configuration
@Profile("test")
@PropertySource("classpath:application-test.yml")
public class TestConfig {
    public static String dummyUsername = "johnsmith";
    public static String dummyUserSignupJson = """
                {
                    "firstName":"John",
                    "lastName":"Smith",
                    "username":"johnsmith",
                    "password":"1234"
                }
                """;

    public static String dummyUserLoginJson = """
                {
                    "username":"johnsmith",
                    "password":"1234"
                }
                """;
}
