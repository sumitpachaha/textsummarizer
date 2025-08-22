package services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import models.Summary;

@Service
public class SummaryService {
	
	public Summary getSourceContent(Summary summary)
	{
		String url=summary.getSourceUrl();
		String userAgent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36";
		String a="";
		try {
	      Document document = Jsoup.connect(url).userAgent(userAgent).get();

	      String html =String.valueOf(document.body());
	      Document doc = Jsoup.parse(html);
	      Elements paragraphs = doc.getElementsByTag("p");
	      a=paragraphs.text();
	      summary.setSourceText(a);
	      return summary;
	     }
		catch(Exception e)
			{
				System.out.println("Error in the URL"+e);
				return null;
					
			}
		
	}
	

}
