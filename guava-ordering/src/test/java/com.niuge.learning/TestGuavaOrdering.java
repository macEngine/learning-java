package com.niuge.learning;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertFalse;

public class TestGuavaOrdering {
  @Test
  public void givenNullsFirst_testSort_thenOk() {
    List<Integer> toSort = Arrays.asList(3, 5, 4, null, 1, 2);
    Collections.sort(toSort, Ordering.natural().nullsFirst());
    assertThat(toSort.get(0), nullValue());
    System.out.println(toSort);
  }

  @Test
  public void givenNullsLast_testSort_thenOk() {
    List<Integer> toSort = Arrays.asList(3, 5, 4, null, 1, 2);
    Collections.sort(toSort, Ordering.natural().nullsLast());
    assertThat(toSort.get(toSort.size() - 1), nullValue());
  }

  @Test
  public void givenNatural_testSort_thenOk() {
    List<Integer> toSort = Arrays.asList(3, 5, 4, 1, 2);
    Collections.sort(toSort, Ordering.natural());

    assertTrue(Ordering.natural().isOrdered(toSort));
  }

  @Test
  public void givenReverse_testSort_thenOk() {
    List<Integer> toSort = Arrays.asList(3, 5, 4, 1, 2);
    Collections.sort(toSort, Ordering.natural().reverse());
  }

  @Test
  public void givenNullsLastReverse_testSort_thenOk() {
    List<Integer> toSort = Arrays.asList(3, 5, 4, null, 1, 2);
    Collections.sort(toSort, Ordering.natural().nullsLast().reverse());
    assertThat(toSort.get(0), nullValue());
  }

  public static class OrderingByLength extends Ordering<String> {
    @Override
    public int compare(String s1, String s2) {
      return Ints.compare(s1.length(), s2.length());
    }
  }

  @Test
  public void givenCustomOrderByLength_testSort_thenOk() {
    List<String> toSort = Arrays.asList("zz", "aa", "b", "ccc");
    Ordering<String> byLength = new OrderingByLength();
    Collections.sort(toSort, byLength);

    Ordering<String> expectedOrder = Ordering.explicit(Lists.newArrayList("b", "zz", "aa", "ccc"));
    assertTrue(expectedOrder.isOrdered(toSort));
  }

  @Test
  public void givenNaturalAndInteger_testSort_thenOk() {
    List<Integer> toSort = Arrays.asList(3, 5, 4, 2, 1, 2, 11);
    Collections.sort(toSort, Ordering.natural());
    System.out.println(toSort);

    assertFalse(Ordering.natural().isStrictlyOrdered(toSort));
  }

  @Test
  public void givenSecondaryOrdering_testSort_thenOk() {
    List<String> toSort = Arrays.asList("zz", "aa", "b", "ccc");
    Ordering<String> byLength = new OrderingByLength();
    Collections.sort(toSort, byLength.compound(Ordering.natural()));

    Ordering<String> expectedOrder = Ordering.explicit(Lists.newArrayList("b", "aa", "zz", "ccc"));
    assertTrue(expectedOrder.isOrdered(toSort));
  }

  @Test
  public void givenCompound_testSort_thenOk() {
    List<String> toSort = Arrays.asList("zz", "aa", null, "b", "ccc");
    Collections.sort(toSort,
        new OrderingByLength().reverse().compound(Ordering.natural()).nullsLast());
    System.out.println(toSort);
  }

  @Test
  public void givenBinarySearch_testSort_thenOk() {
    List<Integer> toSort = Arrays.asList(1, 2, 11);
    Collections.sort(toSort, Ordering.usingToString());
    int found = Ordering.usingToString().binarySearch(toSort, 2);
    System.out.println(found);
  }

  @Test
  public void givenUsingToString_testSort_thenOk() {
    List<Integer> toSort = Arrays.asList(2, 1, 11, 100, 8, 14);
    int found = Ordering.usingToString().min(toSort);
    assertThat(found, equalTo(1));
  }

  @Test
  public void givenSortedCopy_testSort_thenOk() {
    List<String> toSort = Arrays.asList("aa", "b", "ccc");
    List<String> sortedCopy = new OrderingByLength().sortedCopy(toSort);
    System.out.println(sortedCopy);

    Ordering<String> expectedOrder = Ordering.explicit(Lists.newArrayList("b", "aa", "ccc"));
    assertFalse(expectedOrder.isOrdered(toSort));
    assertTrue(expectedOrder.isOrdered(sortedCopy));
  }

  @Test
  public void givenLeastOf_testSort_thenOk() {
    List<Integer> toSort = Arrays.asList(2, 1, 11, 100, 8, 14);
    List<Integer> leastOf = Ordering.natural().leastOf(toSort, 3);
    List<Integer> expected = Lists.newArrayList(1, 2, 8);
    assertThat(expected, equalTo(leastOf));
  }

  @Test
  public void givenToStringFunction_testSort_thenOk() {
    List<Integer> toSort = Arrays.asList(2, 1, 11, 100, 8, 14);
    Ordering<Object> ordering = Ordering.natural().onResultOf(Functions.toStringFunction());
    List<Integer> sortedCopy = ordering.sortedCopy(toSort);

    List<Integer> expected = Lists.newArrayList(1, 100, 11, 14, 2, 8);
    assertThat(expected, equalTo(sortedCopy));
  }
}
