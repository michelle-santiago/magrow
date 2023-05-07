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
       $query = "SELECT * from farm where user_id='$res_id' and data_status='current' LIMIT $offset, $no_of_records_per_page";
       $results = mysqli_query($con, $query);
       while($row=mysqli_fetch_array($results)){
       $no++;
       
       
       
       echo "<tr>";
       echo "<td>".$no."</td>";
       echo "<td>".$row['name']."</td>";
       echo "<td>".$row['location']."</td>";
       echo "<td>".$row['land_area']."</td>";
       echo "<td>".$row['la_measurement']."</td>";
       echo "<td><Button class='btn btn-secondary shadow' data-toggle='modal' onclick='showFarm2(".$row['id'].",".$offset.",".$no_of_records_per_page.")' value=".$row['id'].">Edit</Button></td>";
       echo "<td><Button class='btn btn-danger shadow' data-toggle='modal' onclick='deleteFarm2(".$row['id'].",".$offset.",".$no_of_records_per_page.")' value=".$row['id'].">Delete</Button></td>";
       echo "</tr>";
       
                       
                       }
                       
                       
                                
                       ?>
 