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

   
$no=0;

 $query = "SELECT * from crop where user_id='$res_id' and data_status='current' ";
                       $results = mysqli_query($con, $query);
                       while($row=mysqli_fetch_array($results)){
                       $no++;
                       
                       
                       
                       echo "<tr>";
                       echo "<td>".$no."</td>";
                       echo "<td>".$row['crop']."</td>";
                       echo "<td>".$row['variety']."</td>";
                       echo "<td>".$row['crop_name']."</td>";
                       echo "<td>".$row['season']."</td>";
                       echo "<td><Button class='btn btn-secondary shadow' data-toggle='modal' onclick='showCrop2(".$row['id'].")' value=".$row['id'].">Edit</Button></td>";
                       echo "<td><Button class='btn btn-danger shadow' data-toggle='modal' onclick='deleteCrop2(".$row['id'].")' value=".$row['id'].">Delete</Button></td>";
                       echo "</tr>";
                       
                       }
                      
                      
                
                      
                      
                               
                      


         
?>
