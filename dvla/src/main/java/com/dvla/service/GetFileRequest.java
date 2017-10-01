package com.dvla.service;

public class GetFileRequest {

	private String Filename;
	private String MimeType;
	private String FileSize;
	private String Extension;
	
	public GetFileRequest() {
		super();
	}
	
	public GetFileRequest(String filename, String mimeType, String fileSize, String extension) {
		super();
		Filename = filename;
		MimeType = mimeType;
		FileSize = fileSize;
		Extension = extension;
	}
	
	public String getFilename() {
		return Filename;
	}
	
	public void setFilename(String filename) {
		Filename = filename;
	}
	
	public String getMimeType() {
		return MimeType;
	}
	
	public void setMimeType(String mimeType) {
		MimeType = mimeType;
	}
	
	public String getFileSize() {
		return FileSize;
	}
	
	public void setFileSize(String fileSize) {
		FileSize = fileSize;
	}
	
	public String getExtension() {
		return Extension;
	}
	
	public void setExtension(String extension) {
		Extension = extension;
	}
	
	@Override
	public String toString() {
		return "DvlaFiles [Filename=" + Filename + ", MimeType=" + MimeType + ", FileSize=" + FileSize + ", Extension="
				+ Extension + "]";
	}

}
