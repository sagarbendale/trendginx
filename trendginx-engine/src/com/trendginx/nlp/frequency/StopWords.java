package com.trendginx.nlp.frequency;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.trendginx.cfg.AppConfiguration;

public class StopWords {

	String stopWords;
	static ArrayList<String> ignoreList;

	public StopWords() {
		stopWords = readFile(AppConfiguration.getStopWordsFile(),
				Charset.defaultCharset());
	}

	public String readFile(String path, Charset encoding) {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(encoded, encoding);
	}

	public String removeStopWords(String sentence) {

		for (String str : ignoreList) {
			stopWords += ("\n" + str);
		}

		sentence = removeUrl(sentence);
		sentence = removeSymbols(sentence);

		String[] input = sentence.split(",");
		String output = "";
		for (String string : input) {
			string = removeUrl(string);
			string = removeSymbols(string);
			if (!stopWords.toLowerCase().contains(string.toLowerCase())) {
				output = output + string + ":";
			}
		}
		return output;
	}

	private String removeSymbols(String string) {
		string = string.replaceAll("[^a-zA-Z0-9]+", ",");
		return string;

	}

	private String removeUrl(String commentstr) {
		try 
		{
			
			commentstr=commentstr.replaceAll("http://", "");
//			String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
//			Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
//			Matcher m = p.matcher(commentstr);
//			int i = 0;
//			while (m.find()) {
//				commentstr = commentstr.replaceAll(m.group(i), "").trim();
//				i++;
//			}
//			return commentstr;

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return commentstr;

	}

	public static void setIgnoreList(ArrayList<String> List) {
		ignoreList = List;
	}

	public static void resetIgnoreList() {
		ignoreList = new ArrayList<String>();
	}

}
