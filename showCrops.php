<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include("connection.php");
$no=0; 



              $query = "SELECT crop.id,name,email,crop_name,crop,variety,season from user inner join crop on user.id=crop.user_id where crop.data_status='current' ";
              $results = mysqli_query($con, $query);
              while($row=mysqli_fetch_array($results)){
              $no++;
              
              
              
              echo "<tr>";
              echo "<td>".$no."</td>";
              echo "<td>".$row['crop']."</td>";
              echo "<td>".$row['variety']."</td>";
              echo "<td>".$row['crop_name']."</td>";
              echo "<td>".$row['season']."</td>";
              echo "<td>".$row['name']."</td>";
              echo "<td>".$row['email']."</td>";
              echo "<td><Button class='btn btn-secondary shadow' data-toggle='modal' onclick='showCrop(".$row['id'].")' value=".$row['id'].">Edit</Button></td>";
              echo "<td><Button class='btn btn-danger shadow' data-toggle='modal' onclick='deleteCrop(".$row['id'].")' value=".$row['id'].">Delete</Button></td>";
              echo "</tr>";
              
              }


         
?>
