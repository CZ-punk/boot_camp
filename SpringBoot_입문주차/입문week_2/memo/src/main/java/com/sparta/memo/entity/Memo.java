package com.sparta.memo.entity;

import com.sparta.memo.dto.MemoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Memo extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, length = 500)
    private String contents;

    public Memo(MemoRequestDto requestDto) {
        username = requestDto.getUsername();
        contents = requestDto.getContents();
    }

    public void update(MemoRequestDto memoRequestDto) {
        username = memoRequestDto.getUsername();
        contents = memoRequestDto.getContents();
    }

}
