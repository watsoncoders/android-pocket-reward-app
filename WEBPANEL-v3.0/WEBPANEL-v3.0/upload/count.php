<?php

 include_once 'connect_database.php';


//Total Requests count

$sql_req = "SELECT COUNT(*) as num FROM Requests";


$total_req = mysqli_query($connect, $sql_req);
	
$total_req = mysqli_fetch_array($total_req);
	
$all_req = $total_req['num'];

// echo $all_req;



//Total CompletedRecords count

$sql_com = "SELECT COUNT(*) as num FROM Completed";


$total_com = mysqli_query($connect, $sql_com);
	
$total_com = mysqli_fetch_array($total_com);
	
$all_com = $total_com['num'];

// echo $all_com;


//Total Users count

$sql_users = "SELECT COUNT(*) as num FROM users";


$total_users = mysqli_query($connect, $sql_users);
	
$total_users = mysqli_fetch_array($total_users);
	
$all_users = $total_users['num'];

// echo $all_users;


?>