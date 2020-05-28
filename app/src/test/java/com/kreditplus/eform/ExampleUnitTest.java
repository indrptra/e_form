package com.kreditplus.eform;


import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        try {
            assertEquals(4, 2 + 2);
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.e("UnitTest", String.valueOf(e));
        }
    }
}