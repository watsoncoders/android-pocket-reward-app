<?php 

include 'config/config.php'; 

// $connect->set_charset('utf8');
	
	$connect    = new mysqli($host, $user, $pass,$database) or die("Error : ".mysql_error());
	
?>