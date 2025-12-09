package com.coopcredit.application.infrastructure.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProblemDetail {
    private String type;
    private String title;
    private int status;
    private String detail;
    private String instance;
}
