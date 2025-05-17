package com.flynn.crypttrade.mapper;


import com.flynn.crypttrade.domain.Note;
import com.flynn.crypttrade.dto.NoteDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteMapperTest {

    private final NoteMapper mapper = new NoteMapper();

    @Test
    void shouldMapToDto() {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Test Title");
        note.setContent("Test Content");

        NoteDto dto = mapper.toDto(note);

        assertEquals(1L, dto.getId());
        assertEquals("Test Title", dto.getTitle());
        assertEquals("Test Content", dto.getContent());
    }

    @Test
    void shouldMapToEntity() {
        NoteDto dto = NoteDto.builder()
                .id(1L)
                .title("DTO Title")
                .content("DTO Content")
                .build();

        Note note = mapper.toEntity(dto);

        assertEquals("DTO Title", note.getTitle());
        assertEquals("DTO Content", note.getContent());
        assertNotNull(note.getCreatedAt());
    }
}