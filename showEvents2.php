<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 


 $no=0;
						$query = "SELECT event.id as id,user.name as uname,email,event,date_start,time_event,amount from user inner join event on user.id=event.user_id_e where event.data_status_e='current'";
                       $results = mysqli_query($con, $query);
                       while($row=mysqli_fetch_array($results)){
                       $no++;
                       
                       
                       
                       echo "<tr>";
                       echo "<td>".$no."</td>";
                       echo "<td>".$row['event']."</td>";
                       echo "<td>".$row['date_start']."</td>";
                       echo "<td>".$row['time_event']."</td>";
                       echo "<td>".$row['amount']."</td>";		
					   echo "<td>".$row['uname']."</td>";
                       echo "<td>".$row['email']."</td>";					   
                       echo "<td><Button class='btn btn-secondary shadow' data-toggle='modal' onclick='showEvent2(".$row['id'].")' value=".$row['id'].">Edit</Button></td>";
                       echo "<td><Button class='btn btn-danger shadow' data-toggle='modal' onclick='deleteEvent2(".$row['id'].")' value=".$row['id'].">Delete</Button></td>";
                       echo "</tr>";
                       
                       
                       }
                       
                       
                                
                       ?>