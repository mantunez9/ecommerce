package com.onebox.ecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("TEST")
@SpringBootTest(classes = EcommerceApplication.class)
@AutoConfigureMockMvc
@SuppressWarnings("")
public abstract class BaseTest {

    @Autowired protected MockMvc mockMvc;
    @Autowired protected WebApplicationContext webApplicationContext;

    public static final String SQL_SCHEMA = "classpath:/sql/schema-h2.sql";

    protected Resource jsonResource(final String name) {
        return new ClassPathResource("/json/" + name + ".json");
    }

    protected ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mapper = new ObjectMapper();
    }

}
