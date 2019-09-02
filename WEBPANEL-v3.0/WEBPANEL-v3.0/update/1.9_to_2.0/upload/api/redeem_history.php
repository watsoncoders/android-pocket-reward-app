<?php

include '../config/config.php'; 


    if( isset($_REQUEST['username']) ){

       $username = $_REQUEST['username'];
	       
	//$username = "yashDev";
	
	$con=mysqli_connect($host,$user,$pass,$database);
	
	  // Check connection
	  if (mysqli_connect_errno())
	  {
	   echo "Failed to connect to MySQL: " . mysqli_connect_error();
	  }
	
	  $query = "SELECT date,gift_name,req_amount,points_used,status FROM Requests WHERE username = '$username' ORDER BY rid DESC";
	  
	   $query2 = "SELECT date,gift_name,req_amount,points_used,status FROM Completed WHERE username = '$username' ORDER BY rid DESC";
	 
	   
	  $result = mysqli_query($con,$query);
	
	  $result2 = mysqli_query($con,$query2);
	
	  $rows = array();
	  while($r = mysqli_fetch_array($result)) {
	    $rows[] = $r;
	  }
	  
	   $rows2 = array();
	  while($r2 = mysqli_fetch_array($result2)) {
	    $rows2[] = $r2;
	  }
	  
	  $total = array_merge($rows,$rows2);
	  
	  
			 header( 'Content-Type: application/json; charset=utf-8' );
	
	     echo $val= str_replace('\\/', '/', json_encode($total,JSON_UNESCAPED_UNICODE));
	
	  mysqli_close($con);
	  
	}else{
	
        echo '404 - Not Found <br/>';
        echo 'the Requested URL is not found on this server ';
        
    	}
?>