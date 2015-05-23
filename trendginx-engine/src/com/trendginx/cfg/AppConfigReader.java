package com.trendginx.cfg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trendginx.util.PropertyFileParser;



public class AppConfigReader
{
	static Logger logger = LoggerFactory.getLogger(AppConfigReader.class);

	public static void readAndSetUp(String filepath)
	{

		try
		{
			PropertyFileParser propertFileParser = new PropertyFileParser(filepath);
			logger.info("Setting Twtitter Consumer Key");
			AppConfiguration.setTwt_consumer_key(propertFileParser.getValue("twt_consumer_key"));
			logger.info("Setting Twtitter Secret Key");
			AppConfiguration.setTwt_consumer_secret(propertFileParser.getValue("twt_consumer_secret"));
			logger.info("Setting Twtitter OAuth Token");
			AppConfiguration.setTwt_oauth_access_token(propertFileParser.getValue("twt_oauth_access_token"));
			logger.info("Setting Twtitter OAuth Token Secret");
			AppConfiguration.setTwt_oauth_access_token_secret(propertFileParser.getValue("twt_oauth_access_token_secret"));
			logger.info("Setting RequestProcessorThreads");
			AppConfiguration.setNoRequestProcessorThreads(Integer.parseInt(propertFileParser.getValue("no_request_processor_threads")));
			logger.info("Setting TweetLimit");
			AppConfiguration.setTwtLimit(Integer.parseInt(propertFileParser.getValue("twt_limit")));	
			logger.info("Setting Stop Words Filepath");
			AppConfiguration.setStopWordsFile(propertFileParser.getValue("stopwords_file"));
			logger.info("Setting Gmail Outgoing Account");
			AppConfiguration.setGmailUsername(propertFileParser.getValue("gmail_username"));
			AppConfiguration.setGmailPassword(propertFileParser.getValue("gmail_password"));
			logger.info("Reading Url");
			AppConfiguration.setUrl(propertFileParser.getValue("url"));

			
		}
		catch (Exception e)
		{
			logger.error(e.toString());
			logger.error("Error while reading configuration file.Terminating Program");
			System.exit(0);
			e.printStackTrace();
		}
	}
}
