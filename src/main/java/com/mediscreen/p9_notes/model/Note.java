package com.mediscreen.p9_notes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Document(collection ="note")
public class Note {

    @Id
    private String id;

    private String textNote;
    private int patientId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateNote ;

    public Note() {
    }

    public Note(String id, String textNote, int patientId, LocalDate dateNote) {
        this.id = id;
        this.textNote = textNote;
        this.patientId = patientId;
        this.dateNote = dateNote;
    }

    public Note(String textNote, int patientId, LocalDate dateNote) {
        this.textNote = textNote;
        this.patientId = patientId;
        this.dateNote = dateNote;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", textNote='" + textNote + '\'' +
                ", patientId=" + patientId +
                ", dateNote=" + dateNote +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTextNote() {
        return textNote;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public LocalDate getDateNote() {
        return dateNote;
    }

    public void setDateNote(LocalDate dateNote) {
        this.dateNote = dateNote;
    }
}
