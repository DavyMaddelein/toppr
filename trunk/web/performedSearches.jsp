<%@ page import="be.proteomics.pprIA.search.PerformedSearches" %>
<HTML>
<HEAD><TITLE>Performed searches</TITLE>
    <link rel="stylesheet" type="text/css" href="pprStyle.css">
    <style type="text/css">
        body{
            background-color:#31A208
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
</HEAD>
<BODY>
<div id="content800">
    <%
        PerformedSearches searches = (PerformedSearches) session.getValue("searches");
        if(searches==null){
     %>
        <span>No searches were performed!</span>
     <% } else {
     %> <div class="startBox800Gr">&nbsp</div>
            <div class="midRBox800Gr">
                <div class="midLBox800Gr">
                    <TABLE width="95%">
                        <TR><TH colspan="4" align="center">Performed searches</TH></TR>
                        <TR><TD width="40%">Parameters</TD><td width="15%">Found proteins</td><td width="15%">Found sites</td><td width="15%">Found unique peptide sequences</td><td width="15%">View result as csv</td></TR>
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
        </a></span></TD><td width="15%"><%=searches.getProteinCount(i)%></td><td width="15%"><%=searches.getProcessingSiteCount(i)%></td><td width="15%"><%=searches.getUniquePeptideCount(i)%></td><td><A HREF="/toppr/ShowCsv?session=<%=i%>" TARGET="_blank"><img src="images/csv.png" alt="" id="csv" name="csv"></A>
                </td></TR>
    <%
            }
    %>
                 </TABLE>
            </div>
        </div>
    <div class="endBox800Gr">&nbsp</div>
    <%
        }
    %>
</div>
</BODY>
</HTML>