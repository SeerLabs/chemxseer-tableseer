/**
 * 
 */
package edu.psu.seersuite.extractors.tableextractor.model;

import java.io.File;
import java.util.ArrayList;

/**
 * This class defines the table-related parameters and methods.
 * 
 * @author  Ying, Shuyi
 * 
 */
public class Table
{
    /**
     * The PDF file where the table is located in
     */
	private String sourcePdfFile; 

	/**
	 * The id of the document page where the table is located in
	 */
    private int pageNumber;

    /**
     * The table id in the document
     */
    private int order;

    /**
     * The number of rows in this table 
     */
    private int rowNumber;

    /**
     * The number of columns in this table
     */
    private int columnNumber;

    /**
     * The number of head columns in this table
     */
    private int headColNumber;
    
    /**
     * The width of the table
     */
    private float width;

    /**
     * THe height of the table
     */
    private float height;

    /**
     * The caption of the table
     */
    private String caption;

    /**
     * The heading rows of the table
     */
    private ArrayList<String> xheading;

    /**
     * THe body/data rows of the table
     */
    private ArrayList<String> xbody;
    
    /**
     * THe footnote of the table
     */
    private String footNote;

    /**
     * The reference text of the table in the document
     */
    private String refTextList;

    /**
     * The coordinate information of each table column
     */
    private ArrayList<Float> columnCoordinates;
    
    public Table(){
    	this.xheading = new ArrayList<String>();
    	this.xbody = new ArrayList<String>();
    	
    }
    /**
     *Gets the body cells of a table
     * @return the body content of a table
     */
    public ArrayList<String> getTableBody()
    {
    	return xbody;
    }

    /**
     * Gets the caption of a table
     * @return the caption
     */
    public String getCaption()
    {
    	return caption;
    }

    /**
     * Gets the coordinate information of the table columns
     * @return the Coordinates of table columns
     */
    public ArrayList<Float> getColumnCoordinates()
    {
    	return columnCoordinates;
    }

    /**
     * Gets the number of columns in a table
     * @return the number of the columns in a table
     */
    public int getColumnNumber()
    {
    	return columnNumber;
    }

    /**
     * Gets the number of head columns in a table
     * @return the number of the head columns in a table
     */
    public int getHeadColNumber()
    {
    	return headColNumber;
    }
    
    /**
     * Gets the footnote of a table
     * @return the footnote of a table
     */
    public String getFootNote()
    {
    	return footNote;
    }

    /**
     * Gets the heading rows of a table 
     * @return the heading of a table
     */
    public ArrayList<String> getHeading()
    {
    	return xheading;
    }

    /**
     * Gets the height of a table
     * @return the height of a table
     */
    public float getHeight()
    {
    	return height;
    }

    /**
     * @return the order id of a table in the document
     */
    public int getOrder()
    {
    	return order;
    }

    /**
     * Gets the number of the page where the table is located in
     * @return the page number of a table
     */
    public int getPageNumber()
    {
    	return pageNumber;
    }

    /**
     * Gets the reference text of a table in the document
     * @return the reference text of a table 
     */
    public String getRefTextList()
    {
    	return refTextList;
    }

    /**
     * Gets the number of rows in a table
     * @return the number of rows
     */
    public int getRowNumber()
    {
    	return rowNumber;
    }

    /**
     * Gets the PDF file where the table is located in
     * @return the source PDF file
     */
    public String getSourcePdfFile()
    {
    	return sourcePdfFile;
    }

    /**
     * Gets the width of a table
     * @return the width of a table
     */
    public float getWidth()
    {
    	return width;
    }

    /**
     * Loads table-set from a file
     * 
     * @param f
     *         the input file
     */
    public void load(File f)
    {

    }

    /**
     * Saves table to a file
     * 
     * @param f
     *         the output file
     */
    public void save(File f)
    {

    }

    /**
     * Sets the table body cells into an array
     * @param body
     *            the table body content to set
     */
    public void setBody(String body)
    {
    	String xbody = body;
    	String [] row = xbody.split("\n");
    	for(int i=0; i<row.length; i++){
    		this.xbody.add(row[i]);
    	}
    }

    /**
     * Sets the table caption
     * @param caption
     *            the caption to set
     */
    public void setCaption(String caption)
    {
    	this.caption = caption;
    }

    /**
     * Sets the coordinates of table columns
     * @param columnCoordinates
     *            the columnCoordinates to set
     */
    public void setColumnCoordinates(ArrayList<Float> columnCoordinates)
    {
    	this.columnCoordinates = columnCoordinates;
    }

    /**
     * Sets the number of table columns 
     * @param columnNumber
     *            the columnNumber to set
     */
    public void setColumnNumber(int columnNumber)
    {
    	this.columnNumber = columnNumber;
    }
    
    /**
     * Sets the number of table head columns
     * @param headColNumber
     * 			the number of head column to set
     */
    public void setHeadColNumber(int headColNumber)
    {
    	this.headColNumber = headColNumber;
    }

    /**
     * Sets the table footnote
     * @param footNote
     *            the footNode to set
     */
    public void setFootNote(String footNote)
    {
    	this.footNote = footNote;
    }

    /**
     * Sets the table heading rows
     * @param heading
     *            the heading to set
     */
    public void setHeading(String heading)
    {
    	String m_heading = heading; 
    	String [] preCol = m_heading.split("\n");
    	String [][] cells = new String[preCol.length][this.columnNumber];
    
    	for(int i=0; i<preCol.length; i++)
    		cells[i] = preCol[i].split(";", this.columnNumber);
    	
    	for(int i=0; i<cells[0].length; i++)
    	{
    		String xhead = "";
    		for(int j=0; j<preCol.length; j++){
    			xhead += (cells[j][i]+ " ");
    		}
    		this.xheading.add(xhead);
    	}   	
    }

    /**
     * Sets the table height
     * @param height
     *            the height to set
     */
    public void setHeight(float height)
    {
    	this.height = height;
    }

    /**
     * Sets the order ID of a table
     * @param order
     *            the order to set
     */
    public void setOrder(int order)
    {
    	this.order = order;
    }

    /**
     * Sets the page number where the table is located in
     * @param pageNumber
     *            the pageNumber to set
     */
    public void setPageNumber(int pageNumber)
    {
    	this.pageNumber = pageNumber;
    }

    /**
     * Sets the reference text of a table in a document
     * @param refTextList
     *            the refTextList to set
     */
    public void setRefTextList(String refTextList)
    {
    	this.refTextList = refTextList;
    }

    /**
     * Sets the number of table rows 
     * @param rowNumber
     *            the rowNumber to set
     */
    public void setRowNumber(int rowNumber)
    {
    	this.rowNumber = rowNumber;
    }

    /**
     * Sets the source PDF file
     * @param sourcePdfFile
     *            the sourcePdfFile to set
     */
    public void setSourcePdfFile(String sourcePdfFile)
    {
    	this.sourcePdfFile = sourcePdfFile;
    }

    /**
     * Sets the width of a table
     * @param width
     *            the width to set
     */
    public void setWidth(float width)
    {
    	this.width = width;
    }
    
    /**
     * Sets the metadata of a valid table
     * @param tc
     *      TableCandidate
     * */
    public void setValidTable(TableCandidate tc)
    {
    	/**
    	 * TODO [improve]: Table/TableCandidate not match
    	 * */
    	this.setHeight(tc.getHeight());
    	this.setWidth(tc.getWidth());
    	this.setCaption(tc.getCaption());
    	this.setRowNumber(tc.getRows().size());
    	this.setHeadColNumber(tc.getHeadColNumber());
    	this.setColumnNumber(tc.getColumnNumthisTable());
    	this.setRefTextList(tc.getRefText());
    	this.setFootNote(tc.getFootnoteText());
    	this.setHeading(tc.getColumnHeadings());
    	this.setBody(tc.getTableContent());  	
    }

}
