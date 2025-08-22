package controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import models.Summary;
import models.Upload;
import services.UploadService;  


@RestController
@RequestMapping("/ats")
public class UploadController {  
	@Autowired
	private UploadService uploadService;
  @RequestMapping(value="/uploadedContent", method=RequestMethod.POST)
    	public Upload getSourceContent(@RequestBody Upload upload)
    	{
	  		String filePath=upload.getFilePath();
	  		upload.setFileContent(uploadService.getFileContents(filePath));
	  		return upload;
	  		
    	} 
}  