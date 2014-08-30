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
 * This class extracts features to classify the header rows and original data rows of given a table,
 * and writes those features into a file conforming to weka .arff format. 
 * 
 * @author Jing
 */
public class RowFeatureExtractor {
	// class label
	private int classLabel;
	
	// independent features of a certain row
	private int rowCellNum; 
	private int averageCellLength;
	private int rowCharNum; 
	private float ratioDigitChar;
	private float ratioAlphaChar;
	private float ratioSymbolChar;
	private float fontSize;
	private float ratioFontBold;
	private float ratioFontItalic;
	private float ratioNumericCells;

	// consistency features between adjacent rows
	private float ratioCellNums;
	private float ratioSpanningCell;
	private float avgAlignment;
	private float avgOverlap;
	private float avgCharNumDiff;
	private float avgSimDataType;
	private float avgSimFontSize;
	private float ratioSimFontStyle;
	private float ratioSimContent;

    /**
     * Extract features used to classify the header and data rows
     * @param tc
     *        the candidate table to be extracted
     * @param featureFolder
     * 		  the path of the feature file to be stored
     */
	public void extractFeatures(TableCandidate tc, File predictFile) {
		ArrayList<TableRow> rows = new ArrayList<TableRow>();
		rows = tc.getRows();
		int headingLineNumber = tc.getHeadingLineNumber() + 1;
		createWekaModel(predictFile);
		
		for (int i = 0; i < rows.size(); ++i) {
			if (i < headingLineNumber) classLabel = 1; // header row
			else classLabel = 0; // data row

			ArrayList<TextPiece> pieces = new ArrayList<TextPiece>();
			pieces = rows.get(i).getCells();
			rowCellNum = pieces.size();
			int boldNum = 0;
			int italicNum = 0;
			int digitChar = 0;
			int alphaChar = 0;
			int symbolChar = 0;
			int numericCells = 0;
			for (TextPiece piece : pieces) {
				String str = piece.getText();
				rowCharNum += str.length();
				char[] chars = str.toCharArray();
				for (int j = 0; j < chars.length; ++j) {
					if (Character.isDigit(chars[j])) {
						digitChar++;
					} else if (Character.isLetter(chars[j])) {
						alphaChar++;
					} else
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

			averageCellLength = rowCharNum / pieces.size();
			ratioDigitChar = (float) digitChar / rowCharNum;
			ratioAlphaChar = (float) alphaChar / rowCharNum;
			ratioSymbolChar = (float) symbolChar / rowCharNum;
			fontSize = (float) fontSize / pieces.size();
			ratioFontBold = (float) boldNum / pieces.size();
			ratioFontItalic = (float) italicNum / pieces.size();
			ratioNumericCells = (float) numericCells / pieces.size();

			// consistency features between the adjacent rows
			if (i != rows.size() - 1) {
				ArrayList<TextPiece> nextPieces = new ArrayList<TextPiece>();
				nextPieces = rows.get(i + 1).getCells();
				int nextRowCellNum = nextPieces.size();
				ratioCellNums = (float) Math.abs(rowCellNum - nextRowCellNum) / (rowCellNum + nextRowCellNum);

				extractCellFeatures(pieces, nextPieces);
			}
			
			// output arff file for Weka
			writeWekaModel(predictFile);
		}
	}

    /**
     * Extract consistency features between adjacent rows
     * @param currRowCells
     *        text pieces of current row
     * @param NextRowCells
     * 		  text pieces of next row
     */
	private void extractCellFeatures(ArrayList<TextPiece> currRowCells, ArrayList<TextPiece> NextRowCells) {
		HashMap<TextPiece, ArrayList<TextPiece>> map = new HashMap<TextPiece, ArrayList<TextPiece>>();
		int spanningCell = 0;
		for (int i = 0; i < currRowCells.size(); ++i) {
			map.put(currRowCells.get(i), null);
			ArrayList<TextPiece> listTp = new ArrayList<TextPiece>();
			for (int j = 0; j < NextRowCells.size(); ++j) {
				// overlap cells
				if (NextRowCells.get(j).getX() < currRowCells.get(i).getEndX() 																			
					&& NextRowCells.get(j).getEndX() > currRowCells.get(i).getX()) {
					listTp.add(NextRowCells.get(j));
				}
			}
			map.put(currRowCells.get(i), listTp);
			if (listTp.size() > 1)
				spanningCell++;
		}
		ratioSpanningCell = (float) spanningCell / currRowCells.size();

		int alignment = 0;
		float simOverlap = 0.0f;
		float simCharNum = 0.0f;
		int simDataType = 0;
		float simFontSize = 0.0f;
		int simFontStyle = 0;
		int simLevenDis = 0;
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			TextPiece key = (TextPiece) entry.getKey();
			ArrayList<TextPiece> val = (ArrayList<TextPiece>) entry.getValue();

			for (TextPiece tp : val) {
				// alignment
				if (Math.abs(key.getX() - tp.getX()) < 1)
					alignment += 1;
				else if (Math.abs(key.getEndX() - tp.getEndX()) < 1)
					alignment += 2;
				else if (Math.abs((key.getEndX() - key.getX()) / 2 - (tp.getEndX() - tp.getX()) / 2) < 1)
					alignment += 3;
				else
					alignment += 4;
				// bounding box overlap
				simOverlap += (float) (Math.min(tp.getEndX(), key.getEndX()) - Math.max(tp.getX(), key.getX()))
						/ Math.min(tp.getEndX() - tp.getX(), key.getEndX() - key.getX());
				// cell length
				simCharNum += (float) Math.min(key.getText().length(), tp.getText().length()) 
						/ Math.max(key.getText().length(), tp.getText().length());
				// data type
				simDataType += isNumString(key.getText()) == isNumString(tp.getText()) ? 1 : 0;
				// font size
				simFontSize += (float) Math.min(key.getFontSize(),
						tp.getFontSize()) / Math.max(key.getFontSize(), tp.getFontSize());
				// font style
				if (key.getFontBold() == tp.getFontBold() && key.getFontItalic() == tp.getFontItalic())
					simFontStyle++;
				// LD of content
				simLevenDis += computeLevenshteinDistance (key.getText(),tp.getText());
			}
		}
		avgAlignment = (float) alignment / map.entrySet().size();
		avgOverlap = simOverlap / map.entrySet().size();
		avgCharNumDiff = simCharNum / map.entrySet().size();
		avgSimDataType = (float) simDataType / map.entrySet().size();
		avgSimFontSize = simFontSize / map.entrySet().size();
		ratioSimFontStyle = (float) simFontStyle / map.entrySet().size();
		ratioSimContent = (float) simLevenDis / map.entrySet().size();
	}

	private boolean isNumString(String str) {
		Pattern pattern = Pattern.compile("[0-9]*.[0-9]*");
		return pattern.matcher(str).matches();
	}

    /**
     * Calculate Levenshtein Distance of two strings as the similarity of cell content
     * @param s
     *        the first string
     * @param t
     * 		  the second string
     * @return 
     * 		  the Levenshtein distance value
     */
	private static int computeLevenshteinDistance(String s, String t) {
		char[] s_arr = s.toCharArray();
		char[] t_arr = t.toCharArray();

		if (s.equals(t))
			return 0;
		if (s_arr.length == 0)
			return t_arr.length;
		if (t_arr.length == 0)
			return s_arr.length;

		int[][] matrix = new int[s_arr.length + 1][t_arr.length + 1];
		for (int i = 0; i < s_arr.length; ++i) {
			if (i < s_arr.length && Character.isDigit(s_arr[i]))
				s_arr[i] = '#';
			matrix[i][0] = i;
		}
		for (int j = 0; j < t_arr.length; ++j) {
			if (j < t_arr.length && Character.isDigit(t_arr[j]))
				t_arr[j] = '#';
			matrix[0][j] = j;
		}

		for (int i = 0; i < s_arr.length; ++i) {
			for (int j = 1; j <= t_arr.length; ++j) {
				matrix[i + 1][j] = Math.min( matrix[i][j] + 1, Math.min(matrix[i + 1][j - 1] + 1, matrix[i][j - 1] + (s_arr[i] == t_arr[j - 1] ? 0 : 1)));
			}
		}
		return matrix[s_arr.length][t_arr.length];
	}

    /**
     * Create the .arff file in a given directory path.
     * This file is used to predict the row categories of the given table
     * 
     * @param featureFolder
     *        the directory path of the file to be created
     */
	private void createWekaModel(File predictFile) {
		try {
	    	PrintWriter rowfeatureFileWriter = new PrintWriter(new FileOutputStream(predictFile));	    	
	    	rowfeatureFileWriter.write("@RELATION HeaderDataClassification\n\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE CellNum\t\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE AvgCellLen\t\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE CharNum\t\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE DigitChRatio\t\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE AlphaChRatio\t\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE SymbolChRatio\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE FontSize\t\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE FontBold\t\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE FontItalic\t\tNUMERIC\n");    	
	    	rowfeatureFileWriter.write("@ATTRIBUTE NumCellRatio\t\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE CellNumRatio\t\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE SpanningCell\t\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE AvgAlign\t\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE AvgOverlap\t\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE AvgChNumDiff\t\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE AvgSimDataType\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE AvgSimFontSize\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE SimFontStyle\t\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE SimContent\t\tNUMERIC\n");
	    	rowfeatureFileWriter.write("@ATTRIBUTE class	{HeaderDataClassification-0, HeaderDataClassification-1}\n\n");
	    	rowfeatureFileWriter.write("@DATA\n");
	    	 
	    	if (rowfeatureFileWriter != null){
	    		rowfeatureFileWriter.close();
	    	}    
	    } catch (FileNotFoundException e){
    		System.out.print(e);
    	}
	}
	
    /**
     * Write feature values into the .arff file been created.
     * 
     * @param featureFolder
     *        the directory path of the feature file 
     */
	public void writeWekaModel(File predictFile) {	
		try {
			FileOutputStream out = new FileOutputStream(predictFile, true);
			PrintWriter rowfeatureFileWriter = new PrintWriter(out);	    	
	    	 
	    	Class c = this.getClass();
			Field[] fields = c.getDeclaredFields();
			for (int i = 1; i < fields.length; ++i) {
				fields[i].setAccessible(true);
				String s = fields[i].get(this).toString();
				if (s.length() > 4)
					s = s.substring(0, 4);
				rowfeatureFileWriter.write(s + ",\t");
			}
			rowfeatureFileWriter.write("HeaderDataClassification-" + fields[0].get(this).toString());
			rowfeatureFileWriter.write('\n');
			
	    	if (rowfeatureFileWriter != null){
	    		rowfeatureFileWriter.close();
	    	}
	    
	    } catch (FileNotFoundException e){
    		System.out.print(e);
    	} catch (IllegalAccessException e) {
			System.out.print(e);
		}
	}
}