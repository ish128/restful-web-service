package com.example.restfulwebservice.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

/**
 * The type User dao service.
 */
@Service
public class UserDaoService {

  /**
   * The constant list.
   */
  private static final List<User> list = new ArrayList<>();

  /**
   * The constant initCount.
   */
  private static int initCount;

  static {
    list.add(new User(1, "임성현", "서울시 구로구 26-12, 다청림시티 206호", LocalDateTime.now(), "1234",
        "811208-1111111"));
    list.add(new User(2, "ish102", "서울시 구로구 26-12, 다청림시티 207호", LocalDateTime.now(), "1234",
        "811208-1111112"));
    list.add(new User(3, "ish103", "서울시 구로구 26-12, 다청림시티 208호", LocalDateTime.now(), "1234",
        "811208-1111113"));
    initCount = list.size();
  }

  /**
   * Find all list.
   *
   * @return the list
   */
  public List<User> findAll() {
    return list;
  }

  /**
   * Find one user.
   *
   * @param id the id
   * @return the user
   */
  public User findOne(final Integer id) {
    return list.stream()
        .filter(u -> u.getId() == id)
        .findAny()
        .orElse(null);
  }

  /**
   * Save user.
   *
   * @param user the user
   * @return the user
   */
  public User save(final User user) {
    if (user.getId() == null) {
      user.setId(++initCount);
      list.add(user);
    } else {
      final int idx = IntStream.range(0, list.size())
          .filter(i -> list.get(i).getId() == user.getId())
          .findAny()
          .orElseThrow();
      list.add(idx, user);
    }
    return user;
  }

  public User deleteById(int id) {
    Iterator<User> iterator = list.iterator();
    while (iterator.hasNext()) {
      User user = iterator.next();
      if (user.getId() == id) {
        iterator.remove();
        return user;
      }
    }
    return null;
  }
}
