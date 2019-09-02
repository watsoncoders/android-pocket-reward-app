<?php


include '../connect_database.php'; 

    if($_SERVER['REQUEST_METHOD']=='POST'){

       $username = $_POST['name'];
       $fcm_id = $_POST['fcm_id'];
        

$sqltok = "UPDATE users SET gcm_regid = '$fcm_id' WHERE login = '$username'";


$result = $connect->query($sqltok);

if($result == 1 ){

echo "Fcm updated :". $fcm_id;

}else{

echo "Server Error! Please Try Again Later";
}

/**
echo $username;
echo $fcm_id;
  **/

 }else{
        echo '404 - Not Found <br/>';
        echo 'the Requested URL is not found on this server ';
    }
?>