package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class MemoControllerV2 {

    private final JdbcTemplate jdbcTemplate;

    public MemoControllerV2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into memo (username, contents) values (?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, requestDto.getUsername());
            ps.setString(2, requestDto.getContents());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        memo.setId(id);

        // Entity -> ResponseDto
        return new MemoResponseDto(memo);
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        return jdbcTemplate.query("select * from memo", new RowMapper<MemoResponseDto>() {
            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new MemoResponseDto(rs.getLong("id"), rs.getString("username"), rs.getString("contents"));
            }
        });
    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        // 해당 메모 DB 존재하는지 확인
        Memo memo = findById(id);
        if (memo != null) {
            // 해당 메모 가져오기
            String sql = "update memo set username = ?, contents = ?";
            jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getContents());

            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        Memo memo = findById(id);
        if (memo != null) {
            // 해당 메모 삭제
            String sql = "delete from memo where id = ?";
            jdbcTemplate.update(sql, id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    private Memo findById(Long id) {
        String sql = "select * from Memo m where m.id = ?";
        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Memo memo = new Memo();
                memo.setContents(resultSet.getString("contents"));
                memo.setUsername(resultSet.getString("username"));
                return memo;
            } else {
                return null;
            }
        }, id);
    }
}

