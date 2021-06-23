package com.mediscreen.notes.IT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) /*désactive tous les filtres dans la configuration SpringSecurity */
@PropertySource("classpath:application.properties")
public class NoteControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteService noteService;

    private Note noteTest;
    String textNoteTest="Le patient déclare n'avoir aucun problème";
    LocalDate dateNoteTest=LocalDate.of(2021,6,21);

    @BeforeEach
    public void setUpEach(){
        noteTest=new Note("1",textNoteTest,1,dateNoteTest);
    }

    @Test
    public void listNotes() throws Exception {

        //GIVEN WHEN THEN
        mockMvc.perform(get("/patHistory/list"))
                .andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    public void addCorrectNote() throws Exception {

        List<Note> noteBeforeAdd;
        List<Note> noteAfterAdd;

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonUser = obm.createObjectNode();

        //GIVEN
        /*jsonUser.set("id", TextNode.valueOf(noteTest.getId()));*/
        jsonUser.set("textNote", TextNode.valueOf(noteTest.getTextNote()));
        jsonUser.set("patientId", TextNode.valueOf(String.valueOf(noteTest.getPatientId())));
        jsonUser.set("dateNote", TextNode.valueOf(String.valueOf(noteTest.getDateNote())));

        noteBeforeAdd = noteService.findAll();

        // WHEN
        // THEN
        mockMvc.perform(post("/patHistory/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        noteAfterAdd = noteService.findAll();
        assertEquals(noteAfterAdd.size(),noteBeforeAdd.size()+1);
    }

}
