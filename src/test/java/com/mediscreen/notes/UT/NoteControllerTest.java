package com.mediscreen.notes.UT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.service.NoteService;
import com.mediscreen.notes.web.controller.NoteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NoteController.class)
@AutoConfigureMockMvc(addFilters = false)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    // Constantes pour le jeu de test
    private Note noteTest;
    String textNoteTest="Le patient déclare n'avoir aucun problème";
    LocalDate dateNoteTest=LocalDate.of(2021,6,21);

    @BeforeEach
    public void setUpEach(){
        noteTest=new Note("1",textNoteTest,1,dateNoteTest);
    }

    @Test
    public void getAllNoteTest() throws Exception {

        //GIVEN
        List<Note> noteList = new ArrayList<>();
        noteList.add(noteTest);

        Mockito.when(noteService.findAll()).thenReturn(noteList);
        //WHEN THEN
        mockMvc.perform(get("/patHistory/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getNoteByIdTest() throws Exception {

        //GIVEN
        Optional<Note> note = Optional.of(noteTest);

        Mockito.when(noteService.findById(any(String.class))).thenReturn(note);
        //WHEN THEN
        mockMvc.perform(get("/patHistory/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addNoteTest() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonUser = obm.createObjectNode();

        //GIVEN
        jsonUser.set("id", TextNode.valueOf(noteTest.getId()));
        jsonUser.set("textNote", TextNode.valueOf(noteTest.getTextNote()));
        jsonUser.set("patientId", TextNode.valueOf(String.valueOf(noteTest.getPatientId())));
        jsonUser.set("dateNote", TextNode.valueOf(String.valueOf(noteTest.getDateNote())));

        //WHEN
        Mockito.when(noteService.addNote(any(Note.class))).thenReturn(noteTest);

        //THEN
        mockMvc.perform(post("/patHistory/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void updateNoteTest() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonUser = obm.createObjectNode();
        Optional<Note> note = Optional.of(noteTest);

        //GIVEN
        jsonUser.set("id", TextNode.valueOf(noteTest.getId()));
        jsonUser.set("textNote", TextNode.valueOf(noteTest.getTextNote()));
        jsonUser.set("patientId", TextNode.valueOf(String.valueOf(noteTest.getPatientId())));
        jsonUser.set("dateNote", TextNode.valueOf(String.valueOf(noteTest.getDateNote())));

        Mockito.when(noteService.findById(any(String.class))).thenReturn(note);
        Mockito.when(noteService.updateNote(any(Note.class))).thenReturn(noteTest);

        //WHEN THEN
        mockMvc.perform(put("/patHistory/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNoteTest() throws Exception {

        Optional<Note> note = Optional.of(noteTest);

        //GIVEN

        Mockito.when(noteService.findById(any(String.class))).thenReturn(note);
        Mockito.doNothing().when(noteService).deleteNote(any(String.class));

        //WHEN THEN
        mockMvc.perform(post("/patHistory/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
