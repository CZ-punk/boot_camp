package com.sparta.memo.dto;

import com.sparta.memo.entity.Memo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
public class MemoResponseDto {

    private Long id;
    private String username;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MemoResponseDto(Memo memo) {
        id = memo.getId();
        username = memo.getUsername();
        contents = memo.getContents();
        createdAt = memo.getCreatedAt();
        modifiedAt = memo.getModifiedAt();
    }
}
