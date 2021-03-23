package com.onebox.ecommerce.controller;

import com.onebox.ecommerce.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {BaseTest.SQL_SCHEMA})
class CartControllerTest extends BaseTest {

    @Test
    void createCart() throws Exception {
        mockMvc.perform(post("/cart"))
                .andExpect(status().isOk());
    }

    @Test
    void addProducts() throws Exception {

        mockMvc.perform(put("/cart/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonResource("addProducts").getInputStream().readAllBytes()))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(post("/cart"))
                .andExpect(status().isOk());

        mockMvc.perform(put("/cart/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonResource("addProducts").getInputStream().readAllBytes()))
                .andExpect(status().isOk());
    }

    @Test
    void getCart() throws Exception {

        mockMvc.perform(get("/cart/1"))
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/cart"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/cart/1"))
                .andExpect(status().isOk());

    }

    @Test
    void deleteCart() throws Exception {

        mockMvc.perform(delete("/cart/1"))
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/cart"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/cart/1"))
                .andExpect(status().is2xxSuccessful());

    }

}