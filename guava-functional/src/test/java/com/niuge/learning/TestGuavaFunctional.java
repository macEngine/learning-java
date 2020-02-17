package com.niuge.learning;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.number.OrderingComparison.lessThan;

public class TestGuavaFunctional {

  // filter a collection by a condition (custom Predicate)
  @Test
  public void givenPredicate_testFilter_thenOk() {
    List<Integer> numbers = Lists.newArrayList(1, 2, 3, 6, 10, 34, 57, 89);

    // Predicate<泛型>（断言，断定），是Guava中的一个基础接口，其内部只有一个方法boolean apply(T input)，这个方法输入是一个泛型对象，输出是一个布尔值，非常简单
    Predicate<Integer> acceptEven = new Predicate<Integer>() {
      @Override
      public boolean apply(Integer number) {
        return (number % 2) == 0;
      }
    };

    // 由于jdk已经占据了Collections类名 因此Google在Collection基础上创建了Collections2。
    List<Integer> evenNumbers = Lists.newArrayList(Collections2.filter(numbers, acceptEven));
    System.out.println(evenNumbers);

    Integer found = Collections.binarySearch(evenNumbers, 57);
    System.out.println(found);
    assertThat(found, lessThan(0));
  }

  @Test
  public void givenPredicateNotNull_testFilter_thenOk() {
    List<String> withNulls = Lists.newArrayList("a", "bc", null, "def");
    Iterable<String> withoutNuls = Iterables.filter(withNulls, Predicates.notNull());
    assertTrue(Iterables.all(withoutNuls, Predicates.notNull()));
  }

  @Test
  public void givenPredicateAcceptEven_testIterablesAllthenOk() {
    List<Integer> evenNumbers = Lists.newArrayList(2, 6, 8, 10, 34, 90);
    Predicate<Integer> acceptEven = new Predicate<Integer>() {
      @Override
      public boolean apply(Integer number) {
        return (number % 2) == 0;
      }
    };

    // Iterables.all第一次使用，参数传递了函数，就叫做函数式编程。
    assertTrue(Iterables.all(evenNumbers, acceptEven));
    System.out.println(evenNumbers);
  }

  @Test
  public void givenPredicateAcceptOld_testIterablesAll_thenOk() {
    List<Integer> evenNumbers = Lists.newArrayList(2, 6, 8, 10, 34, 90);
    Predicate<Integer> acceptOdd = new Predicate<Integer>() {
      @Override
      public boolean apply(Integer number) {
        return (number % 2) != 0;
      }
    };
    assertTrue(Iterables.all(evenNumbers, Predicates.not(acceptOdd)));
  }

  @Test
  public void givenFunctionsToString_testTransform_thenOk() {
    List<Integer> numbers = Lists.newArrayList(1, 2, 3);
    List<String> asStrings = Lists.transform(numbers, Functions.toStringFunction());
    System.out.println(asStrings);
    assertThat(asStrings, contains("1", "2", "3"));
  }

  @Test
  public void givenOrdering_testSortedCopy_thenOk() {
    List<Integer> numbers = Arrays.asList(2, 1, 11, 100, 8, 14);
    Ordering<Object> ordering = Ordering.natural().onResultOf(Functions.toStringFunction());
    List<Integer> inAlphabeticalOrder = ordering.sortedCopy(numbers);
    System.out.println(inAlphabeticalOrder);
    List<Integer> correctAlphabeticalOrder = Lists.newArrayList(1, 100, 11, 14, 2, 8);
    assertThat(correctAlphabeticalOrder, equalTo(inAlphabeticalOrder));
  }


  @Test
  public void givenComplexFunction_testTransform_thenOk() {
    List<Integer> numbers = Arrays.asList(2, 1, 11, 100, 8, 14);
    Predicate<Integer> acceptEvenNumber = new Predicate<Integer>() {
      @Override
      public boolean apply(Integer number) {
        return (number % 2) == 0;
      }
    };
    Function<Integer, Integer> powerOfTwo = new Function<Integer, Integer>() {
      @Override
      public Integer apply(Integer input) {
        return (int) Math.pow(input, 2);
      }
    };

    // FluentIterable 是guava集合类中常用的一个类，主要用于过滤、转换集合中的数据；
    // FluentIterable是一个抽象类，实现了Iterable接口，大多数方法都返回FluentIterable对象，这也是guava的思想之一。
    FluentIterable<Integer> powerOfTwoOnlyForEvenNumbers =
        FluentIterable.from(numbers).filter(acceptEvenNumber).transform(powerOfTwo);
    assertThat(powerOfTwoOnlyForEvenNumbers, contains(4, 10000, 64, 196));
  }

  @Test
  public void givenFunction_testTransform_thenOk() {
    List<Integer> numbers = Arrays.asList(2, 3);
    Function<Integer, Integer> powerOfTwo = new Function<Integer, Integer>() {
      @Override
      public Integer apply(Integer input) {
        return (int) Math.pow(input, 2);
      }
    };
    List<Integer> result = Lists.transform(numbers,
        Functions.compose(powerOfTwo, powerOfTwo));
    assertThat(result, contains(16, 81));
  }

  @Test
  public void givenPowerOfTwo_testAsMap_thenOk() {
    Function<Integer, Integer> powerOfTwo = new Function<Integer, Integer>() {
      @Override
      public Integer apply(Integer input) {
        return (int) Math.pow(input, 2);
      }
    };
    Set<Integer> lowNumbers = Sets.newHashSet(2, 3, 4);

    // asMap和toMap是不相同的。
    Map<Integer, Integer> numberToPowerOfTwoMuttable = Maps.asMap(lowNumbers, powerOfTwo);
    Map<Integer, Integer> numberToPowerOfTwoImuttable = Maps.toMap(lowNumbers, powerOfTwo);
    System.out.println(numberToPowerOfTwoMuttable);
    System.out.println(numberToPowerOfTwoImuttable);
    assertThat(numberToPowerOfTwoMuttable.get(2), equalTo(4));
    assertThat(numberToPowerOfTwoImuttable.get(2), equalTo(4));
  }

  // create a Function out of a Predicate
  @Test
  public void givenPredicate_testFunctions_thenOk() {
    List<Integer> numbers = Lists.newArrayList(1, 2, 3, 6);
    Predicate<Integer> acceptEvenNumber = new Predicate<Integer>() {
      @Override
      public boolean apply(Integer number) {
        return (number % 2) == 0;
      }
    };
    Function<Integer, Boolean> isEventNumberFunction = Functions.forPredicate(acceptEvenNumber);
    List<Boolean> areNumbersEven = Lists.transform(numbers, isEventNumberFunction);
    System.out.println(areNumbersEven);

    assertThat(areNumbersEven, contains(false, true, false, true));
  }
}
