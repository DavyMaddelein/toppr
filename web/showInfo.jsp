<%@ page import="be.proteomics.pprIA.general.InformationBox" %>
<%@ page import="java.util.Vector" %>
<html>
<head>
    <title>TOPPR information</title>
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
<div id="content">
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

    <%
        InformationBox[] infos = (InformationBox[]) session.getValue("info");
        for(int i =0; i<infos.length ;i++ ){
            InformationBox info = infos[i];
    %>
    <a name="<%=info.getAnchor()%>" id="<%=info.getAnchor()%>">&nbsp</a>
    <div class="startBox1024">&nbsp</div>
        <div class="midRBox1024">
            <div class="midLBox1024">
                <table width="95%">
                        <tr><th colspan="2" align="center"><%=info.getTitle()%></th></tr>
                    <%
                        Vector descriptions = info.getDecriptions();
                        Vector values = info.getValues();
                        Vector links = info.getLinks();
                        for(int j = 0; j<descriptions.size() ; j++){
                            if(links.get(j) == null){
                            %>
                            <tr><td width="60%"><%=descriptions.get(j)%></td><td width="40%"><%=values.get(j)%></td></tr>
                    <%
                            } else {
                    %>
                            <tr><td width="60%"><%=descriptions.get(j)%></td><td width="40%"><a href="<%=links.get(j)%>" target="_blank"><%=values.get(j)%></a></td></tr> 
                    <%
                            }
                        }
                    %>
                </table>
            </div>
        </div>
    <div class="endBox1024">&nbsp</div>
    <%
        }

    %>

</div>   
</body>
</html>