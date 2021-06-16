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
import java.util.Optional;

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
    public Optional<Note> getNote(@PathVariable("id") String id) throws NotFoundException {

        Optional<Note> resultNote = noteService.findById(id);
        if (!resultNote.isPresent()){
            logger.warn("The note with id " + id + " does not exist");
            throw new NotFoundException("The note with id " + id + " does not exist");
        }
        logger.info("patHistory/  note with " + id + " : OK");
        return resultNote;
    }

    /*---------------------------  GET note by patient id -----------------------------*/

    /**
     *
     * @param patientId
     * @return notes for patient id
     * @throws NotFoundException
     */
    @GetMapping(value = "/patHistoryPatient/{patientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getPatientNoteByPatientId(@PathVariable("patientId") Integer patientId) throws NotFoundException {

        List<Note> noteList = noteService.findNoteByPatientId(patientId);

        logger.info("patHistoryPatient/ " + patientId + " :  OK");
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
    public Note updateNote(@PathVariable("id") String id, @RequestBody Note note) {

        Optional<Note> noteToUpdate = noteService.findById(id);
        if (!noteToUpdate.isPresent()) {
            logger.error("patHistory/update note with " + id + " not found");
            throw new NotFoundException("patHistory/update  note with " + id + " not found");
        }
        Note noteUpdated = noteService.updateNote(note);
        logger.info("patHistory/update " + noteUpdated.toString() + " :  OK");
        return noteUpdated;
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

        Optional<Note> noteToDelete = noteService.findById(id);
        if (!noteToDelete.isPresent()) {
            logger.error("patHistory/delete note with " + id + " not found");
            throw new NotFoundException("patHistory/delete note with " + id + " not found");
        }
        noteService.deleteNote(id);
        logger.info("patHistory/delete " + noteToDelete.toString() + " :  OK");
        return noteToDelete.get();
    }

}
