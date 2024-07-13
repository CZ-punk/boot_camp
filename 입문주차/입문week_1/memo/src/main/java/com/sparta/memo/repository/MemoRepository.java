package com.sparta.memo.repository;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemoRepository {

    private final JdbcTemplate jdbcTemplate;

    public Memo save(Memo memo) {
        // DB 저장
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "Insert Into memo (username, contents) values (?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, memo.getUsername());
            ps.setString(2, memo.getContents());
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();
        memo.setId(id);
        return memo;
    }


    public List<MemoResponseDto> findAll() {
        return jdbcTemplate.query("select * from memo", new RowMapper<MemoResponseDto>() {
            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new MemoResponseDto(rs.getLong("id"), rs.getString("username"), rs.getString("contents"));
            }
        });
    }

    public void update(Long id, MemoRequestDto requestDto) {
        String sql = "update memo set username = ?, contents = ? where id = ?";
        jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getContents(), id);
    }

    public void delete(Long id) {
        String sql = "delete from memo where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Memo findById(Long id) {
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
