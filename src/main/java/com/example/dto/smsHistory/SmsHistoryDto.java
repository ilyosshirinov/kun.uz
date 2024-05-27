package com.example.dto.smsHistory;

import com.example.enums.SmsStatus;
import com.example.enums.SmsType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsHistoryDto {
    // todo (id, phone,message,status,type(if necessary),created_date)
    private Integer id;
    private String phone;
    private String message;
    private SmsStatus status;
    private SmsType type;
    private LocalDateTime createdDate;
}
