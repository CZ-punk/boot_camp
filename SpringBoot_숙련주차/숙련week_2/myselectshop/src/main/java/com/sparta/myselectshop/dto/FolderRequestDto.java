package com.sparta.myselectshop.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class FolderRequestDto {
    List<String> folderNames;

    // 폴더 여러개를 한번에 추가할 수 있도록 List<String> Type 을 받아온다.
}