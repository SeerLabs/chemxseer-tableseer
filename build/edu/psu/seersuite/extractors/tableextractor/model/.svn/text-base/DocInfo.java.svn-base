/**
 * This package defines all the models
 */
package edu.psu.seersuite.extractors.tableextractor.model;

import org.jdom.Document;

/**
 * This class defines the document-related parameters and methods. 
 * 
 * @author Ying, Shuyi
 * 
 */
public class DocInfo
{
    /**
     * The average width of the characters in a document
     */
	private float averageCharWidth = -1;   
	
	/**
	 * The average width of the lines in a document
	 */
    private float averageLineWidth = -1;          
	
	/*
	 * the text scale of the document body contents
	 */
    private float bodyTextScale = -1;             
    
    /**
	 * the font size of the document title
	 */
    private float titleFont = -1;                
    
    /**
     * the average gap between two consecutive lines in a document
     */
    private float averageLineGap = 10;           
    
    /**
     * the average gap between the left-ends of two text pieces in a document
     */
    private float averageXGap = 10;               
    
    /**
     * the minimum X axis in this document
     */
    private float minX = 1000;                    
    
    /**
     * the maximum X axis in this document
     */
    private float m_maxX = 0;     
    
    /**
     * The minimum Y axis in this document
     */
    private float minY = 1000;  
    
    /**
     * the maximum Y axis in this document
     */
    private float maxY = 0;             
    
    /**
     * the X axis of the middle point of this document
     */
    private float middleX = 0;                    
    
    /**
     * the left-side X axis of the middle area of this document
     */
    private float middleArea_X = 0;               
    
    /**
     * the right-side X axis of the middle area of this document
     */
    private float middleArea_EndX = 0;            
    
    /**
     * the Html codes from &#32 to &#127 
     */
    private String[] Html2Char = new String[96]; 
    
    /**
     * The string to store the table metadata in this document
     */
    private String tableMetadata = "";
    
    /**
     * The string to store the document metadata
     */
    private String docMeta = "";
    
    /**
     * The possible error message of this document after processing
     */
    private String errorMsg= "";
   
    /**
     * The total number of pages in this document
     */
    private int pageNum = 0;
    
    /**
     * The total number of tables in this document
     */
    private int tableNum = 0;
    
    /**
     * The document metadata in XML format 
     */
    private Document xdocMeta; //for standard XML 
    
    /**
     * Gets the table metadata in this document
     * 
     * @return the XML-based table metadata
     */
    public String getTableMetaInThisDoc() 
    {
    	return tableMetadata;
    }
    
    /**
     * Gets the basic metadata of this document
     * 
     * @return the document metadata
     */
    public String getDocMeta() 
    {
    	return docMeta;
    }
    
    /**
     * Gets the error message of this document, if applicable
     * 
     * @return the error message
     */
    
    public String getErrorMsg() 
    {
    	return errorMsg;
    }
    
    /**
     * Gets the total number of pages in this document
     * 
     * @return the total number of pages 
     */
    public int getPageNum() 
    {
    	return pageNum;
    }
    
    /**
     * Gets the total number of tables in this document
     * 
     * @return the total number of tables
     */
    public int getTableNum() 
    {
    	return tableNum;
    }
    
    
    /**
     * Gets the average width of characters in a document
     * 
     * @return the average Char width
     */
    public float getAverageCharWidth()
    {
    	return averageCharWidth;
    }

    
    /**
     * Gets the average gaps between consecutive lines in a document
     * 
     * @return the average gaps between lines
     */
    public float getAverageLineGap(){
    	return averageLineGap;
    }

    
    /**
     * Gets the average width of lines in a document
     * 
     * @return the average line width in a document -- averageLineWidth
     */
    public float getAverageLineWidth()
    {
    	return averageLineWidth;
    }

    
    /**
     * Gets the average X-axis gaps between two neighbor text pieces in a document
     * 
     * @return the average X gap
     */
    public float getAverageXGap()
    {
    	return averageXGap;
    }

    
    /**
     * Gets the text scale information of the body content area in a document
     * 
     * @return the body Text Scale information
     */
    public float getBodyTextScale()
    {
    	return bodyTextScale;
    }

    /**
     * Gets the font information of the document title
     * 
     * @return the font information of the document title
     */
    public float getTitleFont()
    {
    	return titleFont;
    }

    /**
     * Gets the minimum X-axis in a document
     * @return the minimum X-axis
     */
    public float getMinX()
    {
    	return minX;
    }
    
    /**
     *Gets the maximum X-axis in a document
     * @return the max X-axis
     */
    public float getMaxX()
    {
    	return m_maxX;
    }
    
    /**
     * Gets the minimum Y-axis in a document
     * @return the minimum Y-axis
     */
    public float getMinY()
    {
    	return minY;
    }
    
    /**
     * Gets the maximum Y-axis in a document
     * @return the max Y-axis
     */
    public float getMaxY()
    {
    	return maxY;
    }
    
    /**
     * Gets the middle X-axis in a document
     * @return the middle X-axis
     */
    public float getMiddleX()
    {
    	return middleX;
    }
    
    /**
     * Gets the left-end X axis of the middle area in a document
     * @return the left-end x-axis of the middle area
     */
    public float getMiddleArea_X()
    {
    	return middleArea_X;
    }
    
    /**
     * Gets the right-end X-axis of the middle area in a document
     * @return the right-end x-axis of the middle area
     */
    public float getMiddleArea_EndX()
    {
    	return middleArea_EndX;
    }
    
    /**
     * Sets the average character width in a document
     * @param averageCharWidth
     *            the average Char Width to set
     */
    public void setAverageCharWidth(float averageCharWidth)
    {
    	this.averageCharWidth = averageCharWidth;
    }

    /**
     * Sets the average gaps between the consecutive lines in a document
     * @param averageLineGap
     *            the average Line Gap to set
     */
    public void setAverageLineGap(float averageLineGap)
    {
    	this.averageLineGap = averageLineGap;
    }

    /**
     * Sets the average line width in a document
     * @param averageLineWidth
     *            the average Line Width to set
     */
    public void setAverageLineWidth(float averageLineWidth)
    {
    	this.averageLineWidth = averageLineWidth;
    }

    /**
     * Sets the average gap between the left ends of two neighbor text pieces in a document
     * @param averageXGap
     *            the average Gap between X-axes to set
     */
    public void setAverageXGap(float averageXGap)
    {
    	this.averageXGap = averageXGap;
    }

    /**
     * Sets the text scale information of the document body content areas in a document
     * @param bodyTextScale
     *            the text scale information of the document body content areas to set
     */
    public void setBodyTextScale(float bodyTextScale)
    {
    	this.bodyTextScale = bodyTextScale;
    }

    /**
     * Sets the font information of the document title 
     * @param titleFont
     *            the Font of the title to set
     */
    public void setTitleFont(float titleFont)
    {
    	this.titleFont = titleFont;
    }
    
    /**
     * Sets the minimum X-axis in a document
     * @param minX
     */
    public void setMinX(float minX)
    {
    	this.minX = minX;
    }
    /**
     * Sets the maximum X-axis in a document
     * @param maxX
     */
    public void setMaxX(float maxX)
    {
    	m_maxX = maxX;
    }
    
    /**
     * Sets the minimum Y-axis in a document
     * @param minY
     */
    public void setMinY(float minY)
    {
    	this.minY = minY;
    }
    
    /**
     * Sets the maximum Y-axis in a document
     * @param maxY
     */
    public void setMaxY(float maxY)
    {
    	this.maxY = maxY;
    }
    
    /**
     * Sets the middle X-axis in a document
     * @param middleX
     */
    public void setMiddleX(float middleX)
    {
    	this.middleX = middleX;
    }
    
    /**
     * Sets the left-end X-axis of the middle area in a document
     * @param middleArea_X
     *         the left-end X-axis of the middle area in a document
     */
    public void setMiddleArea_X(float middleArea_X)
    {
    	this.middleArea_X = middleArea_X;
    }
    
    /**
     * Sets the middle X-axis of the middle area in a document 
     * @param middleArea_EndX
     *        the middle X-axis of the middle area in a document 
     */
    public void setMiddleArea_EndX(float middleArea_EndX)
    {
    	this.middleArea_EndX = middleArea_EndX;
	}
    
    /**
     * Sets the table metadata in a document 
     * @param tableMetadata
     *         the extracted table metadata
     */
    public void setTableMetadata(String tableMetadata) {
    	this.tableMetadata = tableMetadata;
    }
    
    /**
     * Sets the document metadata      
     * @param docMeta
     *         the extracted document metadata
     */
    public void setDocMeta(String docMeta){
    	this.docMeta = docMeta;
    }
    
    /**
     * Sets the document metadata in the XML-format
     * @param xdocMeta
     * 		the document medata in the XML-format
     * 
     * */
    public void setDocMeta(Document xdocMeta){
    	
    	this.xdocMeta = xdocMeta;
    }
    
    /**
     * Sets the error message of this document
     * @param errorMsg
     *         the error message to set
     */
    public void setErrorMsg(String errorMsg) {
    	this.errorMsg = errorMsg;
    }
    
    /**
     * Sets the total number of pages in a document
     * @param pageNum
     *         the total number of pages
     */
    public void setPageNum (int pageNum) {
    	this.pageNum = pageNum;
    }
    
    /**
     * Sets the total number of the extracted tables in a document
     * @param tableNum
     *        the total number of tables
     */
    public void setTableNum (int tableNum) {
    	this.tableNum = tableNum;
    }

    /**
     * Gets the mapping table of the HTML code
     * @return the string array recording the decode mapping table
     */
    public String[] getHtml2CharMapping () {
    	Html2Char[0] = " ";	//&#32
    	Html2Char[1] = "!";
    	Html2Char[2] = "\"";
    	Html2Char[3] = "#";	
    	Html2Char[4] = "$";	//36
    	Html2Char[5] = "%";
    	Html2Char[6] = "&";
    	Html2Char[7] = "'";
    	Html2Char[8] = "(";
    	Html2Char[9] = ")";	//41
    	Html2Char[10] = "*";
    	Html2Char[11] = "+";
    	Html2Char[12] = ",";
    	Html2Char[13] = "-";
    	Html2Char[14] = ".";	//46
    	Html2Char[15] = "/";	
    	Html2Char[16] = "0";
    	Html2Char[17] = "1";
    	Html2Char[18] = "2";
    	Html2Char[19] = "3";	//51
    	Html2Char[20] = "4";
    	Html2Char[21] = "5";
    	Html2Char[22] = "6";
    	Html2Char[23] = "7";
    	Html2Char[24] = "8";	//56
    	Html2Char[25] = "9";
    	Html2Char[26] = ":";
    	Html2Char[27] = ";";
    	Html2Char[28] = "<";
    	Html2Char[29] = "=";	//61
    	Html2Char[30] = ">";
    	Html2Char[31] = "?";
    	Html2Char[32] = "@";
    	Html2Char[33] = "A";
    	Html2Char[34] = "B";	//66
    	Html2Char[35] = "C";
    	Html2Char[36] = "D";
    	Html2Char[37] = "E";
    	Html2Char[38] = "F";
    	Html2Char[39] = "G";	//71
    	Html2Char[40] = "H";
    	Html2Char[41] = "I";
    	Html2Char[42] = "J";
    	Html2Char[43] = "K";
    	Html2Char[44] = "L";	//76
    	Html2Char[45] = "M";
    	Html2Char[46] = "N";
    	Html2Char[47] = "O";
    	Html2Char[48] = "P";
    	Html2Char[49] = "Q";	//81
    	Html2Char[50] = "R";
    	Html2Char[51] = "S";
    	Html2Char[52] = "T";
    	Html2Char[53] = "U";
    	Html2Char[54] = "V";	//86
    	Html2Char[55] = "W";
    	Html2Char[56] = "X";
    	Html2Char[57] = "Y";
    	Html2Char[58] = "Z";
    	Html2Char[59] = "[";	//91
    	Html2Char[60] = "\\";
    	Html2Char[61] = "]";
    	Html2Char[62] = "^";
    	Html2Char[63] = "_";
    	Html2Char[64] = "`";	//96
    	Html2Char[65] = "a";		
    	Html2Char[66] = "b";
    	Html2Char[67] = "c";
    	Html2Char[68] = "d";
    	Html2Char[69] = "e";	//101
    	Html2Char[70] = "f";
    	Html2Char[71] = "g";
    	Html2Char[72] = "h";
    	Html2Char[73] = "i";
    	Html2Char[74] = "j";	//106
    	Html2Char[75] = "k";
    	Html2Char[76] = "l";
    	Html2Char[77] = "m";
    	Html2Char[78] = "n";
    	Html2Char[79] = "o";	//111
    	Html2Char[80] = "p";
    	Html2Char[81] = "q";
    	Html2Char[82] = "r";
    	Html2Char[83] = "s";
    	Html2Char[84] = "t";	//116
    	Html2Char[85] = "u";
    	Html2Char[86] = "v";
    	Html2Char[87] = "w";
    	Html2Char[88] = "x";
    	Html2Char[89] = "y";	//121
    	Html2Char[90] = "z";
    	Html2Char[91] = "{";
    	Html2Char[92] = "|";
    	Html2Char[93] = "}";
    	Html2Char[94] = "~";	//126

    	return Html2Char;
    }
}
