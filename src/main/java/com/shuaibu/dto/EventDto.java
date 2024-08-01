package com.shuaibu.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class EventDto {

    private Long id;

    @NotEmpty(message = "* Event title is mandatory")
    private String eventTitle;

    @NotEmpty(message = "* Event message is mandatory")
    private String eventMessage;

    @NotEmpty(message = "* Event time and date is mandatory")
    private String timeAndDate;

}
