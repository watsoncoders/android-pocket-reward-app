<?php
//database connection
	

 include_once 'connect_database.php';

	$connect    = new mysqli($host, $user, $pass,$database) or die("Error : ".mysql_error());
		
		
			if(isset($_GET['id'])){
				$ID = $_GET['id'];
			}else{
				$ID = "";
			}

			// adding data 
			$sql_query = "INSERT INTO Completed (rid,request_from,dev_name,dev_man,gift_name,req_amount,points_used,date,username) SELECT rid,request_from,dev_name,dev_man,gift_name,req_amount,points_used,date,username FROM Requests WHERE rid = ?";
			
$stmt = $connect->stmt_init();
			if($stmt->prepare($sql_query)) {	
				// Bind your variables to replace the ?s
				$stmt->bind_param('s', $ID);
				// Execute query
				$stmt->execute();
				// store result 
				$add_result = $stmt->store_result();
				$stmt->close();
			}
			
// delete data 
			$sql_query = "DELETE FROM Requests WHERE rid = ?";
			
$stmt = $connect->stmt_init();
			if($stmt->prepare($sql_query)) {	
				// Bind your variables to replace the ?s
				$stmt->bind_param('s', $ID);
				// Execute query
				$stmt->execute();
				// store result 
				$delete_result = $stmt->store_result();
				$stmt->close();
			}
			
			// if delete data success back to reservation page
			if($delete_result && $add_result){
		
				header("location: requests.php");

			}else{

				header("location: requests.php");
			}
		
		
	$connect->close();
	?>