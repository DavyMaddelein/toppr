@ECHO OFF

REM  These lines set the classpath.

REM  The classpath should be OK if the script is ran in the folder containing the
REM  jar files. Else, the '.\' needs to be adapted to point to the correct folder.
set CLASSPATH=.\lib\jalview.jar;.\lib\jcommon-1.0.8-junit.jar;.\lib\jhall.jar;.\lib\jaxrpc.jar;.\lib\junit-4.4.jar;.\lib\commons-math-1.2.jar;.\lib\activation.jar;.\lib\mail.jar;.\lib\oscache-2.4.1.jar;.\lib\commons-logging-1.1.1.jar;.\lib\jfreechart-1.0.9.jar;.\lib\batik-transcoder.jar;.\lib\iText-2.0.8.jar;.\lib\batik-anim.jar;.\lib\log4j-1.2.8.jar;.\lib\javax.servlet.jar;.\lib\jxl.jar;.\lib\commons-discovery-0.2.jar;.\lib\mysql-connector-java-5.0.7-bin.jar;.\lib\xml-apis-ext.jar;.\lib\wsdl4j.jar;.\lib\vamsas-client.jar;.\lib\commons-collections-3.2.jar;.\lib\mydas-1.0.2.jar;.\lib\batik-swing.jar;.\lib\batik-parser.jar;.\lib\wsdl4j-1.5.1.jar;.\lib\batik-gvt.jar;.\lib\WSEmboss.jar;.\lib\ms_lims-6.0.1.jar;.\lib\axis.jar;.\lib\pdf-transcoder-1.0.jar;.\lib\batik-gui-util.jar;.\lib\Jmol-11.0.2.jar;.\lib\castor-0.9.6-xml.jar;.\lib\commons-logging.jar;.\lib\batik-util.jar;.\lib\peptizer-1.2.2.jar;.\lib\batik-script.jar;.\lib\jcommon-1.0.8.jar;.\lib\neobio.jar;.\lib\axis-1.2.jar;.\lib\utilities-2.8.jar;.\lib\batik-extension.jar;.\lib\batik-codec.jar;.\lib\mascot_datfile-1.5.3.jar;.\lib\regex.jar;.\lib\batik-xml.jar;.\lib\batik-svg-dom.jar;.\lib\commons-logging-1.0.4.jar;.\lib\batik-bridge.jar;.\lib\batik-css.jar;.\lib\batik-svggen.jar;.\lib\batik-awt-util.jar;.\lib\ppr-0.1.jar;.\lib\lax.jar;.\lib\saaj.jar;.\lib\batik-ext.jar;.\lib\log4j-1.2.4.jar;.\lib\xercesImpl.jar;.\lib\jfreechart-1.0.9-swt.jar;.\lib\batik-dom.jar;.\lib\Strbio.jar;.\lib\xpp3-1.1.3.4.M.jar;.\lib\logo-1.1.jar;.\lib\jcommon-xml-1.0.8.jar;.\lib\commons-discovery.jar;.\lib\dbtoolkit-3.6.2.jar;.\lib\xml-apis.jar;.\lib\jfreechart-1.0.9-experimental.jar;.\classes\;%CLASSPATH%

REM  The next line runs the program without debug information.
java -Xmx512m  be.proteomics.pprIA.update.Updater
