<%@ page errorPage="errorpage.jsp" %>
<!DOCTYPE html>
<html lang="en">
<%
	String msg = null;
	if(request.getParameter("msg")!=null) {
		msg = request.getParameter("msg");
	}
%>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="Image/ico.png" type="image/icon type">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
        integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
        integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
    <!--  data table js jq css-->
    <script type="text/javascript" src="datatable/js/dataTables.responsive.min.js"></script>
    <script type="text/javascript" src="datatable/js/jquery-3.5.1.js"></script>
    <script type="text/javascript" src="datatable/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="js/extra.js"></script>
    <link rel="stylesheet" href="datatable/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="datatable/css/responsive.dataTables.min.css">
    <!--  data table js jq css-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="style.css">
    <title>Let's Talk</title>
</head>

<body class="bdclass">
    <nav class="navbar">
        <nav class="navbar-brand">Let's Chat</nav>
        <marquee behavior="alternet" scrollamount="50">Wel-Come</marquee>
    </nav>
    <div class="mainbtns">
        <a href="index.jsp"><button type="button" class="btn btn-dark cl">Back</button></a>
    </div>
    <div class="container">
        <div class="mainbox">
            <div class="recoverybox">
                <h1>Recover here</h1>
                <form action="recover" method="post" class="form-group">
                    <label for="email">E-mail</label>
                    <input type="email" placeholder="E-mail" maxlength="50" class="form-control" name="email" value="" required>
                    <small style="color: red;">Enter Registered Email.</small>
                    <%if(msg!=null){ %>
                    <h4 style="color: green;"><%= msg %></h4>
                    <%} %>
                    <button type="submit" class="btn btn-block btn-info">Send</button>
                </form>
            </div>
        </div>
    </div>
</body>

</html>