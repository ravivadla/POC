package com.poc.exercise;

import java.util.List;


import junit.framework.TestCase;

import org.junit.Test;

/**
 * 
 * @author Ravi vadla
 *
 */
public class RegexTextReplacementInFilesTest extends TestCase {
	
	// write before and after methods as implemented by testcase, but i donno if i am suppose to implement all simple junit, so write a sample method to demonstrate Junit skills
	/**
	 * Testing the entire package sample dir which has 5 files and 10 words
	 */

    @Test
    public void testCallProcess() {
    	String startingDir = null, regexPattern = null, replacement = null, fileAcceptPattern = null;
        startingDir = "sample_dir";
        regexPattern = "lan";
        replacement = "Ravi";
        fileAcceptPattern=".txt";
        List list = RegexTextReplacementInFiles.callProcess(startingDir, regexPattern, replacement, fileAcceptPattern);
        assertEquals(10, list.size());
       }
	}


//TODO: write rest of the junit testcases.