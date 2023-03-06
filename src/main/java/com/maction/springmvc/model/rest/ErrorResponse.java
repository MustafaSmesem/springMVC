package com.maction.springmvc.model.rest;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private Integer status;
}
