<?php


include '../connect_database.php'; 

    if($_SERVER['REQUEST_METHOD']=='POST'){

       $username = $_POST['username'];
       $points= $_POST['points'];
       $type= $_POST['type'];
       $date= $_POST['date'];

$daily = "SELECT points FROM tracker WHERE (username ='$username' AND date=CURRENT_DATE() AND type = '$type')";


$dai_result = $connect->query($daily);
$se = $dai_result->fetch_assoc();
//echo $se["points"];

//$y = "20".date('y-m-d');
//echo $y;


if($se["points"] == $points){

echo '0';

}else{


$sql = "SELECT points FROM users WHERE login = '$username'";
$result = $connect->query($sql);
$row = $result->fetch_assoc();
//echo $row["points"];

$prev_points = $row["points"];
$now_points = $points;
$total = $prev_points + $now_points;


$sqlmain = "UPDATE users SET points = '$total' WHERE login = '$username'";


  $sqlaward = "insert into tracker(username,points,type,date) values ('$username','$points','$type',CURRENT_DATE())";


$result = $connect->query($sqlmain);
$result2 = $connect->query($sqlaward);

if($result == 1 && $result2 == 1){
 
echo "1";

}else{

echo "2";
}

/**
echo $username;
echo $points;
echo $type;
echo $date;
  **/


}
 }else{
        echo '404 - Not Found <br/>';
        echo 'the Requested URL is not found on this server ';
    }
?>