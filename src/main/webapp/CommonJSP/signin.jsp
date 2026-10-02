 
<%@page import="java.util.ResourceBundle"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

<!-- Favicon-->
<%
String host_string = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath() + "/";
%>
<title>CampusStack - <%=request.getServerName() %>></title>
<meta name="description"
      content="CampusStack - <%=request.getServerName() %>">
<!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css?family=Roboto:400,700&subset=latin,cyrillic-ext&display=swap" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons&display=swap" rel="stylesheet" type="text/css"> 
<link rel="icon" href="https://cdn.campusstack.in/favicon.ico" type="image/x-icon" />
<!-- Bootstrap Core Css -->
<link href="https://cdn.campusstack.in/assets/plugins/bootstrap/css/bootstrap.css" rel="stylesheet">
<!-- Waves Effect Css -->
<link href="https://cdn.campusstack.in/assets/plugins/node-waves/waves.css" rel="stylesheet" />
<!-- Social media icons Css -->
<link href="https://cdn.campusstack.in/assets/css/social.min.css" rel="stylesheet" />
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.6.3/css/all.min.css" rel="stylesheet" />
<link href="https://cdn.campusstack.in/assets/plugins/animate-css/animate.css" rel="stylesheet" />
<!-- Custom Css -->
<link href="https://cdn.campusstack.in/assets/css/style.min.css" rel="stylesheet">
<link href="https://cdn.campusstack.in/assets/css/materialize.css" rel="preload">
<link href="https://cdn.campusstack.in/assets/css/loginstyle.min.css" rel="stylesheet">
<!-- Jquery Core Js -->
<script src="https://cdn.campusstack.in/assets/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap Core Js -->
<script src="https://cdn.campusstack.in/assets/plugins/bootstrap/js/bootstrap.js"></script>
<!-- Waves Effect Plugin Js -->
<script src="https://cdn.campusstack.in/assets/plugins/node-waves/waves.js"></script>
<!-- Validation Plugin Js -->
<script src="https://cdn.campusstack.in/assets/plugins/jquery-validation/jquery.validate.js"></script>
<!-- Custom Js -->
<script src="https://cdn.campusstack.in/assets/js/admin.js"></script>
<script src="https://cdn.campusstack.in/assets/js/pages/CommonJSP/sign-in.js"></script>
<script src="https://cdn.campusstack.in/assets/js/pages/CommonJSP/notifications.js"></script>
<!-- Bootstrap Notify Plugin Js -->
<script src="https://cdn.campusstack.in/assets/plugins/bootstrap-notify/bootstrap-notify.js"></script>
<script>
	localStorage.setItem('logout-event', 'logout' + Math.random());
</script>
<script type="text/javascript">
	$(document).ready(function() {
		window.history.pushState(null, "", window.location.href);
		window.onpopstate = function() {
			window.history.pushState(null, "", window.location.href);
		};
	});
</script>



</head>

<body class="login-page" id="loginBody" <%=(host_string.contains("test")?"style='background-color:green'":"") %> >
	<div class="login-box" id="signinForm" style="display: none;">
		<div class="body">
			<form id="sign_in" action="../checkLogin" method="POST">
				<div class="logo" style="text-align: center;">
					<img id="orgLogo1" src="" style="width: 100%;">
				</div>
				<div class="input-group has-feedback">
					<div class="">
						<input type="text" class="form-control" name="uname" data-rule-alphanumeric="true" id="username" placeholder="Username" required autofocus> <i class="glyphicon glyphicon-user form-control-feedback"></i>
					</div>
				</div>
				<div class="input-group has-feedback">
					<div class="">
						<input type="password" class="form-control" name="pword" autocomplete="off" data-rule-varchar="true" id="password" placeholder="Password" required> <i class="glyphicon glyphicon-lock form-control-feedback"></i>
					</div>
				</div>
				<div class="msg_center">
					<button class="btn btn-block bg-blue waves-effect" type="button" id="loginbtn">
    <span class="btn-label">LOGIN</span>
    <i class="fa fa-spinner fa-spin" style="display:none;margin-left:5px;"></i>
</button>

				</div>
				<div class="msg_center">(or)</div>
				<div class="msg_center">
					<img src="https://cdn.campusstack.in/assets/images/gsignin.png" style="width: 100%; height: 40px;" onClick="window.location.href = '../gSignIn'; " />
				</div>
				<div class="row m-t-15 m-b--20">
					<div class="col-xs-6 "></div>
					<div class="col-xs-12 align-right">
						<a href="forgot-password.jsp">Forgot Password?</a>
					</div>
				</div>
			</form>
			
		</div>
	</div>
	<div class="login-box" id="welcome">
		<div class="body">
			<div class="logo" style="text-align: center;">
				<img id=orgLogo src="" style="width: 100%;">
			</div>
			<% if ((request.getParameter("logintype")!=null) && request.getParameter("logintype").equalsIgnoreCase("studentonly") ) ; else { %>
			<div class="msg_center">
				<a class="btn btn-block bg-blue waves-effect" style="border-radius:53px;" href="javascript:shownSignin()" role="button"><b style="color: white; font-size: 24px">Staff Login</b></a>
			</div>
			<% } %>
			<div class="msg_center">
				<br> <br>
			</div>
			<div class="msg_center">
				<a class="btn btn-block bg-blue waves-effect" style="border-radius:53px;" href="ssignin.jsp" role="button"><b style="color: white; font-size: 24px">Student Login</b></a>
			</div>
		</div>
	</div>
	
	<%
			if (request.getParameter("er_c") != null && request.getParameter("er_c").equalsIgnoreCase("0")) {
				out.print(" <script>  showNotification('alert-danger', 'Incorrect username / Password','bottom', 'center',  '', '',false,'glyphicon glyphicon-remove-circle');</script>");
			} else if (request.getParameter("er_c") != null && request.getParameter("er_c").equalsIgnoreCase("1")) {
				out.print(" <script>  showNotification('bg-deep-orange', 'The account is deactivated contact Administrator','bottom', 'center',  '', '',false,'glyphicon glyphicon-pushpin');</script>");
			}
	%>

		<ul id="socialMediaList"></ul>
	<script>
		function shownSignin() {
			$('#signinForm').show();
			$('#welcome').hide();
		}
	</script>
	
	<script>
$(function () {
	// Login button click
	$('#loginbtn').click(function() {
	    var btn = $(this);
	    var form = $('#sign_in');

	    if (!form.valid()) return; // check validation

	    btn.prop('disabled', true);
	    btn.find('.btn-label').text('Logging in...');
	    btn.find('.fa-spinner').show();
	    
	    // Small delay for browser repaint
	    setTimeout(function() {
	        form.submit();
	    }, 50);
	});

	// Enter key handler for all inputs in the form
	$('#sign_in input').on('keydown', function(e) {
	    if (e.key === 'Enter') {
	        e.preventDefault(); // prevent default form submit
	        $('#loginbtn').click(); // trigger same login logic
	    }
	});

});
</script>


	<script>
	$(function () {
	    $.ajax({
	        url: '../getSM',
	        type: 'GET',
	        dataType: 'json',
	        success: function (data) {
	            let html = '';

	            $.each(data, function (i, sm) {
	                html += '<li>'
	                      + ' <a target="_blank" href="' + sm.link + '"'
	                      + '    style="background:' + sm.color + ' !important;">'
	                      + '   <i class="fab ' + sm.icon + ' icon"'
	                      + '      style="color:white!important;"></i>'
	                      + ' </a>'
	                      + '</li>';
	            });

	            $('#socialMediaList').html(html);
	        },
	        error: function () {
	            console.warn('Social media load failed');
	        }
	    });
	});
	</script>
	
	<script>
	$(function () {
	    $.ajax({
	        url: '../getLogo',
	        type: 'GET',
	        dataType: 'json',
	        success: function (res) {
	
	            if (!res.status) return;
	
	            // background color
	            $('#loginBody').css('background-color', res.data.bgcolor);
	
	            // logo
	            $('#orgLogo1').attr('src', res.data.logo);
	            $('#orgLogo').attr('src', res.data.logo);
	        },
	        error: function () {
	            console.error('API failed');
	        }
	    });
	});
	</script>



</body>
</html>

 