package com.mediscreen.notes.service;

import com.mediscreen.notes.dao.NoteDao;
import com.mediscreen.notes.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

   private NoteDao noteDao;

    @Autowired
    public NoteServiceImpl(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    public List<Note> findAll() {
        return noteDao.findAll();
    }

    @Override
    public Optional<Note> findById(String id) {
        return noteDao.findById(id);
    }

    @Override
    public List<Note> findNoteByPatientId(Integer patientId) {
        return noteDao.findNoteByPatientId(patientId);
    }

    @Override
    public Note addNote(Note note) {
        return noteDao.save(note);
    }

    @Override
    public Note updateNote(Note note) {
        return noteDao.save(note);
    }

    @Override
    public void deleteNote(String id) {
        noteDao.deleteById(id);
    }

}
