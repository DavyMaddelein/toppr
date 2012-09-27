<%@ page import="be.proteomics.pprIA.general.JalviewResult" %>
<%@ page import="java.util.Vector" %>
<html>
<head>
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
    Vector jalview = (Vector) session.getValue("jalview");
    if(jalview.size() == 0){
%>
    Problem loading jalview! Please select a treament.
<%
    } else {
%>
<applet code="jalview.bin.JalviewLite" width="140" height="35" archive="/toppr/jalviewApplet.jar">
    <%
        for(int i = 0; i<jalview.size(); i ++){
            JalviewResult res = (JalviewResult) jalview.get(i);
    %>
    <param name="sequence<%=i+1%>" value="<%=res.getName()%> <%=res.getSequence()%>">
    <%
        }
        }
    %>
</applet>
</body>
</html>