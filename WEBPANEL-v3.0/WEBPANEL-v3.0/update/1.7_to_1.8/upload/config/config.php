<?php

//database configuration
	

// Also Edit In fcm/connect.inc.php 

	$host       = "localhost";
	$user       = "DATABASE_USER_NAME_HERE";
	$pass       = "PASSWORD_HERE";
	$database   = "DATABASE_NAME_HERE";

// Admin Details

$display_name  = "admin";
$image_url = "images/img.jpg";

//$image_url = "http://example.com/images/img.jpg";

$Google_Play_Link = "https://play.google.com/store/apps/details?id=com.droidoxy.pocket";
	

// modify below

$APP_NAME = "POCKET";

$APP_TITLE = "POCKET - Android Rewards App";

//edit to your domain example:yourdomain.com
$APP_HOST = "pocket.example.com"; 

//edit to your domain url
$Server_URL = "http://pocket.example.com";

$Company_URL = "http://example.com";


// SMTP Settings | For password recovery

//SMTP auth (Enable SMTP authentication)
$SMTP_AUTH = true;

//SMTP secure (Enable TLS encryption, `ssl` also accepted)
$SMTP_SECURE = 'tls';

//SMTP port (TCP port to connect to)
$SMTP_PORT = 587;

//SMTP from email to show on recipient Email
$SMTP_EMAIL = 'example@gmail.com';

//SMTP username to Authentiacte
$SMTP_USERNAME = 'example@gmail.com';

//SMTP password
$SMTP_PASSWORD = 'EMAIL_PASSWORD_HERE';


?>