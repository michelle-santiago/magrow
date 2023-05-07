<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 
$username=$_GET['username'];
$password1=$_GET['password1'];
$password2=$_GET['password2'];

$old_password=md5($password1);
$new_password=md5($password2);

$user_check_query1 = "SELECT * FROM user WHERE password='$old_password' and username='$username' or email='$username'";
		  $result1 = mysqli_query($con, $user_check_query1);
		if (mysqli_num_rows($result1) == 0) {	
		
			echo "failed";
		}
  else
  {
    $sql = "UPDATE user set password='$new_password' where username='$username' or email='$username'";

		if(mysqli_query($con,$sql)){
        echo "Password has been changed";
        }

  }

?>