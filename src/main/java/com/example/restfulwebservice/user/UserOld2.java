package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @JsonIgnoreProperties 를 이용해서 response data 의 특정필드 filtering
 */
@Data
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"password", "ssn"})
public class UserOld2 {

  /**
   * The Id.
   */
  private Integer id;

  /**
   * The Name.
   */
  @NotBlank
  @Size(min = 2, message = "name은 두글자 이상 입력해주세요.")
  private String name;

  /**
   * The Address.
   */
  @Size(max = 100, message = "address 최대 100 이하로 입력해주세요.")
  private String address;

  /**
   * The Join date time.
   */
  @Past
  private LocalDateTime joinDateTime;

  /**
   * The Password.
   */
  private String password;
  /**
   * The Ssn.
   */
  private String ssn;
}
