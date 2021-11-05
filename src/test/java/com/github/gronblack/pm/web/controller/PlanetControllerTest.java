package com.github.gronblack.pm.web.controller;

import com.github.gronblack.pm.model.Planet;
import com.github.gronblack.pm.repository.PlanetRepository;
import com.github.gronblack.pm.util.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.gronblack.pm.web.testdata.CommonTD.*;
import static com.github.gronblack.pm.web.testdata.PlanetTD.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlanetControllerTest extends BaseControllerTest {
    private static final String REST_URL = PlanetController.REST_URL + '/';

    @Autowired
    private PlanetRepository repository;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("pageSize", String.valueOf(14)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_TO.contentJson(planetTos));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + planet2.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(WITH_LORD_MATCHER.contentJson(planet2FullTo));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void createWithLocation() throws Exception {
        Planet newPlanet = new Planet(null, "New Planet", 56236);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newPlanet)))
                .andExpect(status().isCreated());
        Planet created = MATCHER.readFromJson(action);
        int newId = created.getId();
        newPlanet.setId(newId);
        MATCHER.assertMatch(created, newPlanet);
        MATCHER.assertMatch(repository.getById(newId), newPlanet);
    }

    @Test
    void createInvalid() throws Exception {
        Planet invalid = new Planet(null, "", 100569);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Planet updated = new Planet(EXIST_ID, "Updatable Planet", 56987);
        perform(MockMvcRequestBuilders.put(REST_URL + EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MATCHER.assertMatch(repository.getById(EXIST_ID), updated);
    }

    @Test
    void updateInvalid() throws Exception {
        Planet invalid = new Planet(EXIST_ID, "", 0);
        perform(MockMvcRequestBuilders.put(REST_URL + EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        Planet updated = new Planet(EXIST_ID, "<script>alert(123)</script>", 15836);
        perform(MockMvcRequestBuilders.put(REST_URL + EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + planet10.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(planet10.getId()).isPresent());
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}