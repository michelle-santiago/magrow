<?php

include('connection.php');

$id=$_POST["id"];
$name = $_POST["name"];
$username = $_POST["username"];
$email = $_POST["email"];




$error=0;
$error2=0;
        

		$user_check_query1 = "SELECT * FROM user WHERE username='$username' and id!='$id'";
		  $result1 = mysqli_query($con, $user_check_query1);
		if (mysqli_num_rows($result1) == 1) {
			$error=1;
		}

		$user_check_query2 = "SELECT * FROM user WHERE email='$email' and id!='$id'";
		  $result2 = mysqli_query($con, $user_check_query2);
		if (mysqli_num_rows($result2) == 1) {
			$error2=2;
		}		
		
	    if($error==1&&$error2==2)
		{
    		echo "Username and Email Already Exists!";
		}
		else if($error==1){
    		echo "Username Already Exists!";
    	}
    	else if($error2==2){
    	echo "Email Already Exists!";
    	}
    	
		else if($error==0&&$error2==0)
		{
			$sql = "update user set name='$name',username='$username',email='$email' WHERE id='$id'";

			if(mysqli_query($con,$sql))
			{	
			echo  "Successfully Updated";
			}
			else
			{	
			echo "Failed";
			}
		}
		
		

?>