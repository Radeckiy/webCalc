package com.radeckiy.localPropsLoader;

import org.junit.Assert;
import org.junit.Test;

public class Main {
    private LocalProps testProps = new LocalProps();

    @Test
    public void testPropsLoaderIsNotNull() {
        Assert.assertNotNull(testProps);
    }

    @Test
    public void testLoadProps() {
        Assert.assertEquals("Test", testProps.getProperty("test.message"));
    }
}
