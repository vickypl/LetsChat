<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="image/ico.png" type="image/icon type">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
        integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
        integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
    <link rel="stylesheet" href="style.css">
    <title>Error</title>
</head>
<%
	String msg = null;
	if(request.getParameter("msg")!=null) {
		msg = request.getParameter("msg");
	}
%>
<body class="bdclass">
    <nav class="navbar">
        <nav class="navbar-brand">Let's Chat</nav>
    </nav>
    <div class="mainbtns">
        <a href="index.jsp"><button type="button" class="btn btn-dark cl">Home</button></a>
    </div>
    <div class="container">
        <div style="margin: 2% auto; width: 80%; text-align: center; height: 400px; background-color: black; opacity: 0.8;">
            <div class="mainicon">
	            <%if(msg!=null){ %>
	            <center>
	            <span style="color: lime; font-family: cursive;"><%=msg %></span>
	            </center>
            <%} %>
        	</div>
        		<span  style="color: white; font-size: 60px; font-family: cursive;">Something went Wrong...</span><br>
        		<span  style="color: white; font-size: 60px; font-family: cursive;">Try Again</span>
        </div>
        <div style="border-radius: 4px; text-align: center; height: auto; width: auto; opacity: 0.8; margin-top: 10px; background-color: black;">
            <p style="color: white; font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;">&copy; All Rights Reserved 2021</p>
            <p style="color: white; font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;">vicky542011@gmail.com</p>
        </div>
    </div>
</body>
</html>