package com.example.restfulwebservice.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonFilter("UserInfo")
public class User {

    @GeneratedValue
    @Id
    private Integer id;

    @NotBlank
    @Size(min = 2, message = "name은 두글자 이상 입력해주세요.")
    private String name;

    @Size(max = 100, message = "address 최대 100 이하로 입력해주세요.")
    private String address;

    @Past
    private LocalDateTime joinDateTime;
    private String password;
    private String ssn;
}
