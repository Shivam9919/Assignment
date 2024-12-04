package com.example.requested_APIs.model;

import jakarta.annotation.Priority;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.info.SslInfo.CertificateValidityInfo.Status;

@Entity
@Setter
@Getter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority; // Enum: LOW, MEDIUM, HIGH

    @Enumerated(EnumType.STRING)
    private Status status; // Enum: TODO, DONE

    private LocalDate dueDate;

    private Boolean isDeleted = false; // For soft deletion

}
