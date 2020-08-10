package com.datacvg.cases;

import com.beust.jcommander.internal.Lists;
import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;


public class TestNGRun {

    public static void main(String[] args) {
        TestNG testNG = new TestNG();
        List suites = Lists.newArrayList();

//        suites.add("src/main/resources/exceldriver.xml"); F:\WorkSpace\Datacvg\src\main\resources\testng.xml
//        suites.add("src/main/resources/testng.xml");
        suites.add("src/main/resources/testng.xml");

        testNG.setTestSuites(suites);

        testNG.run();
    }

}
