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
	
	  $query = "SELECT date, type, points FROM tracker WHERE username = '$username' ORDER BY id DESC";
	  
	  $result = mysqli_query($con,$query);
	
	  $rows = array();
	  while($r = mysqli_fetch_array($result)) {
	    $rows[] = $r;
	  }
			 header( 'Content-Type: application/json; charset=utf-8' );
	
	     echo $val= str_replace('\\/', '/', json_encode($rows,JSON_UNESCAPED_UNICODE));
	
	  mysqli_close($con);
	  
	}else{
	
        echo '404 - Not Found <br/>';
        echo 'the Requested URL is not found on this server ';
        
    	}
?>