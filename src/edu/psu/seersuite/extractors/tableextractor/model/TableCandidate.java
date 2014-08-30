/**
 * 
 */
package edu.psu.seersuite.extractors.tableextractor.model;

import java.util.ArrayList;

//XML library jdom.org

/*
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.jdom.output.XMLOutputter;
*/

/**
 * This class defines the internal data structure for a table candidate.
 * 
 * @author Ying, Shuyi
 * 
 */
public class TableCandidate {
    private boolean valid = true;                    //whether this table candidate is a valid table or not
    private boolean WideTable = false;               //whether this table is a wide table or not
    private boolean topCaptionLocation = true;       //whether the caption of the table is on the top of the table body or not
    
    private String caption;                          //the caption of this table candidate
    private String keyword;                          //the table keyword of this table candidate
    private String metadata_HighLevel = "";          //the metadata of this table in the high-level (without the table structure decomposition result)
    private String metadata_StructureLevel = "";     //the metadata of this table in the structure-level (with the table structure decomposition result)  
    private String rowHeadings = "";                 //the heading rows of a table candidate
    private String columnHeadings = "";              //the heading columns of a table candidate
    
    private String classficationData;                //the classification data for further table understanding  
    private String referenceText = "";               //the reference text in the document of a table candidate
    private String footnoteText = "";                //the table footnote
    private String tablecontent = "";                //the table content
    
    private int pageId = 0;                          //the page Id where the table is located in
    private int captionStartLine;                    //the line ID of the first table caption line in the document page
    private int captionEndLine;                      //the line ID of the last table caption line in the document page
    private int bodyStartLine;                       //the line ID of the first table body line in the document page
    private int bodyEndLine;                         //the line ID of the last table body line in the document page
    private int headerEndLine;					     //the line ID of the last table header line in the table body
    private int footnoteBeginRow = -1;               //the line ID of the first table footnote line in the document page, if appliable
    private int headingLineNumber = 0; 	             //the number of combined lines in the heading rows
    private int maxColumnNumber = 0;                 //the maximum number of table columns
    private int columnNum = 0;                       //the real number of table columns
    private int rowNum = 0;                          //the number of table rows
    private int headColNum = 0;						
   
    private float captionX;                          //the left-end X axis of the table caption  
    private float captonEndX;                        //the right-end X axis of the table caption 
    private float bodyX;                             //the left-end X axis of the table body 
    private float bodyEndX;                          //the right-end X axis of the table body 
    private float minGapBtwColumns = 1000.0f;        //the minimum gaps between every two neighbor table columns 
    private float height = 0.0f;                     //the table height
    private float width = 0.0f;                      //the table width
        
    public String[][] cells;                         //the table cells
    public boolean[][] crossCells;                   //the cross row/column table cells (nesting cells)
    private float[] leftX_tableColumns;              //the left-end X axis of each table column
    private float[] rightX_tableColumns;             //the right-end X axis of each table column
    
    private ArrayList<TableRow> rows = new ArrayList<TableRow>();
    private ArrayList<TableColumn> columns = new ArrayList<TableColumn>();  
    private ArrayList<TextPiece> stub = new ArrayList<TextPiece>();
    //private Document xml_doc;
    
    /**
     * Gets the left-end X-axis of each table column
     * @return  the left-end X-axis of each table column
     */
    public float[] getLeftX_tableColumns() {
    	return leftX_tableColumns;
    }
    
    /**
     * Gets the right-end X-axis of each table column
     * @return  the right-end X-axis of each table column
     */
    public float[] getRightX_tableColumns() {
    	return rightX_tableColumns;
    }
    
    /**
     * Gets the table cells
     * @return all the cells of a table
     */
    public String[][] getCells() {
    	return cells;
    }
    
    /**
     * Gets all the nesting cells
     * @return all the nesting cells
     */
    public boolean[][] getCrossCells() {
    	return crossCells;
    }
    
    /**
     * Adds a newly detected row into a table
     * @param row
     *            the row to add
     */
    public void addRow(TableRow row) {
    	rows.add(row);
    }
    
    /**
     * Add a column into a table
     * @param column
     * 				the column to add
     */
    public void addColumn(TableColumn column) {
    	columns.add(column);
    }

    /**
     * Gets the number of columns of this table
     * @return the number of columns of this table
     */
    public int getColumnNumthisTable() {
    	return columnNum;
    }
    
    /**
     * Gets the page number where the table is located
     * @return pageId
     *            the page number
     */
    public int getContainingPageNumber() {
    	return pageId;
    }
    
    /**
     * Gets the table height
     * @return height
     *           the table height
     */
    public float getHeight(){
    	return this.height;
    }
      
    /**
     * Gets the table width 
     * @return width
     *         the table width
     */
    public float getWidth(){
    	return this.width;
    }
    
    /** 
     * Gets the total number of table caption lines
     * @return the number of lines in table caption
     */
    public int getCaptionLineCount() {
    	return captionEndLine - captionStartLine + 1;
    }

    /**
     * Gets the width of the table caption area
     * @return the caption width
     */
    public float getCaptionWidth() {
    	return captonEndX - captionX;
    }

    /**
     * Gets the number of cells in a table
     * @return the number of cells
     */
    public int getCellCount(){
    	return bodyEndLine - bodyStartLine + 1;
    }

    /**
     * Gets the last line of the table body
     * @return the last line of the table body
     */
    public int getBodyEndLine(){
    	return bodyEndLine;
    }

    /**
     * Gets the right-end X-axis of a table body
     * @return the right-end X-axis of a table body
     */
    public float getBodyEndX(){
    	return bodyEndX;
    }

    /**
     * Gets the first line of a table body
     * @return the first line of a table body
     */
    public int getBodyStartLine() {
    	return bodyStartLine;
    }
    
    /**
     * Gets the last line of a table header
     * @return the last line of a table header
     */
    public int getHeaderLastLine(){
    	return headerEndLine;
    }

    /**
     * Gets the left-end X-axis of a table body
     * @return left-end X-axis of a table body
     */
    public float getBodyX(){
    	return bodyX;
    }
    
    /**
     * Gets the minimum gap between table columns
     * @return the minimum gap between table columns
     */
    public float getMinGapBtwColumns() {
    	return minGapBtwColumns;
    }

    /**
     * Gets the table caption
     * @return the table caption
     */
    public String getCaption(){
    	return caption;
    }
    
    /**
     * Gets the table reference text
     * @return The table reference text
     */
    public String getRefText() {
    	return referenceText;
    }
   
    /**
     * Gets the table footnote
     * @return the table footnote
     */
    public String getFootnoteText() {
    	return  footnoteText;
    }

    /**
     * Gets the table metadata in high level (without the table structure decomposition)
     * @return the high-level table metadata
     */
    public String getMetadata_HighLevel(){
    	return metadata_HighLevel;
    }
    
    /**
     * Gets the table metadata with table structure decomposition information
     * @return the table metadata with table structure decomposition information
     */
    public String getMetadata_StructureLevel() {
    	return metadata_StructureLevel;
    }
    
    /**
     * Gets the heading rows of a table
     * @return the row headings 
     */
    public String getRowHeadings() {
    	return rowHeadings;
    }
    
    /**
     * Gets the heading columns of a table
     * @return the column headings
     */
    public String getColumnHeadings() {
    	return columnHeadings;
    }
    
    /**
     * Gets the table content/data cells
     * @return the table content
     */
    public String getTableContent(){
    	return tablecontent;
    }
    
    /**
     * Gets the table classification data
     * @return the table classification data
     */
    public String getClassificationData(){
    	return classficationData;
    }
    
    /**
     * Gets the ending line of the table caption
     * @return the the ending line of the table caption --- captionEndLine
     */
    public int getCaptionEndLine() {
    	return captionEndLine;
    }

    /**
     * Gets the starting line of the table caption
     * @return the starting line of a table caption -- captionStartLine
     */
    public int getCaptionStartLine(){
    	return captionStartLine;
    }

    /**
     * Gets the starting X-axis of the table caption
     * @return the starting X-axis of the table caption -- captionX
     */
    public float getCaptionX() {
    	return captionX;
    }

    /**
     * Gets the ending X-axis of the table caption
     * @return the ending X-axis of the table caption -- captonEndX
     */
    public float getCaptonEndX(){
    	return captonEndX;
    }

    /**
     * Gets the beginning line of the table footnote
     * @return the beginning line of the table footnote -- footnoteBeginRow
     */
    public int getFootnoteBeginRow() {
    	return footnoteBeginRow;
    }
    
    /**
     * Gets the number of head columns of the table
     * @return the number of head columns of the table
     */
    public int getHeadColNumber() {
    	return headColNum;
    }

    /**
     * Get the number of table heading lines 
     * @return the number of table heading lines -- headingLineNumber
     */
    public int getHeadingLineNumber(){
        return headingLineNumber;
    }

    /**
     * Gets the keyword of a table caption
     * @return the keyword of a table caption
     */
    public String getKeyword(){
    	return keyword;
    }

    /**
     * Gets the maximum number of columns
     * @return the maximum number of columns -- maxColumnNumber
     */
    public int getMaxColumnNumber()  {
    	return maxColumnNumber;
    }

    /**
     * Gets the table rows
     * @return the table rows
     */
    public ArrayList<TableRow> getRows(){
    	return rows;
    }
    
    /**
     * Gets the table columns
     * @return the table column
     */
    public ArrayList<TableColumn> getColumns(){
    	return columns;
    }
    
    /**
     * Gets table stub
     */
    public ArrayList<TextPiece> getTableStub(){
    	return stub;
    }

    /**
     * Judges whether the table candidate is a valid table
     * @return the validity of the table
     */
    public boolean isValid(){
    	return valid;
    }

    /**
     * Judges whether it is a wide table or not
     * @return whether it is a wide table or not
     */
    public boolean isWideTable(){
    	return WideTable;
    }

    /**
     * Judges the table caption location -- top caption or below caption
     * @return the table caption location
     */
    public boolean isTopCaption() {
    	return topCaptionLocation;
    }
    
   /**
    * Sets the high-level table metadata
    * @param i 
    *        the total number of pages of the document
    */
    public void setHighLevelMeta(int i) {
    	String meta = "<Table>\n";
    	meta = meta + "<pageNumInDoc>" + (i+1) + "</pageNumInDoc>\n"; 
    	caption=replaceAllSpecialChracters(caption);
    	meta = meta + "<TableCaption>" + caption + "</TableCaption>\n";
    	String location = "";
    	if (topCaptionLocation) location="top";
    	else location="below";
    	location=replaceAllSpecialChracters(location);
    	meta = meta + "<CaptionLocation>" + location + "</CaptionLocation>\n";
    	metadata_HighLevel=replaceAllSpecialChracters(metadata_HighLevel);
    	meta = meta + "<dataHighLevel>" + metadata_HighLevel + "</dataHighLevel>\n";
    	this.setMetadataHighLevel(meta);
    }
    
    /**
     * Sets the table metadata with the detailed structure decomposition information
     * @param YNum
     *          the total number of table lines including footnote
     * @param cc 
     *          the total number of table columns
     * @param wordsOfAPage
     *          the list of words in a document page
     * @param docInfo
     *          an object of DocInfo         
     */
    public void setMetadataStructureLevel(int YNum, int cc, ArrayList<TextPiece> wordsOfAPage, DocInfo docInfo) {
		String rowHeading = this.getRowHeadings();
		int heads = this.getHeadingLineNumber();
    	int footnoteLineIndex = this.getFootnoteBeginRow();
    	int MAXcolumnNum = this.getMaxColumnNumber();
    	float[] leftX_tableColumns = this.getLeftX_tableColumns();
    	float[] rightX_tableColumns = this.getRightX_tableColumns();
    	String[][] cells = this.getCells();
		String detailedTableMeta = "";
		detailedTableMeta = "<TableColumnHeading>\n" + columnHeadings + "</TableColumnHeading>" + "\n";
		
		detailedTableMeta = detailedTableMeta + "<TableContent>\n";
		for (int tt=(heads+1); tt<footnoteLineIndex; tt++) {
			int cellNumThisRow = cells[tt].length;
			String contentThisRow = "";
			for (int i=0; i<cellNumThisRow; i++) {
				contentThisRow = contentThisRow + cells[tt][i] + ";";
			}
			contentThisRow = replaceAllSpecialChracters(contentThisRow);
			this.tablecontent += contentThisRow+"\n"; // for standard XML only
			detailedTableMeta = detailedTableMeta + contentThisRow + "\n";
		}
		detailedTableMeta = detailedTableMeta + "</TableContent>"  + "\n";
		rowHeading = replaceAllSpecialChracters(rowHeading);
		detailedTableMeta = detailedTableMeta + "<TableRowHeading>" + rowHeadings + "</TableRowHeading>" + "\n";
		
		detailedTableMeta = detailedTableMeta + "<TableFootnote>";
		int footnote = footnoteLineIndex;
		while (footnote<YNum) {
			int cellNumThisRow = this.getRows().get(footnote).getCells().size();
			String contentThisRow = "";
			for (int i=0; i<cellNumThisRow; i++) {
				contentThisRow = contentThisRow + this.getRows().get(footnote).getCells().get(i).getText() + "\t";
			}
			contentThisRow = replaceAllSpecialChracters(contentThisRow);
			footnoteText = footnoteText + contentThisRow + "\n";
			footnote++;
		}
		detailedTableMeta = detailedTableMeta + footnoteText  + "... </TableFootnote>"  + "\n";
		
		if (MAXcolumnNum<cc) MAXcolumnNum=cc;
		detailedTableMeta = detailedTableMeta + "<ColumnNum>" + cc + "</ColumnNum>" + "\n";
		detailedTableMeta = detailedTableMeta + "<RowNum>" + footnoteLineIndex + "</RowNum>" + "\n";
		
		String columnCoordinates = "";
		for (int qq=0; qq<cc; qq++) {
			columnCoordinates = columnCoordinates + "(" + leftX_tableColumns[qq] + "," + rightX_tableColumns[qq] + "); ";
		}
		columnCoordinates = replaceAllSpecialChracters(columnCoordinates);
		detailedTableMeta = detailedTableMeta + "<ColumnCoordinates>" + columnCoordinates + "</ColumnCoordinates>" + "\n";
		
		this.height = wordsOfAPage.get(this.getBodyEndLine()).getEndY() - wordsOfAPage.get(this.getBodyStartLine()).getY(); 
		detailedTableMeta = detailedTableMeta + "<TableHeight>"  + this.height + "</TableHeight>" + "\n";

		System.out.println(this.getBodyEndLine());
		
		this.width = rightX_tableColumns[cc-1] - leftX_tableColumns[0];
		
		detailedTableMeta = detailedTableMeta + "<TableWidth>"  + this.width + "</TableWidth>" + "\n";
		if (WideTable==false) {	//fix the wideTableOrNot based on the width
			if (rightX_tableColumns[cc-1]>(docInfo.getMiddleX()) && leftX_tableColumns[0]<docInfo.getMiddleX()) 
				WideTable = true;
		}
		detailedTableMeta =detailedTableMeta + "<isWideTable>" + WideTable + "</isWideTable>\n";
		referenceText = replaceAllSpecialChracters(referenceText);
		detailedTableMeta = detailedTableMeta + "<TableReferenceText>" + referenceText + "</TableReferenceText>" + "\n";
		detailedTableMeta = detailedTableMeta + "</Table>" + "\n";
		
		setMetadataStructureLevel(detailedTableMeta);
		int tableNum = docInfo.getTableNum();
		docInfo.setTableNum(tableNum+1);
	}
      
    /**
     * Sets an empty metadata file 
     * @param YNum
     *          the total number of table lines including footnote
     * @param cc 
     *          the total number of table columns
     * @param wordsOfAPage
     *          the list of words in a document page
     * @param docInfo
     *          an object of DocInfo
     */
    public void setEmptyMetadataStructureLevel(int YNum, int cc, ArrayList<TextPiece> wordsOfAPage, DocInfo docInfo) {
		String detailedTableMeta = "";
		detailedTableMeta = "<TableColumnHeading>\n" + "</TableColumnHeading>" + "\n";
		
		detailedTableMeta = detailedTableMeta + "<TableContent>\n";
		detailedTableMeta = detailedTableMeta + "</TableContent>"  + "\n";
		detailedTableMeta = detailedTableMeta + "<TableRowHeading>" + "</TableRowHeading>" + "\n";
		
		detailedTableMeta = detailedTableMeta + "<TableFootnote>";
		detailedTableMeta = detailedTableMeta + "... </TableFootnote>"  + "\n";
		detailedTableMeta = detailedTableMeta + "<ColumnNum>" + "</ColumnNum>" + "\n";
		detailedTableMeta = detailedTableMeta + "<RowNum>" + "</RowNum>" + "\n";
		detailedTableMeta = detailedTableMeta + "<ColumnCoordinates>" + "</ColumnCoordinates>" + "\n";
		
		detailedTableMeta = detailedTableMeta + "<TableHeight>"  + "</TableHeight>" + "\n";
		
		detailedTableMeta = detailedTableMeta + "<TableWidth>"  + "</TableWidth>" + "\n";
		detailedTableMeta =detailedTableMeta + "<isWideTable>" + WideTable + "</isWideTable>\n";
		referenceText = replaceAllSpecialChracters(referenceText);
		detailedTableMeta = detailedTableMeta + "<TableReferenceText>" + referenceText + "</TableReferenceText>" + "\n";
		detailedTableMeta = detailedTableMeta + "</Table>" + "\n";
		
		setMetadataStructureLevel(detailedTableMeta);
		int tableNum = docInfo.getTableNum();
	}

    /**
     * Replaces all special characters in a given string
     * @param toBeReplaced
     *         The string to be replaced
     * @return
     *         the string after the replace
     */ 
   public static String replaceAllSpecialChracters(String toBeReplaced) {
 	   toBeReplaced = toBeReplaced.replaceAll("&", "&amp;");
 	   toBeReplaced = toBeReplaced.replaceAll("<", "&lt;");
 	   toBeReplaced = toBeReplaced.replaceAll(">", "&gt;");
 	   toBeReplaced = toBeReplaced.replaceAll("\"", "&quot;");
 	   toBeReplaced = toBeReplaced.replaceAll("'", "&apos;");
 	   int len = toBeReplaced.length();
 	   char [] temp = toBeReplaced.toCharArray();
 	   for(int i=0; i<len; i++)
 	   {
 		   char xCode = temp[i];
 		   if( (xCode > '\u0000' && xCode <= '\u0020') ||  			 			 				  
 		    (xCode == '\u0026') ||
 			(xCode == '\u0022') || 			
 			(xCode == '\u02C6') ||
 			(xCode == '\u201C') ||
 			(xCode == '\u201D') ||
 			(xCode == '\u201E')) {
 			   String sub1 = toBeReplaced.substring(0, i);
 			   String sub2 = toBeReplaced.substring(i+1, len);
 			   
 			   String replacement = "";
 			   if((xCode > '\u0000' && xCode <= '\u0020')) replacement = " "; 			    			   
 			   if(xCode == '\u0026') replacement = " "; 			
 			   if(xCode == '\u0022') replacement = " ";
 			   if(xCode == '\u02C6') replacement = " ";
 			   if(xCode == '\u201C') replacement = " ";
 			   if(xCode == '\u201D') replacement = " ";
 			   if(xCode == '\u201E') replacement = " ";
 			   
 			   toBeReplaced = sub1+ replacement +sub2;
 		   }
 		   len = toBeReplaced.length();
 		   temp = toBeReplaced.toCharArray();
 	   }
 	   return(toBeReplaced);
    }

    /**
     * Sets the table cells  
     * @param cells
     *         the table cells to be set
     */
    public void setCells(String[][] cells){
    	this.cells = cells;
    }
    
    /**
     * Sets the cross-column cells
     * @param crossCells
     *          the cross-column cells to be set
     */
    public void setCrossCells(boolean[][] crossCells) {
    	this.crossCells = crossCells;
    }
    
    /**
     * Sets the left-end X-axis of table columns
     * @param leftX_tableColumns
     *        the left-end X-axis of table columns to be set
     */
    public void setLeftX_tableColumns(float[] leftX_tableColumns) {
    	this.leftX_tableColumns = leftX_tableColumns;
    }
    
    /**
     * Sets the right-end X-axis of table columns
     * @param rightX_tableColumns
     *          the right-end X-axis of table columns to be set
     */
    public void setRightX_tableColumns(float[] rightX_tableColumns) {
    	this.rightX_tableColumns = rightX_tableColumns;
    }

    /**
     * Sets the last line of the table body
     * @param bodyEndLine
     *            the bodyEndLine to set
     */
    public void setBodyEndLine(int bodyEndLine){
    	this.bodyEndLine = bodyEndLine;
    }

    /**
     * Sets the number of table columns
     * @param columnNum
     *            the number of columns
     */
   public void setColumnNum (int columnNum) {
	   this.columnNum = columnNum;
   }
    
   /**
    * Sets the right-end X-axis of the table body
    * @param bodyEndX
    *         the right-end X-axis of the table body to be set
    */
    public void setBodyEndX(float bodyEndX){
    	this.bodyEndX = bodyEndX;
    }

    /**
     * Sets the starting line of the table body
     * @param bodyStartLine
     *            the bodyStartLine to set
     */
    public void setBodyStartLine(int bodyStartLine){
    	this.bodyStartLine = bodyStartLine;
    }

    /**
     * Sets the left-end X-axis of the table body
     * @param bodyX
     *            the bodyX to set
     */
    public void setBodyX(float bodyX) {
    	this.bodyX = bodyX;
    }

    /**
     * Sets the minimum gap between table columns
     * @param minGapBtwColumns
     *          the minimum gap between table columns to be set 
     */
    public void setMinGapBtwColumns(float minGapBtwColumns) {
    	this.minGapBtwColumns = minGapBtwColumns;
    }
    
    
    /**
     * Sets the table caption
     * @param caption
     *            the table caption to set
     */
    public void setCaption(String caption){
    	this.caption = caption;
    }
    
    /**
     * Sets the table reference text in the document
     * @param referenceText
     *          the reference text of a table
     */
    public void setRefText(String referenceText) {
    	this.referenceText = referenceText;
    }
    
    /**
     * Sets the table footnote
     * @param footnoteText
     *          the table footnote to be set
     */
    public void setFootnoteText(String  footnoteText){
    	this.footnoteText = footnoteText;
    }
    
    /**
     * Sets the high-level table metadata 
     * @param metadata_HighLevel
     *            the high-level table metadata to be set
     */
    public void setMetadataHighLevel(String metadata_HighLevel) {
    	this.metadata_HighLevel = metadata_HighLevel;
    }
    
    /**
     * Sets the table metadata with the detailed structure decomposition information
     * @param metadata_StructureLevel
     *        the table metadata with the structure decomposition information
     */
    public void setMetadataStructureLevel(String metadata_StructureLevel) {
    	this.metadata_StructureLevel= metadata_StructureLevel;
    }

    /**
     * Sets the table row headings
     * @param headingRows
     *        the row headings to be set
     */
    public void setHeadingRows(String headingRows) {
    	rowHeadings = headingRows;
    }
    
    /**
     * Sets the column headings
     * @param columnHeadings
     *         the column headings to be set
     */
    public void setColumnHeadings(String columnHeadings) {
    	this.columnHeadings = columnHeadings;
    }
    
    /**
     * Sets the data for table classification
     * @param classficationData
     *             the table classification data
     */
    public void setClassificationData(String classficationData) {
    	this.classficationData = classficationData;
    }
    
    /**
     * Sets the page number where the table is located
     * @param pageId
     *           the page number where the table is located
     */
    public void setPageId_thisTable(int pageId) {
    	this.pageId = pageId;
    }
    
    /**
     * Sets the last line of the table caption
     * @param captionEndLine
     *            the captionEndLine to set
     */
    public void setCaptionEndLine(int captionEndLine){
    	this.captionEndLine = captionEndLine;
    }

    /**
     * Sets the first line of the table caption
     * @param captionStartLine
     *            the captionStartLine to set
     */
    public void setCaptionStartLine(int captionStartLine){
    	this.captionStartLine = captionStartLine;
    }

    /**
     * Sets the left-end X-axis of the table caption
     * @param captionX
     *            the captionX to set
     */
    public void setCaptionX(float captionX) {
    	this.captionX = captionX;
    }

    /**
     * Sets the right-end X-axis of the table caption
     * @param captonEndX
     *            the captonEndX to set
     */
    public void setCaptonEndX(float captonEndX) {
    	this.captonEndX = captonEndX;
    }

    /**
     * Sets the beginning line of the table footnote
     * @param footnoteBeginRow
     *            the footnoteBeginRow to set
     */
    public void setFootnoteBeginRow(int footnoteBeginRow){
    	this.footnoteBeginRow = footnoteBeginRow;
    }

    /**
     * Sets the total number of the column heading lines 
     * @param headingLineNumber the headingLineNumber to set
     */
    public void setHeadingLineNumber(int headingLineNumber){
    	this.headingLineNumber = headingLineNumber;
    }

    /**
     * Sets the number of head columns of a table
     * @param headColNum is the number of head columns
     */
    public void setHeadColNumber(int headColNum){
    	this.headColNum = headColNum;
    }
    /**
     * Sets the table caption keywords
     * @param keyword
     *            the keyword to set
     */
    public void setKeyword(String keyword) {
    	this.keyword = keyword;
    }
  
    /**
     * Sets the maximum number of table columns
     * @param maxColumnNumber
     *            the maxColumnNumber to set
     */
    public void setMaxColumnNumber(int maxColumnNumber) {
    	this.maxColumnNumber = maxColumnNumber;
    }

    /**
     * Sets the validility of the table
     * @param valid
     *            the valid to set
     */
    public void setValid(boolean valid) {
    	this.valid = valid;
    }
    
    /**
     * Sets the side (wide or not) of a table
     * @param wideTable
     *         whether the table is wide or not
     */
    public void setWideTable(boolean wideTable) {
    	this.WideTable = wideTable;
    }
    
    /**
     * Sets the location of the table caption -- top or below
     * @param topCaptionLocation
     *          the table caption location to be set
     */
    public void setTopCaption(boolean topCaptionLocation) {
    	this.topCaptionLocation = topCaptionLocation;
    }
    
    /**
     * Sets the last line of table header
     * @param headerEndLine
     * 			the last line of table header to be set
     */
    public void setHeaderLastLine(int headerEndLine) {
    	this.headerEndLine = headerEndLine;
    }
    
    /**
     * Sets table stub
     * @param stub
     * 			the text pieces belonging to both row header and column header
     */
    public void setTableStub(ArrayList<TextPiece> stub){
    	this.stub = stub;
    }
}
