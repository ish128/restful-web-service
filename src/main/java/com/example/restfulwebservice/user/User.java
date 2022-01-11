package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonFilter("UserInfo")
public class User {

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
