package com.flynn.crypttrade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;
}
