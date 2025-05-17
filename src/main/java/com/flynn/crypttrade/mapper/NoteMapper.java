package com.flynn.crypttrade.mapper;

import com.flynn.crypttrade.domain.Note;
import com.flynn.crypttrade.dto.NoteDto;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NoteMapper {

    public NoteDto toDto(Note note) {
        return NoteDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .build();
    }

    public Note toEntity(NoteDto dto) {
        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setCreatedAt(LocalDateTime.now());
        return note;
    }
}
