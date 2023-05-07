<?php

include('connection.php');



$username = $_POST["username"];
$password1 = $_POST["password"];
$newpassword1 = $_POST["password2"];

$password = md5($password1);
$newpassword=md5($newpassword1);



$error=0;
$error2=0;


		$user_check_query1 = "SELECT * FROM user WHERE username='$username' and password='$password'";
		  $result1 = mysqli_query($con, $user_check_query1);
		if (mysqli_num_rows($result1) == 0) {	
		
			echo "Incorrect Password";
		}
		else
		{
			
		$changepass = "UPDATE user set password='$newpassword' where username='$username'";

		if(mysqli_query($con,$changepass))
		{
		echo  "Password has been changed";
		}
		
		else
		{			
		echo "Failed";
		}

		}

?>