package com.transportation.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transportation.app.binding.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    // add custom queries if needed
}