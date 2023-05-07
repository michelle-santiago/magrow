<?php
include('connection.php'); 
session_start();
$error=0;

$username=$_GET['username'];
$email=$_GET['email'];
$name=$_GET['name'];
$password_=$_GET['password'];
$password=md5($password_);


$sql = "INSERT INTO user (username,email,name,password) VALUES ('$username','$email','$name','$password')";

			if (!mysqli_query($con,$sql))
							{
							echo "Account creation failed";
							}
							
						
							
				echo "User account successfully registered!";
				
	$query = "SELECT id,username,name,email FROM user WHERE username='$username' or email ='$username' AND password='$password'";
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
 mysqli_close($con);
	 

?>