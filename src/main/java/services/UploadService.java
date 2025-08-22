package services;
import java.io.FileInputStream;  
import org.apache.poi.openxml4j.opc.OPCPackage;  
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;  
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;  

@Service
public class UploadService {  
	
	public String getFileContents(String filePath) {
		try{  
			FileInputStream fis = new FileInputStream(filePath);
			XWPFDocument file   = new XWPFDocument(OPCPackage.open(fis));  
	        XWPFWordExtractor ext = new XWPFWordExtractor(file);  
	        String content=ext.getText();
	        System.out.println(content); 
	        return content;
	        
		}catch(Exception e) {  
	       System.out.println(e);  
	       return null;
	    }
		
	}  
}  