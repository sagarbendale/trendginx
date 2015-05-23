package com.trendginx.nlp.frequency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.trendginx.nlp.NLP;
import com.trendginx.nlp.util.ComparableObj;

public class TrendFinder {
	HashMap<String, Integer> trend=null;
	StopWords stopWords=new StopWords();
	NLP nlp= new NLP();

	public void generateMap(String text)
	{
		String[] temp=text.split("-->count");
		int count=Integer.parseInt(temp[1]);
		String sentence=temp[0];
		
		sentence=stopWords.removeStopWords(sentence);
		String[] input=sentence.split(":");
		for (String word : input) {
			
			word=word.toLowerCase();
			if(trend.containsKey(word))
			{
				trend.put(word, trend.get(word)+count);
			}
			else {
				if(count==0)
				{
					trend.put(word, 1);
				}
				else {
					trend.put(word,count);
				}
			}
		}
	}

	public void generateMap(ArrayList<String> input)
	{
		trend=new HashMap<String,Integer>();
		for (String string : input) {
			generateMap(string);
		}
	}

	public  List<String> getTopTrend(int n)
	{
		Entry<String,Integer> maxEntry = null;

		for(Entry<String,Integer> entry : trend.entrySet()) {
			if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
				maxEntry = entry;
			}
		}
		List<String> myTopOccurrence = findMaxOccurance(trend, n);
		return myTopOccurrence; 
	}


	public static List<String> findMaxOccurance(Map<String, Integer> map, int n) {
		List<ComparableObj> l = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : map.entrySet())
			l.add(new ComparableObj(entry.getKey(), entry.getValue()));

		Collections.sort(l);
		List<String> list = new ArrayList<>();
		for (ComparableObj w : l.subList(0, n))
			list.add(w.input + ":" + w.occurrences);
		return list;
	}
}