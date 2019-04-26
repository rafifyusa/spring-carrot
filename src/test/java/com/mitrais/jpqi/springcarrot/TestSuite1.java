package com.mitrais.jpqi.springcarrot;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@Suite.SuiteClasses(value = {TestSelenium.class, TestSelenium2.class})
public class TestSuite1 {
}

