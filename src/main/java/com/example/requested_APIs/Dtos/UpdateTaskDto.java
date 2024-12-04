package com.example.requested_APIs.Dtos;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.info.SslInfo.CertificateValidityInfo.Status;

@Getter
@Setter
public class UpdateTaskDto {

    private LocalDate dueDate;
    private Status status;
}
