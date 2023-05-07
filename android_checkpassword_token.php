<?php

include('connection.php');



$email = $_POST["email"];
$token = $_POST["token"];




		$user_check_query1 = "SELECT * FROM user WHERE email='$email' and password_token='$token'";
		  $result1 = mysqli_query($con, $user_check_query1);
		if (mysqli_num_rows($result1) == 0) {	
		
			echo "failed";
		}
		else{	

			echo "success";
		}
		
		
		 
		 
		

?>