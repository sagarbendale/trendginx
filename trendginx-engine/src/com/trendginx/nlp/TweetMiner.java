package com.trendginx.nlp;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trendginx.bean.WordResult;
import com.trendginx.db.dao.NegWordsDAO;
import com.trendginx.db.dao.PosWordsDAO;
import com.trendginx.nlp.frequency.ReadDB;
import com.trendginx.nlp.frequency.StopWords;
import com.trendginx.nlp.frequency.TrendFinder;
import com.trendginx.workers.RequestProcessor;

public class TweetMiner {

	static Logger logger = LoggerFactory.getLogger(TweetMiner.class);

	public void proceess(int reqId, ArrayList<String> ignoreList) {

		TrendFinder trendFinder = new TrendFinder();
		ReadDB rd = new ReadDB();
		ArrayList<String> tempArrayList = null;
		StopWords.setIgnoreList(ignoreList);
		WordResult wordResult = null;
		NegWordsDAO negWordsDAO = new NegWordsDAO();
		PosWordsDAO posWordsDAO = new PosWordsDAO();

		logger.info("Mining Negative Words");

		trendFinder.generateMap(rd.readNegativeTweetFromReqID(reqId));
		tempArrayList = (ArrayList<String>) trendFinder.getTopTrend(15);

		for (String string : tempArrayList) {
			if (string.contains(":")) {
				String[] words = string.split(":");
				wordResult = new WordResult();

				wordResult.setReq_id(reqId);
				wordResult.setWord(words[0]);
				wordResult.setCount(Integer.parseInt(words[1]));
				negWordsDAO.insertRecord(wordResult);
			}
		}

		logger.info("Negative Words Mining Completed");

		logger.info("Mining Positive Words");
		trendFinder.generateMap(rd.readPositiveTweetFromReqID(reqId));
		logger.info("Map Generated");
		tempArrayList = (ArrayList<String>) trendFinder.getTopTrend(15);
		logger.info("Processing Words");

		for (String string : tempArrayList) {
			if (string.contains(":")) {
				String[] words = string.split(":");
				wordResult = new WordResult();
				wordResult.setReq_id(reqId);
				wordResult.setWord(words[0]);
				wordResult.setCount(Integer.parseInt(words[1]));
				posWordsDAO.insertRecord(wordResult);
			}
		}
		logger.info("Positive Words Mining Completed");

		StopWords.resetIgnoreList();

	}

}
