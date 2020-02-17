package com.niuge.learning;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.lessThan;
public class TestGuavaFunctional {
  @Test
  public void test () {
    List<Integer> numbers = Lists.newArrayList(1, 2, 3, 6, 10, 34, 57, 89);
    Predicate<Integer> acceptEven = new Predicate<Integer>() {
      @Override
      public boolean apply(Integer number) {
        return (number % 2) == 0;
      }
    };
    List<Integer> evenNumbers = Lists.newArrayList(Collections2.filter(numbers, acceptEven));
    Integer found = Collections.binarySearch(evenNumbers, 57);
    assertThat(found, lessThan(0));
  }
}
