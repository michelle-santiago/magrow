<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include("connection.php");
$no=0; 


$query = "SELECT * from user";
$results = mysqli_query($con, $query);
while($row=mysqli_fetch_array($results)){
$no++;


echo "<tr>";
echo "<td>".$no."</td>";
echo "<td>".$row['name']."</td>";
echo "<td>".$row['username']."</td>";
echo "<td>".$row['email']."</td>";
if($row['status']=="1")
{
    echo "<td>Enabled</td>";
}
else
{
    echo "<td style='color:red;'>Disabled</td>";
}
echo "<td><Button class='btn btn-secondary shadow' data-toggle='modal' onclick=showAccount(".$row['id'].")>Edit</Button></td>";
if($row['status']=="1")
{
    echo "<td><Button class='btn btn-warning shadow' value=".$row['id']." onclick=update_user_status(".$row['id'].",".$row['status'].")>Disable</Button></td>";
}
else
{
    echo "<td><Button class='btn btn-success shadow' value=".$row['id']." onclick=update_user_status(".$row['id'].",".$row['status'].")>Enable</Button></td>";
}
echo "</tr>";

}


         
?>
