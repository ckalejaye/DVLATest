package com.dvla.service;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RestControllerAdvice
public class DvlaServiceController {
	
	@RequestMapping(value = "/file/list", method = RequestMethod.GET)
	public ResponseEntity<List<DvlaFiles>> listFiles() {		
		List<DvlaFiles> availableFiles = DvlaHelper.listAllFiles(); 
		return new ResponseEntity<List<DvlaFiles>>(availableFiles, HttpStatus.OK); 
	}
	
	@RequestMapping(value = "/file/get", method = RequestMethod.POST)
	public HttpEntity<byte[]> getFiles(@RequestBody GetFileRequest requestedFile,  UriComponentsBuilder ucBuilder) {		
		byte[] documentBody = DvlaHelper.getFile(requestedFile);

	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.TEXT_PLAIN);
	    header.set(HttpHeaders.CONTENT_DISPOSITION,
	                   "attachment; filename=XXXXXXXX.csv");
	    header.setContentLength(documentBody.length);
	    
	    /* text/csv
	     * 
	     try {
		        String filePathToBeServed = //complete file name with path;
		        File fileToDownload = new File(filePathToBeServed);
		        InputStream inputStream = new FileInputStream(fileToDownload);
		        response.setContentType("application/force-download");
		        response.setHeader("Content-Disposition", "attachment; filename="+fileName+".txt"); 
		        IOUtils.copy(inputStream, response.getOutputStream());
		        response.flushBuffer();
		        inputStream.close();
		    } catch (Exception e){
		        LOGGER.debug("Request could not be completed at this moment. Please try again.");
		        e.printStackTrace();
		    }
	     */

	    return new HttpEntity<byte[]>(documentBody, header);
	}
	
	@RequestMapping("/")
	@ResponseBody
	public String index() {
		 
		return System.getProperty("user.dir");
	}

}
