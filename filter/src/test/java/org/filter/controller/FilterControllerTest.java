package org.filter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ValidationException;
import org.filter.dto.CriteriaDto;
import org.filter.dto.FilterDto;
import org.filter.service.FilterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilterController.class)
class FilterControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private FilterServiceImpl filterService;
    private ObjectMapper objectMapper;
    private FilterDto correctFilterDto;
    private FilterDto incorrectFilterDto;
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        objectMapper = new ObjectMapper();
        CriteriaDto criteriaDto = new CriteriaDto("AMOUNT", "More", "4");
        correctFilterDto = new FilterDto("Test", List.of(criteriaDto));
        incorrectFilterDto = new FilterDto("", List.of(criteriaDto));
    }
    @Test
    void Should_CreateFilter_When_CorrectInput() throws Exception {
        doNothing().when(filterService).createNewFilter(correctFilterDto);

        mockMvc.perform(post("/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(correctFilterDto)))
                .andExpect(status().isOk());
        then(filterService).should().createNewFilter(correctFilterDto);
    }
    @Test
    void Should_ThrowException_When_IncorrectInput() throws Exception {
        doThrow(ValidationException.class).when(filterService).createNewFilter(incorrectFilterDto);

        mockMvc.perform(post("/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incorrectFilterDto)))
                .andExpect(status().is4xxClientError());
        then(filterService).should().createNewFilter(incorrectFilterDto);
    }
    @Test
    void Should_ReturnFilters_When_SingleFilter() throws Exception {
        when(filterService.readAllFilters()).thenReturn(List.of(correctFilterDto));

        MvcResult result = mockMvc.perform(get("/filter")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        then(filterService).should().readAllFilters();
        assertEquals(objectMapper.writeValueAsString(List.of(correctFilterDto)), responseBody);
    }
}