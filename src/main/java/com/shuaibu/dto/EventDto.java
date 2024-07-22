package com.shuaibu.dto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class EventDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventTitle;
    private String eventMessage;
    private String timeAndDate;

}
