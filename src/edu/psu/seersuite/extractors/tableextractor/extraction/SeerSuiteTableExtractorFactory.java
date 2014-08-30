package edu.psu.seersuite.extractors.tableextractor.extraction;

import java.io.File;
import java.io.FileFilter;


public class SeerSuiteTableExtractorFactory {
	static class PDFFilter implements FileFilter {
		public boolean accept(File pathname) {
			if(pathname.canRead() && pathname.getName().endsWith(".pdf"))
				return true;
			return false;
		}
	}

	public static void main(String[] arguments) throws Exception {	
      	if(arguments.length == 0){
    		showUsage();
    	}
      	
      	else {
	    	String pdfFileDir = arguments[0];
	    	String fullnamepdf="";
	    	File pdfDir = new File(pdfFileDir);
	    	int counter = 1;
	    	if(!pdfDir.isDirectory()) {
	    		System.out.println("Need directory in this mode");
	    		System.exit(-1);
	    	}
	    	for (File fid: pdfDir.listFiles(new PDFFilter())) {
	    		fullnamepdf=pdfDir.getPath()+"/"+fid.getName();
				String tmpFilename1[]=fullnamepdf.split("/");
				String tmpFilename2[]=tmpFilename1[tmpFilename1.length-1].split(".pdf");
				
				//new AllPossibleCaptionsforEndline(fullnamepdf);
				String[] argsto={fullnamepdf,tmpFilename2[0]};
				SeerSuiteTableExtractor.main(argsto);
				System.out.println("File number "+counter+", filename: "+tmpFilename2[0]+ " processed");
				counter++;
	    	}
	    	System.out.println("Extraction Complete");
	    }
    }

    private static void showUsage() {
		System.out.println("For batch processing: ImageCaptionMentionExtractFactory <FULL PATH FOR FOLDER CONTAINING PDF FILES> \n");
    }

}
