<%@ page session="true" import="be.proteomics.pprIA.general.SearchOptions" %>
<%@ page import="be.proteomics.pprIA.general.Statistics" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <title>TOPPR statistics</title>
        <link rel="stylesheet" type="text/css" href="pprStyle.css">
        <script type="text/javascript" src="showhide.js"></script>
        <SCRIPT language="JavaScript" src="pprIA.js"></SCRIPT>
        <script type="text/javascript" src="graphs.js"></script>
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
<div id="content">
    <%
        Statistics stat = (Statistics) session.getValue("stat");
    %>
    <center>
    <div class="head" id="head" align="center" width="800">
    <span id="vib">
    <a href="http://www.vib.be" target="_blank"><img src="images/vib.jpg" border="0" ></a></span>
    <span><img src="images/ppr.jpg" border="0"></span>
    <span id="ugent">
    <a href="http://www.ugent.be" target="_blank"><img src="images/ugent.jpg" border="0"></a></span>
<div class="menu">
			<ul>
				<li><a class="first" href="home.html">Home</a></li>
				<li><a class="middle" href="manual.pdf">Manual</a></li>
				<li><a class="middleS" href="/toppr/SearchFill">Search</a></li>
				<li><a class="middle" href="searchInfo.jsp">Search log</a></li>
				<li><a class="last" href="login.html">Log in</a></li>
			</ul>
</div>
    </div>
        <b>Statistics</b>
    </center>


    <div class="startBox1024">&nbsp</div>
    <div class="midRBox1024">
        <div class="midLBox1024">
            <table width="95%">
                <tr>
                    <td>
                        Total substrates stored in TOPPR:
                    </td>
                    <td>
                        <%=stat.getTotalSubstrates()%>
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp &nbsp &nbsp Human subtrates stored:
                    </td>
                    <td>
                        <%=stat.getHumanSubstrates()%>
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp &nbsp &nbsp Mouse subtrates stored:
                    </td>
                    <td>
                        <%=stat.getMouseSubstrates()%>
                    </td>
                </tr>
                <tr>
                    <td>
                        Total processed sites stored in TOPPR:
                    </td>
                    <td>
                        <%=stat.getTotalSites()%>
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp &nbsp &nbsp Human processed sites stored:
                    </td>
                    <td>
                        <%=stat.getHumanSites()%>
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp &nbsp &nbsp Mouse processed sites stored:
                    </td>
                    <td>
                        <%=stat.getMouseSites()%>
                    </td>
                </tr>
                <tr>
                    <td>
                        Identified spectra/peptides stored in PPR:
                    </td>
                    <td>
                        <%=stat.getIdentifiedPeptides()%>
                    </td>
                </tr>
                <tr>
                    <td>
                        Gene ontolotgy entries linked to the substrates:
                    </td>
                    <td>
                        <%=stat.getGos()%>
                    </td>
                </tr>
                <tr>
                    <td>
                        Pdb files linked to the substrates:
                    </td>
                    <td>
                        <%=stat.getPdbs()%>
                    </td>
                </tr>
                <tr>
                    <td>
                        Domains linked to the substrates:
                    </td>
                    <td>
                        <%=stat.getDomains()%>
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp &nbsp &nbsp Pfam domains linked to the substrates:
                    </td>
                    <td>
                        <%=stat.getPfamDomains()%>
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp &nbsp &nbsp Smart domains linked to the substrates:
                    </td>
                    <td>
                        <%=stat.getSmartDomains()%>
                    </td>
                </tr>
                <tr>
                    <td>
                        Number of substrate orthologues stored in PPR:
                    </td>
                    <td>
                        <%=stat.getOrthologues()%>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        Processed site per protein
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <script language="JavaScript"> <!--
                    <%
                    int[] sites  = stat.getSitesPerProtein();
                    String values = "";
                    String labels = "";
                    for(int i = 0; i<sites.length; i ++){
                        labels = labels + (i + 1) + ",";
                        values = values + sites[i] + ",";
                    }
                    values = values.substring(0, values.lastIndexOf(","));
                    labels = labels.substring(0, labels.lastIndexOf(","));
                    %>
                    var graph = new BAR_GRAPH("vBar");
                    graph.values = "<%=values%>";
                    graph.labels = "<%=labels%>";
                    graph.showValues = 2;
                    graph.barWidth = 20;
                    graph.barLength = 1;
                    graph.labelSize = 10;
                    graph.absValuesSize = 10;
                    graph.percValuesSize = 12;
                    graph.graphPadding = 5;
                    graph.graphBGColor = "white";
                    graph.graphBorder = "0px solid black";
                    graph.barColors = "#6E6E6E";
                    graph.barBGColor = "#E6E6E6";
                    graph.barBorder = "1px outset #424242";
                    graph.labelColor = "#000000";
                    graph.labelBGColor = "#E6E6E6";
                    graph.labelBorder = "1px groove white";
                    graph.absValuesColor = "#000000";
                    graph.absValuesBGColor = "#FFFFFF";
                    graph.absValuesBorder = "1px groove white";
                    document.write(graph.create());
                    //--> </script>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        Number of treatments that cleave a protein
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <script language="JavaScript"> <!--
                    <%
                    int[] treats  = stat.getTreatmentsPerProtein();
                    String values2 = "";
                    String labels2 = "";
                    for(int i = 0; i<treats.length; i ++){
                        labels2 = labels2 + (i + 1) + ",";
                        values2 = values2 + treats[i] + ",";
                    }
                    values2 = values2.substring(0, values2.lastIndexOf(","));
                    labels2 = labels2.substring(0, labels2.lastIndexOf(","));
                    %>
                    var graph = new BAR_GRAPH("vBar");
                    graph.values = "<%=values2%>";
                    graph.labels = "<%=labels2%>";
                    graph.showValues = 2;
                    graph.barWidth = 20;
                    graph.barLength = 1;
                    graph.labelSize = 10;
                    graph.absValuesSize = 10;
                    graph.percValuesSize = 12;
                    graph.graphPadding = 5;
                    graph.graphBGColor = "white";
                    graph.graphBorder = "0px solid black";
                    graph.barColors = "#6E6E6E";
                    graph.barBGColor = "#E6E6E6";
                    graph.barBorder = "1px outset #424242";
                    graph.labelColor = "#000000";
                    graph.labelBGColor = "#E6E6E6";
                    graph.labelBorder = "1px groove white";
                    graph.absValuesColor = "#000000";
                    graph.absValuesBGColor = "#FFFFFF";
                    graph.absValuesBorder = "1px groove white";
                    document.write(graph.create());
                    //--> </script>
                    </td>
                </tr>
            </table>
        </div>
    <div class="endBox1024">&nbsp</div>
</div>
</body>
</html>