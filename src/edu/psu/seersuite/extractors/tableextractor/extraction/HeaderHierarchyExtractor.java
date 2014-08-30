/**
 * This package defines the core table extraction classes
 */
package edu.psu.seersuite.extractors.tableextractor.extraction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.lang.Character;

import edu.psu.seersuite.extractors.tableextractor.model.*;
import edu.psu.seersuite.extractors.tableextractor.*;

public class HeaderHierarchyExtractor {
	
	private ArrayList<TextPiece> tableStub;
	
	public enum Alignment {
		LEFT, RIGHT, CENTER, TOP
	}

	/**
	 * Extract hierarchy of column headers
	 * @param tc
	 * 			table candidate
	 */
	public void ExtractColHeaderHierarchy(TableCandidate tc) {	
		MergeMultiLineSubHeader(tc);	
		Alignment al = JudgeColHeaderAlignment(tc);
		ExtractColHeaderLevel(tc, al);		
	}
	
	/**
	 * Extract hierarchy of row headers
	 * @param tc
	 * 			table candidate
	 */
	public void ExtractRowHeaderHierarchy(TableCandidate tc) {
		int rowHeaderNum = tc.getHeadColNumber();
		if (rowHeaderNum > 1){
			Alignment al = JudgeRowHeaderAlignment(tc);				
			ExtractRowHeaderLevel(tc, al);
		}			
	}
	
	/**
	 * Merge the adjacent header lines in following conditions:
	 * 1. The upper header line has more cells than the lower one and no spanning cells exist
	 * 2. For corresponding cell pair, if the upper cell begin with capital letter or end with '-', 
	 * 	  and the lower cell begin with low case letter or '(', '%', we merge the cell pair. If no spanning cells
	 * 	  exist, we could merge the two lines, otherwise, just merge certain cell pairs.
	 * 
	 * @param tc
	 */
	private void MergeMultiLineSubHeader(TableCandidate tc) {		
		ArrayList<TableRow> rows = tc.getRows();
		// outputHeadRows(rows);	
		int heading = tc.getHeadingLineNumber();
		ArrayList<TextPiece> stub = tc.getTableStub();
		
		// merge adjacent header lines 
		for (int i = 0; i < tc.getHeadingLineNumber(); ++i){
			ArrayList<TextPiece> uppieces = rows.get(i).getCells();
			ArrayList<TextPiece> lopieces = rows.get(i+1).getCells();
			uppieces.removeAll(stub);
			lopieces.removeAll(stub);
			boolean hasSpanCell = false;
			
			// Judge whether the upper line has spanning cells
			HashMap<TextPiece, ArrayList<TextPiece>> map = new HashMap<TextPiece, ArrayList<TextPiece>>();
			for (int j = 0; j < uppieces.size(); ++j){
				map.put(uppieces.get(j), null);
				ArrayList<TextPiece> list = new ArrayList<TextPiece>();
				for (int k = 0; k < lopieces.size(); ++k){
					if (isOverlap(uppieces.get(j), lopieces.get(k)))
						list.add(lopieces.get(k));
				}
				if (list.size() > 1)	
					hasSpanCell = true;
				map.put(uppieces.get(j), list);
			}
			
			// Case1: The upper line has no less than cells the lower one
			if (uppieces.size() >= lopieces.size() && hasSpanCell == false) {
				for (int j = 0; j < uppieces.size(); ++j) {
					for (int z = 0; z < lopieces.size(); ++z)
						if (isOverlap(uppieces.get(j), lopieces.get(z))){
							uppieces.set(j, uppieces.get(j).merge(lopieces.get(z)));
							break;
						}
				}
				rows.remove(i+1);
				i--;
				tc.setHeadingLineNumber(tc.getHeadingLineNumber()-1);
			}	
			else if (uppieces.size() < lopieces.size()  // Case2
					&& hasSpanCell == false 
					&& isMergeable(uppieces, lopieces)){			
				for (int j = 0; j < lopieces.size(); ++j){
					for (int z = 0; z < uppieces.size(); ++z){
						if (isOverlap(uppieces.get(z), lopieces.get(j))){
							lopieces.set(j, uppieces.get(z).merge(lopieces.get(j)));
							break;
						}
					}
				}
				rows.remove(i);
				tc.setHeadingLineNumber(tc.getHeadingLineNumber()-1);
			}
		}			
		//outputHeadRows(rows);	
	}
	
	/**
	 * Judge whether two text piece are overlap horizontally
	 * @param tp1, tp2
	 * 			The two text pieces to be compared
	 */
	private boolean isOverlap(TextPiece tp1, TextPiece tp2) {		
		return tp1.getX() < tp2.getEndX() && tp1.getEndX() > tp2.getX();
	}
	
	/**
	 * Judge whether two text lines can be merge as one table row
	 * @param tps1, tps2
	 * 			The two text piece lists to be compared
	 */
	private boolean isMergeable(ArrayList<TextPiece> tps1, ArrayList<TextPiece> tps2){
		boolean mark = false;
		for (int j = 0; j < tps1.size(); ++j){
			for (int z = 0; z < tps2.size(); ++z){
				String s1 = tps1.get(j).getText().trim();
				String s2 = tps2.get(z).getText().trim();
				if (Character.isUpperCase(s1.charAt(0)) && Character.isLowerCase(s2.charAt(0))
					|| s1.charAt(s1.length()-1) == '-'
					|| s1.charAt(s1.length()-1) == '&'
					|| s2.charAt(0) == '('
					|| s2.charAt(0) == '%'){
					mark = true;
					break;
				}				
			}
		}
		return mark;
	}
	
	/**
	 * Judge cell alignment for column headers
	 * Method: accumulate the left differences between the left boundaries of the cell and its column,
	 * 			and the right differences between the right boundaries of the cell and its column respectively,
	 * 			then set the alignment according to the ratio of left and right difference
	 * @param tc
	 * 			table candidate
	 */
	private Alignment JudgeColHeaderAlignment(TableCandidate tc) {
		//outputColBoundary(tc);		
		float[] leftX_tableColumns = tc.getLeftX_tableColumns();
		float[] rightX_tableColumns = tc.getRightX_tableColumns();			
		float leftabs = 0.0f;
		float rightabs = 0.0f;
		
		for (int i = 0; i < tc.getHeadingLineNumber()+1; ++i){			
			ArrayList<TextPiece> tp = tc.getRows().get(i).getCells();
			for (int j = 1; j < tp.size(); ++j){ 				
				for (int k = 0; k < tc.getColumnNumthisTable(); ++k){
					if (tp.get(j).getX() >= leftX_tableColumns[k] && tp.get(j).getX() < leftX_tableColumns[k+1]){
						leftabs += Math.abs(tp.get(j).getX() - leftX_tableColumns[k]);						
						//System.out.print(leftX_tableColumns[k]);
						//System.out.print('\t');						
						break;
					}
				}
				for (int k = 0; k < tc.getColumnNumthisTable(); ++k){
					if (tp.get(j).getEndX() <= rightX_tableColumns[0]+3){ //TODO: make threshold a constant
						rightabs += Math.abs(tp.get(j).getEndX() - rightX_tableColumns[0]);
						//System.out.print(rightX_tableColumns[k]);
						//System.out.print('\t');
						break;
					}
					if (tp.get(j).getEndX() >= rightX_tableColumns[k] && tp.get(j).getEndX() < rightX_tableColumns[k+1]+3){  //TODO: make threshold a constant
						rightabs += Math.abs(tp.get(j).getEndX() - rightX_tableColumns[k+1]);
						//System.out.print(rightX_tableColumns[k+1]);
						//System.out.print('\t');
						break;
					}
				}
				//System.out.print('\n');
			}
		}
    	
		float alignRatio = (float) Math.min(leftabs, rightabs) / Math.max(leftabs, rightabs);
    	if (alignRatio < 0.6 && leftabs > rightabs)
    		return Alignment.RIGHT;
    	else if (alignRatio < 0.6 && leftabs < rightabs)
    		return Alignment.LEFT;
    	else 
    		return Alignment.CENTER;    	
	}
	
	/**
	 * Extract hierarchy structure for multi-level column headers 
	 * This is done according to the positional information of cells in adjacent levels,
	 * and for different alignments, we propose different strategies. 
	 * @param tc
	 * 			table candidate
	 * @param al
	 * 			column header cell alignment
	 */
	private void ExtractColHeaderLevel(TableCandidate tc, Alignment al) {	
		ArrayList<TextPiece> stub = tc.getTableStub();
		for (int i = 0 ; i < tc.getHeadingLineNumber(); ++i){
			ArrayList<TextPiece> tps1 = tc.getRows().get(i).getCells();
			ArrayList<TextPiece> tps2 = tc.getRows().get(i+1).getCells();
			tps1.removeAll(stub);
			tps2.removeAll(stub);
			
			if (al == Alignment.CENTER){			
				for (int j = 0; j < tps1.size(); ++j){
					int k = 0;
					for (; k < tps2.size(); ++k){
						if (tps2.get(k).getColParent() == null)
							break;
					}					
					float leftchild = tps2.get(k).getX();
					float leftparent = tps1.get(j).getX();
					float rightparent = tps1.get(j).getEndX();
					float rightchild = leftparent - leftchild + rightparent;
					if (leftchild >= rightchild)
						continue;
					ArrayList<TextPiece> children = new ArrayList<TextPiece>();					
					for (int m = k; m < tps2.size(); ++m){
						if (tps2.get(m).getX() < rightchild + 3.0){ // TODO: make it constant
							tps2.get(m).setColParent(tps1.get(j));
							children.add(tps2.get(m));	
						}
					}	
					tps1.get(j).setChildren(children);	
				}
			}
			else if (al == Alignment.LEFT){		
				int marked = 0;
				for (int j = 0; j < tps1.size(); ++j) {
					ArrayList<TextPiece> children = new ArrayList<TextPiece>();
					for (int k = 0; k < tps2.size(); ++k) {
						if (k >= marked && j < tps1.size()-1 && tps2.get(k).getEndX() < tps1.get(j+1).getX()){
							tps2.get(k).setColParent(tps1.get(j));
							children.add(tps2.get(k));	
							marked++;
						}
						else if (j == tps1.size()-1){
							tps2.get(k).setColParent(tps1.get(j));
							children.add(tps2.get(k));							
						}
					}				
					tps1.get(j).setChildren(children);						
				}						
			}
			else {			
				int marked = tps2.size();
				ArrayList<TextPiece> tempStore = new ArrayList<TextPiece>();
				for (int j = tps1.size()-1; j >= 0; --j) {
					ArrayList<TextPiece> children = new ArrayList<TextPiece>();
					for (int k = tps2.size()-1; k >= 0; --k) {
						if (k < marked && j > 0 && tps2.get(k).getX() > tps1.get(j-1).getEndX()){
							tps2.get(k).setColParent(tps1.get(j));
							children.add(tps2.get(k));	
							marked--;
						}
						else if (k < marked && j == 0){
							tps2.get(k).setColParent(tps1.get(j));
							children.add(tps2.get(k));
						}
					}				
					tps1.get(j).setChildren(children);						
				}			
			}
		}
	}
	
	/**
	 * Usually the first heading column has less cells than the second heading column,
	 * the alignment will either be center-aligned or top-aligned, which can be seen whether
	 * their first cells have overlap to each other.
	 * @param tc
	 * @return alignment of adjacent heading columns.
	 */
	private Alignment JudgeRowHeaderAlignment(TableCandidate tc){	
		ArrayList<TextPiece> col1 = new ArrayList<TextPiece>();
		ArrayList<TextPiece> col2 = new ArrayList<TextPiece>();
		int headingrows = tc.getHeadingLineNumber();
		
		for (int i = headingrows; i < tc.getColumns().get(0).getCells().size(); ++i)
			if (tc.getColumns().get(0).getCells().get(i) != null)
				col1.add(tc.getColumns().get(0).getCells().get(i));
		
		for (int i = headingrows; i < tc.getColumns().get(1).getCells().size(); ++i)
			if (tc.getColumns().get(1).getCells().get(i) != null)
				col2.add(tc.getColumns().get(1).getCells().get(i));
		
		if (col1.size() == 0|| col2.size() == 0)
			return Alignment.TOP;
		else{
			TextPiece tp1 = col1.get(0);
			TextPiece tp2 = col2.get(0);

			if (tp2.getY() < tp1.getEndY() && tp2.getEndY() > tp1.getY()){
				return Alignment.TOP;
			}
			else{
				return Alignment.CENTER;
			}
		}
	}
	
	/**
	 * Extract hierarchy structure for multi-level row headers 
	 * This is done according to the positional information of cells in adjacent columns,
	 * @param tc
	 * 			table candidate
	 * @param al
	 * 			column header cell alignment
	 */
	private void ExtractRowHeaderLevel(TableCandidate tc, Alignment al){
		ArrayList<TextPiece> stub = tc.getTableStub();
		for (int i = 0 ; i < tc.getHeadColNumber()-1; ++i){
			ArrayList<TextPiece> tps1 = new ArrayList<TextPiece>();
			ArrayList<TextPiece> tps2 = new ArrayList<TextPiece>();
			
			for (TextPiece tp: tc.getColumns().get(i).getCells())
				if (tp != null)
					tps1.add(tp);
			for (TextPiece tp: tc.getColumns().get(i+1).getCells())
				if (tp != null)
					tps2.add(tp);
			tps1.removeAll(stub);
			tps2.removeAll(stub);
			
			if (tps1.size() == 0 || tps2.size() == 0)
				return;
					
			if (al == Alignment.TOP){
				int marked = 0;
				for (int j = 0; j < tps1.size(); ++j) {
					ArrayList<TextPiece> children = new ArrayList<TextPiece>();
					for (int k = 0; k < tps2.size(); ++k) {
						if (k >= marked && j < tps1.size() - 1 && tps2.get(k).getEndY() < tps1.get(j+1).getY()){
							tps2.get(k).setColParent(tps1.get(j));
							children.add(tps2.get(k));
							marked++;
						}
						else if (k >= marked && j == tps1.size()-1)
						{
							tps2.get(k).setColParent(tps1.get(j));
							children.add(tps2.get(k));							
						}
					}				
					tps1.get(j).setChildren(children);						
				}						
			}
			else if (al == Alignment.CENTER){
				for (int j = 0; j < tps1.size(); ++j){
					int k = 0;
					for (; k < tps2.size()-1; ++k){
						if (tps2.get(k).getColParent() == null)
							break;
					}					
					float topchild = tps2.get(k).getEndY();
					float topparent = tps1.get(j).getEndY();
					float bottomparent = tps1.get(j).getY();
					float bottomchild = topparent - topchild + bottomparent;
					
					ArrayList<TextPiece> children = new ArrayList<TextPiece>();					
					for (int m = k; m < tps2.size(); ++m){
						if (tps2.get(m).getY() > bottomchild){
							tps2.get(m).setColParent(tps1.get(j));
							children.add(tps2.get(m));	
						}
					}	
					tps1.get(j).setChildren(children);	
				}
			}			
		}
	}
	
	// As a test for heading rows
	private void outputHeadRows(ArrayList<TableRow> headerrows){
		for (int i = 0; i < headerrows.size(); ++i){
			for (int j = 0; j < headerrows.get(i).getCells().size(); ++j)
				System.out.print(headerrows.get(i).getCells().get(j).getText() + "  ");
			System.out.print('\n');
		}
		System.out.print('\n');
	}
	
	// As a test for column boundary
	private void outputColBoundary(TableCandidate tc){
		float[] leftX_tableColumns = tc.getLeftX_tableColumns();
		float[] rightX_tableColumns = tc.getRightX_tableColumns();	
		
		for (int i = 0; i < tc.getColumnNumthisTable(); ++i)
			System.out.print('(' + Float.toString(leftX_tableColumns[i]) + ", " + Float.toString(rightX_tableColumns[i]) + ')' + '\t');
		
		System.out.print('\n');	
		for (int i = 0; i < tc.getHeadingLineNumber(); ++i){
			for (int j = 0; j < tc.getRows().get(i).getCells().size(); ++j){
				String left = Float.toString(tc.getRows().get(i).getCells().get(j).getX());
				String right = Float.toString(tc.getRows().get(i).getCells().get(j).getEndX());
				System.out.print('(' + left + ", " + right + ')' + '\t');
			}
		}
		System.out.print('\n');	
	}
}


