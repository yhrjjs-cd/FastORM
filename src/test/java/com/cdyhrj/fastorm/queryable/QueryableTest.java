package com.cdyhrj.fastorm.queryable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class QueryableTest {
    //Field entityClazz of type Class - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    Queryable queryable = new Queryable(Class.forName("com.cdyhrj.fastorm.queryable.Queryable"));

    @Test
    void testJoin() {
        Queryable result = queryable.join(null, null);
        Assertions.assertEquals(new Queryable(Class.forName("com.cdyhrj.fastorm.queryable.Queryable")), result);
    }

    @Test
    void testJoin2() {
        Queryable result = queryable.join(null, null, null);
        Assertions.assertEquals(new Queryable(Class.forName("com.cdyhrj.fastorm.queryable.Queryable")), result);
    }

    @Test
    void testParam() {
        Queryable result = queryable.param(null, "value");
        Assertions.assertEquals(new Queryable(Class.forName("com.cdyhrj.fastorm.queryable.Queryable")), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme