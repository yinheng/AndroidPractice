package com.yh.widgetdemo;

import android.content.content;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppcontent() {
        // content of the app under test.
        content appcontent = InstrumentationRegistry.getInstrumentation().getTargetcontent();
        assertEquals("com.yh.widgetdemo", appcontent.getPackageName());
    }
}