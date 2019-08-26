package com.songy.drawdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/**
 * Description:
 *
 * @author by song on 2019-08-07.
 * email：bjay20080613@qq.com
 */
@RunWith(JUnit4.class)
public class FizzBuzzTest {
    @Test
    public void should_be_able_to_calculate() {
        String result=FizzBuzz.get(3);
        assertThat(result,is("fizz"));
    }
}
