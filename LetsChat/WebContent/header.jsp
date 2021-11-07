<%@ page errorPage="errorpage.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="image/ico.png" type="image/icon type">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="vicky542011@gmail.com" content="7828789845">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
    <!--  sweet alert cdns-->
    <link rel="stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@10.10.1/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10.10.1/dist/sweetalert2.all.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <!--  sweet alert -->
    <script type="text/javascript" src="js/myjs.js"></script>
    <link rel="stylesheet" href="style.css">
    <title>Let's Chat</title>
</head>
<body class="bdclass">
    <nav class="navbar">
        <nav class="navbar-brand">Let's Chat</nav>
    </nav>
    <div class="mainbtns">
        <a href="userloggedin.jsp"><button type="button" class="btn btn-dark cl">Home</button></a>
        <a href="getpeople"><button type="button" class="btn btn-dark cl">People</button></a>
        <a href="getgroupchats"><button type="button" id="mainchatbtn" class="btn btn-dark cl">Groups</button></a>
        <a href="getfriends"><button type="button" class="btn btn-dark cl">Friend's</button></a>
        <a href="logout"><button type="button" class="btn btn-dark cl">Logout</button></a>
        <a href="aboutus.jsp"><button type="button" class="btn btn-dark cl">AboutUs</button></a>
    </div>
    <div class="container">