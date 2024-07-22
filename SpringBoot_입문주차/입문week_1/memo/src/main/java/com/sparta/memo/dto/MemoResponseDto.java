package com.sparta.memo.dto;

import com.sparta.memo.entity.Memo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class MemoResponseDto {

    private Long id;
    private String username;
    private String contents;

    public MemoResponseDto(Memo memo) {
        id = memo.getId();
        username = memo.getUsername();
        contents = memo.getContents();
    }

    public MemoResponseDto(Long id, String username, String contents) {
        this.id = id;
        this.username = username;
        this.contents = contents;
    }
}
