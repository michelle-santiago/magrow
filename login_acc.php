<?php
include('connection.php'); 
session_start();
$error=0;

$username=$_GET['username'];
$password_=$_GET['password'];
$password=md5($password_);

$user_check_query1 = "SELECT id,username,name,email FROM user WHERE username='$username' or email ='$username'";
$result1 = mysqli_query($con, $user_check_query1);
if (mysqli_num_rows($result1) != 1) {
  echo "Account does not exists!";
  $error=1;
}
if ($error !=1) {
  $query = "SELECT id,username,name,email FROM user WHERE username='$username'  AND password='$password' or email ='$username' AND password='$password'";
    $results = mysqli_query($con, $query);
    while($row=mysqli_fetch_array($results)){

    $id=$row['id'];
	 $name=$row['name'];
	
    }
    if (mysqli_num_rows($results) == 1) {
      $_SESSION['username'] = $username;
      $_SESSION['id'] = $id;
  	  $_SESSION['name'] = $name;
	
    }
    else {
      echo "Incorrect Password";
    }

}

?>