<%@ page session="true" import="be.proteomics.pprIA.general.SearchOptions" %>
<html>
    <head>
        <title>TOPPR search</title>
        <link rel="stylesheet" type="text/css" href="pprStyle.css">
        <script type="text/javascript" src="showhide.js"></script>
        <SCRIPT language="JavaScript" src="pprIA.js"></SCRIPT>
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
        SearchOptions option = (SearchOptions) session.getValue("options");
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
    </center>


    <div class="startBox1024">&nbsp</div>
    <div class="midRBox1024">
        <div class="midLBox1024">
            <span class="title">Parameter search</span>
            <form id="form1" name="form1" method="post" action="/toppr/ParameterSearch">
            <table width="95%">
                <tr>
                    <td width="50%">Select (one or) more <a href="/toppr/ShowTreatments" target="_blank">treatment(s)</a>:<br>
                    <i> this option will return all the proteins affected by this treatment </i>
                    </td>
                    <td width="50%"><SELECT NAME="treatment" MULTIPLE size="8" class="searchSelector">
                        <%
                            String[] treat = option.getTreatments();
                            for (int i = 0; i < treat.length; i++) {
                        %>
                        <OPTION VALUE="<%= treat[i] %>"><%= treat[i] %>
                        </OPTION>
                        <%

                            }

                        %>
                        </select>
                    </td>
                </tr>

            </table>

            <span id="advanced" style="cursor:pointer; display: inline; float: right;font-size:1.5em;color:red;" onclick="showhide('advancedBox'); showhide('advanced')">
               Advanced search?<img src="images/goto.png" align="middle">
            </span>

                <div id="advancedBox" style="display:none">
                    <table width="95%">
                        <tr>
                            <td width="50%">
                                Search type: <input type="radio" name="andOr" value=" AND" checked="checked" onclick="hideElement('span','posTreats'); changeImgSrc('andNotOr','images/and.jpg');">And  <input type="radio" name="andOr" value=" OR" onclick="hideElement('span','posTreats'); changeImgSrc('andNotOr','images/or.jpg');">Or <input type="radio" name="andOr" value=" AND NOT" onclick="showElement('span','posTreats'); changeImgSrc('andNotOr','images/not_and.jpg');">And not
                            </td>
                            <td width="50%">
                                <img src="images/and.jpg" name="andNotOr" id="andNotOr">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp
                            </td>
                            <td>
                                <span id="posTreats" style="display:none">
                                    Positive set: &nbsp 
                                    <SELECT NAME="positive" class="searchSelector">
                                        <%
                                            for (int i = 0; i < treat.length; i++) {
                                        %>
                                        <OPTION VALUE="<%= treat[i] %>"><%= treat[i] %>
                                        </OPTION>
                                        <%

                                            }

                                        %>
                                    </select>
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Same site search? <input type="radio" name="sameSite" checked="checked" value="no" onclick="changeImgSrc('sameSiteImg','images/sameSiteNormal.jpg')">No <input type="radio" name="sameSite" value="yes" onclick="changeImgSrc('sameSiteImg','images/sameSite.jpg')">Yes
                            </td>
                            <td>
                                 <img src="images/sameSiteNormal.jpg" name="sameSiteImg" id="sameSiteImg">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Search for specific amino acids or a combinations of amino acids before (pre-site motif) and after (post-site motif) the processed site. <span style="text-decoration:underline; color:blue; cursor: pointer;" onclick="showhide('motifAdvanced')">More info?</span>
                            </td>
                            <td>
                                Pre-site motif: <input type="text" name="pre"> Post-site motif: <input type="text" name="post">
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <span class="example" id="motifAdvanced" style="display:none">Different search strategies can be used. The easiest strategy is to search for a specific combination. <i>Example: Pre-site motif = DEVD.
                                    PPR will only find processed sites where the non-prime amino acids are DEVD. Pre-site motif = DEVD, post-site motif = G. PPR will only find processed sites where the non-prime amino acids are DEVD and the prime amino acid is G.</i>
                                    An other method to find specific processed sites is to use the character *. Any amino acid can occur at the position of *. <i>Example: Pre-site motif = D**D. PPR will find processed site werhe the non-prime amino acids are DEVD, DSED, DGAD, ... . </i>
                                    There is an other way to find processed site where on one position two different amino acids can occur. Search for [AA 1/AA 2]. <i>Example: pre-site motif = DE[V/I]D. PPR will only find processed site where the non-prime amino acids are DEVD or DEID.</i>
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Select one (or more) <a href="/toppr/ShowExperiments" target="_blank">experimental type(s)</a>:
                            </td>
                            <td>
                                <SELECT NAME="experiment" MULTIPLE size="4" class="searchSelector">
                                    <%
                                        String[] exp = option.getExperiments();
                                        for (int i = 0; i < exp.length; i++) {
                                    %>
                                    <OPTION VALUE="<%= exp[i] %>"><%= exp[i] %>
                                    </OPTION>
                                    <%

                                        }

                                    %>
                                </SELECT>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Select one (or more) <a href="/toppr/ShowCellSources" target="_blank">cell source(s)</a>:
                            </td>
                            <td>
                                <SELECT NAME="cellsource" MULTIPLE size="4" class="searchSelector">
                                    <%
                                        String[] cs = option.getCellSources();
                                        for (int i = 0; i < cs.length; i++) {
                                    %>
                                    <OPTION VALUE="<%= cs[i]%>"><%= cs[i] %>
                                    </OPTION>
                                    <%

                                        }

                                    %>
                                </SELECT>
                            </td>
                        </tr>
                        <tr>
                            <td>
                               Select one (or more) <a href="/toppr/ShowTaxonomys" target="_blank">taxonomy(/ies)</a>:
                            </td>
                            <td>
                                <SELECT NAME="taxonomy" MULTIPLE size="4" class="searchSelector">
                                    <%
                                        String[] tax = option.getTaxonomys();
                                        for (int i = 0; i < tax.length; i++) {
                                    %>
                                    <OPTION VALUE="<%= tax[i] %>"><%= tax[i] %>
                                    </OPTION>
                                    <%

                                        }

                                    %>
                                </SELECT>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                 Select one (or more) <a href="/toppr/ShowInhibitors" target="_blank">inhibitor(s)</a>:
                            </td>
                            <td>
                                 <SELECT NAME="inhibitor" MULTIPLE size="4" class="searchSelector">
                                    <%
                                        String[] inhib = option.getInhibitors();
                                        for (int i = 0; i < inhib.length; i++) {
                                    %>
                                    <OPTION VALUE="<%= inhib[i] %>"><%= inhib[i] %>
                                    </OPTION>
                                    <%
                                        }
                                    %>
                                </SELECT>
                            </td>
                        </tr>
                    </table>
                    </div>
                <div id="button">
                    <label><input type="submit" name="knop1" id="knop1" value="Search"/> </label>
                    <label><input type="reset" name="knop2" id="knop2" value="Deselected all"/> </label>
                </div>
            </form>
        </div>
    </div>
    <div class="lineBox1024" id="spLine">&nbsp</div>
    <div class="midRBox1024">
        <div class="midLBox1024">
            <span class="title">UniProtKB/Swiss-Prot search</span>
            <form id="form2" name="form2" action="/toppr/ProteinSearch" method="post">
            <table>
                <tr>
                    <td width="30%">
                          UniProtKB/Swiss-Prot search type:
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="radio" name="uniprotType" checked="checked" value="accession">UniProtKB/Swiss-Prot accession number search
                    </td>
                    <td>
                        <span class="example">Search for a specific UniProtKB/Swiss-Prot accession number. <i>Example: P60709. If PPR doesn't find a protein, it will use ncbi's <a href="http://www.ncbi.nlm.nih.gov/sites/entrez?db=homologene">HomoloGene</a> database to search for orthologues. Example: If you search for Q8CGP1 (H2B1K_MOUSE), PPR will find O60814 (H2B1K_HUMAN).</i></span>
                    </td>
                <tr>
                    <td>
                        <input type="radio" name="uniprotType" value="entry_name">UniProtKB/Swiss-Prot entry name search
                    </td>
                    <td>
                        <span class="example">Search for a specific UniProtKB/Swiss-Prot entry name. <i>Example: ACTB_HUMAN.</i></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="radio" name="uniprotType" value="description">UniProtKB/Swiss-Prot description search
                    </td>
                    <td>
                        <span class="example">Search for a protein description. <i>Example: "Vimentin" => TOPPR will find P08670 (Vimentin - Homo sapiens) and P20152 (Vimentin - Mus musculus).</i></span>
                    </td>
                </tr>
            </table>

                <input type="text" name="protein">
                <label><input type="submit" name="knop1" id="knop3" value="Search"/> </label>
            </form>
        </div>
    </div>
    <div class="lineBox1024" id="spLine">&nbsp</div>
    <div class="midRBox1024">
        <div class="midLBox1024">
            <span class="title">Motif search</span>
            <table>
            <tr>
                <td>
                    Search for specific amino acids or a combinations of amino acids before (pre-site motif) and after (post-site motif) the processed site. <span style="cursor: pointer; color:blue; text-decoration: underline" onclick="showhide('motif')">More info?</span>
                </td>
            </tr>
            <tr>
                <td>
                    <span class="example" id="motif" style="display:none">Different search strategies can be used. The easiest strategy is to search for a specific combination. <i>Example: Pre-site motif = DEVD.
                        PPR will only find processed sites where the non-prime amino acids are DEVD. Pre-site motif = DEVD, post-site motif = G. PPR will only find processed sites where the non-prime amino acids are DEVD and the prime amino acid is G.</i>
                        An other method to find specific processed sites is to use the character *. Any amino acid can occur at the position of *. <i>Example: Pre-site motif = D**D. PPR will find processed site werhe the non-prime amino acids are DEVD, DSED, DGAD, ... . </i>
                        There is an other way to find processed site where on one position two different amino acids can occur. Search for [AA 1/AA 2]. <i>Example: pre-site motif = DE[V/I]D. PPR will only find processed site where the non-prime amino acids are DEVD or DEID.</i>
                    </span>
                </td>
            </tr>

                <tr>
                    <td>
                        <form id="form3" name="form3" action="/toppr/SiteSearch" method="post">
                            Pre-site motif: <input type="text" name="pre"> Post-site motif: <input type="text" name="post">
                            <input type="submit" name="knop1" id="knop4" value="Search"/>
                        </form>
                    </td>
                </tr>

            </table>
        </div>
    </div>
    <div class="endBox1024">&nbsp</div>
</div>
</body>
</html>