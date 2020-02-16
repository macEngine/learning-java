package com.niuge.learning;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class TestGuavaCollections {
  static class CastFunction<F, T extends F> implements Function<F, T> {
    public final T apply(final F from) {
      return (T) from;
    }
  }

  @Test
  public void givenList_testTransform_thenOk() {
    List<Object> originalList = Lists.newArrayList();
    originalList.add(1);
    originalList.add(2);
    originalList.add(3);
    List<Integer> theList = Lists.transform(originalList, new CastFunction<>());
    System.out.println(theList);

    // Lists.transform等价于map函数
    List<Integer> list2 = originalList.stream().map(v -> (Integer) v).collect(Collectors.toList());
    System.out.println(list2);


    // 下面两种写法，在java1.5有用，在java1.8里根本用不到了，
    // 但是这种转换方式，是第一次看到。
    theList = (List<Integer>) (List<? extends Object>) originalList;
    System.out.println(theList);

    List<?> originalList1 = originalList;
    theList = (List<Integer>) originalList1;
    System.out.println(theList);
  }

  // adding an iterable to a collection
  @Test
  public void givenIterables_testAddAll_thenOk() {
    Iterable<String> iter = Lists.newArrayList();
    ((ArrayList<String>) iter).add("name3");
    Collection<String> collector = Lists.newArrayList();
    ((ArrayList<String>) collector).add(0, "name1");
    ((ArrayList<String>) collector).add(1, "name2");
    System.out.println(collector);
    Iterables.addAll(collector, iter);
    System.out.println(collector);
  }

  // check if collection contains element(s) according to a custom matching rule
  @Test
  public void givenIterables_testAny_thenOk() {
    Iterable<String> theCollection = Lists.newArrayList("a", "bc", "def");
    boolean contains = Iterables.any(theCollection, new Predicate<String>() {
      @Override
      public boolean apply(final String input) {
        return input.length() == 1;
      }
    });
    assertTrue(contains);
  }

  @Test
  public void givenIterables_testFind_thenOk() {
    Iterable<String> theCollection = Sets.newHashSet("b", "a", "bc", "def");
    String result = Iterables.find(theCollection, new Predicate<String>() {
      @Override
      public boolean apply(final String input) {
        return input.length() == 1;
      }
    });
    System.out.println(result);
  }

  @Test
  public void givenSets_testFilter_thenOk() {
    Set<String> theCollection = Sets.newHashSet("b", "a", "bc", "def");
    Set<String> resultSet = Sets.filter(theCollection, new Predicate<String>() {
      @Override
      public boolean apply(final String input) {
        return input.length() == 1;
      }
    });
    System.out.println(resultSet);
  }

  @Test
  public void givenIterables_testFind_thenException() {
    Iterable<String> theCollection = Sets.newHashSet("abcd", "efgh", "ijkl");
    Predicate<String> inputOfLengthOne = new Predicate<String>() {
      @Override
      public boolean apply(final String input) {
        return input.length() == 1;
      }
    };
    try {
      String found = Iterables.find(theCollection, inputOfLengthOne);
    } catch (NoSuchElementException e) {
      // ok
    }

    String found = Iterables.find(theCollection, inputOfLengthOne, "default");
    System.out.println(found);
  }

  @Test
  public void givenNotNullPredicates_testFilter_thenOk() {
    List<String> values = Lists.newArrayList("a", null, "b", "c");
    Iterable<String> withoutNulls = Iterables.filter(values, Predicates.notNull());
    System.out.println(withoutNulls);
  }

  @Test
  public void testCreateImmutableCollections_thenOk() {
    ImmutableList<String> immutableList = ImmutableList.of("a", "b", "c");
    ImmutableSet<String> immutableSet = ImmutableSet.of("a", "b", "c");
    ImmutableMap<String, String> imuttableMap =
        ImmutableMap.of("k1", "v1", "k2", "v2", "k3", "v3");
    System.out.println(imuttableMap);
  }

  @Test
  public void givenStandardCollection_testCreateImmutableCollections_thenOk() {
    List<String> muttableList = Lists.newArrayList();
    muttableList.add("1");
    muttableList.add("2");
    muttableList.add("3");
    ImmutableList<String> immutableList = ImmutableList.copyOf(muttableList);

    Set<String> muttableSet = Sets.newHashSet();
    ImmutableSet<String> immutableSet = ImmutableSet.copyOf(muttableSet);

    Map<String, String> muttableMap = Maps.newHashMap();
    ImmutableMap<String, String> imuttableMap = ImmutableMap.copyOf(muttableMap);
    System.out.println(immutableList);
  }

  @Test
  public void givenStandardCollection_testCreateImmutableCollectionsWithBuilder_thenOk() {
    List<String> muttableList = Lists.newArrayList();
    muttableList.add("1");
    muttableList.add("2");
    muttableList.add("3");
    ImmutableList<String> immutableList =
        ImmutableList.<String>builder().addAll(muttableList).build();

    Set<String> muttableSet = Sets.newHashSet();
    ImmutableSet<String> immutableSet =
        ImmutableSet.<String>builder().addAll(muttableSet).build();

    Map<String, String> muttableMap = Maps.newHashMap();
    ImmutableMap<String, String> imuttableMap =
        ImmutableMap.<String, String>builder().putAll(muttableMap).build();
    System.out.println(immutableList);
  }
}
