package com.example.zerodividend.model;

import com.example.zerodividend.persist.entity.MemberEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    private Long id;
    private String username;
    private String password;
    private String role;

    @Data
    public static class SignIn{

        private String username;
        private String password;

    }

    @Data
    public static class SignUp{
        private String username;
        private String password;
        private String role;

        public MemberEntity toEntity() {
            return MemberEntity.builder()
                    .username(this.username)
                    .password(this.password)
                    .role(this.role)
                    .build();
        }

    }


}
