package com.mediscreen.notes.service;

import com.mediscreen.notes.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    /**
     * find list of all notes
     * @return list of notes
     */
    List<Note> findAll();

    Optional<Note> findById(String id);

    List<Note> findNoteByPatientId(Integer patientId);

    Note addNote(Note note);
}
