package com.ceng316.ceng316_oims_backend.Documents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByType(DocumentType type);
    Optional<List<Document>> findAllByType(DocumentType type);
}
