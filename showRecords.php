<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include("connection.php");
session_start();

$res_id=0; 
$email=$_SESSION['username'];


 $result2= mysqli_query($con,"select * from user where username='$email' or email='$email'");
             while($row2=mysqli_fetch_array($result2))
       {
       $res_id=$row2['id']; 
       }

       $offset=$_GET['offset'];
       $no_of_records_per_page=$_GET['no_of_records_per_page'];

						$no=0;
                       $query = "SELECT record_log.id as id,crop_name,crop,variety,area_harvested,ah_measurement,area_planted,ap_measurement,no_of_cavans_harvested,weight_per_cavan from crop,record_log where crop.id=record_log.crop_id and record_log.user_id_r='$res_id' and record_log.data_status_r='current' LIMIT $offset, $no_of_records_per_page";
                       $results = mysqli_query($con, $query);
                       while($row=mysqli_fetch_array($results)){
                       $no++;
                       
                       
                       
                       echo "<tr>";
                       echo "<td>".$no."</td>";
                       echo "<td>".$row['crop_name']."</td>";
                       echo "<td>".$row['crop']."</td>";
                       echo "<td>".$row['variety']."</td>";
                       echo "<td>".$row['area_harvested']."</td>";
                       echo "<td>".$row['ah_measurement']."</td>";
                       echo "<td>".$row['area_planted']."</td>";
                       echo "<td>".$row['ap_measurement']."</td>";
                       echo "<td>".$row['no_of_cavans_harvested']."</td>";
                       echo "<td>".$row['weight_per_cavan']."</td>";				   
                       echo "<td><Button class='btn btn-secondary shadow' data-toggle='modal' onclick='showRecord(".$row['id'].",".$offset.",".$no_of_records_per_page.")' value=".$row['id'].">Edit</Button></td>";
                       echo "<td><Button class='btn btn-danger shadow' data-toggle='modal' onclick='deleteRecord(".$row['id'].",".$offset.",".$no_of_records_per_page.")' value=".$row['id'].">Delete</Button></td>";
                       echo "</tr>";
       
                       
                       }
                       
                       
                                
                       ?>
 