<?php

include('connection.php');
	 
$user_name = $_POST["username"];
$password = $_POST["password"];

$error=0;

$mysql_qry = "select * from user where username like '$user_name' or email like '$user_name';";
$result = mysqli_query($con ,$mysql_qry);
/**/
if(mysqli_num_rows($result) > 0) 
{
$mysql_qry = "select * from user where username like '$user_name' and password like '$password' or email like '$user_name' and password like '$password';";
$result = mysqli_query($con ,$mysql_qry);	
    if(mysqli_num_rows($result) > 0) {
        while($obj = mysqli_fetch_object($result)) {
        $arr[] = $obj;
        }
        echo json_encode($arr);
    }
    else{
        echo "Incorrect Password!";
    }   
}

else 
{
    echo "Account does not exists!";
}
	
?>