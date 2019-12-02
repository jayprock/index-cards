package com.bitbus.indexcards.studyguide;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import com.bitbus.indexcards.BaseSecuredControllerTest;
import com.bitbus.indexcards.card.FlashCardDto;
import com.bitbus.indexcards.card.IndexCard;
import com.bitbus.indexcards.tag.StudyGuideTag;
import com.bitbus.indexcards.user.User;
import com.bitbus.indexcards.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

//@formatter:off
@WebMvcTest(StudyGuideController.class)
public class StudyGuideControllerTest extends BaseSecuredControllerTest {

    @MockBean
    private StudyGuideService studyGuideService;
    @MockBean
    private UserService userService;

    @SuppressWarnings("unchecked")
    @WithMockUser("user")
    @Test
    public void testCreateStudyGuide_noCsrf_403() throws Exception {
        when(studyGuideService.create(any(StudyGuide.class), any(List.class))).thenReturn(dummyStudyGuide());
        
        mvc.perform(
                post("/api/studyguides")
                .content(studyGuideDtoAsJson())
                .contentType(MediaType.APPLICATION_JSON))//
            .andExpect(status().isForbidden());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateStudyGuide_unauthorized_401() throws Exception {
        when(studyGuideService.create(any(StudyGuide.class), any(List.class))).thenReturn(dummyStudyGuide());
        
        // Note: Removed the @WithMockUser annotation
        mvc.perform(
                post("/api/studyguides")
                .content(studyGuideDtoAsJson())
                .contentType(MediaType.APPLICATION_JSON)//
                .with(csrf()))
            .andExpect(status().isUnauthorized());
    }
    
    @SuppressWarnings("unchecked")
    @WithMockUser("user")
    @Test
    public void testCreateStudyGuide_valid_200() throws Exception {
        when(userService.findByLogin(anyString())).thenReturn(new User(1));
        when(studyGuideService.create(any(StudyGuide.class), any(List.class))).thenReturn(dummyStudyGuide());
        mvc.perform(
                post("/api/studyguides")
                .contentType(MediaType.APPLICATION_JSON)//
                .content(studyGuideDtoAsJson())
                .with(csrf()))
            .andExpect(status().isOk());
    }
    
    // Accessble to the public
    @Test
    public void testFindStudyGuide() throws Exception {
        when(studyGuideService.findWithAllChildren(anyLong())).thenReturn(dummyStudyGuide());
        mvc.perform(
                get("/api/studyguides/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    
    // Accessble to the public
    @Test
    public void testSearchStudyGuide() throws Exception {
        when(studyGuideService.search(anyString())).thenReturn(new ArrayList<>());
        mvc.perform(
                get("/api/studyguides?search=math")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    private String studyGuideDtoAsJson() throws Exception {
        StudyGuideDto dto = new StudyGuideDto();
        dto.setStudyGuideName("Test");
        dto.setDescription("My test study guide");
        dto.setCategories(Arrays.asList("blah-blah"));
        dto.setFlashCards(new ArrayList<>());

        FlashCardDto flashCardDto = new FlashCardDto();
        flashCardDto.setBack("back");
        flashCardDto.setFront("front");
        dto.getFlashCards().add(flashCardDto);

        return new ObjectMapper().writeValueAsString(dto);
    }

    private StudyGuide dummyStudyGuide() {
        StudyGuide studyGuide = new StudyGuide();
        studyGuide.setName("Test");
        studyGuide.setDescription("My test study guide");
        studyGuide.setIndexCards(new ArrayList<>());
        studyGuide.setTags(new ArrayList<>());

        IndexCard card = new IndexCard();
        card.setStudyGuide(studyGuide);
        card.setFront("front");
        card.setBack("back");
        studyGuide.getIndexCards().add(card);

        StudyGuideTag tag = new StudyGuideTag();
        tag.setName("aaa");
        tag.setStudyGuides(Arrays.asList(studyGuide));
        studyGuide.getTags().add(tag);

        return studyGuide;
    }
}
