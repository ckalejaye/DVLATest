package com.dvla.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DvlaHelper {

	private static final String BASE_PATH = "C:\\Users\\User\\eclipse-workspace\\DVLATest\\CSV Files"; 
	
	public static List<DvlaFiles> listAllFiles() { 
		List<DvlaFiles> results = new ArrayList<DvlaFiles>();
		File[] files = new File(BASE_PATH).listFiles(); 

		for (File file : files) {
		    if (file.isFile()) { 
						results.add(
								new DvlaFiles(file.getName(), getMimeType(file), 
										String.valueOf(file.getTotalSpace()), 
										file.getPath().substring(file.getPath().length()-3)));
					 
		    }
		}
		return results;
	}
	
	
	@SuppressWarnings("deprecation")
	public static String getMimeType(File file) {
		String result = "unknown";
		try {
			result = file.toURL().openConnection().getContentType();
		} catch (MalformedURLException e) { 
			e.printStackTrace();
			 result = "unknown";
		} catch (IOException e) { 
			e.printStackTrace();
			result = "unknown";
		}
		
		//result = Files.probeContentType(file);
		return result;
	}

	public static byte[] getFile(GetFileRequest requestedFile) { 
		byte[] result = null;  
		Path fileLocation = Paths.get(BASE_PATH+"/"+requestedFile.getFilename());
		try {
			result = Files.readAllBytes(fileLocation);
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
		return result;
	}

}
