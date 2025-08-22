package summarizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Summarizer {
	private int summaryLength;
	private String source=""; 
	public ArrayList<String> callSummarizer(int summaryLength,String source)
	{
		this.summaryLength=summaryLength;
		this.source=source;
		GenerateSummary generateSummary = new GenerateSummary(this.source);
		return (generateSummary.summarize(this.summaryLength));
	}
	      
 }

