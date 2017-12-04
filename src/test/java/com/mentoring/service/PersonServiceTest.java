package com.mentoring.service;

import com.mentoring.controller.PersonController;
import com.mentoring.model.dto.Winner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonServiceTest {
    private MockMvc mockMvc;

    @Mock
    private  PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeAll
    public  void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(personController)
                .build();
    }

    @Test
    public void testWinner() throws Exception {
        Winner winner = new Winner(1, "winner");
                when(personService.getWinner()).thenReturn(winner);
        mockMvc.perform(get("/winner"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect((ResultMatcher) content().string(contains("winner")))
                .andExpect((ResultMatcher) content().string(contains("1")));
        verify(personService, times(1)).getWinner();
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {
        when(personService.getWinner()).thenReturn(null);
        mockMvc.perform(get("/looser"))
                .andExpect(status().isNotFound());
        verifyNoMoreInteractions(personService);
    }
}
