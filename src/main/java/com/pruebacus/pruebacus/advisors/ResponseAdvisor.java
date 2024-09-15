package com.pruebacus.pruebacus.advisors;


import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAdvisor {
    private int errorCode;
    private String statusError;
    private List<String> errorMessages = new ArrayList<>();

    public ResponseAdvisor(int errorCode, String statusError) {
        this.errorCode = errorCode;
        this.statusError = statusError;
    }

    public void setMessage(String message) {
        this.errorMessages.add(message);
    }
}

