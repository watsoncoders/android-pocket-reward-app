<?php

    /*!
	 * POCKET v1.8
	 *
	 * http://droidoxy.oxywebs.com
	 * droid@oxywebs.com, yash@oxywebs.com
	 *
	 * Copyright 2016 DroidOXY ( http://droidoxy.oxywebs.com/ )
 */

    include_once($_SERVER['DOCUMENT_ROOT']."/core/init.inc.php");

    if (!admin::isSession()) {

        header("Location: index.php");
    }


include 'fcm/connect.inc.php'; 


	$query = "SELECT * FROM users";
	
	$stmt = $db->query($query);
	
	$target_path = 'uploads/';

if (!empty($_POST)) {

	$response = array("error" => FALSE);
	
	function send_gcm_notify($reg_id, $message, $img_url, $tag) {
	
		define("GOOGLE_API_KEY", "");
		define("GOOGLE_GCM_URL", "https://fcm.googleapis.com/fcm/send");
	
        $fields = array(
            
			'to'  						=> $reg_id ,
			'priority'					=> "high",
            'notification'              => array( "title" => "DroidOXY", "body" => $message, "tag" => $tag ),
			'data'						=> array("message" =>$message, "image"=> $img_url),
        );
		
        $headers = array(
			GOOGLE_GCM_URL,
			'Content-Type: application/json',
            'Authorization: key=' . GOOGLE_API_KEY 
        );
		
		echo "<br>";

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, GOOGLE_GCM_URL);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
		
        $result = curl_exec($ch);
        if ($result === FALSE) {
            die('Problem occurred: ' . curl_error($ch));
        }
		
        curl_close($ch);
       // echo $result;
    }
	
    $reg_id = $_POST['fcm_id'];
    $msg = $_POST['msg'];
	$img_url = '';
	$tag = 'text';
	if ($_FILES['image']['name'] != '') {
	
		$tag = 'image';
		$target_file = $target_path . basename($_FILES['image']['name']);
		$img_url = 'http://dev.oxywebs.in/fcm/'.$target_file;
			try {
			// Throws exception incase file is not being moved
			if (!move_uploaded_file($_FILES['image']['tmp_name'], $target_file)) {
				// make error flag true
				//echo json_encode(array('status'=>'fail', 'message'=>'could not move file'));
			}
 
			// File successfully uploaded
			//echo json_encode(array('status'=>'success', 'message'=> $img_url));
		} catch (Exception $e) {
			// Exception occurred. Make error flag true
			//echo json_encode(array('status'=>'fail', 'message'=>$e->getMessage()));
		}
		
		send_gcm_notify($reg_id, $msg, $img_url, $tag);
		
	} else {
		
		send_gcm_notify($reg_id, $msg, $img_url, $tag);
		
	}
	
}

?>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title> Pocket Android  | DroidOxy </title>

    <!-- Bootstrap core CSS -->

    <link href="css/bootstrap.min.css" rel="stylesheet">

    <link href="fonts/css/font-awesome.min.css" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">

    <!-- Custom styling plus plugins -->
    <link href="css/custom.css" rel="stylesheet">
    <link href="css/icheck/flat/green.css" rel="stylesheet">

    <script src="js/jquery.min.js"></script>


		<script>
		$(function(){
			$("textarea").val("");
		});
		function checkTextAreaLen(){
			var msgLength = $.trim($("textarea").val()).length;
			if(msgLength == 0){
				alert("Please enter message before hitting submit button");
				return false;
			}else{
				return true;
			}
		}
		</script>

    <!--[if lt IE 9]>
      <script src="../assets/js/ie8-responsive-file-warning.js"></script>
    <![endif]-->

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>


  <body class="nav-md">

    <div class="container body">


      <div class="main_container">

        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">

            <div class="navbar nav_title" style="border: 0;">
              <a href="admin.php" class="site_title"><i class="fa fa-gift"></i> <span>DroidOXY</span></a>
            </div>
            <div class="clearfix"></div>

            <!-- menu prile quick info -->
            <div class="profile">
              <div class="profile_pic">
                <img src="<?php echo $image_url; ?>" alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <span>Welcome,</span>
                <h2><?php echo $display_name; ?></h2>
              </div>
            </div>
            <!-- /menu prile quick info -->

            <br />

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">

              <div class="menu_section">
                <h3>General</h3>
                <ul class="nav side-menu">
                  <li><a href="admin.php" ><i class="fa fa-home"></i>Dashboard</a>

     <li><a href="push.php" ><i class="fa fa-bullhorn"></i>Push Panel <span ></span></a>
                   </li>
              
                <li><a><i class="fa fa-th-list"></i>Records<span class="fa fa-chevron-down"></span></a>
                  <ul class="nav child_menu" style="display: none">
                    <li><a href="requests.php">Pending Requests</span></a>
                    </li>
                    <li><a href="completed.php">Completed</a>
                    </li>
                  </ul>
                </li>
                  <li><a href="users.php"><i class="fa fa-user"></i> Users</a>
                  </li>
                  <li><a href="tracker.php"><i class="fa fa-bar-chart-o"></i>Track Users Activity</a>
                  </li>
                                </div>

            </div>
            <!-- /sidebar menu -->

            <!-- /menu footer buttons -->
            <div class="sidebar-footer hidden-small">
              <a data-toggle="tooltip" data-placement="top" title="Settings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Logout">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
              </a>
            </div>
            <!-- /menu footer buttons -->
          </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">

          <div class="nav_menu">
            <nav class="" role="navigation">
              <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
              </div>

              <ul class="nav navbar-nav navbar-right">
                <li class="">
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                    <img src="images/img.jpg" alt=""><?php echo $display_name; ?>
                    <span class=" fa fa-angle-down"></span>
                  </a>
                  <ul class="dropdown-menu dropdown-usermenu pull-right">
                    <li><a href="javascript:;">  Profile</a>
                    </li>
                    <li>
                      <a href="javascript:;">
                        <span>Settings</span>
                      </a>
                    </li>
                    <li>
                      <a href="javascript:;">Help</a>
                    </li>
                    <li><a href="/admin/logout.php/?access_token=<?php echo admin::getAccessToken(); ?>&continue=/"><i class="fa fa-sign-out pull-right"></i> Log Out</a>
                    </li>
                  </ul>
                </li>

              </ul>
            </nav>
          </div>

        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>Push Panel</h3>
              </div>

              <div class="title_right">
                <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                  <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search for...">
                    <span class="input-group-btn">
                              <button class="btn btn-default" type="button">Go!</button>
                          </span>
                  </div>
                </div>
              </div>
            </div>

          <div class="clearfix"></div>
          <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
              <div class="x_panel">
                <div class="x_title">
                  <h2>Send a Push Notification <small></small></h2>
                  <ul class="nav navbar-right panel_toolbox">


                    <li><a class="collapsenk"><i class="fa fa-chev"></i></a>
                    </li>
   
                    <li><a class="colla-link"><i class="fa fa-chev"></i></a>
                    </li>

                    <li><a class="colle-link"><i class="fa fa-chev"></i></a>
                    </li>

                    <li><a class="colle-link"><i class="fa fa-chev"></i></a>
                    </li>
   
                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                    </li>
    
                  </ul>
                  <div class="clearfix"></div>
                </div>
                <div class="x_content">
                  <br />


<!-- Form Starts from Here ..! -->

                  <form action="fcm/send_push.php" method="post" enctype="multipart/form-data" onsubmit="return checkTextAreaLen()" id="demo-form2" data-parsley-validate class="form-horizontal form-label-left">


                    <div class="form-group">
                      <label class="control-label col-md-3 col-sm-3 col-xs-12">Select User</label>
                      <div class="col-md-6 col-sm-6 col-xs-12">
                        <select class="select2_single form-control" tabindex="-1" name="fcm_id" required>

<option value="" disabled selected>Select a User</option>
							<?php while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
								echo "<option value='".$row['gcm_regid']."'> ".$row['login']."</option>";
							} ?>
						


                        </select>
                      </div>
                    </div>
                   

 <div class="form-group">
                      <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Title : <span class="required">*</span>
                      </label>
                      <div class="col-md-6 col-sm-6 col-xs-12">
                        <input type="text" name="title" id="title" required="required" class="form-control col-md-7 col-xs-12" placeholder="Title of push Notification">

                      </div>
                    </div>



  <div class="form-group">
                    
<br><br>
				

                  <label class="control-label col-md-3 col-sm-3 col-xs-12">Message : </label>  

 <div class="col-md-6 col-sm-6 col-xs-12">
                   
                   <textarea type="text" id="msg" name="msg" required="required" placeholder="Message Text" class="resizable_textarea form-control" style="width: 100%; overflow: hidden; word-wrap: break-word; resize: horizontal; height: 87px;" ></textarea>


                  </div>
                </div>

                    <div class="ln_solid"></div>
                    <div class="form-group"><div class="pull-right">
                      <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                        <button type="reset" class="btn btn-success">Reset</button>
                        <button type="submit" class="btn btn-primary">Send Now</button>
                      </div>
                    </div>


                  </form>

<!-- Form Ends Here -->

</div>
          </div>
        </div>
      </div></div>
      <!-- /page content -->



                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="dashboard_graph">

                            <div class="row x_title">
                                 
                                <div class="col-md-6">
                            
                         
      <div class="row marketing">
        <div class="col-lg-6">



                            <div class="clearfix"></div>

        
          
        </div>

        <div class="col-lg-6">
          <h4>How it works?</h4>
          <p>When the Android app POCKET starts, it will send its token to the server configured in it. The token is saved in the database, and this script allows you to cycle all the tokens and send a Push Notification.</p>

          

        </div>
      </div>


                                </div>
                            </div>

                            <div class="col-md-12 col-sm-12 col-xs-12">
                                ...
                            </div>

                            <div class="clearfix"></div>
                        </div>
                    </div>

                </div>
                <br />
            </div>
            <!-- /page content -->

      </div>
    </div>
        <!-- footer content -->
        <footer>
          <div >
            <div align="center"> Pocket - Powerd by <a href="http://droidoxy.oxywebs.com" target="_blank" >DroidOXY</a>
          </div></div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>


    <div id="custom_notifications" class="custom-notifications dsp_none">
      <ul class="list-unstyled notifications clearfix" data-tabbed_notifications="notif-group"></ul>
      <div class="clearfix"></div>
      <div id="notif-group" class="tabbed_notifications"></div>
    </div>

    <script src="js/bootstrap.min.js"></script>

    <!-- bootstrap progress js -->
    <script src="js/progressbar/bootstrap-progressbar.min.js"></script>
    <!-- icheck -->
    <script src="js/icheck/icheck.min.js"></script>

    <script src="js/custom.js"></script>

    <!-- pace -->
    <script src="js/pace/pace.min.js"></script>
  </body>
</html>