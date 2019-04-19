package com.stitane.tdd.tests;

import com.stitane.tdd.examples.StringCalculator;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Create a simple String calculator with a method int Add(string numbers)
 * The method can take 0, 1 or 2 numbers, and will return their sum (for an empty string it will return 0) for example “” or “1” or “1,2”
 * Allow the Add method to handle an unknown amount of numbers
 * Allow the Add method to handle new lines between numbers (instead of commas).
 * The following input is ok: “1\n2,3” (will equal 6)
 * Support different delimiters
 * To change a delimiter, the beginning of the string will contain a separate line that looks like this: “//[delimiter]\n[numbers…]” for example “//;\n1;2” should return three where the default delimiter is ‘;’ .
 * The first line is optional. All existing scenarios should still be supported
 * Calling Add with a negative number will throw an exception “negatives not allowed” – and the negative that was passed. If there are multiple negatives, show all of them in the exception message stop here if you are a beginner.
 * Numbers bigger than 1000 should be ignored, so adding 2 + 1001 = 2
 * Delimiters can be of any length with the following format: “//[delimiter]\n” for example: “//[—]\n1—2—3” should return 6
 * Allow multiple delimiters like this: “//[delim1][delim2]\n” for example “//[-][%]\n1-2%3” should return 6.
 * Make sure you can also handle multiple delimiters with length longer than one char
 */
public class StringCalculatorTest {

    @Test(expected = RuntimeException.class)
    public final void whenNonNumberIsUsedThenExceptionIsThrown() {
        StringCalculator.add( "1,X");
    }
    @Test(expected = RuntimeException.class)
    @Ignore
    public final void whenMoreThan2NumbersAreUsedThenExceptionIsThrown() {
        StringCalculator.add( "1,2,3");
    }
    @Test
    public final void when2NumbersAreUsedThenNoExceptionIsThrown() {
        int sum = StringCalculator.add( "1,2");
        Assert.assertEquals(3, sum);
    }

    @Test
    public final void whenPassEmptyStringThenReturn0() {
        int sum = StringCalculator.add( "");
        Assert.assertEquals(0, sum);
    }
    @Test
    public final void whenOneNumberIsUsedThenReturnValueIsThatSameNumber() {
        Assert.assertEquals(3, StringCalculator.add( "3"));
    }

    @Test
    public final void whenTwoNumbersAreUsedThenReturnValueIsTheirSum() {
        Assert.assertEquals(9, StringCalculator.add( "3,6"));
    }

    @Test
    public final void whenAnyNumberOfNumbersIsUsedThenReturnValuesAreTheirSums() {
        Assert.assertEquals(3+6+15+18+46+33, StringCalculator.add( "3,6,15,18,46,33"));
    }
    @Test
    public final void whenNewLineIsUsedBetweenNumbersThenReturnValuesAreTheirSums() {
        Assert.assertEquals(3+6+15, StringCalculator.add( "3,6n15"));
    }
    @Test
    public final void whenDelimiterIsSpecifiedThenItIsUsedToSeparateNumbers() {
        Assert.assertEquals(3+6+15, StringCalculator.add( "//;n3;6;15"));
    }
    @Test(expected = RuntimeException.class)
    public final void whenNegativeNumberIsUsedThenRuntimeExceptionIsThrown() {
        StringCalculator.add( "3,-6,15,18,46,33");
    }
    @Test
    public final void whenNegativeNumbersAreUsedThenRuntimeExceptionIsThrown() {
        RuntimeException exception = null;
        try {
            StringCalculator.add( "3,-6,15,-18,46,33");
        } catch (RuntimeException e) {
            exception = e;
        }
        Assert.assertNotNull(exception);
        Assert.assertEquals("Negatives not allowed: [-6, -18]", exception.getMessage());
    }

    @Test
    public final void whenOneOrMoreNumbersAreGreaterThan1000IsUsedThenItIsNotIncludedInSum() {
        Assert.assertEquals(3+1000+6, StringCalculator.add( "3,1000,1001,6,1234"));
    }

    @Test
    public final void delimitersCanBeOfAnyLength() {
        Assert.assertEquals(1+2+3, StringCalculator.add( "//—n1—2—3"));
    }
    @Test
    public final void allowMultipleDelimiters() {
        Assert.assertEquals(1+2+3, StringCalculator.add( "//[-][%]n1-2%3"));
    }
}
