<?php


include '../connect_database.php'; 

    if($_SERVER['REQUEST_METHOD']=='POST'){

       $username = $_POST['username'];
        
$sql = "SELECT points FROM users WHERE login = '$username'";
$result = $connect->query($sql);
$row = $result->fetch_assoc();
echo $row["points"];
  

 }else{
        echo '404 - Not Found <br/>';
        echo 'the Requested URL is not found on this server ';
    }
?>