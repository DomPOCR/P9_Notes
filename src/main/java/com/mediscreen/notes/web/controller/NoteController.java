package com.mediscreen.notes.web.controller;

import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.service.NoteService;
import com.mediscreen.notes.web.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "*")
@Controller
public class NoteController {

    // Pour le log4j2
    final Logger logger = LogManager.getLogger(this.getClass().getName());

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    /*---------------------------  GET all notes -----------------------------*/

    /**
     * Find all notes in database
     * @return list of notes
     */
    @GetMapping(value = "/patHistory/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getAllNote() {

       List<Note> noteList = noteService.findAll();

        for (Note note : noteList) {
            System.out.println(note);
        }
       logger.info("patHistory/list : OK");
       return noteList;

    }

    /*---------------------------  GET notes by id -----------------------------*/

    @GetMapping(value = "/patHistory/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Note> getNote(@PathVariable("id") String id) throws NotFoundException {

        Optional<Note> resultNote = noteService.findById(id);
        if (!resultNote.isPresent()){
            logger.warn("The note with id " + id + " does not exist");
            throw new NotFoundException("The note with id " + id + " does not exist");
        }
        logger.info("patHistory/" + id + " : OK");
        return resultNote;
    }

    /*---------------------------  GET note by patient id -----------------------------*/

    @GetMapping(value = "/patHistoryPatient/{patientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getPatientNoteByPatientId(@PathVariable("patientId") Integer patientId) throws NotFoundException {

        return noteService.findNoteByPatientId(patientId);
    }
}
