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
<table>
<%
    String list = (String) session.getValue("list");
    if(list.length() > 0){
        list = list.substring(1);
        String[] sites = list.split("\n");
        for(int i = 0; i<sites.length; i++){

%>
        <tr><td><tt><%=sites[i]%></tt></td></tr>
<%
        }
    } else {
%>
        <tr><td><tt>No peptides found</tt></td></tr>
<%
    }
%>

</table>

</body>
</html>