package com.go.rest.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void before() {
        System.out.println("Before Scenario");
    }

    @After
    public void after() {
        System.out.println("After Scenario");
    }

}
