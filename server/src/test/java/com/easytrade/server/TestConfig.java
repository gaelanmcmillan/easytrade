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
}
