package com.mediscreen.notes.dao;

import com.mediscreen.notes.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteDao extends MongoRepository<Note, String> {


    List<Note> findNoteByPatientId (Integer patientId);

}
