package com.trendginx.nlp;


import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trendginx.bean.TwitterResult;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class NLP {
    static StanfordCoreNLP pipeline;
	static Logger logger = LoggerFactory.getLogger(NLP.class);

    public static void init(String filePath) 
    {
    	logger.info("Setting NLP Parameters");
        pipeline = new StanfordCoreNLP(filePath);
    }

    public String findSentiment(String tweet) {

    	logger.info(" NLP Processing "+tweet);
        int mainSentiment = 0;
        if (tweet != null && tweet.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(tweet);
            for (CoreMap sentence : annotation
            		.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }

            }
        }
        if (mainSentiment==2 || mainSentiment>4 || mainSentiment<0) {
        	return "Neutral";
		}
        else if (mainSentiment>2){
			return "Positive";
		}
        else {
			return "Negative";
		}
    }
    
    public ArrayList<TwitterResult> process(ArrayList<TwitterResult> input) 
    {
    	for (TwitterResult tweet : input) 
    	{
    		tweet.setSentiment(findSentiment(tweet.getComment()));    		
		}
		return input;
	}
    
    public String getLemma(String input)
    {
        Annotation document = pipeline.process(input);  
        String lemma="";
        for(CoreMap sentence: document.get(SentencesAnnotation.class))
        {    
            for(CoreLabel token: sentence.get(TokensAnnotation.class))
            {       
                lemma = token.get(LemmaAnnotation.class); 
            }
        }
		return lemma;
    }
}
