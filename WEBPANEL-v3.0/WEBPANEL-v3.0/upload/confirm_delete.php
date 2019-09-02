<?php
//database connection
	

 include_once 'connect_database.php';

	$connect    = new mysqli($host, $user, $pass,$database) or die("Error : ".mysql_error());
		
		
			if(isset($_GET['id'])){
				$ID = $_GET['id'];
			}else{
				$ID = "";
			}

			// delete data from menu table
			$sql_query = "DELETE FROM Requests 
					WHERE rid = ?";
			
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
			if($delete_result){

				header("location: requests.php");

			}else{

				header("location: requests.php");
			}
		
		
	$connect->close();
	?>