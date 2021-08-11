package com.mediscreen.notes.service;

import com.mediscreen.notes.model.Note;

import java.util.List;

public interface NoteService {

    /**
     * find list of all notes
     * @return list of notes
     */
    List<Note> findAll();

    /**
     *
     * @param id note id
     * @return note by id
     */
    Note findById(String id);

    /**
     *
     * @param patientId patient id
     * @return note of patient
     */
    List<Note> findNoteByPatientId(Integer patientId);

    /**
     *
     * @param note note to add
     * @return note added
     */
    Note addNote(Note note);

    /**
     *
     * @param note note to update
     * @return note updated
     */
    Note updateNote(Note note);

    /**
     *
     * @param id note id to delete
     */
    void deleteNote(String id);
}
