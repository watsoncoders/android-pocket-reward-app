<?php

include 'connect.inc.php'; 
							
if (!empty($_POST)) {

	$title = $_POST['title'];
    	$message = $_POST['msg'];
	$img_url = '';
	$tag = 'text';
	
	
	define("GOOGLE_API_KEY", "AIzaSyAv23NYMxosFEZdEF7kkWxs2Dv_FwdOGqo");
	
	
	
	function send_push($fcm_id, $title, $message, $img_url, $tag){
	
		$fields = array(
        	'to'		=> $fcm_id ,
		'priority'	=> "high",
            	'notification'	=> array( "title" => $title, "body" => $message, "tag" => $tag ),
		'data'		=> array("title" =>$title, "message" =>$message, "image"=> $img_url),
	        );
			
	        $headers = array('https://fcm.googleapis.com/fcm/send','Content-Type: application/json','Authorization: key='.GOOGLE_API_KEY);
	        
	        $ch = curl_init();
	        curl_setopt($ch, CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send');
	        curl_setopt($ch, CURLOPT_POST, true);
	        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
	        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
	        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
			
	        $result = curl_exec($ch);
	        if ($result === FALSE) {
	            die('Problem occurred: ' . curl_error($ch));
	        }
			
	        curl_close($ch);
	        //echo $result;
	
	}
	
	
	$query = "SELECT * FROM users";
	
	$stmt = $db->query($query);
	
	
	while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
								
		send_push($row['gcm_regid'], $title, $message, $img_url, $tag);
	
	
	}
	
	
	
	//echo $title ;
	
        
	   
	header("Location: ../push-all.php");

	
}else{

    //echo "not posted";
    header("Location: ../push-all.php");
}

header("Location: ../push-all.php");

?>