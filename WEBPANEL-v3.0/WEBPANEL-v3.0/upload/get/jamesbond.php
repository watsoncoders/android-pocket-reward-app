<?php


include '../connect_database.php'; 

    if($_SERVER['REQUEST_METHOD']=='POST'){

       $username = $_POST['username'];
       $fullname = $_POST['fullname'];
       $referer= $_POST['referer'];
       $points= $_POST['points'];
       $type= $_POST['type'];
       $date= $_POST['date'];
       $james_ip = $_SERVER['REMOTE_ADDR'];
        

	$bond= "SELECT points FROM referers WHERE (username ='$username' AND type = '$type')";

	$bond_result = $connect->query($bond);
	$se = $bond_result->fetch_assoc();
	
// checking if points have or not
	if($se["points"] == $points){

		echo '0';
		
// granting points
	}else{


	$sqlchk = "Select login,ip_addr from users WHERE refer = '$referer'";

	$dai_result = $connect->query($sqlchk);
	$se = $dai_result->fetch_assoc();
	
	$se_refer_username = $se["login"];
	$se_refer_ip = $se["ip_addr"];

// checking refer code validation
	if(!empty($se_refer_username) && $se_refer_username != ''){

// checking for self refer

		if($james_ip == $se_refer_ip){
		
		// self refer
		echo "self";
		
		}else{
		
			$sql = "SELECT points,ip_addr FROM users WHERE login = '$username'";
			$result = $connect->query($sql);
			$row = $result->fetch_assoc();
			//echo $row["points"];
			
			if($se_refer_ip == $row["ip_addr"]){
				// self refer again
				echo "self";
			
			}else{
			
			$prev_points = $row["points"];
			$now_points = $points;
			$total = $prev_points + $now_points;
		
			$sqll = "SELECT points FROM users WHERE refer = '$referer'";
			$result2 = $connect->query($sqll);
			$row2 = $result2->fetch_assoc();
			
			//echo $row["points"];
		
			$prev_points2 = $row2["points"];
			$now_points2 = $points;
			$total2 = $prev_points2 + $now_points2;
		
			$from = "Referal Income From : ".$fullname;
			$joined = "Invite Rewards from ".$se_refer_username." "." by using code : ".$referer;
		
			$sqlmain = "UPDATE users SET points = '$total' WHERE login = '$username'";
		
			$sqlagain = "UPDATE users SET points = '$total2' WHERE login= '$se_refer_username'";
		
		  	$sqladduser = "insert into tracker(username,points,type,date) values ('$username','$points','$joined',CURRENT_DATE())";
		  
		  	$sqladdrefer = "insert into tracker(username,points,type,date) values ('$se_refer_username','$points','$from',CURRENT_DATE())";
		  
		  	$sqlreferer = "insert into referers(username,referer,points,type,date) values ('$username','$referer','$points','$type',CURRENT_DATE())";
		
		
			$result = $connect->query($sqlmain);
			$result2 = $connect->query($sqlagain);
			$result3 = $connect->query($sqladduser);
			$result4 = $connect->query($sqladdrefer);
			$result5 = $connect->query($sqlreferer);
		
				if($result == 1 && $result2 == 1 && $result3 == 1 && $result4 == 1 && $result5 == 1){
		 
					echo "1";
			
				}else{
		
					echo "2";
				}
			}
		
		//end self refer
		}
		

	}else{
// invalid refer code

		echo "3";
	
	}
// end granting points
	}
	
	
// if not posted anything
 }else{
        echo '404 - Not Found <br/>';
        echo 'the Requested URL is not found on this server ';
    }
?>