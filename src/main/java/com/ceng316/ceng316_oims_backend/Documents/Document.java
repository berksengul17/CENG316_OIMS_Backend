package com.ceng316.ceng316_oims_backend.Documents;

import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;
    @Lob // Indicates that this field should be treated as a Large Object
    @Basic(fetch = FetchType.LAZY) // Large objects can be lazily fetched
    private byte[] content; // Use byte array to store the Blob data
    private String contentType;
    @Enumerated(EnumType.STRING)
    private DocumentType type;
    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    public Document(byte[] content, String contentType, DocumentType type, DocumentStatus status) {
        this.content = content;
        this.contentType = contentType;
        this.type = type;
        this.status = status;
    }
}
