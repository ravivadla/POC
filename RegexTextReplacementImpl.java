package com.poc.exercise;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import Interface.RegexTextReplacement;

/**
 * 
 * @author Ravi vadla
 *
 */
public class RegexTextReplacementImpl implements RegexTextReplacement{

	
	public  Map processFile(File file, String fileAcceptPattern, String regexPattern, 
			String replacement, Map occuranceMap){
		
		BufferedReader reader = null;
		FileWriter writer = null;
			String outputFileName = "";
			outputFileName = file + ".processed";
			
			String newText = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				writer = new FileWriter(outputFileName);
				String line = "";
				while ((line = reader.readLine()) != null) {
					String[] token = line.split(" ");
					for (String s : token) {
						if(s.contains(",")){
							s = s.replace(",", "");
						}
						newText = s;
						//code block to match, replace first one and store the matching words
						if (newText.contains(regexPattern)) {
							if (occuranceMap.get(newText) != null) {
								int value = (int) occuranceMap.get(newText);
								occuranceMap.put(newText, value+1);
								newText = newText.replaceAll(regexPattern, replacement);
							} else {
								occuranceMap.put(newText, 1);
								newText = newText.replaceAll(regexPattern, replacement);
							}
						}
						writer.write(newText + " ");
					}
					writer.write("\r\n");
				}
				
			} catch (FileNotFoundException ne) {
				System.out.println("File not found : " + ne.getMessage());
			} catch (IOException oe){
				System.out.println("IO Exception :"+ oe.getMessage());
			} catch (Exception e){
				System.out.println("Exception :"+ e.getMessage());
			} finally {
				try {
					if(reader != null) reader.close();
					if(writer != null)writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return occuranceMap;
	}
}
