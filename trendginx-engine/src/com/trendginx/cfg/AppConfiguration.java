package com.trendginx.cfg;

public class AppConfiguration
{
	private static String twt_consumer_key = "";
	private static String twt_consumer_secret = "";
	private static String twt_oauth_access_token = "";
	private static String twt_oauth_access_token_secret = "";
	private static int noRequestProcessorThreads=0;
	private static int twtLimit=0;
	private static String gmailUsername="";
	private static String gmailPassword="";
	private static String stopWordsFile="";
	private static String url;
	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		AppConfiguration.url = url;
	}

	public static String getStopWordsFile()
	{
		return stopWordsFile;
	}

	public static void setStopWordsFile(String stopWordsFile)
	{
		AppConfiguration.stopWordsFile = stopWordsFile;
	}

	public static String getGmailUsername()
	{
		return gmailUsername;
	}

	public static void setGmailUsername(String gmailUsername)
	{
		AppConfiguration.gmailUsername = gmailUsername;
	}

	public static String getGmailPassword()
	{
		return gmailPassword;
	}

	public static void setGmailPassword(String gmailPassword)
	{
		AppConfiguration.gmailPassword = gmailPassword;
	}

	public static int getTwtLimit()
	{
		return twtLimit;
	}

	public static void setTwtLimit(int twtLimit)
	{
		AppConfiguration.twtLimit = twtLimit;
	}

	public static int getNoRequestProcessorThreads()
	{
		return noRequestProcessorThreads;
	}

	public static void setNoRequestProcessorThreads(int noRequestProcessorThreads)
	{
		AppConfiguration.noRequestProcessorThreads = noRequestProcessorThreads;
	}

	public static String getTwt_consumer_key()
	{
		return twt_consumer_key;
	}

	public static void setTwt_consumer_key(String twt_consumer_key)
	{
		AppConfiguration.twt_consumer_key = twt_consumer_key;
	}

	public static String getTwt_consumer_secret()
	{
		return twt_consumer_secret;
	}

	public static void setTwt_consumer_secret(String twt_consumer_secret)
	{
		AppConfiguration.twt_consumer_secret = twt_consumer_secret;
	}

	public static String getTwt_oauth_access_token()
	{
		return twt_oauth_access_token;
	}

	public static void setTwt_oauth_access_token(String twt_oauth_access_token)
	{
		AppConfiguration.twt_oauth_access_token = twt_oauth_access_token;
	}

	public static String getTwt_oauth_access_token_secret()
	{
		return twt_oauth_access_token_secret;
	}

	public static void setTwt_oauth_access_token_secret(String twt_oauth_access_token_secret)
	{
		AppConfiguration.twt_oauth_access_token_secret = twt_oauth_access_token_secret;
	}
}
