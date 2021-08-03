package com.mediscreen.notes.web.controller;

import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.service.NoteService;
import com.mediscreen.notes.web.exception.NotFoundException;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class NoteController {

    // Pour le log4j2
    final Logger logger = LogManager.getLogger(this.getClass().getName());

    private final NoteService noteService;

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

       logger.info("patHistory/list : OK");
       return noteList;

    }

    /*---------------------------  GET notes by id -----------------------------*/

    /**
     *
     * @param id
     * @return note by id
     * @throws NotFoundException
     */
    @GetMapping(value = "/patHistory/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Note getNote(@PathVariable("id") String id) throws NotFoundException {

        try {
            Note resultNote = noteService.findById(id);
            logger.info("patHistory/  note with " + id + " : OK");
            return resultNote;
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("The note with id " + id + " does not exist");
        }
    }

    /*---------------------------  GET note by patient id -----------------------------*/

    /**
     *
     * @param patientId
     * @return notes for patient id
     * @throws NotFoundException
     */
    @GetMapping(value = "/patHistory/patient/{patientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getPatientNoteByPatientId(@PathVariable("patientId") Integer patientId) throws NotFoundException {

        List<Note> noteList = noteService.findNoteByPatientId(patientId);

        logger.info("patHistory/patient/ " + patientId + " :  OK");
        return noteList;
    }

    /*---------------------------  Add note  -----------------------------*/

    /**
     *
     * @param note
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
     * @param note
     * @return note updated
     */

    @PutMapping(value = "/patHistory/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Note updateNote(@PathVariable("id") String id, @RequestBody Note note) throws NotFoundException {

        try {
            Note noteToUpdate = noteService.findById(id);
            Note noteUpdated = noteService.updateNote(note);
            logger.info("patHistory/update " + noteUpdated.toString() + " :  OK");
            return noteUpdated;
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("patHistory/update  note with " + id + " not found");
        }
    }

    /*---------------------------  Delete note -----------------------------*/

    /**
     *
     * @param id
     * @return note deleted
     * @throws NotFoundException
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
            throw new IllegalArgumentException("patHistory/delete  note with " + id + " not found");
        }

    }

}
