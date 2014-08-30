/**
 * This package defines the core table extraction classes
 */
package edu.psu.seersuite.extractors.tableextractor.extraction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.lang.*;
import java.lang.reflect.Field;  

import edu.psu.seersuite.extractors.tableextractor.model.*;
import edu.psu.seersuite.extractors.tableextractor.*;

/**
 * This class extracts features to classify the header columns and original data columns of given a table,
 * and writes those features into a file conforming to weka .arff format. 
 * 
 * @author Jing
 */
public class ColFeatureExtractor {
	// class label
	private int classLabel;
	// header & data column classification features:
	private int colCellNum;  
	private int averageCellLength;
	private int colCharNum;  
	private float ratioDigitChar;
	private float ratioAlphaChar;
	private float ratioSymbolChar;
	private float fontSize;
	private float ratioFontBold;
	private float ratioFontItalic;
	private float ratioNumericCells; 
	private float ratioCellNums;   

    /**
     * Extract features used to classify the header and data columns
     * @param tc
     *        the candidate table to be extracted
     * @param featureFolder
     * 		  the path of the feature file to be stored
     */
	public void extractFeatures(TableCandidate tc, File predictFile) {
		ArrayList<TableColumn> cols = new ArrayList<TableColumn>();
		cols = tc.getColumns();  
		createWekaModel(predictFile);
		
    	for (int i = 0; i < cols.size(); ++i){ 		
    		if (i == 0) classLabel = 1;
    		else classLabel = 0;
    		ArrayList<TextPiece> pieces = new ArrayList<TextPiece>();
    		pieces = cols.get(i).getCells();
    		colCellNum = pieces.size();
    		
    		int boldNum = 0;
    		int italicNum = 0;
    		int digitChar = 0;
    		int alphaChar = 0;
    		int symbolChar = 0;
    		int numericCells = 0;  		
    		for (TextPiece piece : pieces){
    			if (piece == null)
    				continue;
    			String str = piece.getText();
    			colCharNum += str.length();
    			char[] chars = str.toCharArray();
    			for (int j = 0; j < chars.length; ++j){
    				if (Character.isDigit(chars[j])){
    					digitChar++;
    				}
    				else if (Character.isLetter(chars[j])){
    					alphaChar++;
    				}
    				else
    					symbolChar++;
    			}
    			if (piece.getFontBold())
    				boldNum++;
    			if (piece.getFontItalic())
    				italicNum++;
    			fontSize += piece.getFontSize();
    			if (isNumString(piece.getText()))
    				numericCells++;    				
    		} 		
    		averageCellLength = colCharNum / pieces.size();
    		ratioDigitChar = (float) digitChar / colCharNum;
    		ratioAlphaChar = (float) alphaChar / colCharNum;
    		ratioSymbolChar = (float) symbolChar / colCharNum;
    		fontSize = (float)fontSize / pieces.size();
    		ratioFontBold = (float) boldNum / pieces.size();
    		ratioFontItalic = (float) italicNum / pieces.size();
    		ratioNumericCells = (float) numericCells / pieces.size();
    		
    		if (i != cols.size()-1) {
    			ArrayList<TextPiece> nextPieces = new ArrayList<TextPiece>();
    			nextPieces = cols.get(i+1).getCells();
    			int nextRowCellNum = nextPieces.size();
    			ratioCellNums = (float) Math.abs(colCellNum - nextRowCellNum) / (colCellNum + nextRowCellNum);
    		}
    		
    		writeWekaModel(predictFile);
    	}   	
	}
	
    private boolean isNumString(String str) {
    	Pattern pattern = Pattern.compile("[0-9]*.[0-9]*");
        return pattern.matcher(str).matches();    
    }
    
    /**
     * Create the .arff file in a given directory path.
     * This file is used to predict the column categories of the given table
     * 
     * @param featureFolder
     *        the directory path of the file to be created
     */
    private void createWekaModel(File predictFile) {
       	try {	    	
	    	PrintWriter cfeatureFileWriter = new PrintWriter(new FileOutputStream(predictFile));
	    	cfeatureFileWriter.write("@RELATION HeaderDataClassification\n\n");
	    	cfeatureFileWriter.write("@ATTRIBUTE CellNum\t\tNUMERIC\n");
	    	cfeatureFileWriter.write("@ATTRIBUTE AvgCellLen\t\tNUMERIC\n");
	    	cfeatureFileWriter.write("@ATTRIBUTE CharNum\t\tNUMERIC\n");
	    	cfeatureFileWriter.write("@ATTRIBUTE DigitChRatio\t\tNUMERIC\n");
	    	cfeatureFileWriter.write("@ATTRIBUTE AlphaChRatio\t\tNUMERIC\n");
	    	cfeatureFileWriter.write("@ATTRIBUTE SymbolChRatio\tNUMERIC\n");
	    	cfeatureFileWriter.write("@ATTRIBUTE FontSize\t\tNUMERIC\n");
	    	cfeatureFileWriter.write("@ATTRIBUTE FontBold\t\tNUMERIC\n");
	    	cfeatureFileWriter.write("@ATTRIBUTE FontItalic\t\tNUMERIC\n");    	
	    	cfeatureFileWriter.write("@ATTRIBUTE NumCellRatio\t\tNUMERIC\n");
	    	cfeatureFileWriter.write("@ATTRIBUTE CellNumRatio\t\tNUMERIC\n");	    
	    	cfeatureFileWriter.write("@ATTRIBUTE class	{HeaderDataClassification-0, HeaderDataClassification-1}\n\n");
	    	cfeatureFileWriter.write("@DATA\n");
	    	
	    	if (cfeatureFileWriter != null){
	    		cfeatureFileWriter.close();
	    	}
	    	}
    	catch (FileNotFoundException e){
    		System.out.print(e);
    	}
    }
    
    /**
     * Write feature values into the .arff file been created.
     * 
     * @param featureFolder
     *        the directory path of the feature file 
     */
    private void writeWekaModel(File predictFile) {   	
    	try {
    		FileOutputStream out = new FileOutputStream(predictFile, true);
    		PrintWriter featureWriter = new PrintWriter (out);
		
        	try {
        		Class c = this.getClass();
        		Field[] fields = c.getDeclaredFields();
        		for (int i = 1; i < fields.length; ++i) {
        			fields[i].setAccessible(true);
        			String s = fields[i].get(this).toString();
        			if (s.length() > 4)
        				s = s.substring(0,4);
        			featureWriter.write(s + ",\t");
        		}
        		featureWriter.write("HeaderDataClassification-" + fields[0].get(this).toString());
        		featureWriter.write('\n');
        		featureWriter.close();
        	}
        	catch (IllegalAccessException e) {
        		System.out.print(e);
        	}
    	}
    	catch (FileNotFoundException e){
    		System.out.print(e);
    	}
    }
       
}