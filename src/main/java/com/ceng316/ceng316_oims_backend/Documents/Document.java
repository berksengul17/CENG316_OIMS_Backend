package com.ceng316.ceng316_oims_backend.Documents;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;
    private String title;
    private Blob content;
    private DocumentType type;
    private DocumentStatus status;
}
