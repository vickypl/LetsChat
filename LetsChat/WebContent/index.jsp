<%@ page errorPage="errorpage.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="image/ico.png" type="image/icon type">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
        integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
        integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
    <link rel="stylesheet" href="style.css">
    <title>Let's Talk</title>
</head>
<%
	
	HttpSession sess = request.getSession(false);
	if((sess!=null && sess.getAttribute("user")!=null)) {
		response.sendRedirect("userloggedin.jsp");
		return;
	}

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
        <a href="signup"><button type="button" class="btn btn-dark cl">SignUp</button></a>
        <a href="aboutus.jsp"><button type="button" class="btn btn-dark cl">AboutUs</button></a>
    </div>
    <div class="container">
        <div class="leftside">
            <div class="mainicon">
            <%if(msg!=null){ %>
            <center>
            <span style="color: lime; font-family: cursive;"><%=msg %></span>
            </center>
            <%} %>
                <p class="mainline">When you are enthusiastic about what you do, you feel this positive energy. Simple as that.</p>
            </div>
            <div class="regform">
                <form  action="login" method="post">
                    <div class="form-group">
                        <label for="exampleInputEmail1">Username or E-mail</label>
                        <input type="text" class="form-control" name="username" maxlength="40" placeholder="Username/E-mail" required="required">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Password</label>
                        <input type="password" class="form-control" name="password" placeholder="Password" required="required">
                    </div>
                    <div class="form-group form-check">
                        <a href="recover">ForgottenPassword?</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="checkbox" class="form-check-input" id="exampleCheck1">
                        <label class="form-check-label" for="exampleCheck1">Remember Me</label>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">Sign-In</button>
                </form>
            </div>
        </div>
        <div style="border-radius: 4px; text-align: center; height: auto; width: auto; opacity: 0.8; margin-top: 10px; background-color: black;">
            <p style="color: white; font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;">&copy; All Rights Reserved 2021</p>
            <p style="color: white; font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;">vicky542011@gmail.com</p>
        </div>
    </div>
</body>
</html>