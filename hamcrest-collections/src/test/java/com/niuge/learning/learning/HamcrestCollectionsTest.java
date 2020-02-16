package com.niuge.learning.learning;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsCollectionContaining.hasItems;


// Hamcrest is typically viewed as a third generation matcher framework.
// Hamcrest is a framework for creating matchers ('Hamcrest' is an anagram of 'matchers'),
// Hamcrest一般被视作第三代匹配器框架.第一代使用断言(逻辑语句),但这样的测试不易读.
// 第二代测试框架引入了特殊的断言方法,例如assertEquals().然而这种方式会导致编写过多类似的断言方法.
// Hamcrest采用了assertThat方法和匹配器表达式来确定测试是否成功,解决上述两个缺点.
public class HamcrestCollectionsTest {
  // check if single element is in a collection
  @Test
  public void givenCollection_whenHasItem_thenOk() {
    List<String> collection = Lists.newArrayList("ab", "cd", "ef");
    assertThat(collection, hasItem("cd"));
    assertThat(collection, not(hasItem("zz")));
  }

  // check if multiple elements are in a collection
  @Test
  public void givenCollection_whenHasItems_thenOk() {
    List<String> collection = Lists.newArrayList("ab", "cd", "ef");
    assertThat(collection, hasItems("ab", "cd"));
    assertThat(collection, not(hasItems("zz", "ff")));
  }

  // check all elements in a collection (with strict order)
  @Test
  public void givenCollection_whenContains_thenOk() {
    List<String> collection = Lists.newArrayList("ab", "cd", "ef");
    assertThat(collection, contains("ab", "cd", "ef"));
  }

  // check all elements in a collection (with any order)
  @Test
  public void givenCollection_whenContainsInAnyOrder_thenOk() {
    List<String> collection = Lists.newArrayList("ab", "cd", "ef");
    assertThat(collection, containsInAnyOrder("cd", "ab", "ef"));
  }

  // check if collection is empty
  @Test
  public void givenCollection_whenEmpty_thenOk() {
    List<String> collection = Lists.newArrayList();
    assertThat(collection, empty());
  }

  // check if collection is empty
  @Test
  public void givenArray_whenEmptyArray_thenOk() {
    String[] array = new String[]{"ab"};
    assertThat(array, not(emptyArray()));
  }

  // check if Map is empty
  @Test
  public void givenMap_whenEmpty_thenOk() {
    Map<String, String> collection = Maps.newHashMap();
    assertThat(collection, equalTo(Collections.EMPTY_MAP));
  }

  // check if Iterable is empty
  @Test
  public void givenIterable_whenEmptyIterable_thenOk() {
    Iterable<String> collection = Lists.newArrayList();
    assertThat(collection, emptyIterable());
  }

  // check size of a collection
  @Test
  public void givenList_whenHasSize_thenOk() {
    List<String> collection = Lists.newArrayList("ab", "cd", "ef");
    assertThat(collection, hasSize(3));
  }

  // checking size of an iterable
  @Test
  public void givenIterable_whenIterableWithSize_thenOk() {
    Iterable<String> collection = Lists.newArrayList("ab", "cd", "ef");
    assertThat(collection, Matchers.<String>iterableWithSize(3));
  }

  // check condition on every item
  @Test
  public void givenList_whenEveryItemGreaterThan_thenOk() {
    List<Integer> collection = Lists.newArrayList(15, 20, 25, 30);
    assertThat(collection, everyItem(greaterThan(10)));
  }
}
