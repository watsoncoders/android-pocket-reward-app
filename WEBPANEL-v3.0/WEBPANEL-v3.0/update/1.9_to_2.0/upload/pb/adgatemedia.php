<?php

include '../connect_database.php'; 

/**

http://yoururl.com/pb/adgatemedia.php?offer_name={offer_name}&sub_id={s1}&currency={points}&payout={payout}

**/

$offer_name = $_REQUEST['offer_name'];
$subid = $_REQUEST['sub_id'];
$payout = $_REQUEST['payout'];
$virtual_currency =  $_REQUEST['currency'];

$type = "AdGateMedia offerwall Credit";

$whitelist = array("104.130.7.162", "52.42.57.125");

	if (in_array($_SERVER["REMOTE_ADDR"], $whitelist)){


		$sql = "SELECT points FROM users WHERE login = '$subid'";
		$result = $connect->query($sql);
		$row = $result->fetch_assoc();
		//echo $row["points"];

		$prev_points = $row["points"];

		$now_points = $virtual_currency;
		$total = $prev_points + $now_points;
				
				
		$sqlmain = "UPDATE users SET points = '$total' WHERE login = '$subid'";
				
				
		$sqlaward = "insert into tracker(username,points,type,date) values ('$subid','$virtual_currency','$type',CURRENT_DATE())";
				
				
		$result = $connect->query($sqlmain);
		$result2 = $connect->query($sqlaward);
				
		if($result == 1 && $result2 == 1){
				 
			echo "updated";
				
		}else{
				
			echo "Not Updated";
		}

	}	
        
        
?>