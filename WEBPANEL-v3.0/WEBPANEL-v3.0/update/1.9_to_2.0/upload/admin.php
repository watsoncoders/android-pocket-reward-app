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

?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Pocket Android | DroidOxy </title>

    <!-- Bootstrap core CSS -->

    <link href="css/bootstrap.min.css" rel="stylesheet">

    <link href="fonts/css/font-awesome.min.css" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">

    <!-- Custom styling plus plugins -->
    <link href="css/custom.css" rel="stylesheet">
    <link href="css/icheck/flat/green.css" rel="stylesheet">

    <script src="js/jquery.min.js"></script>

    <!--[if lt IE 9]>
      <script src="../assets/js/ie8-responsive-file-warning.js"></script>
    <![endif]-->

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<?php

 include_once 'connect_database.php';

?>

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
                  <li><a href="admin.php" ><i class="fa fa-home"></i>Dashboard <span ></span></a>
                    
                  </li>
                  
                  <li><a><i class="fa fa-bullhorn"></i> Push Notifications <span class="fa fa-chevron-down"></span></a>
                  <ul class="nav child_menu" style="display: none">
                    <li><a href="push-all.php">Send to All users</span></a>
                    </li>
                    <li><a href="push.php">Send to Single user</a>
                    </li>
                  </ul>
                </li>
      
              
                <li><a><i class="fa fa-th-list"></i> Records<span class="fa fa-chevron-down"></span></a>
                  <ul class="nav child_menu" style="display: none">
                    <li><a href="requests.php">Pending Requests</span></a>
                    </li>
                    <li><a href="completed.php">Completed</a>
                    </li>
                  </ul>
                </li>
                  <li><a href="users.php"><i class="fa fa-user"></i> All Users</a>
                  </li>
                  <li><a href="tracker.php"><i class="fa fa-bar-chart-o"></i> Track Users Activity</a>
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
                    <img src="<?php echo $image_url; ?>" alt=""><?php echo $display_name; ?>
                    <span class=" fa fa-angle-down"></span>
                  </a>
                  <ul class="dropdown-menu dropdown-usermenu pull-right">
                  
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

        <br />
        <div class="">

          <div class="row top_tiles">
          
<a href="users.php">
            <div class="animated flipInY col-lg-4 col-md-4 col-sm-8 col-xs-16">
              <div class="tile-stats">
                <div class="icon"><i class="fa fa-users"></i>
                </div>
                <div class="count"><?php include_once 'count.php'; echo $all_users; ?></div>
                <h3>See All Users</h3>
                <p>Block or UnBlock Authorizations.</p>
              </div>
            </div></a>
            
<a href="requests.php">
            <div class="animated flipInY col-lg-4 col-md-4 col-sm-8 col-xs-16">
              <div class="tile-stats">
                <div class="icon"><i class="fa fa-gift"></i>
                </div>
                <div class="count"><?php include_once 'count.php'; echo $all_req; ?></div>
                <h3>New Requests</h3>
                <p>Process (or) Delete a Request.</p>
              </div>
            </div>
</a>


<a href="completed.php">
            <div class="animated flipInY col-lg-4 col-md-4 col-sm-8 col-xs-16">
              <div class="tile-stats">
                <div class="icon"><i class="fa fa-check-square-o"></i>
                </div>
                <div class="count"><?php include_once 'count.php'; echo $all_com; ?></div>
                <h3>Requests Processed</h3>
                <p>Completed Payment Records.</p>
              </div>
            </div>
</a>


           
            </div>
</div>
      <!-- /page content -->
<br/>

<div class="row">

	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="dashboard_graph">
			<div class="row x_title">
				<div class="col-md-9">
					<div class="row marketing">
						<div class="col-lg-9">
							<div class="clearfix"></div>
								<h4> &nbsp; Requirements</h4>
									<p><ul>
										<li>&nbsp; Pocket ( Android Code ) v1.6 + </li>
										<li>&nbsp; <a href="http://www.codyhub.com/item/pocket-backend-webpanel?ref=DroidOXY" target="_blank">Web/Admin Panel</a> </li>
										<li>&nbsp; Google Play Account</li>
										<li>&nbsp; Hosting with Support for PHP5 & Mysql database</li>
									</ul>
									</p>
									<br/>
								<h4>&nbsp; Where can I download Pocket - Android Code ?</h4>
								
								<p>&nbsp; <a href="https://www.codyhub.com/item/android-rewards-app-pocket?ref=DroidOXY" target="_blank">&nbsp; Click here to download from CodyHub.com</a></p>
								
								<p>&nbsp; <a href="https://codecanyon.net/item/android-rewards-app-pocket/17413949?ref=DroidOXY" target="_blank">&nbsp; Click here to download from CodeCanyon</a></p>
								
								<br/>
								
								<h4>&nbsp; Where can I download Web/Admin Panel ?</h4>
								
								<p>&nbsp; <a href="http://www.codyhub.com/item/pocket-backend-webpanel?ref=DroidOXY" target="_blank">&nbsp; Click here to download from CodyHub.com</a></p>
								<br/>
								
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
	
	<br/><br/>
</div>

      <!-- /page content -->
      
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