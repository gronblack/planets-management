package com.github.gronblack.pm.web.controller;

import com.github.gronblack.pm.model.Lord;
import com.github.gronblack.pm.model.Planet;
import com.github.gronblack.pm.repository.LordRepository;
import com.github.gronblack.pm.repository.PlanetRepository;
import com.github.gronblack.pm.to.PlanetTo;
import com.github.gronblack.pm.util.json.JsonUtil;
import com.github.gronblack.pm.web.testdata.LordTD;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.gronblack.pm.web.testdata.CommonTD.*;
import static com.github.gronblack.pm.web.testdata.LordTD.lord10;
import static com.github.gronblack.pm.web.testdata.PlanetTD.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlanetControllerTest extends BaseControllerTest {
    private static final String REST_URL = PlanetController.REST_URL + '/';

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private LordRepository lordRepository;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("size", String.valueOf(14)))
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
        PlanetTo newTo = new PlanetTo(null, "New Planet", null);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andExpect(status().isCreated());
        Planet created = MATCHER.readFromJson(action);
        int newId = created.getId();
        newTo.setId(newId);

        Planet newPlanet = new Planet(newTo.getId(), newTo.getName());
        MATCHER.assertMatch(created, newPlanet);
        MATCHER.assertMatch(planetRepository.getById(newId), newPlanet);
    }

    @Test
    void createInvalid() throws Exception {
        PlanetTo invalid = new PlanetTo(null, "", null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        PlanetTo updatedTo = new PlanetTo(EXIST_ID, "Updatable Planet", null);
        perform(MockMvcRequestBuilders.put(REST_URL + EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Planet updatedPlanet = new Planet(updatedTo.getId(), updatedTo.getName());
        MATCHER.assertMatch(planetRepository.getById(EXIST_ID), updatedPlanet);
    }

    @Test
    void updateInvalid() throws Exception {
        PlanetTo invalid = new PlanetTo(EXIST_ID, "", null);
        perform(MockMvcRequestBuilders.put(REST_URL + EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        PlanetTo updated = new PlanetTo(EXIST_ID, "<script>alert(123)</script>", null);
        perform(MockMvcRequestBuilders.put(REST_URL + EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void setLord() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + planet14.getId())
                .param("lordId", String.valueOf(lord10.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        Lord lord = lordRepository.getWithPlanets(lord10.getId()).orElseThrow();
        Planet planet = planetRepository.getById(planet14.getId());
        LordTD.MATCHER.assertMatch(planet.getLord(), lord);
        assertTrue(lord.getPlanets().contains(planet));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + planet10.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(planetRepository.findById(planet10.getId()).isPresent());
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}