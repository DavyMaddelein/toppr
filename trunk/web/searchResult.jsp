<%@ page import="be.proteomics.pprIA.search.FoundProcessingSite" %>
<%@ page import="be.proteomics.pprIA.search.FoundProtein" %>
<%@ page import="be.proteomics.pprIA.search.PerformedSearches" %>
<%@ page session="true" import="be.proteomics.pprIA.general.SearchOptions" %>
<%@ page import="java.util.Vector" %>
<html>
<head>
    <title>TOPPR search result</title>
    <link rel="stylesheet" type="text/css" href="pprStyle.css">

    <script language="JavaScript" src="pprIA.js"></script>
    <script type="text/javascript" src="pdbFiles/Jmol.js"></script>
    <script type="text/javascript" src="showhide.js"></script>
    <script type="text/javascript" src="logo.js"></script>
    <script src="jquery-1.2.1.pack.js" type="text/javascript"></script>
    <script src="jquery-easing.1.2.pack.js" type="text/javascript"></script>
    <script src="jquery-easing-compatibility.1.2.pack.js" type="text/javascript"></script>
    <script src="coda-slider.1.1.1.pack.js" type="text/javascript"></script>

    <script src="jquery.dimensions.js" type="text/javascript"></script>
    <script src="jquery.accordion.js" type="text/javascript"></script>
    <script type="text/javascript" language="JavaScript">
        function checkForm() {
        answer = true;
        if (siw && siw.selectingSomething)
            answer = false;
        return answer;
        }//
    </script>

    <script type="text/javascript">
    <!--
    $(function () {
    $('ul.drawers').accordion({
            header: 'H2.drawer-handle',
                selectedClass: 'open',
                event: 'mouseover'
    });
    });
        //-->

    </script>


    	<script type="text/javascript">
		jQuery(window).bind("load", function() {
			jQuery("div#slider1").codaSlider()
		});
	</script>
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
<div class="head" id="head" align="center" width="800">
    <span id="vib">
    <a href="http://www.vib.be" target="_blank"><img src="images/vib.jpg" border="0"></a></span>
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
<%
    SearchOptions option = (SearchOptions) session.getValue("options");
    String browserType = request.getHeader("User-Agent");
    PerformedSearches searches = (PerformedSearches) session.getValue("searches");
    int sessionid = (Integer) session.getValue("session");
    //if the session is -1, process the last results
    if (sessionid == -1) {
        sessionid = searches.getSessionCount() - 1;
    } else {
        Integer sessionMin = -1;
        session.putValue("session", sessionMin);
    }

    FoundProtein[] proteins = searches.getFoundProteins(sessionid);
    int compilationSession = -1;
    int sessionCount = -2;
    sessionCount = searches.getSessionCount();
    for(int i = 0; i<sessionCount; i ++) {
        if(searches.getCompilation(i)){
            compilationSession = i;
            i = sessionCount;
        }
    }
    Vector protiensInCompilation = new Vector();
    if(compilationSession != -1){
        FoundProtein[] compilationProtein = searches.getFoundProteins(compilationSession);
        for(int p = 0; p<compilationProtein.length ; p ++){
            protiensInCompilation.add(compilationProtein[p].getSpAccession());
        }
    }
    String[] compilationProteins = new String[protiensInCompilation.size()];
    protiensInCompilation.toArray(compilationProteins);
%>
<div class="startBox1024">&nbsp</div>
<div class="midRBox1024">
    <div class="midLBox1024">
        <span class="title">Search summary</span>
        <TABLE width="95%">
            <TR>
                <TD width="40%">Search parameters</TD>
                <td width="15%">Found proteins</td>
                <td width="15%">Found sites</td>
                <td width="15%">Found unique peptide sequences</td>
                <td width="15%">View result as csv</td>
            </TR>
            <tr>
                <td>
                    <%
                        String[] params = searches.getSearchParameters(sessionid);
                        for (int j = 0; j < params.length; j++) {
                    %>
                    <%=params[j]%>&nbsp
                    <%
                        }
                    %>
                </td>
                <td><%=searches.getProteinCount(sessionid)%>
                </td>
                <td><%=searches.getProcessingSiteCount(sessionid)%>
                </td>
                <td><%=searches.getUniquePeptideCount(sessionid)%>
                </td>
                <td><A HREF="/toppr/ShowCsv?session=<%=sessionid%>" TARGET="_blank"><img src="images/csv.png" alt="" id="csv" name="csv"></A>
                </td>
            </tr>
        </table>
        <%
            if (searches.getProteinSearch(sessionid) == false) {
        %>
        <br><a href="#motif" class="peptideLink"><b>Motif section &nbsp </b> <img src="images/goto.png" align="middle"></a> &nbsp&nbsp&nbsp In the motif section different tools can be used to analyze the found substrates.
        <%
            }
        %>
        <%
            if (searches.getProteinSearch(sessionid) == false) {
        %>
        <br><a href="#peptides" class="peptideLink"><b>Peptide section &nbsp </b> <img src="images/goto.png" align="middle"></a> &nbsp&nbsp&nbsp All the found processed sites are listed in the peptide section.
        <%
            }
        %>
        <%
            if (searches.getParameterSearch(sessionid) == false) {
        %>
        <br><a href="#proteins" class="peptideLink"><b>Protein section &nbsp </b> <img src="images/goto.png" align="middle"></a> &nbsp&nbsp&nbsp Every found substrate is represent with its sequence and additional information.
        <%
            }
        %>
    </div>
</div>
<div class="endBox1024">&nbsp</div>
<%
    //if a parameter search was performed, show additional info
    if (searches.getProteinSearch(sessionid) == false) {
        String[] treatmentsSearched = searches.getSearchedTreatments(sessionid);
%>



<a name="motif" id="motif">&nbsp</a>
<div class="startBox1024">&nbsp</div>
<div class="midRBox1024">
    <div class="midLBox1024">
        <span class="title">Motif section</span><br>

        <div class="slider-wrap">
	<div id="slider1" class="csw">
		<div class="panelContainer">
            <div class="panel" title="iceLogo">
				<div class="wrapper">
					<h3>iceLogo</h3>
					        <form id="tslForm" name="tslForm" method="post" width="100%" action="/toppr/LogoServlet"
              target="tsl">
            <table width="95%">
                <tr>
                    <td width="10%"> </td>
                    <td style="background-color:#00CC00">Positive set</td>
                    <td style="background-color:#CC0000">Negative set</td>
                </tr>
                <tr>
                    <td width="10%"> </td>
                    <td>
                        Select (one or) more treatment(s):<br>
                        <SELECT NAME="positive" id="positive" MULTIPLE size="4" WIDTH="300">
                            <%
                                // only searched treatments will be selected
                                for (int i = 0; i < treatmentsSearched.length; i++) {
                                if(i == 0){%>
                                    <OPTION VALUE="<%=treatmentsSearched[i]%>" SELECTED=""><%= treatmentsSearched[i] %></OPTION>

                                <%        } else { %>
                                    <OPTION VALUE="<%=treatmentsSearched[i]%>"><%= treatmentsSearched[i] %></OPTION>

                                <%        }
                                %>
                            <%
                                }
                            %>
                        </select>
                    </td>
                    <td>
                        <table width="100%" border="0" cellpadding="0" cellspacing="1" id="negSeqTable">
					<tr>
					<td align="center" height="10%" valign="center"><input type="checkbox" id="negSeqChb" name="negSeqChb" value="" onclick="checkUncheck('negSeqChb','negSwChb')"></td>
					<td align="center" height="90%" valign="top">Select (one or) more treatment(s):<br>
                        <SELECT NAME="negative" id="negative" MULTIPLE size="4" WIDTH="300">
                            <%
                                // only searched treatments will be selected
                                for (int i = 0; i < treatmentsSearched.length; i++) {
                                if(i == 0){%>
                                    <OPTION VALUE="<%=treatmentsSearched[i]%>" SELECTED=""><%= treatmentsSearched[i] %></OPTION>

                                <%        } else { %>
                                    <OPTION VALUE="<%=treatmentsSearched[i]%>"><%= treatmentsSearched[i] %></OPTION>

                                <%        }
                                %>
                            <%
                                }
                            %>
                        </select></td>
					</tr>
					<tr>
					<td align="center" width="10%" valign="center"><input type="checkbox" id="negSwChb" name="negSwChb" value="use" onclick="checkUncheck('negSwChb','negSeqChb')" checked></td>
                        <td align="center" width="90%" valign="top">
                            <SELECT NAME="species" id="species" size="4" WIDTH="300">
                                <%
                                // only searched treatments will be selected
                                for (int i = 0; i < option.getTaxonomysScientific().length; i++) {
                                if(i == 0){%>
                                    <OPTION VALUE="<%=option.getTaxonomysScientific()[i]%>" SELECTED=""><%= option.getTaxonomysScientific()[i] %></OPTION>

                                <%        } else { %>
                                    <OPTION VALUE="<%=option.getTaxonomysScientific()[i]%>"><%= option.getTaxonomysScientific()[i] %></OPTION>

                                <%        }
                                %>
                            <%
                                }
                            %>
                        </select>
					</td>
					</tr>
					</table>

                    </td>
                </tr>
                <tr>
                    <td width="10%"> </td>
                    <td>
                        <INPUT TYPE=CHECKBOX NAME="selectedPositive" id="selectedPositive" value="notuse" onclick="setUse('selectedPositive')"> Only selected peptides?
                    </td>
                    <td>
                        <INPUT TYPE=CHECKBOX NAME="selectedNegative" id="selectedNegative" value="notuse" onclick="setUse('selectedNegative')"> Only selected peptides?
                    </td>
                </tr>
                <tr>
                    <td width="10%"> </td>
                    <td width="40%">Set the y-axis heigth

                        <SELECT NAME="yAxis" id="yAxis" size="4">
                            <OPTION VALUE="15" selected="">15</OPTION>
                            <OPTION VALUE="5">5</OPTION>
                            <OPTION VALUE="10">10</OPTION>
                            <OPTION VALUE="20">20</OPTION>
                            <OPTION VALUE="25">25</OPTION>
                            <OPTION VALUE="30">30</OPTION>
                            <OPTION VALUE="35">35</OPTION>
                            <OPTION VALUE="40">40</OPTION>
                            <OPTION VALUE="45">45</OPTION>
                            <OPTION VALUE="50">50</OPTION>
                        </select>
                    </td>
                    <td width="40%">
                    </td>
                </tr>
                <tr>
                    <td width="10%"> </td>
                    <td colspan="2">
                    <div align="center"><img src="images/white.gif" alt="" id="logoImg" name="logoImg"><br>
                    <input style="width:61%;height:25px;z-index:5" type="button" onclick="postLogo()" id="makeLogo" name="makeLogo" value="Generate"></div><br>

                    </td>
                </tr>
             </table>
            <INPUT TYPE=hidden NAME=session id="session" VALUE="<%=sessionid%>">
        </form>
				</div>
			</div>
			<div class="panel" title="Weblogo">
				<div class="wrapper">
					<h3>Weblogo</h3>
					<form id="weblogoForm" name="weblogoForm"  width="100%" method="post" action="/toppr/WeblogoViewStarter"
                      target="weblogo">
                    <table width="95%">
                        <tr>
                            <td width="20%"> </td>
                            <td width="40%">Select (one or) more treatment(s):</td>
                            <td width="40%">
                                <SELECT NAME="treatment" MULTIPLE size="4" WIDTH="300">
                                    <%
                                        // only searched treatments will be selected
                                        for (int i = 0; i < treatmentsSearched.length; i++) {
                                    %>
                                    <OPTION VALUE="<%=treatmentsSearched[i]%>"><%= treatmentsSearched[i] %>
                                    </OPTION>
                                    <%
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td width="20%"> </td>
                            <td width="40%"><INPUT TYPE=CHECKBOX NAME="weblogoSelected" id="weblogoSelected" value="notuse" onclick="setUse('weblogoSelected')">Only selected peptides?</td>
                            <td width="40%">
                                Weblogo bits:
                                <SELECT NAME="bits">
                                    <OPTION VALUE="4.0">4.0</OPTION>
                                    <OPTION VALUE="3.5">3.5</OPTION>
                                    <OPTION VALUE="3.0">3.0</OPTION>
                                    <OPTION VALUE="2.5">2.5</OPTION>
                                    <OPTION VALUE="2.0">2.0</OPTION>
                                    <OPTION VALUE="1.5">1.5</OPTION>
                                    <OPTION VALUE="1.0">1.0</OPTION>
                                </SELECT>
                            </td>
                        </tr>
                        <tr>
                            <td width="20%"> </td>
                            <td width="40%">
                                Weblogo width:
                                <SELECT NAME="width">
                                    <OPTION VALUE="15">15 => P15 - P'15</OPTION>
                                    <OPTION VALUE="10">10 => P10 - P'10</OPTION>
                                    <OPTION VALUE="8">8 => P8 - P'8</OPTION>
                                    <OPTION VALUE="4">4 => P4 - P'4</OPTION>
                                    <OPTION VALUE="2">2 => P2 - P'2</OPTION>
                                </SELECT>                    </td>
                            <td width="40%"><input type="submit" name="viewWeblogo" id="viewWeblogo" value="View" onclick="show('weblogo')"></td>
                        </tr>
                        <tr>
                            <td width="20%"> </td>
                            <td colspan="2" width="80%">
                                <iframe src="searching.html" frameborder="0" height="600" width="800" id="weblogo" name="weblogo"
                                        style=display:none>
                                </iframe>
                            </td>
                        </tr>
                    </table>
                    <INPUT TYPE=hidden NAME=session VALUE="<%=sessionid%>">
                </form>
				</div>
			</div>
			<div class="panel" title="Jalview">
				<div class="wrapper">
					<h3>Jalview</h3>
				 <form id="jalviewForm" name="jalviewForm" method="post" width="100%" action="/toppr/Jalview"
              target="jalview">
            <table width="95%">
                <tr>
                    <td width="20%"> </td>
                    <td width="40%">Select (one or) more treatment(s):</td>
                    <td width="40%">
                        <SELECT NAME="treatment" MULTIPLE size="4" WIDTH="300">
                            <%
                                // only searched treatments will be selected
                                for (int i = 0; i < treatmentsSearched.length; i++) {
                            %>
                            <OPTION VALUE="<%=treatmentsSearched[i]%>"><%= treatmentsSearched[i] %>
                            </OPTION>
                            <%
                                }
                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="20%"> </td>
                    <td width="40%"><INPUT TYPE=CHECKBOX NAME="jalviewSelected" id="jalviewSelected" value="notuse" onclick="setUse('jalviewSelected')">Only selected peptides</td>
                    <td width="40%"><input type="submit" name="viewJalview" id="viewJalview" value="View" onclick="show('jalview');"></td>
                </tr>
                <tr>
                    <td width="20%"> </td>
                    <td colspan="2" width="80%">
                        <iframe src="searching.html" align="center" frameborder="0" height="100" width="400" id="jalview" name="jalview"
                            style=display:none>
                        </iframe>
                    </td>
                </tr>
            </table>

            <INPUT TYPE=hidden NAME=session VALUE="<%=sessionid%>">
        </form>
				</div>
			</div>
			<div class="panel" title="POPS model">
				<div class="wrapper">
					<h3>POPS model</h3>
				        <form id="popsForm" name="popsForm" method="post" width="100%" action="/toppr/Pops"
              target="pops">
            <table width="95%">
                <tr>
                    <td width="20%"> </td>
                    <td width="40%">Select (one or) more treatment(s):</td>
                    <td width="40%">
                        <SELECT NAME="treatment" MULTIPLE size="4" WIDTH="300">
                            <%
                                // only searched treatments will be selected
                                for (int i = 0; i < treatmentsSearched.length; i++) {
                            %>
                            <OPTION VALUE="<%=treatmentsSearched[i]%>"><%= treatmentsSearched[i] %>
                            </OPTION>
                            <%
                                }
                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="20%"> </td>
                    <td width="40%"><INPUT TYPE=CHECKBOX NAME="popsSelected" id="popsSelected" value="notuse" onclick="setUse('popsSelected')">Only selected peptides?</td>
                    <td width="40%">
                        Pops model width:
                        <SELECT NAME="width">
                            <OPTION VALUE="8">8 => P8 - P'8</OPTION>
                            <OPTION VALUE="4">4 => P4 - P'4</OPTION>
                            <OPTION VALUE="2">2 => P2 - P'2</OPTION>
                        </SELECT>
                    </td>
                </tr>
                <tr>
                    <td width="20%"> </td>
                    <td width="40%">
                                           </td>
                    <td width="40%"><input type="submit" name="calculate model" id="calculate model" value="View" onclick="show('pops')"></td>
                </tr>
                <tr>
                    <td width="20%"> </td>
                    <td align="center" colspan="2" width="80%">
                    <a href="http://pops.csse.monash.edu.au/pops-cgi/pops.jnlp">The PoPS program</a>
                    </td>
                </tr>
            </table>
            <iframe src="searching.html" frameborder="0" height="530" width="850" id="pops" name="pops" style=display:none>
            </iframe>                
            <INPUT TYPE=hidden NAME=session VALUE="<%=sessionid%>">
        </form>
				</div>
			</div>
                        <div class="panel" title="Processing site list">
				<div class="wrapper">
					<h3>Processed site list</h3>
                        <form id="listForm" name="listForm" method="post" width="100%" action="/toppr/SiteList" target="list">
            <table width="95%">
                <tr>
                    <td width="20%"> </td>
                    <td width="40%">Select (one or) more treatment(s):</td>
                    <td width="40%">
                        <SELECT NAME="treatment" MULTIPLE size="4" WIDTH="300">
                            <%
                                // only searched treatments will be selected
                                for (int i = 0; i < treatmentsSearched.length; i++) {
                            %>
                            <OPTION VALUE="<%=treatmentsSearched[i]%>"><%= treatmentsSearched[i] %>
                            </OPTION>
                            <%
                                }
                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="20%"> </td>
                    <td width="40%"><INPUT TYPE=CHECKBOX NAME="listSelected"  id="listSelected" value="notuse" onclick="setUse('listSelected')">Only selected peptides</td>
                    <td width="40%"><input type="submit" name="viewList" id="viewList" value="View" onclick="show('list')"></td>
                </tr>
                <tr>
                    <td width="20%"></td>
                    <td colspan="2" width="80%">
                        <iframe src="searching.html" align="center" frameborder="0" height="530" width="300" id="list" name="list"
                                style=display:none>
                        </iframe>
                    </td>
                </tr>
            </table>
            <INPUT TYPE=hidden NAME=session VALUE="<%=sessionid%>">
        </form>				</div>
			</div>

		</div><!-- .panelContainer -->
	</div><!-- #slider1 -->
</div><!-- .slider-wrap -->

    </div>
</div>
<div class="endBox1024">&nbsp</div>
<a name="peptides" id="peptides">&nbsp</a>

<div class="startBox1024">&nbsp</div>
<div class="midRBox1024">
<div class="midLBox1024">
<span class="Treatment">Peptide section</span>
<table width="95%">
    <tr>
        <td width="33%">Select a peptide to see the selected peptide in a weblogo</td>
        <td width="33%">Click <img src="images/add.png" align="middle"> to add protein to your user protein compilation</td>
        <td width="33%">If the <i>peptide</i> is in italic, click it to see the isoforms</td>
    </tr>
</table>
<br>
<span id="orderTreatment" style="color:black; cursor: pointer;display:none"
      onclick="showhide('treatmentLinks'); showhide('orderTreatment'); showhide('orderProtein');showhide('treatmentPeptide') ; showhide('peptideProtein')"><img src="images/order.png"><b> Order by treatments</b></span>
<span id="orderProtein"  style="color:black; cursor: pointer;display:block"
      onclick="showhide('treatmentLinks'); showhide('orderProtein'); showhide('orderTreatment'); showhide('peptideProtein') ; showhide('treatmentPeptide')"><img src="images/order.png"><b> Order by proteins</b></span>
<div id="treatmentLinks" style="display:block">
     <%
            if( searches.getSearchedTreatments(sessionid).length>1){
                for (int t = 0; t < searches.getSearchedTreatments(sessionid).length; t++) {
        %>
        <br> &nbsp &nbsp &nbsp <a href="#<%=searches.getSearchedTreatments(sessionid)[t]%>" class="peptideLink"><%=searches.getSearchedTreatments(sessionid)[t]%>&nbsp &nbsp<img src="images/goto.png" align="middle"></a>
        <%
                }
            }
        %>
</div><br>


<div id="treatmentPeptide" style="display:block">
    <%
        int sites = 0;
        for (int i = 0; i < treatmentsSearched.length; i++) {
    %>
    <a name="<%=treatmentsSearched[i]%>" id="<%=treatmentsSearched[i]%>">&nbsp</a>
    <span class="subtitle"><%=treatmentsSearched[i]%></span>
    <table width="95%">
        <%
            for (int j = 0; j < proteins.length; j++) {
                Vector sitesVector = proteins[j].getProcessingSites();
                for (int k = 0; k < sitesVector.size(); k++) {
                    FoundProcessingSite site = (FoundProcessingSite) sitesVector.get(k);
                    if (site.isShow()) {
                        String[] treats = site.getTreatments();
                        for (int l = 0; l < treats.length; l++) {
                            if (treats[l].equalsIgnoreCase(treatmentsSearched[i])) {
                                sites = sites + 1;
                                String classTr = "uneven";
                                if(sites%2 == 0){
                                    classTr = "even";
                                }
                                String checked = (!site.getSelected()) ? "" : "checked";
                                boolean inCompilation = false;
                                for(int c = 0; c<compilationProteins.length; c ++){
                                    if(proteins[j].getSpAccession().equalsIgnoreCase(compilationProteins[c])){
                                        inCompilation = true;
                                    }
                                }
                                String addStyle = "inline";
                                if(inCompilation){
                                    addStyle = "none";
                                }
        %>
        <tr class="<%=classTr%>">
            <td><INPUT TYPE=CHECKBOX id="treatment<%=j%>,<%=k%>,<%=sessionid%>" NAME="siteCheckBox" <%=checked%> onclick="checkUnchecked('treatment<%=j%>,<%=k%>,<%=sessionid%>','protein<%=j%>,<%=k%>,<%=sessionid%>');selectSite(<%=j%>,<%=k%>,<%=sessionid%>)">
            </td>
            <td width="7%"><a href="/toppr/ProteinSearch?protein=<%=proteins[j].getSpAccession()%>"
                               target="_blank">
                <%=proteins[j].getSpAccession()%>
            </a></td>
            <td width="3%" style="font-size: 0.9em;"><input type="hidden" id="protein<%=sites%>"
                                                            value="<%= proteins[j].getSpAccession()%>"><span
                    id="<%=proteins[j].getSpAccession()%>" style="cursor:pointer; display: <%=addStyle%>;"
                    onclick="addProteinAccession('<%=proteins[j].getSpAccession()%>');"><img src="images/add.png" align="middle"></span>
            </td>
            <td width="35%" style="font-size: 0.9em;"><%=proteins[j].getDescription()%>
            </td>
            <td width="15%">
                 position: <%=site.getPosition()%>
            </td>
            <%

                if (!site.isIsoform()) {
            %>
            <td width="40%" style="text-align:right; font-size: 0.9em;"><tt><%=(site.getStart())%>-<%= site.getPreSite()%><img src="images/scissorsSmall<%=classTr%>.jpg" align="middle"><%= site.getPostSite()%>-<%= (site.getEnd())%></tt></td>
            <%
            } else if(site.isNterminal()) {

            %>
            <td width="40%" style="text-align:right; font-size: 0.9em;"><a title="Found <%=site.getIsoformCount()%> isoforms for this peptide" class="peptideLink"href="/toppr/SiteSearch?pre=&post=<%=site.getPeptide()%>"><tt><i><%=(site.getStart())%>-<%= site.getPreSite()%><img src="images/scissorsSmall<%=classTr%>.jpg" align="middle" border="0"><%= site.getPostSite()%>-<%= (site.getEnd())%></i></tt></a></td>
            <%

            } else{
            %>
            <td width="40%" style="text-align:right; font-size: 0.9em;"><a title="Found <%=site.getIsoformCount()%> isoforms for this peptide" class="peptideLink"href="/toppr/SiteSearch?pre=<%=site.getPeptide()%>&post="><tt><i><%=(site.getStart())%>-<%= site.getPreSite()%><img src="images/scissorsSmall<%=classTr%>.jpg" align="middle" border="0"><%= site.getPostSite()%>-<%= (site.getEnd())%></i></tt></a></td>

           <%         }
                }
            %>
        </tr>
        <%
                        }
                    }
                }
            }
        %>

    </table>
    <%
        }
    %>
</div>
<div id="peptideProtein" style="display:none">
    <%
        for (int j = 0; j < proteins.length; j++) {
            boolean inCompilation = false;
            for(int c = 0; c<compilationProteins.length; c ++){
                if(proteins[j].getSpAccession().equalsIgnoreCase(compilationProteins[c])){
                    inCompilation = true;
                 }
             }
             String addStyle = "inline";
             if(inCompilation){
                addStyle = "none";
             }
    %>
    <table width="95%">
        <tr>
            <td width="12%"><a href="/toppr/ProteinSearch?protein=<%=proteins[j].getSpAccession()%>"
                               target="_blank">
                <%=proteins[j].getSpAccession()%>
            </a> &nbsp &nbsp<input type="hidden" id="protein<%=sites%>" value="<%= proteins[j].getSpAccession()%>"><span
                    id="<%=proteins[j].getSpAccession()%>" style="cursor:pointer; display: <%=addStyle%>;"
                    onclick="addProteinAccession('<%=proteins[j].getSpAccession()%>')">
                                    <img src="images/add.png" align="middle"></span>
            </td>
            <td colspan="3" style="font-size: 0.9em;"><%=proteins[j].getDescription()%>
            </td>
        </tr>
        <%
            Vector sitesVector = proteins[j].getProcessingSites();
            int count = 0;
            for (int s = 0; s < sitesVector.size(); s++) {
                FoundProcessingSite site = (FoundProcessingSite) sitesVector.get(s);
                if (site.isShow()) {
                    String classTr = "uneven";
                    if(count%2 == 0){
                        classTr = "even";
                    }
                    count = count + 1;
                    String checked = (!site.getSelected()) ? "" : "checked";

        %>

        <tr class="<%=classTr%>">
            <td style="text-align:right; font-size: 0.9em;"><INPUT TYPE=CHECKBOX
                                                                   id="protein<%=j%>,<%=s%>,<%=sessionid%>"
                                                                   NAME="siteCheckBox"
                                                                   <%=checked%>
                                                                   onclick="selectSite(<%=j%>,<%=s%>,<%=sessionid%>);checkUnchecked('protein<%=j%>,<%=s%>,<%=sessionid%>','treatment<%=j%>,<%=s%>,<%=sessionid%>')">
            </td>
            <%

                if (!site.isIsoform()) {
            %>
            <td width="45%" style="font-size: 0.9em;"><tt><%=(site.getStart())%>-<%= site.getPreSite()%><img src="images/scissorsSmall<%=classTr%>.jpg" align="middle"><%= site.getPostSite()%>-<%= (site.getEnd())%></tt></td>
            <%
            } else if(site.isNterminal()) {
            %>
            <td width="45%" style="font-size: 0.9em;"><a title="Found <%=site.getIsoformCount()%> isoforms for this peptide" class="peptideLink" href="/toppr/SiteSearch?pre=&post=<%=site.getPeptide()%>"><tt><i><%=(site.getStart())%>-<%= site.getPreSite()%><img src="images/scissorsSmall<%=classTr%>.jpg" align="middle"  border="0"><%= site.getPostSite()%>-<%= (site.getEnd())%></i></tt></a></td>
            <%
            } else {
            %>
            <td width="45%" style="font-size: 0.9em;"><a title="Found <%=site.getIsoformCount()%> isoforms for this peptide" class="peptideLink" href="/toppr/SiteSearch?pre=<%=site.getPeptide()%>&post="><tt><i><%=(site.getStart())%>-<%= site.getPreSite()%><img src="images/scissorsSmall<%=classTr%>.jpg" align="middle"  border="0"><%= site.getPostSite()%>-<%= (site.getEnd())%></i></tt></a></td>
            <%    }
            %>
            <td width="15%">
                 position: <%=site.getPosition()%>
            </td>
            <td width="30%">
                <%
                    String[] treats = site.getTreatments();
                    for (int t = 0; t < treats.length; t++) {
                %>
                <%=treats[t]%><br>
                <%
                    }
                %>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
    <%
        }
    %>

</div>
</div>
</div>
<div class="endBox1024">&nbsp</div>
<%
    }
    // the additional information is given if a parameter search was performed
%>

<a name="proteins" id="proteins">&nbsp</a>
<% if (searches.getParameterSearch(sessionid) == false) {
    for (int i = 0; i < proteins.length; i++) {
        String style = "";
        if(proteins[i].isHomologue()){
            style = "Red";
        }
%>
<a name="protein<%=i%>" id="protein<%=i%>">&nbsp</a>

<div class="startBox1024<%=style%>">&nbsp</div>
<div class="midRBox1024<%=style%>">
    <div class="midLBox1024<%=style%>">
        <div class="title" id="title<%=i%>">
            <A HREF="http://www.expasy.org/uniprot/<%= proteins[i].getSpAccession()%>"
               TARGET="_blank"><%= proteins[i].getSpAccession() %>
            </A> <%= proteins[i].getUpAccession()%>
            <input type="hidden" id="protein<%=i%>" value="<%= proteins[i].getSpAccession()%>">
        </div>
        <div class="description" id="description<%=i%>">
            <%= proteins[i].getDescription()%>
        </div>
        <div class="sequence" id="seq<%=i%>">
            <table>
                <tr>
                    <td width="400px">
                        <tt><div class="sequence" id="sequence<%=proteins[i].getSpAccession()%>"><%=proteins[i].getSitesInSequence()%></div>
                        </tt>
                    </td>
                    <td width="45%">
                    <div class="drawers-wrapper">
                        <div class="boxcap captop"></div>
                        <ul class="drawers">
                            <li class="drawer">
                                <h2 class="drawer-handle open">Options</h2>
                                <ul>
                                    <li><span style="cursor:pointer;"
                              onclick="predictDomain('<%=proteins[i].getSpAccession()%>',<%=sessionid%>);"><a>1. Show domains</a></span></li>
                                    <li><span style="cursor:pointer;" onclick="predict('<%=proteins[i].getSpAccession()%>',<%=sessionid%>)"><a>2. Show secondary structure</a></span></li>
                                    <li><span style="cursor:pointer;" onclick="getGo('<%=proteins[i].getSpAccession()%>',<%=sessionid%>)"><a>3. Show Gene Ontology terms</a></span></li>
                                    <li><span style="cursor:pointer;" onclick="goTo('spectrumAnchor<%=i%>'); showhide('spectrum<%=i%>'); showhide('spectrumLine<%=i%>')"><A HREF="/toppr/ShowPeptideInfo?protein=<%=i%>&session=<%=sessionid%>" TARGET="spectrum<%=i%>" >4. View information about spectra and peptides</A></span></li>
                                    <li><span style="cursor:pointer;" onclick="goTo('conAnchor<%=i%>'); showhide('conInfo<%=i%>'); conservationFinder(<%=i%>,<%=sessionid%>); showhide('conservationLine<%=i%>');"><a>5. Show the conservation of the processed sites</a></span></li>

                                    <% //3d
                                        if (proteins[i].getPdbFound()) {
                                            String[] pdb = proteins[i].getPdbAccessions();
                                    %>
                                    <li><span style="cursor:pointer;"><a HREF="/toppr/Show3d?protein=<%=i%>&session=<%=sessionid%>" TARGET="dd<%=i%>" onclick="goTo('3dAnchor<%=i%>'); showhide('dd<%=i%>'); showhide('ddLine<%=i%>')">6. View the 3D structure </a></span></li>
                                    <%
                                        }
                                    %>


                                     <%
                                    for (int t = 0; t <= proteins[i].getUsedTreatments().size(); t++) {
                                    %>
                                        <li><span>&nbsp</span></li>
                                        <li><span>&nbsp</span></li>
                                    <%
                                    }
                                    %>
                                </ul>
                            </li>

                            <li class="drawer last">
                                <h2 class="drawer-handle">Treatment</h2>
                                <ul class="allTreatments">

                                    <li id="All sites"><span style="cursor:pointer;" onclick="showHideAllSites('img','<%=proteins[i].getSpAccession()%>')"><img src="images/scissorsSmall99.jpg" align="middle"></span> all sites <span id="<%=proteins[i].getSpAccession()%>AllSites" style="display:none;">none</span></li>
                                    <%
                                        for (int t = 0; t < proteins[i].getUsedTreatments().size(); t++) {
                                    %>

                                    <li id="<%=proteins[i].getUsedTreatments().get(t)%>">
                                        <span style="cursor:pointer;" onclick="showHideProcessingSites('img','<%=proteins[i].getSpAccession()%>_<%=proteins[i].getUsedTreatments().get(t)%>')"><img src="images/scissorsSmall<%=t%>.jpg" align="middle"></span> <%=proteins[i].getUsedTreatments().get(t)%> sites <span id="<%=proteins[i].getSpAccession()%>_<%=proteins[i].getUsedTreatments().get(t)%>.Display" style="display:none;">none</span>
                                    </li>

                                    <%
                                        }
                                    %>

                                </ul>

                            </li>
                        </ul>

                        <div class="boxcap"></div>
                    </div>

                    </td>

                </tr>
                <tr>
                    <td colspan="2">
                        <div id="info<%=proteins[i].getSpAccession()%>">&nbsp</div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><div align="center" id="bar<%=proteins[i].getSpAccession()%>"><%=proteins[i].getNavigationProtein()%></div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<div class="lineBox1024<%=style%>" id="ProcessingSites<%=i%>">&nbsp</div>
<div class="midRBox1024<%=style%>">
    <div class="midLBox1024<%=style%>">
        <div id="processingSites<%=i%>">


            <%  boolean inCompilation = false;
                for(int c = 0; c<compilationProteins.length; c ++){
                    if(proteins[i].getSpAccession().equalsIgnoreCase(compilationProteins[c])){
                        inCompilation = true;
                    }
                }
                String addStyle = "inline";
                if(inCompilation){
                    addStyle = "none";
                }

            %>
            <br>
            
            <table width="100%">
                <td><span>Found <%=proteins[i].getSiteCount()%> site(s) for <%=proteins[i].getTreatmentCount()%> treatment(s)</span></td>
                <td><span id="orderTreatment<%=proteins[i].getSpAccession()%>" style="cursor: pointer;display:none"
                  onclick="showhide('sitesTreatment<%=proteins[i].getSpAccession()%>'); showhide('sitesLocation<%=proteins[i].getSpAccession()%>');showhide('orderTreatment<%=proteins[i].getSpAccession()%>') ; showhide('orderLocation<%=proteins[i].getSpAccession()%>')"><img src="images/order.png"><b> Group by treatment</b></span>
            <span id="orderLocation<%=proteins[i].getSpAccession()%>" style="cursor: pointer;display:block"
                  onclick="showhide('sitesLocation<%=proteins[i].getSpAccession()%>'); showhide('sitesTreatment<%=proteins[i].getSpAccession()%>'); showhide('orderLocation<%=proteins[i].getSpAccession()%>') ; showhide('orderTreatment<%=proteins[i].getSpAccession()%>')"><img src="images/order.png"><b> Group by site</b></span>
            </td>
                <td><span id="<%=proteins[i].getSpAccession()%>" style="cursor:pointer; display: <%=addStyle%>;"
                    onclick="addProteinAccession('<%=proteins[i].getSpAccession()%>')"> <img src="images/add.png" align="middle"> <b>Add</b> to selection</span></td>
            </table>

            <div id="sitesTreatment<%=proteins[i].getSpAccession()%>" style="display:block">
            <% //get processing sites
                Vector proteinSites = proteins[i].getProcessingSites();
                Vector usedTreatments = proteins[i].getUsedTreatments();
                for (int k = 0; k < usedTreatments.size(); k++) {
            %>
            <div class="inBoxTitle">
                <a href="/toppr/ShowTreatments#<%=usedTreatments.get(k)%>"
                   target="_blank"><%=usedTreatments.get(k)%>
                </a> <img src="images/scissorsSmall<%=k%>.jpg" align="middle">
            </div>

            <%
                    for (int l = 0; l < proteinSites.size(); l++) {
                        FoundProcessingSite site = (FoundProcessingSite) proteinSites.get(l);
                        String[] treatmensFotThisSite = site.getTreatments();
                        for (int m = 0; m < treatmensFotThisSite.length; m++) {
                            if (treatmensFotThisSite[m].equalsIgnoreCase((String) usedTreatments.get(k))) {
                                if (!site.isIsoform()) {
                        %>
                        <div style="font-size: 0.9em;"><tt><%=(site.getStart())%>-<%= site.getPreSite()%><img src="images/scissorsSmall<%=k%>.jpg" align="middle"><%= site.getPostSite()%>-<%= (site.getEnd())%></tt> &nbsp  &nbsp  &nbsp  &nbsp  &nbsp position: <%=site.getPosition()%></div>
                        <%
                                } else  if(site.isNterminal()){
                        %>
                        <div width="45%" style="font-size: 0.9em;"><a title="Found <%=site.getIsoformCount()%> isoforms for this peptide" class="peptideLink" href="/toppr/SiteSearch?pre=&post=<%=site.getPeptide()%>"><tt><i><%=(site.getStart())%>-<%= site.getPreSite()%><img src="images/scissorsSmall<%=k%>.jpg" align="middle" border="0"><%= site.getPostSite()%>-<%= (site.getEnd())%></i></tt></a>  &nbsp  &nbsp  &nbsp  &nbsp  &nbsp position: <%=site.getPosition()%></div>

                        <%
                                } else {
                        %>
                        <div width="45%" style="font-size: 0.9em;"><a title="Found <%=site.getIsoformCount()%> isoforms for this peptide" class="peptideLink" href="/toppr/SiteSearch?pre=<%=site.getPeptide()%>&post="><tt><i><%=(site.getStart())%>-<%= site.getPreSite()%><img src="images/scissorsSmall<%=k%>.jpg" align="middle" border="0"><%= site.getPostSite()%>-<%= (site.getEnd())%></i></tt></a>  &nbsp  &nbsp  &nbsp  &nbsp  &nbsp position: <%=site.getPosition()%></div>
                        <%        }
                            }
                        }
                    }
                }
            %>
            </div>
            <div id="sitesLocation<%=proteins[i].getSpAccession()%>" style="display:none">
                <table width="95%">
                    <%
                        for(int s = 0; s<proteinSites.size(); s ++){
                            FoundProcessingSite site = (FoundProcessingSite)proteinSites.get(s);
                            String classTr = "uneven";
                            if(s%2 == 0){
                                classTr = "even";
                            }
                    %>
                    <tr class="<%=classTr%>">

                        <%

                            if (!site.isIsoform()) {
                        %>
                        <td width="45%" style="font-size: 0.9em;"><tt><%=(site.getStart())%>-<%= site.getPreSite()%><img src="images/scissorsSmall99<%=classTr%>.jpg" align="middle"><%= site.getPostSite()%>-<%= (site.getEnd())%></tt></td>
                        <%
                        } else {
                        %>
                        <td width="45%" style="font-size: 0.9em;"><a title="Found <%=site.getIsoformCount()%> isoforms for this peptide" class="peptideLink" href="/toppr/SiteSearch?pre=&post=<%=site.getPeptide()%>"><tt><i><%=(site.getStart())%>-<%= site.getPreSite()%><img src="images/scissorsSmall99<%=classTr%>.jpg" align="middle" border="0"><%= site.getPostSite()%>-<%= (site.getEnd())%></i></tt></a></td>
                        <%
                            }
                        %>
                        <td>
                            position: <%=site.getPosition()%>
                        </td>
                        <td>
                        <%
                            String[] treatmensFotThisSite = site.getTreatments();
                            for(int t = 0; t<proteins[i].getUsedTreatments().size(); t ++){
                                for (int m = 0; m < treatmensFotThisSite.length; m++){
                                    if(((String)proteins[i].getUsedTreatments().get(t)).equalsIgnoreCase(treatmensFotThisSite[m])){
                        %>
                                        <img src="images/scissorsSmall<%=t%><%=classTr%>.jpg" align="middle"> &nbsp &nbsp &nbsp <%=treatmensFotThisSite[m]%><br>
                    <%
                                    }
                                }
                            }
                    %>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </table>

            </div>
        </div>
    </div>
</div>

<div class="lineBox1024<%=style%>" id="ddLine<%=i%>" style="display:none">&nbsp</div>
<div class="midRBox1024<%=style%>">
    <div class="midLBox1024<%=style%>">
        <a name="3dAnchor<%=i%>" id="3dAnchor<%=i%>"></a>
        <iframe src="searching.html" align="center" frameborder="0" height="1000" width="964" id="dd<%=i%>"
                name="dd<%=i%>" style=display:none>
        </iframe>
    </div>
</div>
<div class="lineBox1024<%=style%>" id="spectrumLine<%=i%>" style="display:none">&nbsp</div>
<div class="midRBox1024<%=style%>">
    <div class="midLBox1024<%=style%>">
        <a name="spectrumAnchor<%=i%>" id="spectrumAnchor<%=i%>"></a>
        <iframe src="searching.html" align="center" frameborder="0" height="420" width="964" id="spectrum<%=i%>"
                name="spectrum<%=i%>" style=display:none>
        </iframe>
    </div>
</div>
<div class="lineBox1024<%=style%>" id="conservationLine<%=i%>" style="display:none">&nbsp</div>
<div class="midRBox1024<%=style%>">
    <div class="midLBox1024<%=style%>">
        <a name="conAnchor<%=i%>" id="conAnchor<%=i%>"></a>

        <div class="smalltitle" style=display:none id="conInfo<%= i%>" name="conInfo<%= i%>">
            Conservation
        </div>
        <div id="con<%= i%>"></div>
    </div>
</div>

<div class="endBox1024<%=style%>">&nbsp</div>
<%
        }
    }
%>

<%
  if(browserType.indexOf("msie") > -1 || browserType.indexOf("MSIE") > -1){
%>
</div>

<center>
<div style="position: absolute; right: 0px; bottom: 0px;
    right: auto; bottom: auto;
    left: expression( ( 0 - bar.offsetWidth + ( document.documentElement.clientWidth ? document.documentElement.clientWidth : document.body.clientWidth ) + ( ignoreMe2 = document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft ) ) + 'px' );
    top: expression( ( 0 - bar.offsetHeight + ( document.documentElement.clientHeight ? document.documentElement.clientHeight : document.body.clientHeight ) + ( ignoreMe = document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop ) ) + 'px' );
    border-top: 1px solid #EEEEEE;
	width: 100%;
	color: white;
	background-color: #85C329;
	line-height: 1.5;
	filter: alpha(opacity=90);
	opacity: 0.9;
	-moz-opacity: 0.9;
	z-index: 1;
	display:block" id="bar">
        <p id="bar-contents">
             <span style="cursor: pointer; text-decoration:underline" onclick="slide('examplePanel4', 'slideLink'); showhide('examplePanel4')"><a style="color:white;text-decoration:underline" href="performedSearches.jsp" target="performedSearches">View performed searches.</a></span> Proteins in user compilation: <span id="userCompilation"><%
            String text = "";
            if(compilationProteins.length == 0){
                text = " no proteins found in  compilation!";
            } else {
                text = compilationProteins[0] + "<img src=\"images/del.jpg\" align=\"top\" style=\"cursor:pointer;\" onclick=\"deleteProtein('" + compilationProteins[0] +"')\">";
                for(int p = 1; p< compilationProteins.length; p ++){
                    text = text + ", " + compilationProteins[p] + "<img src=\"images/del.jpg\" align=\"top\" style=\"cursor:pointer;\" onclick=\"deleteProtein('" + compilationProteins[p] +"')\">";
                }
            }

            %><%=text%></span>
        </p>

    </div>

<div style="position: absolute; right: 0px; bottom: 0px;

    right: auto; bottom: auto;
    left: expression( ( -15 - bar.offsetWidth + ( document.documentElement.clientWidth ? document.documentElement.clientWidth : document.body.clientWidth ) + ( ignoreMe2 = document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft ) ) + 'px' );
    top: expression( ( 5 - bar.offsetHeight + ( document.documentElement.clientHeight ? document.documentElement.clientHeight : document.body.clientHeight ) + ( ignoreMe = document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop ) ) + 'px' );

                    width:100%;
                    height:0px;
                    bottom:25px;
                    left:10px;">
           <div id="exampleHeader4"
              style="z-index:1;
                    position:absolute;
                    width:20px;
                    height:20px;
                    top:0px;
                    left:0px;
                    background:#0C6206;
                    text-align:center;
                    color:#FFFFFF;"
           >
              <span id="slideButton" style="cursor: pointer;" onclick="slide('examplePanel4', this); showhide('examplePanel4')"><a style="color:white;text-decoration:none" href="performedSearches.jsp" id="slideLink" target="performedSearches">+</a></span>
           </div>
           <div id="examplePanel4"
                    style="position:absolute;
                    width:150px;
                    height:0px;
                    top:0px;
                    left:0px;
                    background:#31A208;
                    overflow:hidden;
                    display:none;filter: alpha(opacity=90);
	                opacity: 0.9;
	                -moz-opacity: 0.9;
	                z-index: 1;">
              <iframe src="performedSearches.jsp"  frameborder="0" height="260" width="870" id="performedSearches" name="performedSearches">
                        </iframe>
           <div style="cursor:pointer; text-align:left;" onclick="slide('examplePanel4', 'slideLink'); showhide('examplePanel4')">close</div>
           </div>
        </div>
</center>


<%
    } else {
%>
    <div style="display:block" id="bar">
        <p id="bar-contents">
            <span style="cursor: pointer; text-decoration:underline" onclick="slide('examplePanel4', 'slideLink');"><a style="color:white;text-decoration:underline" href="performedSearches.jsp" target="performedSearches">View performed searches.</a></span> Proteins in user compilation: <span id="userCompilation"><%

            String text = "";
            if(compilationProteins.length == 0){
                text = " no proteins found in  compilation!";
            } else {
                text = compilationProteins[0] + "<img src=\"images/del.jpg\" align=\"top\" style=\"cursor:pointer;\" onclick=\"deleteProtein('" + compilationProteins[0] +"')\">";
                for(int p = 1; p< compilationProteins.length; p ++){
                    text = text + ", " + compilationProteins[p] + "<img src=\"images/del.jpg\" align=\"top\" style=\"cursor:pointer;\" onclick=\"deleteProtein('" + compilationProteins[p] +"')\">";
                }
            }


            %><%=text%></span>
        </p>
        <div style="position:fixed;
                    width:100%;
                    height:0px;
                    bottom:25px;
                    left:10px;">
           <div id="exampleHeader4"
              style="z-index:1;
                    position:absolute;
                    width:20px;
                    height:20px;
                    top:0px;
                    left:0px;
                    background:#0C6206;
                    text-align:center;
                    color:#FFFFFF;"
           >
              <span style="cursor: pointer;" onclick="slide('examplePanel4', 'slideLink');"><a style="color:white;text-decoration:none" id="slideLink" href="performedSearches.jsp"  target="performedSearches">+</a></span>
           </div>
           <div id="examplePanel4"
              style="position:absolute;
                    width:150px;
                    height:0px;
                    top:0px;
                    left:0px;
                    background:#31A208;
                    overflow:hidden;">
              <iframe src="performedSearches.jsp"  frameborder="0" height="260" width="870" id="performedSearches" name="performedSearches">
                        </iframe></div>
        </div>
    </div>

<%
    }
%>


<br>
<br>
<br>
</body>
</html>