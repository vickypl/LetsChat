<%@ page errorPage="errorpage.jsp" %>
<!DOCTYPE html>
<%
	HttpSession sess = request.getSession(false);
	if((sess!=null && sess.getAttribute("user")!=null)) {
		response.sendRedirect("userloggedin.jsp");
		return;
	}
%>
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

<body class="bdclass">
    <nav class="navbar">
        <nav class="navbar-brand">Let's Chat</nav>
    </nav>
    <div class="mainbtns">
        <a href="index.jsp"><button type="button" class="btn btn-dark cl">Back to Home</button></a>
    </div>
    <div class="container">
        <div class="leftside">
            <div class="mainicon">
            	<span style="color: red;">Sign-Up here.</span>
            </div>
            <div class="regform">
                <form action="signup" method="post" class="form-group">
                    <label>First Name</label>
                    <input type="text" name="fname" value="" class="form-control" maxlength="15" placeholder="First Name" required>
                    <label>Last Name</label>
                    <input type="text" name="lname" value="" class="form-control" maxlength="15" placeholder="Last Name" required>
                    <label>E-mail</label>
                    <input type="email" name="email" value="" class="form-control" maxlength="50" placeholder="E-mail" required>
                    <label>Username</label>
                    <input type="text" name="username" value="" onblur="isAvail(this.value)" class="form-control" minlength="8" maxlength="20" placeholder="Username" required>
                    <small id="loaddata"></small><br>
                    <label>Password</label>
                    <input type="password" id="pswd1" name="password" value="" class="form-control" maxlength="15" placeholder="Password" required>
                    <br>
                    <input type="password" id="pswd2" onblur="matchPassword()" name="cpassword" value="" class="form-control" maxlength="15" placeholder="Confirm Password" required>
                    <br>
                   <input type="checkbox" align="left"  required="required">
                    <label>I Accept the terms and conditions.</label>
                    <br>
                    <button class="btn btn-success btn-block" type="submit">SignUp</button>
                    <br>
                    <button class="form-control" type="reset">Refresh</button>
                </form>
            </div>
        </div>
        <div style="border-radius: 4px; text-align: center; height: auto; width: auto; opacity: 0.8; margin-top: 10px; background-color: black;">
            <p style="color: white; font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;">&copy; All Rights Reserved 2021</p>
            <p style="color: white; font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;">vicky542011@gmail.com</p>
        </div>
    </div>
    <script type="text/javascript">
    
    function matchPassword() {  
    	  var pw1 = document.getElementById("pswd1").value;  
    	  var pw2 = document.getElementById("pswd2").value;  
    	  if(pw1!=pw2) {
    	    alert("Password/Confirm Password is not same");  
    	  }
    	} 
    
    	function isAvail(usr) {
    		var xmlhttp = new XMLHttpRequest();
    		xmlhttp.onreadystatechange=function() {
    			document.getElementById("loaddata").innerHTML=xmlhttp.responseText;
    		}
    		xmlhttp.open("get", "usernameid?usr="+usr, true);
    		xmlhttp.send();
    	}
    </script>
</body>
</html>