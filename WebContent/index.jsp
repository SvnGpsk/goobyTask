<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>GoobyTask!</title>
<link rel="stylesheet" href="css/style.css">
</head>

<body>

	<div class="login-page">
		<div class="form">
			<form class="register-form" action="../de.bs14/register" method="POST">
				<input type="text" placeholder="Username" name="register-username" id="register-username"/> 
				<input type="password" placeholder="Passwort" name="register-password" id ="register-password"/> 
				<input type="text" placeholder="E-Mail-Addresse" name="register-email" id= "register-email"/>
				<button>Registrieren</button>
				<p class="message">
					Bereits registiert? <a href="#">Melde dich an</a>
				</p>
			</form>
			<form class="login-form" action="../de.bs14/login" method="POST">
				<input type="text" placeholder="Username" name="login-username" id="login-username"/> 
				<input type="password"	placeholder="Passwort" name="login-password" id="login-password"/>
				<button>Login</button>
				<p class="message">
					Nicht registriert? <a href="#">Lege dir einen Account an</a>
				</p>
			</form>
		</div>
	</div>
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

	<script src="js/index.js"></script>



</body>
</html>
