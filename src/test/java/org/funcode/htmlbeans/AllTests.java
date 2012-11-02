package org.funcode.htmlbeans;

import org.funcode.htmlbeans.wrappers.TestObjectWrapperDoGood;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import org.funcode.htmlbeans.mapping.TestJson2JavaMapper;

@RunWith(value = Suite.class)
@SuiteClasses(value = { TestObjectWrapperDoGood.class,
        TestJson2JavaMapper.class })
public class AllTests {

}
