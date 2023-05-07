<?php
include('connection.php');



$email = $_POST["email"];
$password1 = $_POST["password"];

$password2 = md5($password1);

			
		$changepass = "UPDATE user set password='$password2' where email='$email'";

		if(mysqli_query($con,$changepass))
		{
		echo  "Password has been reset";
		}
		
		else
		{			
		echo "Failed";
		}

		

?>