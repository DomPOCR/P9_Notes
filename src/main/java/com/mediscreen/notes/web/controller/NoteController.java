package com.mediscreen.notes.web.controller;

import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.service.NoteService;
import com.mediscreen.notes.web.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*")
@RestController
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
     *
     * @return list of notes
     */
    @GetMapping(value = "/patHistory/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getAllNote() {

        List<Note> noteList = noteService.findAll();

        logger.info("patHistory/list : OK");
        return noteList;

    }

    /*---------------------------  GET notes by id -----------------------------*/

    /**
     * @param id note id
     * @return note by id
     * @throws NotFoundException if note not found
     */
    @GetMapping(value = "/patHistory/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Note getNote(@PathVariable("id") String id) throws NotFoundException {

        try {
            Note resultNote = noteService.findById(id);
            logger.info("patHistory/  note with " + id + " : OK");
            return resultNote;
        } catch (NoSuchElementException e) {
            throw new NotFoundException("The note with id " + id + " does not exist");
        }
    }

    /*---------------------------  GET note by patient id -----------------------------*/

    /**
     * @param patientId patient id
     * @return notes for patient id
     */
    @GetMapping(value = "/patHistory/patient/{patientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getPatientNoteByPatientId(@PathVariable("patientId") Integer patientId)  {

        List<Note> noteList = noteService.findNoteByPatientId(patientId);

        logger.info("patHistory/patient/ " + patientId + " :  OK");
        return noteList;
    }

    /*---------------------------  Add note  -----------------------------*/

    /**
     * @param note note to add
     * @return note added
     */
    @PostMapping(value = "/patHistory/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Note addNote(@RequestBody Note note) {

        Note noteAdded = noteService.addNote(note);
        logger.info("patHistory/add  note with " + noteAdded.getPatientId() + " :  OK");
        return noteAdded;
    }

    /*---------------------------  Update note  -----------------------------*/

    /**
     *
     * @param id note id
     * @param note note to update
     * @return note updated
     * @throws NotFoundException if not found note
     */
    @PutMapping(value = "/patHistory/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Note updateNote(@PathVariable("id") String id, @RequestBody Note note) throws NotFoundException {

        try {
            Note noteToUpdate = noteService.findById(id);

        } catch (NoSuchElementException e) {
            throw new NotFoundException("patHistory/update  note with " + id + " not found");
        }
        Note noteUpdated = noteService.updateNote(note);
        logger.info("patHistory/update " + noteUpdated.toString() + " :  OK");
        return noteUpdated;
    }

    /*---------------------------  Delete note -----------------------------*/

    /**
     * @param id note id
     * @return note deleted
     * @throws NotFoundException if note not found
     */
    @PostMapping(value = "/patHistory/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Note deleteNote(@PathVariable("id") String id) throws NotFoundException {

        try {
            Note noteToDelete = noteService.findById(id);
            noteService.deleteNote(id);
            logger.info("patHistory/delete " + noteToDelete.toString() + " :  OK");
            return noteToDelete;
        } catch (NoSuchElementException e) {
            throw new NotFoundException("patHistory/delete  note with " + id + " not found");
        }

    }

}
