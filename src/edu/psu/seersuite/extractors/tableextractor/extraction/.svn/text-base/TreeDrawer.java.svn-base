/**
 * This package defines the core table extraction classes
 */
package edu.psu.seersuite.extractors.tableextractor.extraction;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import edu.psu.seersuite.extractors.tableextractor.*;
import edu.psu.seersuite.extractors.tableextractor.model.*;

public class TreeDrawer
{
    private Tree tree;
    private TableCandidate tc;
    private String fileName;
    
    /**
     * call the function of drawing the table tree
     * @param tc
     * 			table candidate
     * @param fileName
     * 			name of the pdf file, show it on the drawing window
     * 			
     */
    public void draw(TableCandidate tc, String fileName){
        try{
        	TreeDrawer window = new TreeDrawer();      	
            window.open(tc, fileName);
            
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Draw the tree structure of a whole table
     * @param tc
     * 			table candidate
     * @param fileName
     * 			name of the pdf file, show it on the drawing window
     * 			
     */
    public void open(TableCandidate tc, String fileName){
    	this.tc = tc;
    	this.fileName = fileName;
    	
    	float[] leftX_tableColumns = tc.getLeftX_tableColumns();
		float[] rightX_tableColumns = tc.getRightX_tableColumns();
		
        final Display display = Display.getDefault();
        final Shell shell = new Shell();
        shell.setSize(600, 600);
        shell.setText("Table Tree for : " + this.fileName);
        shell.open();

        tree = new Tree(shell, SWT.BORDER_SOLID);
        tree.setBounds(0, 0, 600, 600);
        TreeItem table = new TreeItem(tree, SWT.NONE);
        
        // Define the root of table
        String root = "";
        for (TextPiece tp : tc.getTableStub())
        	root += tp.getText();
        if (root.equals(""))
        	root = "VIRTUAL ROOT";
        table.setText(root);
        
        ArrayList<TextPiece> coltps = new ArrayList<TextPiece>();
        for (TextPiece tp: tc.getColumns().get(0).getCells())
			if (tp != null)
				coltps.add(tp);
        coltps.removeAll(tc.getTableStub());
        
        // begin from the row headers as higher level of hierarchy
        buildColTree(table, coltps);
        
        shell.layout();
        /*while (!shell.isDisposed()){
            if (!display.readAndDispatch())
                display.sleep();
        }*/
        shell.close();
    }
    
    /**
     * Build the tree from the heading column, making it the higher level than heading rows.
     * It is recursive invocation for the multi-level header
     * @param parent
     * 			parent node
     * @param tps
     * 			children text pieces
     */
    private void buildColTree(TreeItem parent, ArrayList<TextPiece> tps){
    	for (int i = 0; i < tps.size(); ++i) { 
    		TreeItem child = new TreeItem(parent, SWT.NULL);
         	child.setText(tps.get(i).getText());
         	if (tps.get(i).getChildren() != null)
         		buildColTree(child, tps.get(i).getChildren());
         	else {
         		ArrayList<TextPiece> heading = new ArrayList<TextPiece>();
        		ArrayList<TextPiece> stub = tc.getTableStub();
        		for (TextPiece tp : tc.getRows().get(0).getCells())
        			heading.add(tp);
        		heading.removeAll(stub);
        		buildRowTree(tps.get(i), child, heading);
         	}
    	}
    }
    
    /**
     * Build the tree from the heading rows
     * It is recursive invocation for the multi-level header
     * @param parent
     * 			parent node
     * @param tps
     * 			children text pieces
     */
    private void buildRowTree(TextPiece tp, TreeItem parent, ArrayList<TextPiece> tps){     	
    	for (int i = 0; i < tps.size(); ++i) { 
    		TreeItem child = new TreeItem(parent, SWT.NULL);
         	child.setText(tps.get(i).getText());
         	if (tps.get(i).getChildren() != null)
         		buildRowTree(tp, child, tps.get(i).getChildren());
         	else{
         		float x1 = tps.get(i).getX();
         		float x2 = tps.get(i).getEndX();
         		float y1 = tp.getY();
         		float y2 = tp.getEndY();
         		TextPiece datacell = findDataCell(x1,x2,y1,y2);
         		TreeItem data = new TreeItem(child, SWT.NULL);
         		if (datacell != null)
         			data.setText(datacell.getText());
         		else
         			data.setText("");
         	}
    	}
    }
    
    /**
     * find the data cell using the boundary of last level row header and column header
     * @param x1	
     * @param x2    
     * @param y1
     * @param y2
     * @return	the indexed data cell
     */
    private TextPiece findDataCell(float x1, float x2, float y1, float y2){
    	TextPiece tp = null;
    	for (int i = 0; i < tc.getRows().size(); ++i){
    		for (int j = 0; j < tc.getRows().get(i).getCells().size(); ++j){
    			TextPiece t = tc.getRows().get(i).getCells().get(j);
    			if (t.getX() < x2 && t.getEndX() > x1 && t.getY() < y2 && t.getEndY() > y1)
    				tp = t;
    		}
    	}
    	return tp;
    }
}
