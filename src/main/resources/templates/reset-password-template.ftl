<html>
	<head>
	    <title>${subject}</title>
	
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
		
	</head>
	<body>
		
		From: <span>&lt;<a href="mailto:${fromMail}" target="_blank">${fromMail}</a>&gt;</span><br>
		Date: ${.now?string.medium} <br>
		Subject: ${subject} <br>
		To:  &lt;<a href="mailto:${toMail}" target="_blank">${toMail}</a>&gt;<br><br><br>
		
		A password reset has been requested for the username: ${userName}<br><br>
		
		If you did not make this request, you can safely ignore this email. A password reset request can be made by anyone,<br>
		and it does not indicate that your account is in any danger of being accessed by someone else.<br><br> 
		
		If you do actually want to reset your password, Please click <a href="${resetUrl}" target="_blank">here</a> <br><br>
		
		Thank you <br><br>
	
	</body>
</html>
