<?php
error_reporting(0);
  session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }

  if ($_GET['logout']==1) {
      session_destroy();
      unset($_SESSION['username']);
      header("location:login.php");
  }

  include('connection.php');
?>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Tables</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">
    <!-- Bootstrap CSS-->
    <link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.min.css">
    <!-- Table CSS-->
  <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.18/datatables.min.css"/>
    <!-- Font Awesome CSS-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    <!-- Google fonts - Popppins for copy-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,800">
    <!-- orion icons-->
    <link rel="stylesheet" href="css/orionicons.css">
    <!-- theme stylesheet-->
    <link rel="stylesheet" href="css/style.default.css" id="theme-stylesheet">
    <!-- Custom stylesheet - for your changes-->
    <link rel="stylesheet" href="css/custom.css">
    <!-- Favicon-->
    <link rel="shortcut icon" href="img/favicon.png?3">
    <!-- Tweaks for older IEs--><!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
  </head>
  <body>
    <!-- navbar-->
    <header class="header">
  <?php if($_SESSION['username']=="rell"||$_SESSION['username']=="rell.chui@gmail.com") {?>
      <nav class="navbar navbar-expand-lg px-4 py-2 bg-white shadow"><a href="#" class="sidebar-toggler text-gray-500 mr-4 mr-lg-5 lead"><i class="fas fa-align-left"></i></a><a href="index.php" class="navbar-brand font-weight-bold text-uppercase text-base" style="color:#01bea2">Admin Pane</a>
  <?php }?>
  
  <?php if($_SESSION['username']!="rell"&& $_SESSION['username']!="rell.chui@gmail.com") {?>
      <nav class="navbar navbar-expand-lg px-4 py-2 bg-white shadow"><a href="#" class="sidebar-toggler text-gray-500 mr-4 mr-lg-5 lead"><i class="fas fa-align-left"></i></a><a href="index.php" class="navbar-brand font-weight-bold text-uppercase text-base" style="color:#01bea2">Farm Dashboard</a>
  <?php }?>

      <ul class="ml-auto d-flex align-items-center list-unstyled mb-0">
          <p><br><?php echo $_SESSION['name']; ?></p>
          <li class="nav-item dropdown ml-auto"><a id="userInfo" href="http://example.com" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link dropdown-toggle"><img src="img/avatar-6.png" alt="Jason Doe" style="max-width: 2.5rem;" class="img-fluid rounded-circle shadow"></a>
            <div aria-labelledby="userInfo" class="dropdown-menu">
                <a href="#" class="dropdown-item">
                   <?php $username=$_SESSION['username'];
                    $query = "SELECT * from user where username='$username' or email='$username'";
                    $results = mysqli_query($con, $query);
                    while($row=mysqli_fetch_array($results)){?>
                    <strong class="d-block text-uppercase headings-font-family"><?php echo $row['name'] ?>
                    </strong>
                    <small><?php echo $row['username'] ?></small><br>
                    <small><?php echo $row['email'] ?></small>
                    <?php }?>
                </a>
              <div class="dropdown-divider"></div><a href="#" class="dropdown-item" onclick="showProfile('<?php echo $_SESSION['username'];?>')">Profile</a>
              <div class="dropdown-divider"></div><a href="?logout=1"  class="dropdown-item">Logout</a>
            </div>
          </li>
        </ul>
      </nav>
    </header>
    <div class="d-flex align-items-stretch">
      <div id="sidebar" class="sidebar py-3">
        <div class="text-uppercase px-3 px-lg-4 py-4 font-weight-bold small headings-font-family" style="color:#01bea2">MAGROW</div>
        <ul class="sidebar-menu list-unstyled">
            <?php if($_SESSION['username']=="rell"||$_SESSION['username']=="rell.chui@gmail.com") {?>
      
    <li class="sidebar-list-item"><a href="index.php" class="sidebar-link text-muted"><i class="o-user-details-1 mr-3 text-gray"></i><span>Accounts</span></a></li>
              <li class="sidebar-list-item"><a href="charts.php" class="sidebar-link text-muted"><i class="o-stack-1 mr-3 text-gray"></i><span>Crops</span></a></li>
              <li class="sidebar-list-item"><a href="tables.php" class="sidebar-link text-muted active"><i class="o-earth-globe-1 mr-3 text-gray"></i><span>Farms</span></a></li>
              <li class="sidebar-list-item"><a href="forms.php" class="sidebar-link text-muted"><i class="o-archive-folder-1 mr-3 text-gray"></i><span>Records</span></a></li>
              <li class="sidebar-list-item"><a href="?logout=1" class="sidebar-link text-muted"><i class="o-exit-1 mr-3 text-gray"></i><span>Logout</span></a></li> 
    
  <?php }?>
  
  <?php if($_SESSION['username']!="rell"&& $_SESSION['username']!="rell.chui@gmail.com") {?>

<li class="sidebar-list-item"><a href="index.php" class="sidebar-link text-muted"><i class="o-home-1 mr-3 text-gray"></i><span>Home</span></a></li>
              <li class="sidebar-list-item"><a href="charts.php" class="sidebar-link text-muted"><i class="o-sales-up-1 mr-3 text-gray"></i><span>Charts</span></a></li>
              <li class="sidebar-list-item"><a href="tables.php" class="sidebar-link text-muted active"><i class="o-table-content-1 mr-3 text-gray"></i><span>Tables</span></a></li>
              <li class="sidebar-list-item"><a href="forms.php" class="sidebar-link text-muted"><i class="o-survey-1 mr-3 text-gray"></i><span>Reports</span></a></li>
              <li class="sidebar-list-item"><a href="?logout=1" class="sidebar-link text-muted"><i class="o-exit-1 mr-3 text-gray"></i><span>Logout</span></a></li> 

   <?php }?>
        </ul>
        
      </div>
      <div class="page-holder w-100 d-flex flex-wrap">
        <div class="container-fluid px-xl-5">
          <section class="py-5">
      

<?php if($_SESSION['username']=="rell"||$_SESSION['username']=="rell.chui@gmail.com") {?>
      
      <div class="row">
               <div class="col-lg-12">
                 <div class="card">
                   <div class="card-header">
                     <h6 class="text-uppercase mb-0" style="color:#01bea2">Farm</h6>
                   </div>
                   <div class="card-body">   
                  <table id="table_container" class="table table-striped table-hover card-text table-sm" cellspacing="0">
                       <thead>
                         <tr>
                           <th>#</th>
                           <th>Farm Name</th>
                           <th>Location</th>
                           <th>Land Area</th>
                           <th>Owned by</th>
                           <th>Email</th>
                           <th style=" background : none;">Action</th>
                           <th style=" background : none;"></th>                  
                         </tr>
                       </thead>
                       <tbody id="user_table">
                       <?php
                      $no=0;
                       $query = "SELECT farm.id as id,user.name as uname,email,farm.name as fname,location,land_area,la_measurement from user inner join farm on user.id=farm.user_id where farm.data_status='current'";
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
                       echo "<td><Button class='btn btn-secondary shadow' data-toggle='modal' onclick='showFarm(".$row['id'].")' value=".$row['id'].">Edit</Button></td>";
                       echo "<td><Button class='btn btn-danger shadow' data-toggle='modal' onclick='deleteFarm(".$row['id'].")' value=".$row['id'].">Delete</Button></td>";
                       echo "</tr>";
                      
                       
                       }
                       
                       
                                
                       ?>
                       
 
                       </tbody>
                     </table>
 
                   </div>
                 </div>
               </div>
             </div>
   <?php }?>
   
     <?php if($_SESSION['username']!="rell"&& $_SESSION['username']!="rell.chui@gmail.com") {?>
 
      <ul class="nav nav-tabs">
  <li class="nav-item">
  <a href="#aaa" class="nav-link active" data-toggle="tab">Crops</a>
  </li>
  <li class="nav-item">
  <a href="#bbb" class="nav-link" data-toggle="tab">Farm</a>
  </li>
  <li class="nav-item">
  <a href="#ccc" class="nav-link" data-toggle="tab">Records</a>
  </li>
  <li class="nav-item">
  <a href="#ddd" class="nav-link" data-toggle="tab">Events</a>
  </li>
</ul>

      
<div class="tab-content" id="tabs">

    <div class="tab-pane active" id="aaa">
    
    <div class="row">
               <div class="col-lg-12">
                 <div class="card">
                   <div class="card-header">
				   <?php
				       $res_id2=0; 
                       $email2=$_SESSION['username'];
                        $result2= mysqli_query($con,"select * from user where username='$email2' or email='$email2'");
                                    while($row4=mysqli_fetch_array($result2))
                              {
                              $res_id2=$row4['id']; 
                              }
				   ?>
                     <h6 class="text-uppercase mb-0" style="color:#01bea2;float:left;margin-top:10px;">Crops</h6><button class="btn btn-secondary" style="float:right;" onclick="exportTables(<?php echo $res_id2; ?>,'crops')">Export CSV</button>
                   </div>
				   
                   <div class="card-body">
                     <table id="table_container1" class="table table-striped table-hover card-text table-sm" cellspacing="0">
                       <thead>
                         <tr>
                           <th>#</th>
                           <th>Type</th>
                           <th>Variety</th>
                           <th>Crop Name</th>
                           <th>Season</th>  
                           <th>Date</th>    
                         </tr>
                       </thead>
                       <tbody id="user_table">
                       <?php

                       $res_id=0; 
                       $email=$_SESSION['username'];
                        $result2= mysqli_query($con,"select * from user where username='$email' or email='$email'");
                                    while($row2=mysqli_fetch_array($result2))
                              {
                              $res_id=$row2['id']; 
                              }

                       
                       $no=0;
                       $query = "SELECT * from crop where user_id='$res_id' and data_status='current'";
                       $results = mysqli_query($con, $query);
                       while($row=mysqli_fetch_array($results)){
                       $no++;
                       
                       
                       
                       echo "<tr>";
                       echo "<td>".$no."</td>";
                       echo "<td>".$row['crop']."</td>";
                       echo "<td>".$row['variety']."</td>";
                       echo "<td>".$row['crop_name']."</td>";
                       echo "<td>".$row['season']."</td>";
                       echo "<td>".$row['date_added']."</td>";

                       echo "</tr>";
                       
                       }
                       
                       
                                
                       ?>
                       
 
                       </tbody>
                     </table>
 
                   </div>
                 </div>
               </div>
             </div>
    
    </div>


    <div class="tab-pane" id="bbb">
    <div class="row">
               <div class="col-lg-12">
                 <div class="card">
                   <div class="card-header">
            <?php
				       $res_id2=0; 
                       $email2=$_SESSION['username'];
                        $result2= mysqli_query($con,"select * from user where username='$email2' or email='$email2'");
                                    while($row4=mysqli_fetch_array($result2))
                              {
                              $res_id2=$row4['id']; 
                              }
				   ?>
                     <h6 class="text-uppercase mb-0" style="color:#01bea2;float:left;margin-top:10px;">Farms</h6><button class="btn btn-secondary" style="float:right;" onclick="exportTables(<?php echo $res_id2; ?>,'farms')">Export CSV</button>
                   </div>
                   <div class="card-body">   
                    
                     <table id="table_container2" class="table table-striped table-hover card-text table-sm" cellspacing="0">
                       <thead>
                         <tr>
                           <th>#</th>
                           <th>Farm Name</th>
                           <th>Location</th>
                           <th>Land Area</th>
                           <th>Unit</th>
                            
                         </tr>
                       </thead>
                       <tbody id="user_table2">
                       <?php
                       $res_id=0; 
                       $email=$_SESSION['username'];
                        $result2= mysqli_query($con,"select * from user where username='$email' or email='$email'");
                                    while($row2=mysqli_fetch_array($result2))
                              {
                              $res_id=$row2['id']; 
                              }


                       $no=0;
                       $query = "SELECT * from farm where user_id='$res_id' and data_status='current'";
                       $results = mysqli_query($con, $query);
                       while($row=mysqli_fetch_array($results)){
                       $no++;
                       
                       
                       
                       echo "<tr>";
                       echo "<td>".$no."</td>";
                       echo "<td>".$row['name']."</td>";
                       echo "<td>".$row['location']."</td>";
                       echo "<td>".$row['land_area']."</td>";
                       echo "<td>".$row['la_measurement']."</td>";
                       
                       echo "</tr>";
                       
                       
                       }
                       
                       
                                
                       ?>
                       
 
                       </tbody>
                     </table>
 
                   </div>
                 </div>
               </div>
             </div>
    </div>

    <div class="tab-pane" id="ccc">
    
    <div class="row">
               <div class="col-lg-12">
                 <div class="card">
                   <div class="card-header">
                      <?php
				       $res_id2=0; 
                       $email2=$_SESSION['username'];
                        $result2= mysqli_query($con,"select * from user where username='$email2' or email='$email2'");
                                    while($row4=mysqli_fetch_array($result2))
                              {
                              $res_id2=$row4['id']; 
                              }
				   ?>
                     <h6 class="text-uppercase mb-0" style="color:#01bea2;float:left;margin-top:10px;">RECORDS</h6><button class="btn btn-secondary" style="float:right;" onclick="exportTables(<?php echo $res_id2; ?>,'records')">Export CSV</button>
                   </div>
                   <div class="card-body">   
                    
                     <table id="table_container3" class="table table-striped table-hover card-text table-sm" cellspacing="0">
                       <thead>
                         <tr>
                           <th>#</th>
                           <th>Crop Name</th>
                           <th>Type</th>
                           <th>Variety</th>
                           <th>Area Harvested</th>
                      
                           <th>Area Planted</th>
                          
                           <th>No. of Sacks</th>
                           <th>Total Weight</th>
                         </tr>
                       </thead>
                       <tbody id="user_table3">
                       <?php
                       $res_id=0; 
                       $email=$_SESSION['username'];
                        $result2= mysqli_query($con,"select * from user where username='$email' or email='$email'");
                                    while($row2=mysqli_fetch_array($result2))
                              {
                              $res_id=$row2['id']; 
                              }


                       $no=0;
                       $query = "SELECT record_log.id as id,crop_name,crop,variety,area_harvested,ah_measurement,area_planted,ap_measurement,no_of_cavans_harvested,weight_per_cavan from crop,record_log where crop.id=record_log.crop_id and record_log.user_id_r='$res_id' and record_log.data_status_r='current'";
                       $results = mysqli_query($con, $query);
                       while($row=mysqli_fetch_array($results)){
                       $no++;
                       
                       
                       
                       echo "<tr>";
                       echo "<td>".$no."</td>";
                       echo "<td>".$row['crop_name']."</td>";
                       echo "<td>".$row['crop']."</td>";
                       echo "<td>".$row['variety']."</td>";
                       echo "<td>".$row['area_harvested']." (".$row['ah_measurement'].")</td>";
                       echo "<td>".$row['area_planted']." (".$row['ap_measurement'].")</td>";
                       echo "<td>".$row['no_of_cavans_harvested']."</td>";
                       echo "<td>".$row['weight_per_cavan']." kg</td>";           
                       
                       echo "</tr>";
                       
                       
                       }
                       
                       
                                
                       ?>
 
                       </tbody>
                     </table>
                   </div>
                 </div>
               </div>
             </div>
    
    </div>
  
  
  <div class="tab-pane" id="ddd">
    
    <div class="row">
               <div class="col-lg-12">
                 <div class="card">
                   <div class="card-header">
                      <?php
				       $res_id2=0; 
                       $email2=$_SESSION['username'];
                        $result2= mysqli_query($con,"select * from user where username='$email2' or email='$email2'");
                                    while($row4=mysqli_fetch_array($result2))
                              {
                              $res_id2=$row4['id']; 
                              }
				   ?>
                     <h6 class="text-uppercase mb-0" style="color:#01bea2;float:left;margin-top:10px;">EVENTS</h6><button class="btn btn-secondary" style="float:right;" onclick="exportTables(<?php echo $res_id2; ?>,'events')">Export CSV</button>
                   </div>
                   <div class="card-body">   
                    
                     <table id="table_container4" class="table table-striped table-hover card-text table-sm" cellspacing="0">
                       <thead>
                         <tr>
                           <th>#</th>
                           <th>Crop Name</th>
                           <th>Type</th>
                           <th>Variety</th>
                           <th>Activity</th>
                           <th>Date</th>
                           <th>Time</th>
                           <th>Amount</th>
                                
                         </tr>
                       </thead>
                       <tbody id="user_table4">
                       <?php

                       $res_id=0; 
                       $email=$_SESSION['username'];
                        $result2= mysqli_query($con,"select * from user where username='$email' or email='$email'");
                                    while($row2=mysqli_fetch_array($result2))
                              {
                              $res_id=$row2['id']; 
                              }

                      $no=0;
                       $query = "SELECT event.id as id,crop_name,crop,variety,event,date_start,time_event,amount from crop,event where crop.id=event.crop_id and event.user_id_e='$res_id' and  event.data_status_e='current' ";
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
                       
                       echo "</tr>";
                       
                       
                       }
                       
                       
                                
                       ?>
 
                       </tbody>
                     </table>
 
                   </div>
                 </div>
               </div>
             </div>
    
    </div>
</div>
 
 













    <?php }?>






















          </section>
        </div>
        <footer class="footer bg-white shadow align-self-end py-3 px-xl-5 w-100">
          <div class="container-fluid">
            <div class="row">
              <div class="col-md-6 text-center text-md-left text-primary">
                <p class="mb-2 mb-md-0" style="color:#01bea2">MAGROW &copy; 2018-2020</p>
              </div>
              <div class="col-md-6 text-center text-md-right text-gray-400">
                <p class="mb-0">Design by <a href="https://bootstrapious.com/admin-templates" class="external text-gray-400">Bootstrapious</a></p>
                <!-- Please do not remove the backlink to us unless you support further theme's development at https://bootstrapious.com/donate. It is part of the license conditions. Thank you for understanding :)-->
              </div>
            </div>
          </div>
        </footer>
      </div>
    </div>





<!-- MODALS-->

<div id="editFarmModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header" style="background-color:#01bea2;">

        <h4 class="modal-title" style="color:#fff;">Edit </h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
<div id="modalContainerBody">
</div>
    </div>

  </div>
</div>

<!-- MODALS-->

<div id="editCropModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header" style="background-color:#01bea2;">

        <h4 class="modal-title" style="color:#fff;">Edit </h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
<div id="modalContainerBodyCrop">
</div>
    </div>

  </div>
</div>


    <!-- REPONSE MODAL-->
<div id="responseModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header" style="background-color:#01bea2;">

        <h4 class="modal-title" style="color:#fff;">  </h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
<div class="modal-body">
<div id="response"></div>
</div>

  <div class='modal-footer'>
  <button type='button' class="btn btn-secondary shadow" data-dismiss="modal">Close</button>
  </div>

    </div>

  </div>
</div>

<!-- MODAL DELETE-->
<div id="deleteModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header" style="background-color:#01bea2;">

        <h4 class="modal-title" style="color:#fff;">Delete</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
<div id="modalContainerBodyDelete">
</div>
    </div>

  </div>
</div>

<!-- MODALS-->

<div id="profileModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header" style="background-color:#01bea2;">

        <h4 class="modal-title" style="color:#fff;">Profile</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
<div id="modalContainerBodyProfile">
</div>
    </div>

  </div>
</div>


    <!-- CHANGE PASSWORD MODAL-->
    <div id="changePasswordModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header" style="background-color:#01bea2;">

        <h4 class="modal-title" style="color:#fff;">Change Password</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

<div class='modal-body'>
<form>
<div class='form-group'>
<label>Current Password</label>
<input type='text' class='form-control' id='password_edit1' placeholder='Enter current password'>
</div>

<div class='form-group'>
<label>New Password</label>
<input type='text' class='form-control' id='password_edit2' placeholder='Enter new password'>
</div>

<div class='form-group'>
<label>Confirm Password</label>
<input type='text' class='form-control' id='password_edit3' placeholder='Confirm new password'>
</div>
<small id='edit_password_res' class='form-text text-muted'></small>
</form>
</div>

  <div class='modal-footer'>
  <button type='button' class="btn btn-secondary shadow" onclick="changePassword('<?php echo $_SESSION['username']; ?>')">Confirm</button>
  </div>

    </div>

  </div>
</div>









    <!-- JavaScript files-->
    <script src="vendor/jquery/jquery.min.js"></script>

    <script src="vendor/popper.js/umd/popper.min.js"> </script>
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="vendor/jquery.cookie/jquery.cookie.js"> </script>
    <script src="vendor/chart.js/Chart.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/js-cookie@2/src/js.cookie.min.js"></script>
    <script src="js/front.js"></script>
    <script src="js/script.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.18/datatables.min.js"></script>
<script>
$(document).ready(function() {
   $('#table_container').DataTable({stateSave: true});
   $('#table_container1').DataTable();
   $('#table_container2').DataTable();
   $('#table_container3').DataTable();
   $('#table_container4').DataTable();
    if (location.hash) {
        $("a[href='" + location.hash + "']").tab("show");
    }
    $(document.body).on("click", "a[data-toggle]", function(event) {
        location.hash = this.getAttribute("href");
    });
});
$(window).on("popstate", function() {
    var anchor = location.hash || $("a[data-toggle='tab']").first().attr("href");
    $("a[href='" + anchor + "']").tab("show");
    //table pagination

});
</script>
  </body>
</html>