<%@ page import="be.proteomics.pprIA.general.JmolLoad" %>
<html>
<head>
<title>Jmol</title>
    <script type="text/javascript" src="pdbFiles/Jmol.js"></script>
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
<%
  JmolLoad jmol = (JmolLoad) session.getValue("jmol");
%>
    <table align="center">
        <tr>
            <td>
                <script type="text/javascript">
                    <%=jmol.getJmolLoad()%>
                </script>
            </td>
         </tr>
         <tr>
            <td>
                <%=jmol.getJmolMenu()%>
            </td>
        </tr>
        <tr>
            <td>Select a different selected position:</td>
        </tr>
    </table>    
    <table align="center">
        <tr>
            <%
                if(jmol.getPosition() != -2){
            %>
            <td><A HREF="/toppr/Show3d?protein=<%=jmol.getProteinInt()%>&session=<%=jmol.getSession()%>&position=-2">P3</a></td>
            <%
            } else {
            %><td>P3</td>
            <%
                }
                if(jmol.getPosition() != -1){
            %><td><A HREF="/toppr/Show3d?protein=<%=jmol.getProteinInt()%>&session=<%=jmol.getSession()%>&position=-1">P2</a></td>
            <%
            } else {
            %><td>P2</td>
            <%
                }
                if(jmol.getPosition() != 0){
            %><td><A HREF="/toppr/Show3d?protein=<%=jmol.getProteinInt()%>&session=<%=jmol.getSession()%>&position=0">P1</a></td>
            <%
            } else {
            %><td>P1</td>
            <%
                }
                if(jmol.getPosition() != 1){
            %><td><A HREF="/toppr/Show3d?protein=<%=jmol.getProteinInt()%>&session=<%=jmol.getSession()%>&position=1">P'1</a></td>
            <%
            } else {
            %><td>P'1</td>
            <%
                }
                if(jmol.getPosition() != 2){
            %><td><A HREF="/toppr/Show3d?protein=<%=jmol.getProteinInt()%>&session=<%=jmol.getSession()%>&position=2">P'2</a></td>
            <%
            } else {
            %><td>P'2</td>
            <%
                }
                if(jmol.getPosition() != 3){
            %><td><A HREF="/toppr/Show3d?protein=<%=jmol.getProteinInt()%>&session=<%=jmol.getSession()%>&position=3">P'3</a></td>
            <%
            } else {
            %><td>P'3</td>
            <%
                }
            %></tr>
    </table>
</body>
</html>