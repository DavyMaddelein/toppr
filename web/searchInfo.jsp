<%@ page import="be.proteomics.pprIA.search.PerformedSearches" %>
<HTML>
<HEAD><TITLE>TOPPR home</TITLE>
    <link rel="stylesheet" type="text/css" href="pprStyle.css">
    <script language="JavaScript" type="text/javascript">
<!--
function SetImage(id, filename)
{
   var elem=document.getElementById(id);
   if(elem)
   {
      elem.src=filename;
   }
}
//-->
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
</HEAD>
<BODY>
<div id="content">
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
</div>
   
    <%
        PerformedSearches searches = (PerformedSearches) session.getValue("searches");
        if(searches==null){
     %>
        <span>No searches were performed!</span>
     <% } else {
     %>
        <div class="startBox1024">&nbsp</div>
            <div class="midRBox1024">
                <div class="midLBox1024">
                    <TABLE width="95%">
                        <TR><TH colspan="4" align="center">Performed searches</TH></TR>
                        <TR><TD width="40%">Parameters</TD><td width="20%">Found proteins</td><td width="20%">Found sites</td><td width="20%">Found unique peptide sequences</td></TR>
     <%
         for(int i = 0; i<searches.getSessionCount(); i++){
    %>
        <TR><TD width="40%"><span><a href="/toppr/SearchInfo?session=<%=i%>" target="_blank">
            <%
                String[] params = searches.getSearchParameters(i);
                for(int j = 0; j<params.length ; j ++){
            %>
            <%=params[j]%>&nbsp
            <%
                }
            %>
        </a></span></TD><td width="20%"><%=searches.getProteinCount(i)%></td><td width="20%"><%=searches.getProcessingSiteCount(i)%></td><td width="20%"><%=searches.getUniquePeptideCount(i)%></td></TR>
    <%
            }
    %>
                 </TABLE>
            </div>
        </div>
    <div class="endBox1024">&nbsp</div>
    <%
        }
    %>
</div>
</BODY>
</HTML>