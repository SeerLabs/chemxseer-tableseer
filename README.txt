                                                README
                                                ======

                                     TableSeer API 1.0.0 release
                                     ---------------------------

welcome to the TableSeer API 1.0.0 release! This release includes versions of the TableSeer API implementation, some examples, and documentation for the TableSeer API. 

The "tablesearch" directory contains the following subdirectories:
	bin
	doc
	lib
	release
	script
	src

Within the "lib" folder, the following jar files should be included. 
	ant.jar
	bcmail-jdk14-132.jar
	bcprov-jdk14-132.jar
	checkstype-all-4.2.jar
	FontBox-0.1.0-dev.jar
	jaxen-core.jar
	jaxen-jdom.jar
	jdom.jar
	junit.jar
	libtet_java.so
	log4j-all-1.3alpha-8.jar   //the required by table extraction code other jars are required by PDFBox
	lucene-core-2.0.0.jar
	lucene-demos-2.0.0.jar
	PDFBox-0.7.3.jar
	saxpath.jar
	TET.jar
	xalan.jar
	xerces.jar
	xml-apis.jar


path: src/edu/pau/seersuite/extractors/tableextractor/ includes two subdirectories "extraction" and "model" as well as two java files: "Config.java" and "Debug.java".

"extraction" directory includes 6 classes:
       BatchExtraction.java
       iPdfParser.java
       PdfBoxParser.java
       PdfFileFilter.java
       TableExtractor.java
       TetParser.java

"model" directory includes 5 models:
	DocInfo.java
	TableCandidate.java
	Table.java
	TableRow.java
	TextPiece.java



                                     TableSeer API 1.1.0 release
                                     ---------------------------
Welcome to the TableSeer API 1.1.0 release! The release improves table header detection based on Random Forest learning techniques.
Changes based on 1.0.0 release are as follows:

Within the "lib" folder, the following jar files should be supplemented:
       FastRandomForest.jar
       weka.jar 
       
Within the "extraction" directory, the following classes are added:
			 RowFeatureExtractor.java
			 ColFeatureExtractor.java
			 
Within the "model" directory, the following models are added:
			 TableColumn.java

The training files "row_feature.arff" and "col_feature.arff" should be placed into the same directory as the PDF files to be extracted.