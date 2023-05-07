<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 
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
                       $query = "SELECT event.id as id,crop_name,crop,variety,event,date_start,time_event,amount from crop,event where crop.id=event.crop_id and event.user_id_e='$res_id' and event.data_status_e='current' LIMIT $offset, $no_of_records_per_page";
                       $results = mysqli_query($con, $query);
                       while($row=mysqli_fetch_array($results)){
                       $no++;
                       
                       
                       
                       echo "<tr>";
                       echo "<td>".$no."</td>";
                       echo "<td>".$row['crop_name']."</td>";
                       echo "<td>".$row['crop']."</td>";
                       echo "<td>".$row['variety']."</td>";
                       echo "<td>".$row['event']."</td>";
                       echo "<td>".$row['date_start']."</td>";
                       echo "<td>".$row['time_event']."</td>";
                       echo "<td>".$row['amount']."</td>";			   
                       echo "<td><Button class='btn btn-secondary shadow' data-toggle='modal' onclick='showEvent(".$row['id'].",".$offset.",".$no_of_records_per_page.")' value=".$row['id'].">Edit</Button></td>";
                       echo "<td><Button class='btn btn-danger shadow' data-toggle='modal' onclick='deleteEvent(".$row['id'].",".$offset.",".$no_of_records_per_page.")' value=".$row['id'].">Delete</Button></td>";
                       echo "</tr>";
                       
                       
                       }
                       
                       
                                
                       ?>