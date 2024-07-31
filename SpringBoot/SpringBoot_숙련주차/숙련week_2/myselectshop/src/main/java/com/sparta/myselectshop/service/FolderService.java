package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.FolderResponseDto;
import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    public void addFolders(List<String> folderNames, User user) {

        List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);
        // 조건1: 해당 user 와 연관관계가 있는 모든 Folder 필터링
        // 조건2: folderNames 와 일치하는 folder.name 인 모든 Folder 를 필터링

        List<Folder> folderList = new ArrayList<>();

        for (String folderName : folderNames) {
            if (!isExistFolderName(folderName, existFolderList)) {
                Folder folder = new Folder(folderName, user);
                folderList.add(folder);
            } else {
                throw new IllegalArgumentException("중복된 폴더명을 제거해주세요! 폴더명 = " + folderName);
            }
        }
        folderRepository.saveAll(folderList);
    }

    public List<FolderResponseDto> getFolders(User user) {
        List<Folder> folderList = folderRepository.findAllByUser(user);
        return folderList.stream()
                .map(FolderResponseDto::new)
                .collect(Collectors.toList());
    }


    private Boolean isExistFolderName(String folderName, List<Folder> existFolderList) {
        return existFolderList.stream()
                .anyMatch(folder -> folder.getName().equals(folderName));
    }


}
