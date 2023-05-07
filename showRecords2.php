<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include("connection.php");
$no=0; 

$query = "SELECT record_log.id as id,user.name as uname,email,area_harvested,ah_measurement,area_planted,ap_measurement,no_of_cavans_harvested,weight_per_cavan from user inner join record_log on user.id=record_log.user_id_r where record_log.data_status_r='current' ";
                       $results = mysqli_query($con, $query);
                       while($row=mysqli_fetch_array($results)){
                       $no++;
                       
                       
                       
                       echo "<tr>";
                       echo "<td>".$no."</td>";
                       echo "<td>".$row['area_harvested']." (".$row['ah_measurement'].")</td>";
                       echo "<td>".$row['area_planted']." (".$row['ap_measurement'].")</td>";
                       echo "<td>".$row['no_of_cavans_harvested']."</td>";
                       echo "<td>".$row['weight_per_cavan']."</td>";	
					             echo "<td>".$row['uname']."</td>";
                       echo "<td>".$row['email']."</td>";					   
                       echo "<td><Button class='btn btn-secondary shadow' data-toggle='modal' onclick='showRecord2(".$row['id'].")' value=".$row['id'].">Edit</Button></td>";
                       echo "<td><Button class='btn btn-danger shadow' data-toggle='modal' onclick='deleteRecord2(".$row['id'].")' value=".$row['id'].">Delete</Button></td>";
                       echo "</tr>";
                       
                       
                       }


?>