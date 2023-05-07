<?php

include('connection.php');



$email = $_POST["email"];

$error=0;
$error2=0;


		$user_check_query1 = "SELECT * FROM user WHERE email='$email'";
		  $result1 = mysqli_query($con, $user_check_query1);
		if (mysqli_num_rows($result1) == 0) {	
		
			echo "failed";
		}
		else{	
		
		
		
		$pin_code=rand(100000,999999);
		
		$sql = "UPDATE user set password_token='$pin_code' where email='$email'";

		if(mysqli_query($con,$sql)){
		echo "success";
		
		$to = $email;
         $subject = "Password Recovery";
         
         $message = "<h4>Password Recovery Key</h4>";
         $message .= "Good Day!<br><br>Your Agro recovery key is below. You requested this information be sent to you via the Forgot Password link inside the Agro app.<br><br><br><br>Your Recovery key: $pin_code";
         
         $headers = "MIME-Version: 1.0" . "\r\n";
		 $headers .= "Content-type:text/html;charset=UTF-8" . "\r\n";

         $retval = mail ($to,$subject,$message,$headers);
	
		}
		else{	
		echo "Failed to send";
		}
		
		 
		 
		}

?>