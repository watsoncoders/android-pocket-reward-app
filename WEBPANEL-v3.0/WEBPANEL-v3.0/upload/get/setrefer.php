<?php


include '../connect_database.php'; 

     if($_SERVER['REQUEST_METHOD']=='POST'){

       $username = $_POST['name'];

function generateRandomString($length = 6) {
    return substr(str_shuffle(str_repeat($x='0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ', ceil($length/strlen($x)) )),1,$length);
}
        

$referal_id = generateRandomString();

$sqlchk = "Select refer from users WHERE login = '$username'";


$dai_result = $connect->query($sqlchk);
$se = $dai_result->fetch_assoc();

// if has refer code print it
	if(!empty($se["refer"])){

		echo $se["refer"];
// if not have createing refer code
	}else{

	$sqltok = "UPDATE users SET refer = '$referal_id' WHERE login = '$username'";

	$result = $connect->query($sqltok);

		if($result == 1 ){

		echo $referal_id;

		}else{

		echo "2";
		}

	}

// if not posted anything
}else{
        echo '404 - Not Found <br/>';
        echo 'the Requested URL is not found on this server ';
    }
?>