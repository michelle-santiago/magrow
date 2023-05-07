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

  if (isset($_GET['pageno'])) {
    $pageno = $_GET['pageno'];
} else {
    $pageno = 1;
}
?>

<?php include('connection.php'); ?>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Home</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">
    <!-- Bootstrap CSS-->
    <style>

    </style>
    <!-- Table CSS-->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.18/datatables.min.css"/>
    <link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.min.css">
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
  <?php if($_SESSION['username']=="rell"||$_SESSION['username']=="rell.chui@gmail.com"||$_SESSION['username']=="teal"||$_SESSION['username']=="achhhoooo@gmail.com") {?>
      <nav class="navbar navbar-expand-lg px-4 py-2 bg-white shadow"><a href="#" class="sidebar-toggler text-gray-500 mr-4 mr-lg-5 lead"><i class="fas fa-align-left"></i></a><a href="index.php" class="navbar-brand font-weight-bold text-uppercase text-base" style="color:#01bea2">Admin Pane</a>
  <?php }?>
  
  <?php if($_SESSION['username']!="rell"&& $_SESSION['username']!="rell.chui@gmail.com"&&$_SESSION['username']!="teal"&&$_SESSION['username']!="achhhoooo@gmail.com") {?>
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
        <div class=" text-uppercase px-3 px-lg-4 py-4 font-weight-bold small headings-font-family" style="color:#01bea2">MAGROW</div>
        <ul class="sidebar-menu list-unstyled">
		
			<?php if($_SESSION['username']=="rell"||$_SESSION['username']=="rell.chui@gmail.com"||$_SESSION['username']=="teal"||$_SESSION['username']=="achhhoooo@gmail.com") {?>
      
			  <li class="sidebar-list-item"><a href="index.php" class="sidebar-link text-muted active"><i class="o-user-details-1 mr-3 text-gray"></i><span>Accounts</span></a></li>
              <li class="sidebar-list-item"><a href="charts.php" class="sidebar-link text-muted"><i class="o-stack-1 mr-3 text-gray"></i><span>Crops</span></a></li>
              <li class="sidebar-list-item"><a href="tables.php" class="sidebar-link text-muted"><i class="o-earth-globe-1 mr-3 text-gray"></i><span>Farms</span></a></li>
              <li class="sidebar-list-item"><a href="forms.php" class="sidebar-link text-muted"><i class="o-archive-folder-1 mr-3 text-gray"></i><span>Records</span></a></li>
              <li class="sidebar-list-item"><a href="?logout=1" class="sidebar-link text-muted"><i class="o-exit-1 mr-3 text-gray"></i><span>Logout</span></a></li>	
	  
	<?php }?>
	
	<?php if($_SESSION['username']!="rell"&& $_SESSION['username']!="rell.chui@gmail.com"&&$_SESSION['username']!="teal"&&$_SESSION['username']!="achhhoooo@gmail.com") {?>

			  <li class="sidebar-list-item"><a href="index.php" class="sidebar-link text-muted active"><i class="o-home-1 mr-3 text-gray"></i><span>Home</span></a></li>
              <li class="sidebar-list-item"><a href="charts.php" class="sidebar-link text-muted"><i class="o-sales-up-1 mr-3 text-gray"></i><span>Charts</span></a></li>
              <li class="sidebar-list-item"><a href="tables.php" class="sidebar-link text-muted"><i class="o-table-content-1 mr-3 text-gray"></i><span>Tables</span></a></li>
              <li class="sidebar-list-item"><a href="forms.php" class="sidebar-link text-muted"><i class="o-survey-1 mr-3 text-gray"></i><span>Reports</span></a></li>
              <li class="sidebar-list-item"><a href="?logout=1" class="sidebar-link text-muted"><i class="o-exit-1 mr-3 text-gray"></i><span>Logout</span></a></li>	

   <?php }?>
             

				 
        </ul>
    	</div>
		<div class="page-holder w-100 d-flex flex-wrap">
			<!--BODY**********************************-->

        <div class="container-fluid px-xl-5">
		          <section class="py-5">
				  
		<?php if($_SESSION['username']=="rell"||$_SESSION['username']=="rell.chui@gmail.com"||$_SESSION['username']=="teal"||$_SESSION['username']=="achhhoooo@gmail.com") {?>
      
	   <div class="row">
              <div class="col-lg-12">
                <div class="card">
                  <div class="card-header">
                    <h6 class="text-uppercase mb-0" style="color:#01bea2">User Accounts</h6>
                  </div>
                  <div class="card-body">   
                   
                    <table id="table_container" class="table table-striped table-hover card-text table-sm" cellspacing="0">
                      <thead>
                        <tr>
                          <th>#</th>
                          <th>Name</th>
                          <th>Username</th>
                          <th>Email</th>
						              <th>Status</th>
                          <th style=" background : none;">Action</th>
                          <th style=" background : none;"></th>    					  
                        </tr>
                      </thead>
                      <tbody id="user_table">
                      <?php

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
                      echo "<td><Button class='btn btn-secondary shadow' data-toggle='modal' onclick='showAccount(".$row['id'].")'>Edit</Button></td>";
                      if($row['status']=="1")
                      {
                          echo "<td><Button class='btn btn-warning shadow'  onclick='update_user_status(".$row['id'].",".$row['status'].")'>Disable</Button></td>";
                      }
                      else
                      {
                          echo "<td><Button class='btn btn-success shadow'  onclick='update_user_status(".$row['id'].",".$row['status'].")'>Enable</Button></td>";
                      }
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
	
		<?php if($_SESSION['username']!="rell"&& $_SESSION['username']!="rell.chui@gmail.com"&&$_SESSION['username']!="teal"&&$_SESSION['username']!="achhhoooo@gmail.com") {?>
 <section>
            <div class="row">
              <div class="col-xl-4 col-lg-6 mb-4 mb-xl-0">
                <div class="bg-white shadow roundy p-4 h-100 d-flex align-items-center justify-content-between">
                  <div class="flex-grow-1 d-flex align-items-center">
                    <div class="dot mr-3 bg-violet"></div>
                    <div class="text">
                      <h6 class="mb-0">Onion data</h6><span class="text-gray">
                        <?php  
                        $no1=0;
                         $query = "SELECT * from crop where  data_status='current' and crop='onion' and privacy=1";
                       $results = mysqli_query($con, $query);
                       while($row=mysqli_fetch_array($results)){
                       $no1++;
                        }
                        echo $no1;?>
                      </span>
                    </div>
                  </div>
                  <div class="icon text-white bg-violet"><i class="fas fa-server"></i></div>
                </div>
              </div>
              <div class="col-xl-4 col-lg-6 mb-4 mb-xl-0">
                <div class="bg-white shadow roundy p-4 h-100 d-flex align-items-center justify-content-between">
                  <div class="flex-grow-1 d-flex align-items-center">
                    <div class="dot mr-3 bg-green"></div>
                    <div class="text">

                      <h6 class="mb-0">Rice data</h6><span class="text-gray">
                        <?php  
                        $no1=0;
                         $query = "SELECT * from crop where  data_status='current' and crop='rice' and privacy=1";
                       $results = mysqli_query($con, $query);
                       while($row=mysqli_fetch_array($results)){
                       $no++;
                        }
                        echo $no;?>
                      </span>
                    </div>
                  </div>
                  <div class="icon text-white bg-green"><i class="far fa-clipboard"></i></div>
                </div>
              </div>
    
            
            </div>
          </section>
          <br>
 
          <ul class="nav nav-tabs">
          <li class="nav-item">
          <a href="#cropProd" class="nav-link active" data-toggle="tab">Crop Production</a>
          </li>
          <li class="nav-item">
          <a href="#cropProdCost" class="nav-link" data-toggle="tab">Crop Production Cost Distribution</a>
          </li>

        </ul>
        <div class="tab-content" id="tabs">
           <div class="tab-pane active" id="cropProd">
                      <div class="row">
                       <div class="col-lg-12">
                         <div class="card">
                           <div class="card-header">
                             <h6 class="text-uppercase mb-0" style="color:#01bea2">FARMER'S CROP PRODUCTION</h6>
                           </div>
                           <div class="card-body">   
                              <ul class="nav nav-pills">
                                <li class="nav-item">
                                <a href="#rice" class="nav-link active" data-toggle="tab">Rice</a>
                                </li>
                                <li class="nav-item">
                                <a href="#onion" class="nav-link" data-toggle="tab">Onion</a>
                                </li>
                              </ul><br>
                              <div class="tab-content" id="tabs">
                                <div class="tab-pane active" id="rice">
                                      <div class="row">
                                       <div class="input-group col-md-4">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">
                                                Variety:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            </span>
                                        </div>
                                      <select  id="rice_variety" class="form-control">
                                      
                                      <option value="all">All</option>
                                      <option value="NSIC Rc9">NSIC Rc9</option>
                                      <option value="NSIC Rc14">NSIC Rc14</option>
                                      <option value="PSB Rc18">PSB Rc18</option>
                                      <option value="PSB Rc68">PSB Rc68</option>
                                      <option value="NSIC Rc194">NSIC Rc194</option>
                                      <option value="NSIC Rc222">NSIC Rc222</option>
                                      <option value="NSIC Rc300">NSIC Rc300</option>
                                      <option value="NSIC Rc160">NSIC Rc160</option>
                                      <option value="NSIC Rc238">NSIC Rc238</option>
                                      <option value="NSIC Rc216">NSIC Rc216</option>
                                      <option value="NSIC Rc402">NSIC Rc402</option>
                                      <option value="NSIC Rc354">NSIC Rc354</option>
                                      <option value="NSIC Rc218">NSIC Rc218</option>
                                      <option value="PSB Rc10">PSB Rc10</option>
                                      <option value="other">Other</option>
                                    
                                      </select>
                                      </div>
                                    </div><br>
                                      <div class="row" id='rice'>
                                        <div class="input-group col-md-4">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">
                                                Start Date: 
                                            </span>
                                        </div>
                                        <input id="start_date1" name="start_date" class="form-control"  type="date" data-toggle="popover"  data-placement="top" data-date-format="yyyy-MM-dd" data-content="Check start date.">
                                    </div>

                                    <div class="input-group col-md-4">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">
                                                End Date:
                                            </span>
                                        </div>
                                        <input id="end_date1" name="end_date" class="form-control" type="date"  data-toggle="popover"  data-placement="top" data-date-format="yyyy-MM-dd" data-content="Check end date.">
                                    </div>
                                    <div class="col-md-1">
                                          <button  class="btn btn-success" onclick="generate_cropProdPublic('rice')">Filter</button>  
                                    </div>

                                    <div class="col-md-12">
                                      <br>
                                     <canvas id="groupedBarGraphR"></canvas>  
                                   </div>
                                </div>
                                </div>
                                <div class="tab-pane" id="onion">
                                    <div class="row">
                                              <div class="input-group col-md-4">
                                      <div class="input-group-prepend">
                                            <span class="input-group-text">
                                                Variety:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                            </span>
                                        </div>
                                      <select  id="onion_variety" class="form-control" >
                                      <option value="all">All</option>
                                      <option value="BGS 95">BGS 95</option>
                                      <option value="Cal 120">Cal 120</option>
                                      <option value="Cal 202">Cal 202</option>
                                      <option value="Capri">Capri</option>
                                      <option value="CX-12">CX-12</option>
                                      <option value="Grannex 429">Grannex 429</option>
                                      <option value="Hybrid Red Orient">Hybrid Red Orient</option>
                                      <option value="Liberty">Liberty</option>
                                      <option value="Red Creole">Red Creole</option>
                                      <option value="Red Pinoy">Red Pinoy</option>
                                      <option value="Rio Bravo">Rio Bravo</option>
                                      <option value="Rio Hondo">Rio Hondo</option>
                                      <option value="Rio Raji Red">Rio Raji Red</option>
                                      <option value="Rio Tinto">Rio Tinto</option>
                                      <option value="Super Pinoy">Super Pinoy</option>
                                      <option value="SuperX">SuperX</option>
                                      <option value="Texas Grano">Texas Grano</option>
                                      <option value="Yellow Grannex">Yellow Grannex</option>
                                      <option value="other">Other</option>
                                      </select>
                                      </div>
                                    </div><br>
                                    <div class="row" id="onion">
                              
                                        <div class="input-group col-md-4">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">
                                                Start Date: 
                                            </span>
                                        </div>
                                        <input id="start_date2" name="start_date" class="form-control"  type="date" data-toggle="popover"  data-placement="top" data-content="Check start date.">
                                    </div>

                                    <div class="input-group col-md-4">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">
                                                End Date:
                                            </span>
                                        </div>
                                        <input id="end_date2" name="start_date" class="form-control" type="date"  data-toggle="popover"  data-placement="top" data-content="Check end date.">
                                    </div>
                                    <div class="col-md-1">
                                          <button  class="btn btn-success" onclick="generate_cropProdPublic('onion')">Filter</button>  
                                    </div>
                                    <div class="col-md-12">
                                      <br>
                                     <canvas id="groupedBarGraphO"></canvas>  
                                   </div>

                            
                                  
                                  </div>
                                </div>
                              </div>
                      
                           </div>
                         </div>
                       </div>
                     </div>
            </div>
              <div class="tab-pane" id="cropProdCost">
                      <div class="row">
                       <div class="col-lg-12">
                         <div class="card">
                           <div class="card-header">
                             <h6 class="text-uppercase mb-0" style="color:#01bea2"> COST DISTRIBUTION OF FARMER'S CROP PRODUCTION ACTIVITIES</h6>
                           </div>
                           <div class="card-body">
                           <ul class="nav nav-pills">
                                <li class="nav-item">
                                <a href="#rice2" class="nav-link active" data-toggle="tab">Rice</a>
                                </li>
                                <li class="nav-item">
                                <a href="#onion2" class="nav-link" data-toggle="tab">Onion</a>
                                </li>
                              </ul><br>   
                                <div class="tab-content" id="tabs">
                                <div class="tab-pane active" id="rice2">
                                                <div class="row">
                                       <div class="input-group col-md-4">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">
                                                Variety:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            </span>
                                        </div>
                                      <select  id="rice_variety2" class="form-control">
                                      
                                      <option value="all">All</option>
                                      <option value="NSIC Rc9">NSIC Rc9</option>
                                      <option value="NSIC Rc14">NSIC Rc14</option>
                                      <option value="PSB Rc18">PSB Rc18</option>
                                      <option value="PSB Rc68">PSB Rc68</option>
                                      <option value="NSIC Rc194">NSIC Rc194</option>
                                      <option value="NSIC Rc222">NSIC Rc222</option>
                                      <option value="NSIC Rc300">NSIC Rc300</option>
                                      <option value="NSIC Rc160">NSIC Rc160</option>
                                      <option value="NSIC Rc238">NSIC Rc238</option>
                                      <option value="NSIC Rc216">NSIC Rc216</option>
                                      <option value="NSIC Rc402">NSIC Rc402</option>
                                      <option value="NSIC Rc354">NSIC Rc354</option>
                                      <option value="NSIC Rc218">NSIC Rc218</option>
                                      <option value="PSB Rc10">PSB Rc10</option>
                                      <option value="other">Other</option>
                                    
                                      </select>
                                      </div>
                                    </div><br>
                                      <div class="row">
                                        <div class="input-group col-md-4">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">
                                                Start Date: 
                                            </span>
                                        </div>
                                        <input id="start_date3" name="start_date" class="form-control"  type="date" data-toggle="popover"  data-placement="top" data-date-format="yyyy-MM-dd" data-content="Check start date.">
                                    </div>

                                    <div class="input-group col-md-4">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">
                                                End Date:
                                            </span>
                                        </div>
                                        <input id="end_date3" name="end_date" class="form-control" type="date"  data-toggle="popover"  data-placement="top" data-date-format="yyyy-MM-dd" data-content="Check end date.">
                                    </div>
                                    <div class="col-md-1">
                                          <button  class="btn btn-success" onclick="showListPublic('rice')">Filter</button>  
                                    </div>
                                </div><br>
                                  <div class="row" id="row2" style="display: none;">
                                  
                                </div>
                                 
                              </div>
                              <div class="tab-pane" id="onion2">
                                      <div class="row">
                                              <div class="input-group col-md-4">
                                      <div class="input-group-prepend">
                                            <span class="input-group-text">
                                                Variety:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                            </span>
                                        </div>
                                      <select  id="onion_variety2" class="form-control" >
                                      <option value="all">All</option>
                                      <option value="BGS 95">BGS 95</option>
                                      <option value="Cal 120">Cal 120</option>
                                      <option value="Cal 202">Cal 202</option>
                                      <option value="Capri">Capri</option>
                                      <option value="CX-12">CX-12</option>
                                      <option value="Grannex 429">Grannex 429</option>
                                      <option value="Hybrid Red Orient">Hybrid Red Orient</option>
                                      <option value="Liberty">Liberty</option>
                                      <option value="Red Creole">Red Creole</option>
                                      <option value="Red Pinoy">Red Pinoy</option>
                                      <option value="Rio Bravo">Rio Bravo</option>
                                      <option value="Rio Hondo">Rio Hondo</option>
                                      <option value="Rio Raji Red">Rio Raji Red</option>
                                      <option value="Rio Tinto">Rio Tinto</option>
                                      <option value="Super Pinoy">Super Pinoy</option>
                                      <option value="SuperX">SuperX</option>
                                      <option value="Texas Grano">Texas Grano</option>
                                      <option value="Yellow Grannex">Yellow Grannex</option>
                                      <option value="other">Other</option>
                                      </select>
                                      </div>
                                    </div><br>
                                    <div class="row">
                              
                                        <div class="input-group col-md-4">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">
                                                Start Date: 
                                            </span>
                                        </div>
                                        <input id="start_date4" name="start_date" class="form-control"  type="date" data-toggle="popover"  data-placement="top" data-content="Check start date.">
                                    </div>

                                    <div class="input-group col-md-4">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">
                                                End Date:
                                            </span>
                                        </div>
                                        <input id="end_date4" name="start_date" class="form-control" type="date"  data-toggle="popover"  data-placement="top" data-content="Check end date.">
                                    </div>
                                    <div class="col-md-1">
                                          <button  class="btn btn-success" onclick="showListPublic('onion')">Filter</button>  
                                    </div>
                                  </div><br>
                                  <div class="row" id="row3" style="display: none;">
                                </div>
                               
                              </div>
                              </div>
                           </div>
                         </div>
                       </div>
                     </div>
            </div>
          </div><br>
    <div class="row">
               <div class="col-lg-12">
                 <div class="card">
                   <div class="card-header">
                     <h6 class="text-uppercase mb-0" style="color:#01bea2">FARMER'S CROP PRODUCTION RECORDS</h6>
                   </div>
                   <div class="card-body">   
                    
                     <table id="table_container" class="table table-striped table-hover card-text table-sm" cellspacing="0">
                       <thead>
                         <tr>
                           <th>#</th>
                           <th>Name</th>
                           <th>Type</th>
                           <th>Variety</th>
                           <th>Date</th>
                           <th>Harvested</th>
                      
                           <th>Planted</th>
                          
                           <th>No. of Sacks</th>
                           <th>Total Weight</th>
                          
                         </tr>
                       </thead>
                       <tbody id="user_table3">
                       <?php
                      

                       $no=0;
                       $query = "SELECT record_log.id as id,crop_name,crop,variety,area_harvested,ah_measurement,area_planted,ap_measurement,no_of_cavans_harvested,weight_per_cavan,date_added from crop,record_log where crop.id=record_log.crop_id  and record_log.data_status_r='current' and privacy=1";
                       $results = mysqli_query($con, $query);
                       while($row=mysqli_fetch_array($results)){
                       $no++;
                       
                       
                       
                       echo "<tr>";
                       echo "<td>".$no."</td>";
                       echo "<td>".$row['crop_name']."</td>";
                       echo "<td>".$row['crop']."</td>";
                       echo "<td>".$row['variety']."</td>";
                       echo "<td>".$row['date_added']."</td>";   
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

<div id="editAccountModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header" style="background-color:#01bea2;">

        <h4 class="modal-title" style="color:#fff;">Edit Account</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
<div id="modalContainerBody">
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
    <script src="js/charts-home.js"></script>
    <script src="js/front.js"></script>
    <script src="js/script.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.18/datatables.min.js"></script>
     
   <script>
  $(document).ready(function() {
   $('#table_container').DataTable({stateSave: true});

});
</script>       

  </body>
</html>

