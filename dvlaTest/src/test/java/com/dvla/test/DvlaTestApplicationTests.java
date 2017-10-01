package com.dvla.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


public class DvlaTestApplicationTests {
 
	@Test
	public void contextLoads() { 
		Assert.assertEquals(36, System.getProperty("user.dir"));
	}

}
