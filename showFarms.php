<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include("connection.php");
$no=0; 



                       $query = "SELECT farm.id,user.name as uname,email,farm.name as fname,location,land_area,la_measurement from user inner join farm on user.id=farm.user_id where farm.data_status='current' ";
                       $results = mysqli_query($con, $query);
                       while($row=mysqli_fetch_array($results)){
                       $no++;
                       
                       
                       
                         
                       echo "<tr>";
                       echo "<td>".$no."</td>";
                       echo "<td>".$row['fname']."</td>";
                       echo "<td>".$row['location']."</td>";
                       echo "<td>".$row['land_area']." (".$row['la_measurement'].")</td>";
                       echo "<td>".$row['uname']."</td>";
                       echo "<td>".$row['email']."</td>";
                       echo "<td><Button class='btn btn-secondary shadow' data-toggle='modal' onclick=showFarm(".$row['id'].")>Edit</Button></td>";
                       echo "<td><Button class='btn btn-danger shadow' data-toggle='modal' onclick=deleteFarm(".$row['id'].")>Delete</Button></td>";
                       echo "</tr>";
                       
                       }
                       
                       
                                
                       ?>
 