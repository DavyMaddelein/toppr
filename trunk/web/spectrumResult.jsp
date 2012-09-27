<%@ page import="be.proteomics.pprIA.search.FoundPeptide" %>
<%@ page import="be.proteomics.pprIA.search.ProteinLocation" %>
<html>
<head>
    <title>Peptide information</title>
    <link rel="stylesheet" type="text/css" href="pprStyle.css">
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
<body>
<div id="contentSmall">
     <%
         FoundPeptide[] result = (FoundPeptide[]) session.getValue("spectrum");
     %>
     <font face="Times New Roman,Times" size=+3>
      Spectrum/Peptide info<br>
     </font>
    <%
      for (int i=0; i < result.length ;i++) {
     %>
        Go to <a href="#<%=i%>"><%= result[i].getPeptideSequence()%></a> with score (threshold): <%= result[i].getScore()%> (<%= result[i].getThreshold()%>)<br> 
    <%
        }
    %>
      <%
      for (int i=0; i < result.length ;i++) {
     %>
    <HR>
    <a name="<%=i%>" id="<%=i%>">&nbsp</a>
    <TABLE width="850">
        <TR><TH colspan="2"><%= result[i].getPeptideSequence()%></TH></TR>
        <TR><TD width="20%">Modified sequence</TD><TD width="80%"><%= result[i].getModifiedSequence()%></TR>
        <TR><TD width="20%">Cell source</TD><TD width="80%"><a href="/toppr/ShowCellSources#<%=result[i].getCellSource()%>" target="_blank"><%= result[i].getCellSource()%></a></TR>
        <TR><TD width="20%">Experimental type</TD><TD width="80%"><a href="/toppr/ShowExperiments#<%=result[i].getExpType()%>" target="_blank"><%= result[i].getExpType()%></a></TR>
        <TR><TD width="20%">Mascot score</TD><TD width="80%"><%= result[i].getScore()%></TR>
        <TR><TD width="20%">Mascot threshold</TD><TD width="80%"><%= result[i].getThreshold()%></TR>
        <TR><TD width="20%">Confidence</TD><TD width="80%"><%= result[i].getConfidence()%></TR>
        <TR><TD width="20%">Precursor mass</TD><TD width="80%"><%= result[i].getPrecursorMass()%></TR>
        <TR><TD width="20%">Charge</TD><TD width="80%"><%= result[i].getCharge()%></TR>
        <TR><TD width="20%">Mascot version</TD><TD width="80%"><%= result[i].getMascotVersion()%></TR>
        <tr><td width="20%">Processing sites</td></tr>
        <TR><TD colspan="2">
            <table width="95%">
                <tr><td>Protein</td><td>Site</td><td>Treatment(s)</td><td>Inhibitor(s)</td></tr>
            <%
                ProteinLocation[] sites = result[i].getProteinLocation();
                for(int j = 0; j<sites.length; j++){
                    String classTr = "uneven";
                            if(j%2 == 0){
                                classTr = "even";
                            }
            %>
                <TR class="<%=classTr%>"><TD><a href="/toppr/ProteinSearch?protein=<%=sites[j].getProteinId()%>" target="_blank"><%=sites[j].getProteinId()%></a><br>(<%=sites[j].getDescription()%>)</TD>
                    <td width="30%"><%= sites[j].getProcessingSite()%></td><td width="20%"><a href="/toppr/ShowTreatments#<%=sites[j].getTreatmentAsString()%>" target="_blank"><%=sites[j].getTreatmentAsString()%></a></td><td width="20%"><a href="/toppr/ShowInhibitors#<%=sites[j].getInhibitorAsString()%>" target="_blank"><%=sites[j].getInhibitorAsString()%></a></td></TR>
            <%
                }
            %>
                </table></TD>

        <tr><td><a href="/toppr/ShowSpectrum?id=<%=result[i].getIdentificationid()%>&show=false" target="_blank">View spectrum</a> </td><td><a href="/toppr/ShowSpectrum?id=<%=result[i].getIdentificationid()%>&show=true" target="_blank">View annotated spectrum</a> </td></tr>
    </TABLE>
     <%
      }
     %>
</div>
</body>
</html>