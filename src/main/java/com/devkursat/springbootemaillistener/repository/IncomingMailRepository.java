package com.devkursat.springbootemaillistener.repository;

import com.devkursat.springbootemaillistener.model.IncomingEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomingMailRepository extends JpaRepository<IncomingEmail, Long> {
}
