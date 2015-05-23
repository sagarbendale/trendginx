package com.trendginx.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trendginx.cfg.AppConfigReader;
import com.trendginx.cfg.AppConfiguration;
import com.trendginx.nlp.NLP;
import com.trendginx.workers.RequestProcessor;

public class RunMain
{
	static Logger logger = LoggerFactory.getLogger(RunMain.class);

	public static void main(String args[])
	{
		
		logger.info("Application Started...");
		if(args[0]==null || args[0].equals(""))
		{
			logger.error("No Config File Specified. Program Terminating");
			System.exit(0);
		}
		else
		{
			AppConfigReader.readAndSetUp(args[0]);

		}
		if(args[1]==null || args[1].equals(""))
		{
			logger.error("No NLP Config File Specified. Program Terminating");
			System.exit(0);
		}
		else
		{
			NLP.init(args[1]);

		}
		
		logger.info("Initial Configuration Setting Completed...");

		
		

		logger.info("Starting RequestProcessor Threads");
		new Thread()
		{
		    public void run() 
		    {
		    	for(int counter=0;counter<AppConfiguration.getNoRequestProcessorThreads();counter++)
				{
		    		logger.debug("Starting RequestProcessor Thread"+counter);

		    		new Thread(new RequestProcessor()).start();
				}
		    }
		}.start();
		
	

		
		
		
	}
}
