<?php

include 'connect.inc.php'; 

	$query = "SELECT * FROM users";
	
	$stmt = $db->query($query);
	
	$target_path = 'uploads/';

if (!empty($_POST)) {

	$response = array("error" => FALSE);
	
	function send_gcm_notify($reg_id, $title, $message, $img_url, $tag) {
	
		define("GOOGLE_API_KEY", "AIzaSyAziKt5IoZwhPla9GbJyufmllAFTdsd7uk");

		define("GOOGLE_GCM_URL", "https://fcm.googleapis.com/fcm/send");
	
        $fields = array(
            
			'to'  						=> $reg_id ,
			'priority'					=> "high",
            'notification'              => array( "title" => $title, "body" => $message, "tag" => $tag ),
			'data'						=> array("title" =>$title, "message" =>$message, "image"=> $img_url),
        );
		
        $headers = array(
			GOOGLE_GCM_URL,
			'Content-Type: application/json',
            'Authorization: key=' . GOOGLE_API_KEY 
        );
		
		echo "<br>";

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, GOOGLE_GCM_URL);
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
       // echo $result;
	   
	   $reg_id = $_POST['fcm_id'];
$title = $_POST['title'];
    $msg = $_POST['msg'];
	$img_url = '';
	$tag = 'text';
	
		send_gcm_notify($reg_id, $title, $msg, $img_url, $tag);
	
        header("Location: ../push.php");
		
    }
	
	
}

       header("Location: ../push.php");
?>