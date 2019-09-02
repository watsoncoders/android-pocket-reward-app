<?php


include '../connect_database.php'; 

    if($_SERVER['REQUEST_METHOD']=='POST'){

       $username = $_POST['username'];
       $type= $_POST['type'];
        

	$bond= "SELECT points FROM referers WHERE (username ='$username' AND type = '$type')";

	$bond_result = $connect->query($bond);
	$se = $bond_result->fetch_assoc();
	//echo $se["points"];

// checking if points have or not
	if(!empty($se["points"])){

		echo '1';
		
//no points
	}else{
	
		echo "0";
	}
	
	
// if not posted anything
 }else{
        echo '404 - Not Found <br/>';
        echo 'the Requested URL is not found on this server ';
    }
?>