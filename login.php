  <?php
 error_reporting(0);
  
  session_start(); 
  if (isset($_SESSION['username'])) {
    header('location:index.php');
  }
?>




<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Login/Register</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">
    <!-- Bootstrap CSS-->
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
  
    <div class="page-holder d-flex align-items-center">
      <div class="container">
        <div class="row align-items-center py-5">
          <div class="col-5 col-lg-7 mx-auto mb-5 mb-lg-0">
            <div class="pr-lg-5"><img src="img/illustration.svg" alt="" class="img-fluid"></div>
          </div>
      
          <div class="col-lg-5 px-lg-4" id="login_pane">
            <h1 class="text-base text-uppercase mb-4" style="color:#01bea2">MAGROW</h1>
            <h2 class="mb-4">Welcome back!</h2>
            <p class="text-muted">Managing your crops can be tricky. Simplify it with MAGROW!</p>
            <form id="LogoutForm" class="mt-4">
              <div class="form-group mb-4">
                <input type="text" id="username1" name="username" placeholder="Username or Email address" class="form-control border-0 shadow form-control-lg">
              </div>
              <div class="form-group mb-4">
                <input type="password" id="password1" name="passowrd" placeholder="Password" class="form-control border-0 shadow form-control-lg text-violet">
              </div>             
            </form>
      
        <button type="submit" class="btn btn-success shadow px-5" onclick="login()">Log in</button>
        <button type="submit" class="btn btn-secondary shadow px-5" onclick="show_register_pane()">Sign up</button><br><br>
        <button type="submit" class="btn btn-warning shadow px-5" onclick="show_forgot_password_pane()">Forgot Password</button>
        <button type="submit" class="btn btn-success shadow px-5" data-toggle="modal" data-target="#myModal">MAGROW App</button>

      <br><br><span id="loginresponse"></span>
      </div>
      
      
      <div class="col-lg-5 px-lg-4" id="register_pane" style="display:none;">
            <h1 class="text-base text-primary text-uppercase mb-4">MAGROW</h1>
            <h2 class="mb-4">Create An Account</h2>
            <p class="text-muted">Welcome! Be part of the MAGROW community</p>
        <form id="RegisterForm" class="mt-4">
              <div class="form-group mb-4">
                <input type="text" id="username3" name="username" placeholder="Username" class="form-control border-0 shadow form-control-lg">
              </div>
        
        <div class="form-group mb-4">
                <input type="email" id="email2" name="email" placeholder="Email address" class="form-control border-0 shadow form-control-lg">
              </div>
        
        <div class="form-group mb-4">
                <input type="text" id="name2" name="name" placeholder="Name" class="form-control border-0 shadow form-control-lg">
              </div>
        
        <div class="form-group mb-4">
                <input type="password" id="password3" name="passowrd" placeholder="Password" class="form-control border-0 shadow form-control-lg text-violet">
              </div>
        
              <div class="form-group mb-4">
                <input type="password" id="password4" name="passowrd" placeholder="Confirm Password" class="form-control border-0 shadow form-control-lg text-violet">
              </div>
            </form>
      
      <button type="submit" class="btn btn-primary shadow px-5" onclick="show_login_pane()">Back</button>
        <button type="submit" class="btn btn-secondary shadow px-5" onclick="register()">Register</button>
    <br><br><span id="registerresponse"></span>
          </div>
      
        </div>

        <div class="col-lg-5 px-lg-4" id="forgot_password_pane" style="display:none;">
            <h1 class="text-base text-uppercase mb-4" style="color:#01bea2">MAGROW</h1>
            <h2 class="mb-4">Forgot your password?</h2>
            <p class="text-muted">Don't worry, We just need your registered email ID to send your password reset key.</p>
            <form id="LogoutForm" class="mt-4">
              <div class="form-group mb-4">
                <input type="text" id="username1" name="username" placeholder="Enter email ID" class="form-control border-0 shadow form-control-lg">
              </div>        
            </form>
      
            <button type="submit" class="btn btn-primary shadow px-5">Done</button>
      <button type="submit" class="btn btn-primary shadow px-5" onclick="show_login_pane()">Back</button>
      <br><br><span id="forgot_password_response"></span>
      </div>

      
        
    
    
    
    
    
    
  
    
        <p style="color:#01bea2" class="mt-5 mb-0 text-gray-400 text-center">Design by <a href="https://bootstrapious.com/admin-templates" class="external text-gray-400">Bootstrapious</a> & MAGROW</p>
        <!-- Please do not remove the backlink to us unless you support further theme's development at https://bootstrapious.com/donate. It is part of the license conditions. Thank you for understanding :)                 -->
      </div>
    </div>
  
  
  
  <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
         
          <button type="button" class="close" data-dismiss="modal">&times;</button>
   
        </div>
        <div class="modal-body">
      
          <center><img src="img/frame.png"/></center>
      <center><span id="registerresponse">Don't have the app yet?</span></center>
      <center><span id="registerresponse">Scan the code to get started!</span></center>
      <center><span id="registerresponse">Or Click <a href="https://play.google.com/store/apps/details?id=magrow.project.application&hl=en">here<a> to continue</span></center>
      
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  
<script src="js/script.js" type="text/javascript"></script>
  <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/popper.js/umd/popper.min.js"> </script>
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="vendor/jquery.cookie/jquery.cookie.js"> </script>
    <script src="vendor/chart.js/Chart.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/js-cookie@2/src/js.cookie.min.js"></script>
    <script src="js/front.js"></script>
  
  </body>
</html>