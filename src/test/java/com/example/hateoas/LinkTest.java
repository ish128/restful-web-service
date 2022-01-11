package com.example.hateoas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.hateoas.LinkRelation.of;

import com.example.restfulwebservice.person.Person;
import com.example.restfulwebservice.person.PersonModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.UriTemplate;

/**
 * The type Link test.
 * <p>1. Link : 링크 클래스
 * <p>2. LinkTemplate : 링크 만들기 쉽게 템플릿 엔진 기능 지원
 * <p>3. Link Relation : 링크의 자원의 관계 정보
 * <p>4. RepresentationModel : 링크를 담을수 있는 컨테이너 </p>
 * 자원과 링크의 관계
 */
@Slf4j
public class LinkTest {

  /**
   * The constant objectMapper.
   */
  private static ObjectMapper objectMapper;

  /**
   * Initailize.
   */
  @BeforeAll
  public static void initailize() {
    objectMapper = new ObjectMapper();
  }

  /**
   * Link
   * <p> Link는  hypertext reference(href) 와 link relation(rel)를 가진다. (rel의 기본값은 self 이다.)
   * <p> 그외 RFC-8288에 정의된 추가적인 속성을 설정할 수 있는 메소드를 지원한다.
   */
  @Test
  public void link() {
    Link link = Link.of("/something");
    assertThat(link.getHref()).isEqualTo("/something");
    assertThat(link.getRel()).isEqualTo(IanaLinkRelations.SELF);

    link = Link.of("/something", "my-rel");
    assertThat(link.getHref()).isEqualTo("/something");
    assertThat(link.getRel()).isEqualTo(of("my-rel"));
  }

  /**
   * Link template.
   * <p> Link template 을 사용해서, 링크를 생성하기
   * <p>https://docs.spring.io/spring-hateoas/docs/current/reference/html/#fundamentals.uri-templates</p>
   */
  @Test
  public void linkTemplate1() {
    Link link = Link.of("/{segment}/something{?parameter}");
    assertThat(link.isTemplated()).isTrue();
    assertThat(link.getVariableNames()).contains("segment", "parameter");

    Map<String, Object> values = new HashMap<>();
    values.put("segment", "path");
    values.put("parameter", 42);

    assertThat(link.expand(values).getHref())
        .isEqualTo("/path/something?parameter=42");
  }

  /**
   * Link template 2.
   */
  @Test
  public void linkTemplate2() {
    UriTemplate template = UriTemplate.of("/{segment}/something")
        .with(new TemplateVariable("parameter", VariableType.REQUEST_PARAM));

    assertThat(template.toString()).isEqualTo("/{segment}/something{?parameter}");

    Link link = Link.of(template, IanaLinkRelations.SELF);
    Map<String, Object> values = new HashMap<>();
    values.put("segment", "path");
    values.put("parameter", 42);

    assertThat(link.expand(values).getHref())
        .isEqualTo("/path/something?parameter=42");
  }

  /**
   * Link relation. 직접 정의
   */
  @Test
  public void linkRelation() {
    Link link = Link.of("/some-resource", LinkRelation.of("next"));
    assertThat(link.getRel()).isEqualTo(LinkRelation.of("next"));

  }

  /**
   * Iana link relation. IanaLinkRelations (미리정의된 IANA link relations):
   * https://www.iana.org/assignments/link-relations/link-relations.xhtml</p>
   */
  @Test
  public void ianaLinkRelation() {
    Link link = Link.of("/some-resource", IanaLinkRelations.NEXT);
    assertThat(link.getRel()).isEqualTo(LinkRelation.of("next"));
    assertThat(IanaLinkRelations.isIanaRel(link.getRel())).isTrue();
  }


  /**
   * Representation model - 상속을 통한 사용.
   *
   * @throws JsonProcessingException the json processing exception
   */
  @Test
  public void representationModel_상속을_통한_사용() throws JsonProcessingException {
    PersonModel model = new PersonModel();
    model.setFirstname("Dave");
    model.setLastname("Matthews");
    model.add(Link.of("https://myhost/people/42"));

    String value = objectMapper.writeValueAsString(model);
    log.debug(value);
  }

  /**
   * Representation model - 기존 도메인 엔티티를 래핑하는 방식
   *
   * @throws JsonProcessingException the json processing exception
   */
  @Test
  public void representationModel_wrapping_domain_entity() throws JsonProcessingException {
    Person person = new Person("Dave", "Matthews");
    EntityModel<Person> model = EntityModel.of(person);
    model.add(Link.of("https://myhost/people/42"));

    String value = objectMapper.writeValueAsString(model);
    log.debug(value);
  }
}
