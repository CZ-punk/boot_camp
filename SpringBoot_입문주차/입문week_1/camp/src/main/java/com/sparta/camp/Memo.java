package com.sparta.camp;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Memo {

    private final Long id;
    private final String username;
    private final String contents;

}


class Main {
    public static void main(String[] args) {
        Memo memo = new Memo(666L, "Devil", "Fucking!");
        System.out.println(memo);
    }
}
