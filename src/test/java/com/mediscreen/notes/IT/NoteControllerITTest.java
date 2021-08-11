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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) /*désactive tous les filtres dans la configuration SpringSecurity */
@PropertySource("classpath:application.properties")
public class NoteControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteService noteService;

    private Note noteTest;
    String textNoteTest="Le patient déclare n'avoir aucun problème";
    LocalDate dateNoteTest=LocalDate.of(2021,6,21);

    @BeforeEach
    public void setUpEach(){
        noteTest=new Note(textNoteTest,1,dateNoteTest);
    }

    /** List of notes **/

    @Test
    public void listNotes() throws Exception {

        //GIVEN WHEN THEN
        mockMvc.perform(get("/patHistory/list"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    /** Add a note **/
    @Test
    public void addNote() throws Exception {

        List<Note> noteBeforeAdd;
        List<Note> noteAfterAdd;

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonUser = obm.createObjectNode();

        //GIVEN
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

    /** Update note **/
    @Test
    public void updateNote() throws Exception {

        List<Note> noteBeforeUpdate;
        List<Note> noteAfterUpdate;

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonUser = obm.createObjectNode();

        //GIVEN
        noteBeforeUpdate = noteService.findAll();
        Note noteToUpdate = noteService.findById(noteBeforeUpdate.get(1).getId());

        jsonUser.set("id", TextNode.valueOf(noteToUpdate.getId()));
        jsonUser.set("textNote", TextNode.valueOf(noteToUpdate.getTextNote()));
        jsonUser.set("patientId", TextNode.valueOf(String.valueOf(noteToUpdate.getPatientId())));
        jsonUser.set("dateNote", TextNode.valueOf(String.valueOf(noteToUpdate.getDateNote())));

        // WHEN
        // THEN
        mockMvc.perform(put("/patHistory/update/"+noteToUpdate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        noteAfterUpdate = noteService.findAll();
        assertEquals(noteAfterUpdate.size(),noteBeforeUpdate.size());
    }

    /**Delete note **/
    
    @Test
    public void deleteNote() throws Exception {

        List<Note> noteBeforeDelete;
        List<Note> noteAfterDelete;

        //GIVEN
        noteBeforeDelete = noteService.findAll();
        Note noteToDelete = noteService.findById(noteBeforeDelete.get(0).getId());

        // WHEN
        // THEN
        mockMvc.perform(post("/patHistory/delete/"+noteToDelete.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        noteAfterDelete = noteService.findAll();
        assertEquals(noteAfterDelete.size(),noteBeforeDelete.size()-1);
    }
}
