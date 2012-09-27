<%@ page import="be.proteomics.pprIA.general.Spectrum" %>
<!--
- Copyright (c) 2006 The European Bioinformatics Institute, and others.
- All rights reserved. Please see the file LICENSE
- in the root directory of this distribution.
-
- PRIDE spectrum viewing JSP
-
- @author Phil Jones
- @version $Id: viewSpectrum.jsp,v 1.17 2006/10/30 11:54:10 philip_jones Exp $
-->





<html>
<head>
    <title>
        PRIDE Spectrum Viewer.
        Experiment Accession: 12011
		Spectrum Reference:	14277
    </title>
    <script type="text/javascript" src="wz_jsgraphics.js"></script>
	<script type="text/javascript" src="AminoAcidClass.js"></script>
	<style type="text/css">
        td,legend {
		font-family: verdana, arial, sans-serif;
		font-size: 11;
		font-weight: bold;
		}

        input {
		font-family: verdana, arial, sans-serif;
		font-size: 11;
		font-weight: normal;
		border: none;
		}
	</style>
    <script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-9963568-7']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</head>

<body style="margin:0px;border:0px;padding:0px;">
<%
    Spectrum spectrum = (Spectrum) session.getValue("spectrumView");
%>

<!-- The following script import must remain in the body section.-->
<script type="text/javascript" src="wz_dragdrop.js"></script>
<table border="0" width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td width="*">
			<img hspace="0" id = "headerBar" style="margin:0px;border:0px;padding:0px;z-index:5;" src="/toppr/images/spectrumViewer/repeat_vertical_line.gif" alt="" width="100%" height="100" align = "left"/>
		</td>
		<td width="83">
			<a href="http://www.ebi.ac.uk/pride/" style="border: 0"><img hspace="0" style="margin:0px;border:0px;padding:0px;z-index:5;" src="/toppr/images/spectrumViewer/logo_ebi_pride.gif" alt="This MS/MS spectrum is visualized via the EBI Pride MS/MS viewer" width="83" height="100" align = "right"/></a>
		</td>
	</tr>
</table>
<img name="printer" style="position:absolute;left:10px;top:34px;display:block;z-index:10;" src="/toppr/images/spectrumViewer/fileprint.gif" width="32" height="32" alt="Print Spectrogram" title="Print Spectrogram"/>
<img name="grid" style="position:absolute;left:46px;top:34px;display:block;z-index:10;" src="/toppr/images/spectrumViewer/gridOff.gif" width="32" height="32" alt="Toggle Grid Lines" title="Toggle Grid Lines"/>
<img name="annotation" style="position:absolute;left:82px;top:34px;display:block;z-index:10;" src="/toppr/images/spectrumViewer/annotationOn.gif" width="32" height="32" alt="Toggle Annotation" title="Toggle Annotation"/>
<img name="peakListHeading" style="position:absolute;left:118px;top:12px;display:block;z-index:10;" src="/toppr/images/spectrumViewer/peak_list.gif" alt="" title="" width="66" height="16"/>
<img name="valuesText" style="position:absolute;left:118px;top:34px;display:block;z-index:10;" src="/toppr/images/spectrumViewer/valuesTEXT.gif" width="32" height="32" alt="View m/z and intensity values (Plain Text)" title="Retrive m/z and intensity value (Plain Text)"/>
<img name="valuesHTML" style="position:absolute;left:154px;top:34px;display:block;z-index:10;" src="/toppr/images/spectrumViewer/valuesHTML.gif" width="32" height="32" alt="View m/z and intensity values (HTML table)" title="View m/z and intensity values (HTML table)"/>
<img name="zoomOut" style="position:absolute;left:190px;top:34px;display:block;z-index:10;" src="/toppr/images/spectrumViewer/viewmag1.gif" width="32" height="32" alt="Reset Zoom" title="Reset Zoom"/>
<img name="track" style="position:absolute;left:250px;top:18px;display:block;z-index:10;" src="/toppr/images/spectrumViewer/track.gif" width="100" height="50" alt="Zoom Tool" title="Zoom Tool"/>
<img name="leftBound" src="/toppr/images/spectrumViewer/uparrow.gif" width="20" height="20" alt="Left m/z Zoom" title="Left m/z Zoom"/>
<img name="rightBound" src="/toppr/images/spectrumViewer/uparrow.gif" width="20" height="20" alt="Right m/z Zoom" title="Right m/z Zoom"/>
<img name="panZoomWindow" src="/toppr/images/spectrumViewer/small_blue.gif" width="105" height="4" alt="Pan Zoom Window" title="Pan Zoom Window"/>
<img name="intensityBound" src="/toppr/images/spectrumViewer/rightarrow.gif" width="20" height="20" alt="Intensity Zoom" title="Intensity Zoom"/>
<img name="massErrorTrack" style="position:absolute;left:400px;top:44px;display:block;z-index:10;" src="/toppr/images/spectrumViewer/mass_error_track.gif" width="130" height="31" alt="Modify the mass error (Daltons)" title="Modify the mass error (Daltons)"/>
<img name="massPointer" src="/toppr/images/spectrumViewer/uparrow.gif" width="20" height="20" alt="Modify the mass error (Daltons)" title="Modify the mass error (Daltons)"/>
<img name="denovoText" style="position:absolute;left:716px;top:34px;display:block;z-index:10;" src="/toppr/images/spectrumViewer/valuesTEXT.gif" width="32" height="32" alt="View de novo sequence (Plain Text)" title="View de novo sequence (Plain Text)"/>
<img name="denovoHTML" style="position:absolute;left:752px;top:34px;display:block;z-index:10;" src="/toppr/images/spectrumViewer/valuesHTML.gif" width="32" height="32" alt="View de novo sequence (HTML table)" title="View de novo sequence (HTML table)"/>
<%
    if(!spectrum.showFragmentIons()){
%>
    <img name="resetSelectedPeaks" style="position:absolute;left:680px;top:34px;display:block;z-index:10;" src="/toppr/images/spectrumViewer/undo.gif" width="32" height="32" alt="Remove all peak selections" title="Remove all peak selections"/>
    <img name="denovoHeading" style="position:absolute;left:716px;top:12px;display:block;z-index:10;" src="/toppr/images/spectrumViewer/de_novo.gif" alt="" title="" width="66" height="16"/>
<%
    }
%>

<img name="help" style="position:absolute;left:890px;top:34px;display:block;z-index:10;" src="/toppr/images/spectrumViewer/help.gif" width="32" height="32" alt="Help" title="Help"/>
<div style="position:absolute;top:100px;left:0px;">
    <table align="center" width="100%">
        <tr>
            <td align="right" valign="top">
                <table>
                    <tr>
                        <td align="left">
                            <span style="color:#999999;display:block;font-weight:bold;font-style:normal;" id="mzKey">
                                m/z
                                <br/>
                                <input type="text" id="mzDisplay" value="" size="8" readonly="readonly"/>
                            </span>
                            <span style="color:#999999;display:block;font-weight:normal;font-style:italic;" id="intenKey">
                                Intensity
                                <br/>
                                <input type="text" id="intenDisplay" value="" size="8" readonly="readonly"/>
                            </span>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <div id="denovoDisplay"></div>
            </td>
        </tr>
    </table>
</div>

<div id="errorD" style="position:absolute;display:block;left:375px;top:7px;z-index:10;">
    <table>
        <tr>
            <td colspan="2" align="center">
                Mass Error
                <input type="text" id="errorDVal" value="" size="8"
                       style="border:thin;border-style:solid;" readonly="readonly"></input>
                Da
            </td>
        </tr>
    </table>
</div>

<div id="chargeState" style="position:absolute;display:block;left:570px;top:25px;z-index:10;">
	 <table>
		 <tr>
			 <td align = "center">
				 Include ions<br/>
				 where z &gt; 1
			 </td>
		</tr>
		<tr>
			 <td align = "center" valign="middle">
				 <input type = "checkbox" name="chargeCheck" onchange="setCharge(this);" onclick="setCharge(this);"/>
			 </td>
		 </tr>
	 </table>
</div>

<%
    if(!spectrum.showFragmentIons()){
%>
<div id="yOrB" style="position:absolute;display:block;left:788px;top:20px;width:80px;z-index:10;">
    <form name="ionSeriesForm">
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="2" align="center">Ion Series</td>
			</tr>
			<tr>
				<td align="right" style="color:red;">
					Y
				</td>
				<td align = "center">
					<input type = "radio" name="ionSeries" value="y" checked="true" onchange="seriesChanged();" onclick="seriesChanged();"></input>
				</td>
			</tr>
			<tr>
				<td align="right" style="color:blue;">
					B
				</td>
				<td align = "center">
					<input type = "radio" name="ionSeries" value="b" onchange="seriesChanged();" onclick="seriesChanged();"></input>
				</td>
			</tr>
		</table>
    </form>
</div>
<%
    } else {
%>
<form name="ionSeriesForm">
        <div style="display:none">
            <input type = "radio" name="ionSeries" value="y" checked="true" onchange="seriesChanged();" onclick="seriesChanged();"></input>
            <input type = "radio" name="ionSeries" value="b" onchange="seriesChanged();" onclick="seriesChanged();"></input>
            <!--<input type="hidden" name="ionSeries" value="y"/>-->
        </div>
    </form>


<%
    }
%>

<div id="spectrogramCanvas">
</div>

<div id="peakInfoDiv"
     style="position:absolute;left:100px;top:100px;width:130px;padding:6px;border:1px solid #000099;background:#d6e6ff;font-family:verdana,arial,sans-serif;font-size:11;font-weight:normal;display:none;"></div>
<script type="text/javascript" language="JavaScript1.1">
<!--
/*******************************************************************\
Script to render a zoomable mass spectrogram
============================================

Phil Jones, EMBL-EBI, September 2005.
Version 2.0 May 2006 - Now includes simple de novo peptide sequencing
inspired by an interface developed by Lennart Martens.

This script makes use of the Javascript Vector Graphics Library
from www.walterzorn.com that can be found at:
http://www.walterzorn.com/jsgraphics/jsgraphics_e.htm

and the drag & drop DHTML library that can be found at:
http://www.walterzorn.com/dragdrop/dragdrop_e.htm

Tested successfully on:
Firefox all versions (Linux, Apple OSX & Windows),
Internet Explorer 6 (Windows and Apple OSX).
Konqueror 3.4.2 (Linux)
Opera (Windows and Apple OSX)

Note: to use dynamically, just need to re-write the two arrays
holding mz and intensity values and the title
of the graph. (e.g. in a JSP).

LICENSE: LGPL

This script is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License (LGPL) as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

For more details on the GNU Lesser General Public License,
see http://www.gnu.org/copyleft/lesser.html
\*******************************************************************/

/********************************************************\
		Variables that need to be written dynamically
\********************************************************/

// Two arrays holding mz and intensity values.
var mz = new Array (
<%=spectrum.getMzString()%>
);
var inten = new Array (
<%=spectrum.getIntensString()%>
);
<%
if(spectrum.showFragmentIons()){
%>
var typeArray = new Array (<%=spectrum.getFragmentIonTypes()%>);
var labelArray = new Array (<%=spectrum.getFragmentIonAnnotation()%>);
var colourArray = new Array (<%=spectrum.getFragmentIonColor()%>);
var additionalArray = new Array (<%=spectrum.getFragmentIonExtra()%>);
var graphTitle = "<%=spectrum.getTitle()%>";
<%
} else {
%>
var typeArray = null;
var labelArray = null;
var colourArray = null;
var additionalArray = null;
var graphTitle = "<%=spectrum.getTitle()%>";
<%
}
%>



/****************************************************\
		DEFINE GLOBAL VARIABLES
\****************************************************/

// Array to hold selected peaks for de novo sequencing
// Indexed by a String being the mz value of the peak.
var selectedPeaksArray = new Array(2);

selectedPeaksArray[0] = new Array(); // Y ion series
selectedPeaksArray[1] = new Array(); // B ion series
// Zoom tool dimensions (MUST be the same as the track width / height above.)
var TRACK_WIDTH = 100;
var TRACK_HEIGHT = 50;

var deNovoAllowed = <%=!spectrum.showFragmentIons()%>;

var MAX_INTENSITY_FACTOR = 10000;

// Set up variables for zooming (Set to no zoom).
var leftMzZoom = 0;
var rightMzZoom = TRACK_WIDTH;
var intensityZoom = MAX_INTENSITY_FACTOR;

// Variables to hold current window size
var winW = 0;
var winH = 0;

// Constants to hold size and number of axis ticks
var TICK_LENGTH=5;
var TICK_NUMBER=10;

// Variables to hold current mouse position.
var mouseX = 0;
var mouseY = 0;

// Reference to most recently selected peak.
var lastSelectedPeak = new Array(2);
lastSelectedPeak[0]=null;   // Y ion series
lastSelectedPeak[1]=null;   // B ion series

var currentSelectedPeakColour = new Array(2);
currentSelectedPeakColour[0]="ff0000";
currentSelectedPeakColour[1]="0000ff";

var notCurrentSelectedPeakColour = new Array(2);
//notCurrentSelectedPeakColour[0]="#FF9999";
notCurrentSelectedPeakColour[0]="#ffbbbb";
notCurrentSelectedPeakColour[1]="#bbbbff";

var currentSeries = null;

var currentSeriesMessageHtml = new Array(2);
currentSeriesMessageHtml[0] = "<br/><span style='color:red;'>Y Series</span>";
currentSeriesMessageHtml[1] = "<br/><span style='color:blue;'>B Series</span>";

var hasSelectedPeak = false;

// milliseconds timeout for peak details to disappear.
var PEAK_DETAILS_TIMEOUT = 4000;

// Variables holding names of all the relevant images.
var LEFT_MZ_BOUND = "leftBound";
var RIGHT_MZ_BOUND = "rightBound";
var INTENSITY_BOUND = "intensityBound";
var ZOOM_OUT = "zoomOut";
var TRACK = "track";
var PRINTER = "printer";
var GRID = "grid";
var ANNOTATION = "annotation";
var VALUES_TEXT = "valuesText";
var VALUES_HTML = "valuesHTML";
var PEAK_INFO_DIV = "peakInfoDiv";
var MASS_ERROR_TRACK = "massErrorTrack";
var MASS_ERROR_POINTER = "massPointer";
var MASS_ERROR_DISPLAY_DIV_ID = "errorD";
var MASS_ERROR_DISPLAY_ID = "errorDVal"
var RESET_SELECTED_PEAKS = "resetSelectedPeaks";
var HELP = "help";
var DENOVO_TEXT = "denovoText";
var DENOVO_HTML = "denovoHTML";
var DENOVO_HEADING = "denovoHeading";
var PEAKLIST_HEADING = "peakListHeading";
var HEADER_BAR = "headerBar";
var PAN_ZOOM_WINDOW = "panZoomWindow";
// Correction for the distance of the arrow pointer from top left of the image.
var POINTER_CORRECTION = -11;

var MASS_ERROR_TRACK_CORRECTION = 15;

var ARROW_BASE_WIDTH_CORRECTION = -3;

var MINIMUM_HEADING_WIDTH = 200;
var MINIMUM_RIGHT_SIDE_WIDTH = 100;

var SELECTED_PEAK_ARROW_ELEVATION = new Array(2);
SELECTED_PEAK_ARROW_ELEVATION[0] = 68;    // Elevation for Y ion annotation
SELECTED_PEAK_ARROW_ELEVATION[1] = 23;    // Elevation for B ion annotation

// Variable indicating default state of grid.
var grid = false;

// Variable indicating if peaks should be annotated.
var annotate = true;

// Graphics object for drawing spectrum.
var graphicsCanvas = new jsGraphics("spectrogramCanvas");

// Find the smallest and largest values in the two arrays.
var minMz = smallest (mz);
var maxMz = largest (mz);
var maxInten = largest (inten);

var HEADING_FONT_SIZE = "18px";
var LABEL_FONT_SIZE = "13px";
var NUMBER_FONT_SIZE = "11px";

// Slim these fonts down a bit for IE.
if (dd.ie){
	HEADING_FONT_SIZE = "16px";
	LABEL_FONT_SIZE = "11px";
	NUMBER_FONT_SIZE = "9px";
}

/****************************************************\
		Functions to make it all work...
\****************************************************/

// Define class holding x and y coordinates of top of displayed peak and the corresponding m/z value.
function DisplayedPeak (mz, intensity, x, y1, y2, arrayIndex){
	this.mz = mz;
	this.intensity = intensity;
	this.x = x;
	this.y1 = y1;
	this.y2 = y2;
    this.arrayIndex = arrayIndex;
}

// Define array that will hold all of these DisplayedPeak objects with names of form '345' where 345 is the
// x coordinate of the plot and the value is a DisplayedPeak object.
var mzToPeakHash;

// Function to return the largest value in an array of numbers.
function largest (myArray){
	var biggest = null;
	for (var counter = 0; counter < myArray.length; counter++){
		if (biggest == null || myArray[counter] > biggest){
			biggest = myArray[counter];
		}
	}
	return biggest;
}

// Function to return the smallest value in an array of numbers.
function smallest (myArray){
	var smallest = null;
	for (var counter = 0; counter < myArray.length; counter++){
		if (smallest == null || myArray[counter] < smallest){
			smallest = myArray[counter];
		}
	}
	return smallest;
}

/*
  Computes the mass error in the logarithmic range 0.0001 -> 10 from
  a scale value (from the GUI widget) in the range 0 -> 100.
*/
function scaleToMassError (relativeScale){
    return Math.pow(10, (relativeScale/20) -4);
}

/*
  Computes the scale value (from the GUI widget) in the range 0 -> 100
   from the mass error in the logarithmic range 0.0001 -> 10.
*/
function massErrorToScale (massError){
    return Math.round(( (Math.log (massError) / Math.LN10) + 4) * 20);
}

//Calculate the initial relative position of the mass error pointer
var massErrorXPos = massErrorToScale (Molecule.MASS_ERROR_DALTONS);

/*
   Sets global variables to window width / height.
*/
function getWindowSize(){
	winW = dd.getWndW();
	winH = dd.getWndH();
}

// Called to draw / redraw the spectrum based on current zoom / window size.
// param printable - boolean - draw in printable form (slower).
// param completeDraw - when zooming, a partial (fast) draw is performed
// to make the zooming smoother.
function drawSpectrogram(printable, completeDraw){
	// Two arrays holding mz and intensity values.
	if (printable != null){
		 graphicsCanvas.setPrintable(printable);
	}
	else {
		 graphicsCanvas.setPrintable(false);
	}

	getWindowSize();

	// Define boundaries of chart (Nominally set to be 80% of the available width / height)
	var left = Math.round(winW * 0.1);
	var right = winW - MINIMUM_RIGHT_SIDE_WIDTH;
	if (right - left < 50) right = left + 50;
	var top = MINIMUM_HEADING_WIDTH;
	var bottom = Math.round(winH * 0.9);

    var lastDrawnSelectedPeak = new Array(2);
    lastDrawnSelectedPeak[0]=null; // Y series
    lastDrawnSelectedPeak[1]=null; // B series

	// Get drawing...
	graphicsCanvas.clear();
	graphicsCanvas.setColor("#888888");
	// Draw y axis
	graphicsCanvas.drawLine(left, top, left, bottom + TICK_LENGTH);
	// Draw x axis
	graphicsCanvas.drawLine(left - TICK_LENGTH, bottom, right, bottom);

	// Only tick the axes if this is a complete draw (do
	// not do this while zooming for speed.)
    if (completeDraw){
        if (grid){
            graphicsCanvas.drawLine(right, bottom, right, top);
        }
        // Tick y axis
        for (ytick=top; ytick <= bottom; ytick+=(bottom - top) / TICK_NUMBER){
            graphicsCanvas.drawLine (left-TICK_LENGTH ,Math.round(ytick), (grid) ? right : left , Math.round(ytick));
        }
        // Tick x axis
        for (xtick=left; xtick <=right; xtick+=(right-left) / TICK_NUMBER){
            graphicsCanvas.drawLine (Math.round(xtick), (grid) ? top : bottom, Math.round(xtick), bottom+TICK_LENGTH);
        }
	}

	// This is just to ensure there are no nasty index out of bounds errors, caused by the sizes of the mz / intensity arrays being different.
	lineCount = Math.min (mz.length, inten.length);

	graphicsCanvas.setColor("#000000");

	// Calculate the zoom limits.
	var minMzLimit = (( maxMz - minMz) * (leftMzZoom / TRACK_WIDTH)) + minMz;
	var maxMzLimit = (( maxMz - minMz) * (rightMzZoom / TRACK_WIDTH)) + minMz;
	var intensityFactor =  maxInten * (intensityZoom / 10000);

	// Define an array used for finding the top n intensities.
	var topIntens = new Array();

	// Reset the peak array for the new draw.
	mzToPeakHash = new Array();

    var lastPeakXCoordinate = -1;
    var topPeakYCoordinate = bottom;
    var lastPeakColour = null;
    // Draw the peaks themselves.
	for (var indexer = 0; indexer < lineCount; indexer++){
		// Only draw them if the fall within the bounds of minMzlimit and maxMzLimit (zoom boundaries)
		if (completeDraw || inten[indexer]/intensityFactor > 0.03){

		if (mz[indexer] >= minMzLimit && mz[indexer] <= maxMzLimit){
			y1 = Math.round( Math.max( top, (top + (((intensityFactor - inten[indexer])/intensityFactor) * (bottom - top)))));
			x = Math.round( left + (((mz[indexer] - minMzLimit) / (maxMzLimit - minMzLimit)) * (right - left)) );

            // Set the selected peaks to the Y and B ions passed in as annotation.
            if (typeArray != null){
                if (typeArray[indexer] == "Y"){
                    selectedPeaksArray[0]["" + (mz[indexer])] = new DisplayedPeak (mz[indexer], inten[indexer], x, y1, bottom, indexer);
                }
                else if (typeArray[indexer] == "B"){
                    selectedPeaksArray[1]["" + (mz[indexer])] = new DisplayedPeak (mz[indexer], inten[indexer], x, y1, bottom, indexer);
                }
            }

            if (colourArray != null){
                col = colourArray[indexer];
            }
            // Optimisation to ensure only one line is drawn on each pixel column.
			if (lastPeakXCoordinate == -1){
			    lastPeakXCoordinate = x;
			    topPeakYCoordinate = bottom;
                if (colourArray != null){
                    lastPeakColour = col;
                }
            }
			else if (x != lastPeakXCoordinate){
				// OK, moved on from the last X coordinate, so draw the (previous) maximum line.
				// .. but only if there is actually something to draw...
				if (topPeakYCoordinate < bottom){
                    if (colourArray != null){
                        var switchBackColour = graphicsCanvas.color;
                        graphicsCanvas.setColor(lastPeakColour);
                        graphicsCanvas.drawLine (lastPeakXCoordinate, topPeakYCoordinate, lastPeakXCoordinate, bottom);
                        graphicsCanvas.setColor(switchBackColour);
                    }
                    else {
                        graphicsCanvas.drawLine (lastPeakXCoordinate, topPeakYCoordinate, lastPeakXCoordinate, bottom);
                    }
                }
			    lastPeakXCoordinate = x;
			    topPeakYCoordinate = y1;
                if (colourArray != null){
                    lastPeakColour = col;
                }
            }
            else {
            	if (y1 < topPeakYCoordinate){
            	    topPeakYCoordinate = y1;
				}
			}

			if (completeDraw){

				// Store details of peak in Object hash.
				var peakKey = "" + x;
				existingEntry = mzToPeakHash[peakKey];
				currentPeak = new DisplayedPeak (mz[indexer], inten[indexer], x, y1, bottom, indexer);

				// Only add a peak to the array if it is the largest so far at that point.
				if (existingEntry == null || existingEntry.y1 > y1){
					// Ok - not recorded this x coordinate yet, or have but for a lower intensity peak.
					mzToPeakHash[peakKey] = currentPeak;
				}
                for (seriesIndex = 0; seriesIndex <= 1; seriesIndex++){
                    // Update the selectedPeaksArray for both series with the latest x, y1, bottom values.)
                    updateSelectedPeak = selectedPeaksArray[seriesIndex]["" + (mz[indexer])];
                    if (updateSelectedPeak != null){
                        updateSelectedPeak.x = currentPeak.x;
                        updateSelectedPeak.y1 = currentPeak.y1;
                        updateSelectedPeak.bottom = currentPeak.bottom;
                    }

                    selectionLineTopY = top - SELECTED_PEAK_ARROW_ELEVATION[seriesIndex];
                    // Draw red line above selected peak;
                    peak = selectedPeaksArray[seriesIndex]["" + (mz[indexer])];
                    if (peak != null){
                        // This is a selected peak, so draw in the additional lines.
                        if (lastSelectedPeak[seriesIndex] != null && peak.mz == lastSelectedPeak[seriesIndex].mz){
                            graphicsCanvas.setColor(currentSelectedPeakColour[seriesIndex]);
                            graphicsCanvas.setStroke(2);
                        }
                        else {
                            graphicsCanvas.setColor(notCurrentSelectedPeakColour[seriesIndex]);
                            graphicsCanvas.setStroke(1);
                        }
                        graphicsCanvas.drawLine (x, y1, x, selectionLineTopY);

                        graphicsCanvas.setColor(notCurrentSelectedPeakColour[seriesIndex]);
                        graphicsCanvas.setStroke(1);
                        // If a previous peak was drawn, draw a connecting line and
                        // annotate it.
                        if (lastDrawnSelectedPeak[seriesIndex] != null){
                            graphicsCanvas.drawLine (lastDrawnSelectedPeak[seriesIndex].x, selectionLineTopY, x, selectionLineTopY);
                            graphicsCanvas.setStroke(1);
                            // Draw arrows, size dependent on distance between lines.
                            xDist = Math.abs (lastDrawnSelectedPeak[seriesIndex].x - x);

                            // Determine arrow head size.
                            var arrowSize = (xDist > 50) ?  5 :
                                            (xDist > 40) ?  4 :
                                            (xDist > 30) ?  3 :
                                            (xDist > 20) ?  2 :
                                                            0 ;
                            // Draw the arrow heads if the line is long enough.
                            if (arrowSize > 0){
                                var correction = 1;
                                if (lastDrawnSelectedPeak[seriesIndex].x - x < 0){
                                    correction = -1;
                                }

                                graphicsCanvas.drawLine (x, selectionLineTopY, x + (correction * arrowSize), selectionLineTopY + arrowSize);
                                graphicsCanvas.drawLine (x, selectionLineTopY, x + (correction * arrowSize), selectionLineTopY - arrowSize);
                                graphicsCanvas.drawLine (lastDrawnSelectedPeak[seriesIndex].x, selectionLineTopY, lastDrawnSelectedPeak[seriesIndex].x - (correction * arrowSize), selectionLineTopY + arrowSize);
                                graphicsCanvas.drawLine (lastDrawnSelectedPeak[seriesIndex].x, selectionLineTopY, lastDrawnSelectedPeak[seriesIndex].x - (correction * arrowSize), selectionLineTopY - arrowSize);
                            }

                            // Write on the details of the mass difference.
                            graphicsCanvas.setColor("#000000");
                            graphicsCanvas.setFont("verdana",NUMBER_FONT_SIZE,Font.BOLD);
                            // Calculate the mass difference
                            mzDelta = Math.abs (lastDrawnSelectedPeak[seriesIndex].mz - mz[indexer]);
                            var type = (seriesIndex == 0) ? "Y":"B";
                            graphicsCanvas.drawStringRect (createMatchHTML (mzDelta, type), (lastDrawnSelectedPeak[seriesIndex].x > x) ? x : lastDrawnSelectedPeak[seriesIndex].x , selectionLineTopY + 2, xDist, "left");

                        }
                        graphicsCanvas.setColor("#000000");
                        graphicsCanvas.setStroke(1);
                        lastDrawnSelectedPeak[seriesIndex] = peak;
                    }
				}
			}
			if (annotate && completeDraw){
				// Add the current intensity to the bottom of the topEleven array and sort (desc)
				topIntens[topIntens.length] = inten[indexer];
			}
		}
		}
	}
	// Draw the last peak.
	if (topPeakYCoordinate < bottom){
        if (colourArray != null){
            switchBackColour = graphicsCanvas.color;
            graphicsCanvas.setColor(lastPeakColour);
            graphicsCanvas.drawLine (lastPeakXCoordinate, topPeakYCoordinate, lastPeakXCoordinate, bottom);
            graphicsCanvas.setColor(switchBackColour);
        }
        else{
            graphicsCanvas.drawLine (lastPeakXCoordinate, topPeakYCoordinate, lastPeakXCoordinate, bottom);
        }
    }
	// Write heading
	var headingWidth = Math.round((Math.max (200, winW / 2)));
	graphicsCanvas.setFont("arial",HEADING_FONT_SIZE,Font.BOLD);
	graphicsCanvas.drawStringRect (graphTitle, 0, 105, winW, "center");

	if (completeDraw){
		// Write some labels
		graphicsCanvas.setFont("verdana",LABEL_FONT_SIZE,Font.BOLD);
		graphicsCanvas.drawStringRect ("Intensity", 2, top - 26, 100, "left");
		graphicsCanvas.drawStringRect ("m/z", Math.round((winW - headingWidth)/2), Math.round((winH-bottom)/2 + bottom), headingWidth,  "center");

		// Add on max and min for each axis.
		graphicsCanvas.setFont("verdana",NUMBER_FONT_SIZE,Font.PLAIN);

		// Work out a sensible frequency of tick values depending on height / width of the chart
		var horizTickFreq = (right - left > 600) ?  1 :
							(right - left > 400) ?  2 :
							(right - left > 200) ?  5 :
											10;

		var verticTickFreq = (bottom - top > 300) ? 1 :
							 (bottom - top > 150) ? 2 :
							 (bottom - top > 100) ? 5 :
											10;

		// Tick y axis value
		for (ytick=top; ytick <= bottom; ytick+=((bottom - top) / TICK_NUMBER) * verticTickFreq){
			// Calculate the yTickValue and round it appropriately.
			yTickValue = intensityFactor * (bottom - ytick) / (bottom - top);
			if (intensityFactor < 10){
			    yTickValue = "" + yTickValue.toFixed(3);
            }
            else {
                yTickValue = "" + Math.round(yTickValue);
            }
			graphicsCanvas.drawStringRect (yTickValue, 0, ytick - 7, left - (TICK_LENGTH + 2), "right");
		}
		// Tick x axis value
		for (xtick=left; xtick <=right; xtick+=(right-left) / TICK_NUMBER * horizTickFreq){
			xTickValue = "" + Math.round (minMzLimit + ((maxMzLimit - minMzLimit) * (xtick - left) / (right - left)));
			graphicsCanvas.drawStringRect (xTickValue, xtick - 40, bottom + TICK_LENGTH + 2, 80, "center");
		}

		if (annotate){
			var annotationCount=(right - left > 700) ?  15 :
								(right - left > 400) ?  10 :
								(right - left > 200) ?  5 :
												3;

			topIntens.sort(function(a,b){return b-a;});  //Sort descending
			minIntensityToLabel = topIntens[annotationCount - 1];
			graphicsCanvas.setColor("#999999");
			// Annotate the peaks that have intensities above the threshold.
			for (indexer = 0; indexer < lineCount; indexer++){
                // Add the annotation if the intensity is above the threshold, or the peak has external annotation (e.g. ion type)
                if (mz[indexer] >= minMzLimit && mz[indexer] <= maxMzLimit &&
					((minIntensityToLabel == null || inten[indexer] >= minIntensityToLabel) ||
                    (labelArray != null && labelArray[indexer] != "-"))){
					y = Math.round( Math.max( top + 35, (top + (((intensityFactor - inten[indexer]) / intensityFactor) * (bottom - top)))));
					x = Math.round( left + (((mz[indexer] - minMzLimit) / (maxMzLimit - minMzLimit)) * (right - left)) );
                    if (labelArray != null && labelArray[indexer] != "-"){
                        // Have an external annotation, so display the ion label
                        // Colour is dependent on ion type.
                        graphicsCanvas.setColor(colourArray[indexer]);
                        graphicsCanvas.drawStringRect (labelArray[indexer], x + 2 , y-24, 50, "left");
                        graphicsCanvas.setColor("#999999");
                    }
                    graphicsCanvas.setFont("verdana",NUMBER_FONT_SIZE,Font.BOLD);
					graphicsCanvas.drawStringRect (Math.round(mz[indexer]), x + 2, y-12, 50, "left");
					graphicsCanvas.setFont("verdana",NUMBER_FONT_SIZE,Font.ITALIC);
					graphicsCanvas.drawStringRect (Math.round(inten[indexer]), x + 2, y, 50, "left");
                }
			}
		}
	}
	graphicsCanvas.paint();
}

// Place the three arrows in the correct starting (unzoomed) positions relative to the track gif.
function resetArrowPositions(){
	dd.elements.leftBound.moveTo(dd.elements.track.x + POINTER_CORRECTION, dd.elements.track.y + TRACK_HEIGHT - 1);
	dd.elements.rightBound.moveTo(dd.elements.track.x + TRACK_WIDTH + POINTER_CORRECTION, dd.elements.track.y + TRACK_HEIGHT - 1);
	dd.elements.intensityBound.moveTo(dd.elements.track.x - 20, dd.elements.track.y + POINTER_CORRECTION);
	// Position the pan tool.
	dd.elements.panZoomWindow.moveTo(dd.elements.track.x + ARROW_BASE_WIDTH_CORRECTION, dd.elements.track.y + TRACK_HEIGHT + 20);
	// Change the width of the pan tool
	dd.elements.panZoomWindow.resizeTo(dd.elements.rightBound.x - dd.elements.leftBound.x + 5, 4);
	dd.elements.panZoomWindow.hide();
}

/*
  Moves all of the clickable widgets to their starting positions, sets up their
  moveable status.
*/
function initialiseWidgets(){
	FIXED_CLICKABLE = CURSOR_HAND + HORIZONTAL + MAXOFFLEFT + 0 + MAXOFFRIGHT + 0;

    if (deNovoAllowed){
        SET_DHTML(LEFT_MZ_BOUND + CURSOR_HAND + HORIZONTAL + MAXOFFLEFT + 0 + MAXOFFRIGHT + TRACK_WIDTH,
              MASS_ERROR_POINTER + CURSOR_HAND + HORIZONTAL + MAXOFFLEFT + 0 + MAXOFFRIGHT + TRACK_WIDTH,
              RIGHT_MZ_BOUND + CURSOR_HAND + HORIZONTAL + MAXOFFLEFT + TRACK_WIDTH + MAXOFFRIGHT + 0,
              PAN_ZOOM_WINDOW + CURSOR_HAND + HORIZONTAL + MAXOFFLEFT + 0 + MAXOFFRIGHT + 0,
              INTENSITY_BOUND + CURSOR_HAND + VERTICAL + MAXOFFBOTTOM + TRACK_HEIGHT + MAXOFFTOP + 0,
              ZOOM_OUT + FIXED_CLICKABLE,
              PRINTER + FIXED_CLICKABLE,
              GRID + FIXED_CLICKABLE,
              ANNOTATION + FIXED_CLICKABLE,
              VALUES_TEXT + FIXED_CLICKABLE,
              VALUES_HTML + FIXED_CLICKABLE,
              TRACK + FIXED_CLICKABLE,
              PEAK_INFO_DIV,
              MASS_ERROR_TRACK + FIXED_CLICKABLE,
              RESET_SELECTED_PEAKS + FIXED_CLICKABLE,
              HELP + FIXED_CLICKABLE,
              DENOVO_TEXT + FIXED_CLICKABLE,
              DENOVO_HTML + FIXED_CLICKABLE,
              DENOVO_HEADING + FIXED_CLICKABLE,
              PEAKLIST_HEADING + FIXED_CLICKABLE,
              MASS_ERROR_DISPLAY_DIV_ID + FIXED_CLICKABLE);
    }
    else {
        SET_DHTML(LEFT_MZ_BOUND + CURSOR_HAND + HORIZONTAL + MAXOFFLEFT + 0 + MAXOFFRIGHT + TRACK_WIDTH,
			  MASS_ERROR_POINTER + CURSOR_HAND + HORIZONTAL + MAXOFFLEFT + 0 + MAXOFFRIGHT + TRACK_WIDTH,
			  RIGHT_MZ_BOUND + CURSOR_HAND + HORIZONTAL + MAXOFFLEFT + TRACK_WIDTH + MAXOFFRIGHT + 0,
			  PAN_ZOOM_WINDOW + CURSOR_HAND + HORIZONTAL + MAXOFFLEFT + 0 + MAXOFFRIGHT + 0,
			  INTENSITY_BOUND + CURSOR_HAND + VERTICAL + MAXOFFBOTTOM + TRACK_HEIGHT + MAXOFFTOP + 0,
			  ZOOM_OUT + FIXED_CLICKABLE,
			  PRINTER + FIXED_CLICKABLE,
			  GRID + FIXED_CLICKABLE,
			  ANNOTATION + FIXED_CLICKABLE,
			  VALUES_TEXT + FIXED_CLICKABLE,
			  VALUES_HTML + FIXED_CLICKABLE,
			  TRACK + FIXED_CLICKABLE,
			  PEAK_INFO_DIV,
			  MASS_ERROR_TRACK + FIXED_CLICKABLE,
			  HELP + FIXED_CLICKABLE,
              DENOVO_TEXT + FIXED_CLICKABLE,
              DENOVO_HTML + FIXED_CLICKABLE,
			  PEAKLIST_HEADING + FIXED_CLICKABLE,
			  MASS_ERROR_DISPLAY_DIV_ID + FIXED_CLICKABLE);
    }

    resetArrowPositions();

	// Make arrows children of the tracks so that they are correctly positioned
	// and do not disappear when the background is clicked.
	dd.elements.track.addChild(LEFT_MZ_BOUND);
	dd.elements.track.addChild(RIGHT_MZ_BOUND);
	dd.elements.track.addChild(PAN_ZOOM_WINDOW);
	dd.elements.track.addChild(INTENSITY_BOUND);

	dd.elements.massErrorTrack.addChild(MASS_ERROR_POINTER);

	// Set the bound starting points relative to the track.
	dd.elements.leftBound.defx = dd.elements.track.x + POINTER_CORRECTION;
	dd.elements.panZoomWindow.defx = dd.elements.leftBound.x - POINTER_CORRECTION + ARROW_BASE_WIDTH_CORRECTION;
	dd.elements.rightBound.defx = dd.elements.track.x + TRACK_WIDTH + POINTER_CORRECTION;
	dd.elements.intensityBound.defy = dd.elements.track.y + POINTER_CORRECTION;
	dd.elements.massPointer.defx = dd.elements.massErrorTrack.x + MASS_ERROR_TRACK_CORRECTION + POINTER_CORRECTION;

	// Move the massPointer to the correct start position.
	dd.elements.massPointer.moveTo(dd.elements.massErrorTrack.x + MASS_ERROR_TRACK_CORRECTION + POINTER_CORRECTION + massErrorXPos, dd.elements.massErrorTrack.y + 30);
}

/*
  This function is called while a widget is being dragged.  Only respondes to zooming widgets
  to allow the current level of zoom to be displayed.
*/
function my_DragFunc(){
	if (dd.obj.name == LEFT_MZ_BOUND){
		moveLeft(false);
	}
	else if (dd.obj.name == RIGHT_MZ_BOUND){
		moveRight(false);
	}
	else if (dd.obj.name == INTENSITY_BOUND){
		moveIntensityZoom(false);
	}
	else if (dd.obj.name == MASS_ERROR_POINTER){
		massErrorPointerMoved(false);
	}
	else if (dd.obj.name == PAN_ZOOM_WINDOW){
	    panZoomMoved(false);
    }
}

/*
  This function is called when a widget is depressed (mouse down event).  If the widget is a button,
  make it look depressed...
*/
function my_PickFunc(){
	if (dd.obj.name == ZOOM_OUT ||
		dd.obj.name == PRINTER ||
		dd.obj.name == GRID ||
		dd.obj.name == ANNOTATION ||
		dd.obj.name == VALUES_TEXT ||
		dd.obj.name == VALUES_HTML ||
		dd.obj.name == HELP ||
		dd.obj.name == DENOVO_TEXT ||
		dd.obj.name == DENOVO_HTML ||
		dd.obj.name == RESET_SELECTED_PEAKS){

		buttonDown(dd.obj);
	}
}

// Detect the event of a widget being dropped, determine which widget it is and
// respond accordingly...(Also make buttons look released).
function my_DropFunc(){
	if (dd.obj.name == LEFT_MZ_BOUND){
		moveLeft(true);
	}
	else if (dd.obj.name == RIGHT_MZ_BOUND){
		moveRight(true);
	}
	else if (dd.obj.name == INTENSITY_BOUND){
		moveIntensityZoom(true);
	}
	else if (dd.obj.name == PAN_ZOOM_WINDOW){
	    panZoomMoved(true);
    }
	else if (dd.obj.name == ZOOM_OUT){
		buttonUp(dd.obj);
		zoomReset();
	}
	else if (dd.obj.name == PRINTER){
		buttonUp(dd.obj);
		printSpec();
	}
	else if (dd.obj.name == GRID){
		buttonUp(dd.obj);
		toggleGrid()
	}
	else if (dd.obj.name == ANNOTATION){
		buttonUp(dd.obj);
		toggleAnnotation();
	}
	else if (dd.obj.name == VALUES_TEXT){
		buttonUp(dd.obj);
		viewValues(true);
	}
	else if (dd.obj.name == VALUES_HTML){
		buttonUp(dd.obj);
		viewValues(false);
	}
	else if (dd.obj.name == MASS_ERROR_POINTER){
		massErrorPointerMoved(true);
	}
	else if (dd.obj.name == RESET_SELECTED_PEAKS){
		buttonUp(dd.obj)
		resetSelectedPeaks();
	}
	else if (dd.obj.name == HELP){
		buttonUp(dd.obj)
		displayHelp();
	}
	else if (dd.obj.name == DENOVO_TEXT){
	    buttonUp(dd.obj)
		outputDeNovoSequence(DISPLAY_PLAIN_TEXT);
    }
    else if (dd.obj.name == DENOVO_HTML){
	    buttonUp(dd.obj)
		outputDeNovoSequence(DISPLAY_HTML);
    }
}

/*
* Changes the appearance of a button on the mouse down event to provide user feedback -
* moves it down, right and changes the background color to make the button look depressed.
*/
function buttonDown(button){
	button.moveBy(2, 2);
}

/*
* Changes the appearance of a button on the mouse up event to provide user feedback -
* moves it up, left and changes the background color to make the button look released.
*/
function buttonUp(button){
	button.moveBy(-2,-2);
}

/*
* Changes the zoom on the intensity axis when the left mz zoom widget is moved.
*/
function moveLeft(completeDraw){
	if (dd.elements.leftBound.x >= dd.elements.rightBound.x){
		dd.elements.leftBound.moveTo(dd.elements.rightBound.x -1, dd.elements.leftBound.y);
	}
	mzZoom(completeDraw);
}

/*
* Changes the zoom on the intensity axis when the right mz zoom widget is moved.
*/
function moveRight(completeDraw){
	if (dd.elements.leftBound.x >= dd.elements.rightBound.x){
		dd.elements.rightBound.moveTo(dd.elements.leftBound.x + 1, dd.elements.rightBound.y);
	}
	mzZoom(completeDraw);
}

function panZoomMoved(completeDraw){
    dd.elements.leftBound.moveTo (dd.elements.panZoomWindow.x + POINTER_CORRECTION - ARROW_BASE_WIDTH_CORRECTION, dd.elements.leftBound.y);
    dd.elements.rightBound.moveTo (dd.elements.panZoomWindow.x + dd.elements.panZoomWindow.w - 13, dd.elements.rightBound.y);
    calculateMzZoomLimits();
	drawSpectrogram(false, completeDraw);
}

// Re-calculates left and right zoom values and re-draws the spectra.
function mzZoom(completeDraw){
	calculateMzZoomLimits();

    // Re-position the pan tool
    dd.elements.panZoomWindow.moveTo(dd.elements.leftBound.x + 8, dd.elements.panZoomWindow.y);
	// Change the width of the pan tool
	dd.elements.panZoomWindow.resizeTo(dd.elements.rightBound.x - dd.elements.leftBound.x + 5, 4);
	// Change the pan range of the pan tool.
	//dd.elements.panZoomWindow.maxoffl = leftMzZoom;
	dd.elements.panZoomWindow.maxoffr = TRACK_WIDTH - rightMzZoom + leftMzZoom;

	if (dd.elements.panZoomWindow.w == 105){
	    dd.elements.panZoomWindow.hide();
    }
    else {
	    dd.elements.panZoomWindow.show();
	}
	drawSpectrogram(false, completeDraw);
}

function calculateMzZoomLimits(){
    leftMzZoom = dd.elements.leftBound.x - dd.elements.leftBound.defx;
	rightMzZoom = TRACK_WIDTH + (dd.elements.rightBound.x - dd.elements.rightBound.defx);
}

/*
  Changes the zoom on the intensity axis according to the position of the intensity zoom widget.
  Note that this is now logarithmic to allow the user to examine the noise peaks more easily.
*/
function moveIntensityZoom(completeDraw){
	intensityZoom = Math.pow (1.0009214583193, 200 * (TRACK_HEIGHT - (dd.elements.intensityBound.y - dd.elements.intensityBound.defy)));
	drawSpectrogram(false, completeDraw);
}

// Reset the zoom to completely zoomed out.
function zoomReset(){
	leftMzZoom = 0;
	rightMzZoom = TRACK_WIDTH;
	intensityZoom = MAX_INTENSITY_FACTOR;
	resetArrowPositions();
	drawSpectrogram(false, true);
}

// Redraw the spectrum at current zoom for printing,
// open print dialogue.
function printSpec(){
	drawSpectrogram(true, true);
	window.print();
}

// Turns the grid on / off and changes the icon accordingly.
function toggleGrid(){
	if (grid){
		grid = false;
		dd.elements.grid.swapImage("/toppr/images/spectrumViewer/gridOff.gif");
	}
	else {
		grid = true;
		dd.elements.grid.swapImage("/toppr/images/spectrumViewer/gridOn.gif");
	}
	drawSpectrogram(false, true);
}

/*
* Turns on / off the annotation of the top n peaks with mz / intensity values.
*/
function toggleAnnotation(){
	if (annotate){
		annotate = false;
		document.getElementById("mzKey").style.display="none";
		document.getElementById("intenKey").style.display="none";
		dd.elements.annotation.swapImage("/toppr/images/spectrumViewer/annotationOff.gif");
	}
	else {
		annotate = true;
		document.getElementById("mzKey").style.display="block";
		document.getElementById("intenKey").style.display="block";
		dd.elements.annotation.swapImage("/toppr/images/spectrumViewer/annotationOn.gif");
	}
	drawSpectrogram(false, true);
}

// Called when window resized.
function drawSpectrogramUnprintable(){
	drawSpectrogram (false, true);
}

/*
  Displays m/z and intensity values in tab-delim text file (new window).
*/
function viewValues(asText){
	valuesWindow = window.open("", "mzIntenValues","height=600,width=600,left=100,top=100,resizable=yes,scrollbars=yes,menubar=yes,status=yes");
	if (valuesWindow.focus){
		valuesWindow.focus();
	}

	if (asText){
		valuesWindow.document.open("text/plain");
		valuesWindow.document.write(graphTitle);
		valuesWindow.document.writeln(" - tab separated values file.");
		valuesWindow.document.writeln();
		valuesWindow.document.writeln("m/z\tIntensity");
		for (indexer = 0; indexer < lineCount; indexer++){
			valuesWindow.document.write (mz[indexer]);
			valuesWindow.document.write ("\t");
			valuesWindow.document.writeln (inten[indexer]);
		}
		valuesWindow.document.close();
		if (dd.ie){
			valuesWindow.alert ("Use the menu item 'File'..'Save Target As' to save this as a text file.");
		}
		else if (dd.op){
			valuesWindow.alert ("Use the menu item 'File'..'Save As' to save this as a text file.");
		}
		else{
			valuesWindow.alert ("Use the menu item 'File'..'Save Page As' to save this as a text file.");
		}
	}
	else {
		valuesWindow.document.open("text/html");
		valuesWindow.document.writeln("<html><head><style>th,td{font-family:ariel,sans-serif;font-size:11;}</style><title>");
		valuesWindow.document.writeln(graphTitle);
		valuesWindow.document.writeln("</title></head><body><h3>");
		valuesWindow.document.writeln(graphTitle);
		valuesWindow.document.writeln("</h3><br/><table border='1'>");
		for (indexer = 0; indexer < lineCount; indexer++){
			if (indexer % 20 == 0){
				valuesWindow.document.writeln("<tr><th align = 'left' style='padding-right:50;'>m/z</th><th align = 'left'>Intensity</th></tr>");
			}
			valuesWindow.document.write ("<tr><td align = 'left' style='padding-right:50;'>");
			// Adjust colour to indicate intensity.
			var relativeIntensity = inten[indexer] / maxInten;
			var intensityStyle = (relativeIntensity > 0.9) ? "color:#000000;font-size:12;" :
								  (relativeIntensity > 0.8) ? "color:#111111;font-size:12;" :
								  (relativeIntensity > 0.7) ? "color:#222222;font-size:11;" :
								  (relativeIntensity > 0.6) ? "color:#333333;font-size:11;" :
								  (relativeIntensity > 0.5) ? "color:#444444;font-size:11;" :
								  (relativeIntensity > 0.4) ? "color:#555555;font-size:11;" :
								  (relativeIntensity > 0.3) ? "color:#666666;font-size:10;" :
								  (relativeIntensity > 0.2) ? "color:#777777;font-size:10;" :
								  (relativeIntensity > 0.1) ? "color:#888888;font-size:10;" :
															  "color:#999999;font-size:10;";
			valuesWindow.document.write ("<span style=\"" + intensityStyle + "\">");

			valuesWindow.document.writeln (mz[indexer]);
			valuesWindow.document.write ("</span></td><td align = 'left'>");
			valuesWindow.document.write ("<span style='" + intensityStyle + "'>");
			valuesWindow.document.writeln (inten[indexer]);
			valuesWindow.document.writeln ("</span></td></tr>");
		}
		valuesWindow.document.writeln("</table></html>");
		valuesWindow.document.close();
	}
}


/*
  Opens new window / tab containing help for the spectrum viewer.
*/
function displayHelp(){
	helpWindow = window.open("http://www.ebi.ac.uk/pride/viewSpectrumHelp.do", "helpPage","height=600,width=800,resizable=yes,scrollbars=yes,menubar=no,status=yes");
	if (helpWindow.focus){
		helpWindow.focus();
	}
}

/*
  Sets up the mouse listener to listen for mouse move events (to get the
  X / Y coordinates of the mouse) and to listen for mouse click events.
*/
function captureMouseMove (isOn){
	if (isOn){
		if (dd.ie){
            document.attachEvent ("onmousemove", getMouseXY);
            document.attachEvent ("onmousedown", peakSelect);
        }
        else {
			document.captureEvents(Event.MOUSEMOVE)
            document.addEventListener ("mousemove", getMouseXY, true);
            document.addEventListener ("mousedown", peakSelect, true);
		}
	}
	else {
	    if (dd.ie){
            document.detachEvent ("onmousemove", getMouseXY);
            document.detachEvent ("onmousedown", peakSelect);
        }
        else {
			document.captureEvents(Event.MOUSEMOVE)
            document.removeEventListener ("mousemove", getMouseXY, true);
            document.removeEventListener ("mousedown", peakSelect, true);
		}
		document.getElementById ("spectrogramCanvas").removeEventListener ("mousemove", getMouseXY, true);
	}
}

/*
  Listener for mouse movement on the spectrum div.  stores the mouse
  x / y coordinates and displays a pop-up window with details of the
  peak if one is being hovered over.
*/
function getMouseXY(mouseEvent) {
	if (dd.ie) {
	    // Note - +2 correction below to allow correct peak selection in IE.
		mouseX = event.clientX + document.body.scrollLeft;
		mouseY = event.clientY + document.body.scrollTop;
	}
	else {
		mouseX = mouseEvent.pageX;
		mouseY = mouseEvent.pageY;
	}
	if (mouseX < 0){mouseX = 0;}
	if (mouseY < 0){mouseY = 0;}
	displayedPeak = mzToPeakHash["" + mouseX];

    tooltipX = mouseX + 5;
    tooltipY = mouseY - 5;
	if (tooltipX + 145 > winW){
		tooltipX = mouseX - 145;
	}
	if (tooltipY + dd.elements.peakInfoDiv.h + 20 > winH){
		tooltipY = mouseY - (dd.elements.peakInfoDiv.h + 20);
	}
	dd.elements.peakInfoDiv.moveTo (tooltipX, tooltipY);
	if (mouseY < MINIMUM_HEADING_WIDTH || mouseY > winH * 0.9 || mouseX > winW - MINIMUM_RIGHT_SIDE_WIDTH) {
	    dd.elements.peakInfoDiv.hide();
	}

	else if (displayedPeak != null && displayedPeak.y1 - (displayedPeak.y2 - displayedPeak.y1) < mouseY){
		displayPeakDetails (displayedPeak);
		if (dd.elements.peakInfoDiv.y + dd.elements.peakInfoDiv.h + 20 > winH){
			tooltipY = mouseY - (dd.elements.peakInfoDiv.h + 20);
			dd.elements.peakInfoDiv.moveTo (dd.elements.peakInfoDiv.x, dd.elements.peakInfoDiv.y - (dd.elements.peakInfoDiv.h + 30))
		}
	}
}

/*
  If an attempt is made to click a peak, this method detects if a peak has
  been clicked (with an error of one pixel either side of the peak.)

  The status of the peak is then changed.  The transition depends on the
  current status of the peak:
  1. Start state NOT Selected and NOT in selectedPeaksArray -> Selected and placed into selectedPeaksArray
  2. In selectedPeaksArray but NOT selected -> Still in selectedPeaksArray AND Selected.
  3. Selected and in selectedPeaksArray -> NOT selected and REMOVED from selectedPeaksArray.

*/
function peakSelect(mouseEvent){

    // Do not include any clicks above the spectrum area (for the current series).
    if ( ! deNovoAllowed || mouseY < MINIMUM_HEADING_WIDTH - SELECTED_PEAK_ARROW_ELEVATION[currentSeries] ) return;

	clickedPeak = mzToPeakHash["" + mouseX];
    xError = 0;
	// If not clicked on an actual peak, check if one to the left or right provides a valid peak.
	if (clickedPeak == null || (clickedPeak.y1 > mouseY && selectedPeaksArray[currentSeries]["" + clickedPeak.mz] == null)){
	    leftPeak = mzToPeakHash["" + (mouseX - 1)];
	    replaced = false;
        if (leftPeak != null && (leftPeak.y1 < mouseY || selectedPeaksArray[currentSeries]["" + leftPeak.mz] != null)){
        	clickedPeak = leftPeak;
        	replaced = true;
		}
	    rightPeak = mzToPeakHash["" + (mouseX + 1)];
		if (rightPeak != null && (rightPeak.y1 < mouseY || selectedPeaksArray[currentSeries]["" + rightPeak.mz] != null)){
			// If there is a valid peak on both sides, do not select either as ambiguous.
        	if (replaced) return;
        	replaced = true;
        	clickedPeak = rightPeak;
		}
		if (!replaced) return;
	}
	hasSelectedPeak = true;
	if (clickedPeak == null) return;
	if (selectedPeaksArray[currentSeries]["" + clickedPeak.mz] != null){
		// Already selected - if it is the 'currently selected'
		// then de-select, othewise set to currently selected.
		if (lastSelectedPeak[currentSeries] != null && clickedPeak.mz == lastSelectedPeak[currentSeries].mz){
			selectedPeaksArray[currentSeries]["" + clickedPeak.mz]=null;
			lastSelectedPeak[currentSeries] = null;
		}
		else {
			lastSelectedPeak[currentSeries] = clickedPeak;
		}
		drawSpectrogram(false, true);  // Not printable, complete.
	}
	else{
		// Not selected, and peak has been clicked, so add to selectedPeaksArray
		displayPeakDetails (clickedPeak);
		selectedPeaksArray[currentSeries]["" + clickedPeak.mz] = clickedPeak;
		lastSelectedPeak[currentSeries] = clickedPeak;
		drawSpectrogram(false, true);  // Not printable, complete.
	}
}

var DISPLAY_PLAIN_TEXT = 0;
var DISPLAY_HTML = 1;
var DISPLAY_EXCEL = 2;

/*
  Outputs the results of a de novo sequence as either plain text or HTML.
*/
function outputDeNovoSequence(type){
	valuesWindow = window.open("", "deNovoSequence","height=600,width=600,left=100,top=100,resizable=yes,scrollbars=yes,menubar=yes,status=yes");
	if (valuesWindow.focus){
		valuesWindow.focus();
	}
	var out;
	var header = null;
	var sequenceHeading = null;
	var sequenceSeparator = null;
	var sequenceFooter =  null;
	var detailHeading = new Array(2);
	detailHeading[0] = null;
	detailHeading[1] = null;
	var tableFooter = null;
	var footer = null;
	if (type==DISPLAY_PLAIN_TEXT){
	    out = valuesWindow.document.open("text/plain");
	    header = "De novo sequence prediction based upon '" + graphTitle + "'\n"
	    sequenceHeading = "Predicted Sequence (Y ions)\tPredicted Sequence (B ions)\n";
	    sequenceSeparator = "\t";
	    sequenceFooter = "\nMass Error: " + Molecule.MASS_ERROR_DALTONS + " Daltons.\n";
	    detailHeading[0] = "\nY Series Ions\nStart m/z\tEnd m/z\tm/z Delta\tPrediction(s)\n";
	    detailHeading[1] = "\nB Series Ions\nStart m/z\tEnd m/z\tm/z Delta\tPrediction(s)\n";
	    tableFooter = "\n";
	    footer = "";
    }
    else if (type != DISPLAY_PLAIN_TEXT){

        mimeType = (type==DISPLAY_HTML) ? "text/html" : "application/vnd.ms-excel";
        out = valuesWindow.document.open(mimeType);
        header = "<html><head><style>th,td{font-family:ariel,sans-serif;font-size:11;}</style><title>" +
	             "De novo sequence prediction</title></head><body><h3>" +
	             "<i>De novo</i> sequence prediction based upon '" + graphTitle + "'" +
	             "</h3><br/><table border='1'>";
	    sequenceHeading = "<tr><th>Predicted Sequence: Y ions</th><th>Predicted Sequence: B ions</th><th>Mass Error (Daltons)</th></tr><tr><td>";
	    sequenceSeparator = "</td><td>";
	    sequenceFooter = "</td><td>" + Molecule.MASS_ERROR_DALTONS + "</td></tr></table><br/>";
	    detailHeading[0] = "<table border = '1'><tr><th colspan ='4'>Y Series Ions</th></tr><tr><th>Start m/z</th><th>End m/z</th><th>m/z Delta</th><th>Prediction(s)</th></tr>";
	    detailHeading[1] = "<table border = '1'><tr><th colspan ='4'>B Series Ions</th></tr><tr><th>Start m/z</th><th>End m/z</th><th>m/z Delta</th><th>Prediction(s)</th></tr>";
        tableFooter = "</table><br/>";
        footer = "</body></html>";
    }
    // Add on other types...

	lineCount = Math.min (mz.length, inten.length);
	var sequenceString = new Array(2);
	var detailString = new Array(2);
	for (seriesIndex = 0; seriesIndex <= 1; seriesIndex++){
	var lastPeak = null;
	    sequenceString[seriesIndex] = "";
	    detailString[seriesIndex] = "";
	for (indexer = 0; indexer < lineCount; indexer++){
            selectedPeak = selectedPeaksArray[seriesIndex]["" + (mz[indexer])];
		if (selectedPeak != null){
		    // Ok - found a selected peak.
		    if (lastPeak != null){
		        if (type!=DISPLAY_PLAIN_TEXT){
                        detailString[seriesIndex] += "<tr><td>";
                }
		        // Got two peaks, so write out a line.
                    detailString[seriesIndex] += lastPeak.mz;
                    detailString[seriesIndex] += (type==DISPLAY_PLAIN_TEXT) ? "\t" : "</td><td>";
                    detailString[seriesIndex] += selectedPeak.mz;
                    detailString[seriesIndex] += (type==DISPLAY_PLAIN_TEXT) ? "\t" : "</td><td>";
                    detailString[seriesIndex] += (selectedPeak.mz - lastPeak.mz);
                // determine if the mass difference signifies anything...
                matches = returnMatchArray (selectedPeak.mz - lastPeak.mz);
                // for each match, write out details on the same line.
                if (type!=DISPLAY_PLAIN_TEXT){
                        detailString[seriesIndex] += "</td><td>";
                }
                multipleMatches = matches.length > 1;
                    var nextSequenceElement = "";
                if (matches.length > 0){
                    if (multipleMatches){
                            nextSequenceElement += "[";
                    }
                    for (var matchIndex = 0; matchIndex < matches.length; matchIndex++){
                        molecule = matches [matchIndex];
                        if (matchIndex > 0){
                                nextSequenceElement += ",";
                            if (type != DISPLAY_PLAIN_TEXT){
                                    detailString[seriesIndex] +="; ";
                            }
                        }
                            nextSequenceElement += molecule.oneLetterCode;
                        molecule = matches[matchIndex];
                            detailString[seriesIndex] += (type==DISPLAY_PLAIN_TEXT) ? "\t" : "";
                            detailString[seriesIndex] += molecule.oneLetterCode;
                            detailString[seriesIndex] += " (";
                            detailString[seriesIndex] += molecule.name;
                            detailString[seriesIndex] += (")");
                        if (molecule.z != null && molecule.z > 1){
                                detailString[seriesIndex] += " z=+" + molecule.z;
                        }
                    }
                    if (multipleMatches){
                            nextSequenceElement += "]";
                    }
                        // Reverse the order of Y ions.
                        if (seriesIndex == 0){
                            sequenceString[0] = nextSequenceElement + sequenceString[0];
                }
                        else {
                            sequenceString[1] = sequenceString[1] + nextSequenceElement;
            }
                    }
                    detailString[seriesIndex] += (type==DISPLAY_PLAIN_TEXT) ? "\n" : "</td></tr>";
                }
            lastPeak = selectedPeak;
		}
	}
	}
	valuesWindow.document.write (header);
	valuesWindow.document.write (sequenceHeading);
	valuesWindow.document.write (sequenceString[0]);
	valuesWindow.document.write (sequenceSeparator);
	valuesWindow.document.write (sequenceString[1]);
	valuesWindow.document.write (sequenceFooter);
	valuesWindow.document.write (detailHeading[0]);
	valuesWindow.document.write (detailString[0]);
	valuesWindow.document.write (tableFooter);
	valuesWindow.document.write (detailHeading[1]);
	valuesWindow.document.write (detailString[1]);
	valuesWindow.document.write (tableFooter);
	valuesWindow.document.write (footer);
	valuesWindow.document.close();
}

/*
  Updates the floating div (that follows the cursor around the spectrum)
  with the mz / intensity values of the current spectrum, plus
  details of the mass delta / amino acid matches if appropriate.
*/
function displayPeakDetails (displayedPeak){
    var outputString = "";

    // If there is any external annotation (label) add it here.
    if (labelArray != null && (label = labelArray[displayedPeak.arrayIndex]) != "-"){
        if (colourArray != null && colourArray[displayedPeak.arrayIndex] != null && colourArray[displayedPeak.arrayIndex] != ""){
            outputString += "<span style='color:" + colourArray[displayedPeak.arrayIndex] + ";'><b>" + label + "</b></span><br/>";
        }
        else {
            outputString += label + "<br/>";
        }

    }

    outputString += "<b>m/z: " + displayedPeak.mz.toFixed(3) + "<br/>Intensity: " + displayedPeak.intensity + "</b><br/>";
	document.getElementById("mzDisplay").value=displayedPeak.mz;
	document.getElementById("intenDisplay").value=displayedPeak.intensity;

    if (additionalArray != null && (additionalArray[displayedPeak.arrayIndex]) != ""){
        outputString += additionalArray[displayedPeak.arrayIndex] + "<br/>";
    }


    // Look for identifiable mass differences.
	if (hasSelectedPeak){
		if (lastSelectedPeak[currentSeries] != null){
			mzDelta = Math.abs(lastSelectedPeak[currentSeries].mz - displayedPeak.mz);
            diffString = createMatchHTML (mzDelta);
            outputString += "Delta: " + diffString;
        }
		else {
			outputString += "<b><i>No Current Peak<br/>to measure delta from.</i></b>";
		}
		outputString += currentSeriesMessageHtml[currentSeries];
	}
	else if (deNovoAllowed) {
		outputString += "<br/><b>Click on a peak<br/>to begin <i>de novo</i><br/>sequencing</b>";
	}
	dd.elements.peakInfoDiv.write (outputString);
	if (dd.elements.peakInfoDiv.text == null || dd.elements.peakInfoDiv.text == "" || mouseY < MINIMUM_HEADING_WIDTH || mouseY > winH * 0.9 || mouseX > winW - MINIMUM_RIGHT_SIDE_WIDTH) {
	    dd.elements.peakInfoDiv.hide();
	}
	else {
	    dd.elements.peakInfoDiv.show();
	}
}

/*
  Allows the user to view and modify the mass error.  Captures spurious values.
*/
function updateError (inputBox){
	if (inputBox.value == null || inputBox.value == "" ||  isNaN(inputBox.value) || inputBox.value < 0 || inputBox.value >= 10){
	    window.alert ("Sorry - that is an invalid value. (Must be a number >= 0 and < 10)");
		inputBox.value = Molecule.MASS_ERROR_DALTONS;
	}
	else {
		Molecule.MASS_ERROR_DALTONS = inputBox.value;
		newPointerPosition = massErrorToScale (Molecule.MASS_ERROR_DALTONS);
		dd.elements.massPointer.moveTo  (Math.max(dd.elements.massPointer.defx,newPointerPosition + dd.elements.massPointer.defx), dd.elements.massPointer.y);
		drawSpectrogram(false, true);  // Not printable, complete.
	}
}

/*
   Handles movement of the widget controlling the mass error.
   Modifies and displays the MASS_ERROR_DALTONS class variable
*/
function massErrorPointerMoved(reDraw){
    pointerPosition = dd.elements.massPointer.x - dd.elements.massPointer.defx;
    newErrorValue = scaleToMassError (pointerPosition);
    Molecule.MASS_ERROR_DALTONS = newErrorValue;
    document.getElementById (MASS_ERROR_DISPLAY_ID).value = newErrorValue;
    if (reDraw){
        drawSpectrogram(false, true);  // Not printable, complete.
	}
}

/*
  Removes all reference to selected peaks and re-draws the spectrum.
*/
function resetSelectedPeaks(){
    selectedPeaksArray[0] = new Array();
    selectedPeaksArray[1] = new Array();
    lastSelectedPeak[0] = null;
    lastSelectedPeak[1] = null;
    drawSpectrogram(false, true);  // Not printable, complete.
}

/*
  If the user changes the de novo sequencing parameter 'maximum charge state', update
  the value and redraw the spectrum accordingly.
*/
function setCharge(checkBox){
    oldMax = Molecule.MAX_CHARGE_INCLUDED;
	Molecule.MAX_CHARGE_INCLUDED = (checkBox.checked) ? 3 : 1;
	if (oldMax != Molecule.MAX_CHARGE_INCLUDED){
	drawSpectrogram(false, true);  // Not printable, complete.
}
}

/*
  Currently selected ion series (that the user is working on) changed.
  Update.  (No need to re-draw the spectrum - it will look no different.)
*/
function seriesChanged(){
    currentSeries = (document.ionSeriesForm.ionSeries[0].checked) ? 0 : 1;  // 0 = y series, 1 = b series
}

/*
  Function to initialize the spectrum viewer.
*/
function init(){
    // Set the value of the mass error field
    document.getElementById(MASS_ERROR_DISPLAY_ID).value=Molecule.MASS_ERROR_DALTONS;

    // Set the current series as selected.
    seriesChanged()

	// Draw the spectrum for the first time...
	drawSpectrogram(false, true);

	// Create the widgets for zooming.
	initialiseWidgets();

	// Set the call to re-draw the spectrum if the window is resized.
	window.onresize=drawSpectrogramUnprintable;

	// Capture mouse events to allow mouse over and peak clicking to be captured.
	captureMouseMove (true);
}

/****************************************************\
		And finally get drawing
\****************************************************/

init();

//-->
</script>
</body>
</html>