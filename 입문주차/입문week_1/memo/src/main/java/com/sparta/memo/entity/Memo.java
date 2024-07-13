package com.sparta.memo.entity;

import com.sparta.memo.dto.MemoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    private Long id;
    private String username;
    private String contents;

    public Memo(MemoRequestDto requestDto) {
        username = requestDto.getUsername();
        contents = requestDto.getContents();
    }

    public void update(MemoRequestDto requestDto) {
        username = requestDto.getUsername();
        contents = requestDto.getContents();
    }
}
