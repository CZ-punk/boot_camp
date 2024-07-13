package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;


    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto);
        Memo saveMemo = memoRepository.save(memo);
        return new MemoResponseDto(saveMemo);
    }

    public List<MemoResponseDto> getMemos() {
        return memoRepository.findAllByOrderByModifiedAtDesc().stream()
                .map(MemoResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Long updateMemo(Long id, MemoRequestDto requestDto) {
        Memo findMemo = findMemo(id);
        findMemo.update(requestDto);
        return id;
    }

    public Long deleteMemo(Long id) {
        Memo findMemo = findMemo(id);
        memoRepository.delete(findMemo);
        return id;
    }

    public List<MemoResponseDto> getMemosByKeyword(String keyword) {
        return memoRepository.findAllByContentsContainsOrderByModifiedAtDesc(keyword).stream()
                .map(MemoResponseDto::new).collect(Collectors.toList());
    }

    private Memo findMemo(Long id) {
        return memoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
