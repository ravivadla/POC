package com.poc.exercise;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import Interface.RegexTextReplacement;

/**
 * Use log.debug or log.error where ever system.out or system.err for cleaner code. i want to keep it simple to make sure it compiles and run for you
 * @author Ravi vadla
 *
 */
public class RegexTextReplacementInFiles {
	
	// Gives the processing file count
	private static int fileCount = 0;
	// Map holding the replaced word to number of occurrence mapping
	private static Map<String, Integer> occuranceMap = new HashMap<String, Integer>();
	private static List<Entry<String, Integer>> list = null;
	private static Set<Entry<String, Integer>> set;

/**
 * Objective of the process method is browse through the folder structure and files, replace the first matched word 
 * in match group to regex pattern and write the files to their corresponding location with .processed attached
 * @param startingDir
 * @param regexPattern
 * @param replacement
 * @param fileAcceptPattern
 */
	@SuppressWarnings("unchecked")
	public static void process(String startingDir, String regexPattern, String replacement, String fileAcceptPattern) {
		RegexTextReplacement rex = new RegexTextReplacementImpl();
		File dir = new File(startingDir);
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				if(file.isDirectory()){
					// recursion to handle the looping through the subfolders
					process(file.getAbsolutePath(), regexPattern, replacement, fileAcceptPattern);	
				}else if(checkFileType(file, fileAcceptPattern)){
					fileCount++;
					// method to handle files in folders
					occuranceMap = rex.processFile(file,fileAcceptPattern, regexPattern, replacement, occuranceMap);
					set = occuranceMap.entrySet();
					list = new ArrayList<Entry<String, Integer>>(set);

				}
			}
		} else if(dir.list()==null && checkFileType(dir, fileAcceptPattern)){
			// code to handle single file at root
			fileCount++;
			occuranceMap = rex.processFile(dir,fileAcceptPattern, regexPattern, replacement, occuranceMap);
			set = occuranceMap.entrySet();
			list = new ArrayList<Entry<String, Integer>>(set);

		}
	}
	
	/**
	 * Implemented only to execute the JUNIT
	 * @param startingDir
	 * @param regexPattern
	 * @param replacement
	 * @param fileAcceptPattern
	 * @return
	 */
	public static List<Entry<String, Integer>> callProcess(String startingDir, String regexPattern, String replacement, String fileAcceptPattern){
		if ((startingDir != null && !"".equals(startingDir)) 
				&& (regexPattern != null && !"".equals(regexPattern))
					&& (replacement != null && !"".equals(replacement))){
			process(startingDir, regexPattern, replacement, fileAcceptPattern);
			if(list!=null){
				for (Map.Entry<String, Integer> entry : list) {
					System.out.println(entry.getKey() + " : " + entry.getValue() + " occurence");
				}
			}
		} else {
			System.out.println("Expected at least 3 parameter"); // use log4j instead of system.out
		}
		return list;
	}
	
	/**
	 * core logic to generate files
	 * @param file
	 * @param fileAcceptPattern
	 * @param regexPattern
	 * @param replacement
	 * @param flag
	 */
	/*private static void processFile(File file, String fileAcceptPattern, String regexPattern, String replacement, boolean flag){
			
		BufferedReader reader = null;
		FileWriter writer = null;
			fileCount++;
			String outputFileName = "";
			if(flag){
				outputFileName = file + ".processed";
			}else{
				outputFileName = file + "\\" + file.getName() + ".processed";
			}
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
								occuranceMap.put(newText, (occuranceMap.get(newText) + 1));
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
				set = occuranceMap.entrySet();
				list = new ArrayList<Entry<String, Integer>>(set);
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
	}*/
	
	/**
	 * method to check the condition on the file as per file acceptance pattern
	 * @param file
	 * @param fileAcceptPattern
	 * @return
	 */
	private static boolean checkFileType(File file, String fileAcceptPattern){
		boolean conditionFlag=false;
		
		if(file.isFile() 
				&& !file.getName().contains(".processed") 
				&& ((fileAcceptPattern != null && file.getName().endsWith(fileAcceptPattern)) 
				|| (fileAcceptPattern==null || "".equals(fileAcceptPattern)
				))){
			
			conditionFlag=true;
		}
		return conditionFlag;
	}

	/**
	 * Initial main method that that validates on the input, call process and print the file counts and occurances mapping.
	 * @param args
	 */
	public static void main(String[] args) {

		String startingDir = null, regexPattern = null, replacement = null, fileAcceptPattern = null;
		if (args.length == 0 || args.length < 3) {
			System.err.println("Invalid command parameters");
			System.exit(0);
		}
		if (args.length != 0 && args.length >= 3) {
			startingDir = args[0];
			regexPattern = args[1];
			replacement = args[2];
		}
		if (args.length >= 4) {
			fileAcceptPattern = args[3];
		}
		if (startingDir != null) {
			process(startingDir, regexPattern, replacement, fileAcceptPattern);
		} else {
			System.out.println("Expected at least 3 parameter but got :" + args.length);
		}
		if(list!=null){
			for (Map.Entry<String, Integer> entry : list) {
				System.out.println(entry.getKey() + " : " + entry.getValue() + " occurence");
			}
		}
		System.out.println("Processing :" + fileCount + " files");
		System.out.println("Replaced from lan to : " + replacement);

	}

}
