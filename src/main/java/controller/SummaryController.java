package controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import models.Summary;
import models.Test;
import models.User;
import models.UserHistory;
import services.SummaryService;
import services.UserHistoryService;
import summarizer.Summarizer;


@RestController
@RequestMapping("/ats")
public class SummaryController {
	
	private User user;	
	
	private ArrayList<String> fs;	
	private UserHistory userHistory;
	
	@Autowired
	private UserHistoryService userHistoryService;
	
	@Autowired
	private SummaryService summaryService;
	
	@RequestMapping(value="/getcontent", method=RequestMethod.POST)
	public Summary getSourceContent(@RequestBody Summary summary)
	{
		return summaryService.getSourceContent(summary);
	}
	
	@RequestMapping(value="/summary", method=RequestMethod.POST)
	public Summary getsummary(@RequestBody Summary summary)
	{
		StopWatch watch = new StopWatch();	//to calculate processing time
	    watch.start();
	    
		String tempSummary="";
		String url=summary.getSourceUrl();
		int sno=summary.getSentenceNumber();
		String source=summary.getSourceText();	
			
		Summarizer summarizer= new Summarizer();
		fs= new ArrayList<>(summarizer.callSummarizer(sno, source));
		
		watch.stop();	//Stop the watch
		System.out.println("The Summary is: ");
		for(String x: fs)
		{
			System.out.println(x);
			tempSummary=tempSummary+x;			
		}
		summary.setFinalSummary(fs);
		if((summary.isUserActive())!=false) {
			user= new User();
			
			//Save the histroy
			user.setId(summary.getUserId());
			userHistory= new UserHistory();
			
			userHistory.setSummary(tempSummary);
			userHistory.setTimeConsumed(watch.getTotalTimeSeconds());
			userHistory.setUser(user);
			
			if(url==null) {
				userHistory.setSource(source);
			}else {
				userHistory.setSource(url);
			}
			userHistoryService.saveHistory(userHistory);
		}
		System.out.println("Time Elapsed in millisec: "+watch.getTotalTimeMillis());
		System.out.println("Time Elapsed in sec: "+watch.getTotalTimeSeconds());
		System.out.println("Time Elapsed in millisec: "+watch.getTotalTimeMillis());
		System.out.println("Time Elapsed in sec: "+watch.getTotalTimeSeconds());
		
		return summary;
	
	}

}
