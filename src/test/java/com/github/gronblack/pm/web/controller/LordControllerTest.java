package com.github.gronblack.pm.web.controller;

import com.github.gronblack.pm.model.Lord;
import com.github.gronblack.pm.repository.LordRepository;
import com.github.gronblack.pm.to.LordTo;
import com.github.gronblack.pm.util.LordsUtil;
import com.github.gronblack.pm.util.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.github.gronblack.pm.web.testdata.CommonTD.*;
import static com.github.gronblack.pm.web.testdata.LordTD.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LordControllerTest extends BaseControllerTest {
    private static final String REST_URL = LordController.REST_URL + '/';

    @Autowired
    private LordRepository repository;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("size", String.valueOf(12)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_TO.contentJson(lordTos));
    }

    @Test
    void getWithPlanets() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + lord7.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(WITH_PLANETS_MATCHER.contentJson(lord7FullTo));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getIdle() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "idle"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_TO.contentJson(LordsUtil.getTos(List.of(lord11, lord10, lord12, lord9))));
    }

    @Test
    void getYoung() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "young"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_TO.contentJson(LordsUtil.getTos(List.of(lord8, lord4, lord6, lord9, lord12, lord10, lord1, lord11, lord2, lord5))));
    }

    @Test
    void createWithLocation() throws Exception {
        LordTo newTo = new LordTo(null, "New Lord", 30);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andExpect(status().isCreated());
        Lord created = MATCHER.readFromJson(action);
        int newId = created.getId();
        newTo.setId(newId);

        Lord newLord = new Lord(newTo.getId(), newTo.getName(), newTo.getAge());
        MATCHER.assertMatch(created, newLord);
        MATCHER.assertMatch(repository.getById(newId), newLord);
    }

    @Test
    void createInvalid() throws Exception {
        LordTo invalid = new LordTo(null, "", 0);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        LordTo updatedTo = new LordTo(EXIST_ID, "Updatable Lord", 100);
        perform(MockMvcRequestBuilders.put(REST_URL + EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Lord updatedLord = new Lord(updatedTo.getId(), updatedTo.getName(), updatedTo.getAge());
        MATCHER.assertMatch(repository.getById(EXIST_ID), updatedLord);
    }

    @Test
    void updateInvalid() throws Exception {
        LordTo invalid = new LordTo(EXIST_ID, "", 0);
        perform(MockMvcRequestBuilders.put(REST_URL + EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        LordTo updated = new LordTo(EXIST_ID, "<script>alert(123)</script>", 100);
        perform(MockMvcRequestBuilders.put(REST_URL + EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + lord12.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(lord12.getId()).isPresent());
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}