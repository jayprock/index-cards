package com.bitbus.indexcards.tag;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

//@formatter:off
@RunWith(SpringRunner.class)
@WebMvcTest(StudyGuideTagController.class)
public class StudyGuideTagControllerTest {

    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private StudyGuideTagService studyGuideTagService;
    
    // Accessible to the public
    @Test
    public void testSearchByName() throws Exception {
        when(studyGuideTagService.searchByName(anyString())).thenReturn(new ArrayList<>());
        mvc.perform(
                get("/api/categories?search=blah")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
