package com.mediscreen.notes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import java.time.LocalDate;

@Document(collection ="note")
public class Note {

    @Id
    @GeneratedValue
    private String id;

    @Field(value = "textNote")
    private String textNote;

    @Field(value = "patientId")
    private Integer patientId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Field(value = "dateNote")
    private LocalDate dateNote ;

    public Note() {
    }

    public Note(String id, String textNote, Integer patientId, LocalDate dateNote) {
        this.id = id;
        this.textNote = textNote;
        this.patientId = patientId;
        this.dateNote = dateNote;
    }

    public Note(String textNote, Integer patientId, LocalDate dateNote) {
        this.textNote = textNote;
        this.patientId = patientId;
        this.dateNote = dateNote;
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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public LocalDate getDateNote() {
        return dateNote;
    }

    public void setDateNote(LocalDate dateNote) {
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
}
