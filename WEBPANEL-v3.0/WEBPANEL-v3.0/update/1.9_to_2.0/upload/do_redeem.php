<?php

 include_once 'connect_database.php';

if (!empty($_POST)) {

$response = array("error" => FALSE);

$username = $_POST['username'];
$ui = $_POST['user_input'];
$dn = $_POST['deviceName'];
$dm = $_POST['deviceMan'];
$gn = $_POST['gift_name'];
$am = $_POST['amount'];
$po = $_POST['points'];
$cd = $_POST['Current_Date'];

$status = 0;

    
$sqlcheck = "SELECT points FROM users WHERE login = '$username'";
$result = $connect->query($sqlcheck);
$row = $result->fetch_assoc();
//echo $row["points"];

$prev_points = $row["points"];

	if($prev_points >= $po){
	
		
		  $sql = "insert into Requests (request_from,dev_name,dev_man,gift_name,req_amount,points_used,date,username,status) values ('$ui','$dn','$dm','$gn','$am','$po','$cd','$username','$status')";
		  
		  if(mysqli_query($connect,$sql)){
		  
				$response["error"] = FALSE;
				$response["error_msg"] = "Redeemed Successfully!";
				echo json_encode($response);
				// echo "Redeemed Successfully!";
				
		}
		  else{
					$response["error"] = TRUE;
					$response["error_msg"] = "Something's Wrong.. Please Try Again or Update the App !";
					 echo json_encode($response);
					// echo "Something's Wrong.. Please Try Again or Update the App!";
		
		  }
	}
	else{
	
		$response["error"] = TRUE;
		$response["error_msg"] = "Something's Wrong.. Please Try Again or Update the App !";
		 echo json_encode($response);
	
	}
		
	
	

} else {


echo "404 - Not Found";

}

?>