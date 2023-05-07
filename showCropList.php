<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 

$crop=$_GET['crop'];
$date_start=$_GET['date_start'];
$end_date=$_GET['end_date'];
$variety=$_GET['variety'];
if($crop=="rice"){
	
    echo "<div class='input-group col-md-12'>";
    echo "<div class='row'>";
    echo "<div class='input-group col-md-8'>";
                                 echo "<div class='input-group-prepend'>";
                                     echo "<span class='input-group-text'>";
                                         echo "Crop";
                                     echo "</span>";
                                 echo "</div>";
        echo "<select class='form-control' id='crop2' >";
        	echo "<option selected='true' value='0' disabled='disabled'>--Select--</option>";                                  
            $res_id=0; 
            $email=$_SESSION['username'];
            $result="";
            $result2= mysqli_query($con,"select * from user where username='$email' or email='$email'");
            while($row2=mysqli_fetch_array($result2))
            	{
                	$res_id=$row2['id']; 
                }
            if($variety=="all"){        
            	$result = mysqli_query($con,"select * from crop where date_added between '$date_start' and '$end_date' and data_status='current' and user_id='$res_id' and crop='rice';");
        	}
        	else if($variety!="other"&&$variety!="all"){
        		 $result = mysqli_query($con,"select * from crop where date_added between '$date_start' and '$end_date' and data_status='current' and user_id='$res_id' and crop='rice' and variety='$variety';");
        	}
        	else if($variety=="other"){
        		$result = mysqli_query($con,"select * from crop where date_added between '$date_start' and '$end_date' and data_status='current' and user_id='$res_id' and crop='rice' and variety!='NSIC Rc9' and variety!='NSIC Rc14' and variety!='PSB Rc18' and variety!='PSB Rc68' and variety!='NSIC Rc194' and variety!='NSIC Rc222' and variety!='NSIC Rc300' and variety!='NSIC Rc160' and variety!='NSIC Rc238' and variety!='NSIC Rc216' and variety!='NSIC Rc402' and variety!='NSIC Rc354' and variety!='NSIC Rc218' and variety!='PSB Rc10';");
        	}
			while($row=mysqli_fetch_array($result))
             { 
            	echo "<option value='".$row['id']."'>".$row['crop_name']."</option>";        
             }  
             echo"</select>";
    echo "</div>";
    echo "<div class='col-md-1'>";
    echo "<button  class='btn btn-warning' onclick=generate_totalCropProd('rice')>Generate Pie Chart</button>";  
    echo "</div>";
    echo "</div>";
    echo "</div>";
     echo "</div>";
    echo "<br><br>";
    echo "<canvas id='PieChart1'></canvas>";
  
}
else{
    echo "<div class='input-group col-md-12'>";
    echo "<div class='row'>";
    echo "<div class='input-group col-md-8'>";
                                 echo "<div class='input-group-prepend'>";
                                     echo "<span class='input-group-text'>";
                                         echo "Crop";
                                     echo "</span>";
                                 echo "</div>";
    echo "<select class='form-control' id='crop3' >";
        	echo "<option selected='true' value='0' disabled='disabled'>--Select Crop--</option>";                                  
            $res_id=0; 
            $email=$_SESSION['username'];
            $result3="";
            $result4= mysqli_query($con,"select * from user where username='$email' or email='$email'");
            while($row4=mysqli_fetch_array($result4))
            	{
                	$res_id=$row4['id']; 
                }
            if($variety=="all"){        
            	$result3 = mysqli_query($con,"select * from crop where date_added between '$date_start' and '$end_date' and data_status='current' and user_id='$res_id' and crop='onion';");
        	}
        	else if($variety!="other"&&$variety!="all"){
        		 $result3 = mysqli_query($con,"select * from crop where date_added between '$date_start' and '$end_date' and data_status='current' and user_id='$res_id' and crop='onion' and variety='$variety';");
        	}
        	else if($variety=="other"){
        		$result3 = mysqli_query($con,"select * from crop where date_added between '$date_start' and '$end_date' and data_status='current' and user_id='$res_id' and crop='onion' and variety!='BGS 95' and variety!='Cal 120' and variety!='Cal 202' and variety!='Capri' and variety!='CX-12' and variety!='Grannex 429' and variety!='Hybrid Red Orient' and variety!='Liberty' and variety!='Red Creole' and variety!='Red Pinoy' and variety!='Rio Bravo' and variety!='Rio Hondo' and variety!='Rio Raji Red' and variety!='Rio Tinto' and variety!='Super Pinoy' and variety!='SuperX' and variety!='Texas Grano' and variety!='Yellow Grannex';");
        	}
			while($row3=mysqli_fetch_array($result3))
             { 
            	echo "<option value='".$row3['id']."'>".$row3['crop_name']."</option>";  

             }  
             echo"</select>";
    echo "</div>";
    echo "<div class='col-md-1'>";
    echo "<button  class='btn btn-warning' onclick=generate_totalCropProd('onion')>Generate Pie Chart</button>";  
    echo "</div>";
    echo "</div>";
    echo "</div>";
    echo "</div>";
    echo "<br><br>";
    echo "<canvas id='PieChart2'></canvas>";	
}	 

?>