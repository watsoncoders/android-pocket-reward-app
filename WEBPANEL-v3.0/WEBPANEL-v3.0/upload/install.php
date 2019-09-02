<?php

    /*!
	 * POCKET v1.8
	 *
	 * http://droidoxy.oxywebs.com
	 * droid@oxywebs.com, yash@oxywebs.com
	 *
	 * Copyright 2017 DroidOXY ( http://droidoxy.oxywebs.com/ )
 */

include_once($_SERVER['DOCUMENT_ROOT']."/core/init.inc.php");

if (admin::isSession()) {

    header("Location: admin.php");
}

$admin = new admin($dbo);

if ($admin->getCount() > 0) {

    header("Location: /admin/login.php");
}

include_once($_SERVER['DOCUMENT_ROOT']."/core/initialize.inc.php");

$page_id = "install";

$error = false;
$error_message = array();

$user_username = '';
$user_fullname = '';
$user_password = '';
$user_password_repeat = '';

$error_token = false;
$error_username = false;
$error_fullname = false;
$error_password = false;
$error_password_repeat = false;

if (!empty($_POST)) {

    $error = false;

    $user_username = isset($_POST['user_username']) ? $_POST['user_username'] : '';
    $user_password = isset($_POST['user_password']) ? $_POST['user_password'] : '';
    $user_fullname = isset($_POST['user_fullname']) ? $_POST['user_fullname'] : '';
    $token = isset($_POST['authenticity_token']) ? $_POST['authenticity_token'] : '';

    $user_username = helper::clearText($user_username);
    $user_fullname = helper::clearText($user_fullname);
    $user_password = helper::clearText($user_password);
    $user_password_repeat = helper::clearText($user_password_repeat);

    $user_username = helper::escapeText($user_username);
    $user_fullname = helper::escapeText($user_fullname);
    $user_password = helper::escapeText($user_password);
    $user_password_repeat = helper::escapeText($user_password_repeat);

    if (helper::getAuthenticityToken() !== $token) {

        $error = true;
        $error_token = true;
        $error_message[] = 'Error!';
    }

    if (!helper::isCorrectLogin($user_username)) {

        $error = true;
        $error_username = true;
        $error_message[] = 'Incorrect username.';
    }

    if (!helper::isCorrectPassword($user_password)) {

        $error = true;
        $error_password = true;
        $error_message[] = 'Incorrect password.';
    }

    if (!$error) {

        $admin = new admin($dbo);

        $result = array();
        $result = $admin->signup($user_username, $user_password, $user_fullname);

        if ($result['error'] === false) {

            $access_data = $admin->signin($user_username, $user_password);

            if ($access_data['error'] === false) {

                $clientId = 0; // Desktop version

                admin::createAccessToken();

                admin::setSession($access_data['accountId'], admin::getAccessToken());

                header("Location: admin.php");
            }

            header("Location: /install.php");
        }
    }
}

helper::newAuthenticityToken();

$css_files = array("admin.css");
$page_title = APP_TITLE;

include_once($_SERVER['DOCUMENT_ROOT']."/common/header.inc.php");

?>

<body>

<div id="page_wrap">

    <?php

        include_once($_SERVER['DOCUMENT_ROOT']."/common/admin_panel_topbar.inc.php");
    ?>

    <div id="page_layout">

        <div id="page_body" class="banner">
            <div id="wrap3">
                <div id="wrap2">
                    <div id="wrap1">
                        <div id="content">

                            <div class="note orange">
                                <div class="title">Warning!</div>
                                Remember that now Creating an account for Admin Login! <br/>
username = min 5 charecters <br/>
password = min 6 charecters <br/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="page_auth">

            <div class="header">
                <div class="title">
                    Install
                </div>
            </div>

            <div class="error <?php if (!$error) echo "hide"; ?>">
                <?php

                foreach ($error_message as $msg) {

                    echo $msg . "<br />";
                }
                ?>
            </div>

            <div class="frm">
                <form action="/install.php" method="post" id="login_form">
                    <input autocomplete="off" type="hidden" name="authenticity_token" value="<?php echo helper::getAuthenticityToken(); ?>">
                    <div class="frm_header">
                        <label class="noselect" for="user_username">Username:</label>
                    </div>
                    <input autocomplete="off" type="text" id="user_username" class="frm_input" maxlength="24" name="user_username" value="<?php echo stripslashes($user_username); ?>">
                    <div class="frm_header">
                        <label class="noselect" for="user_fullname">Fullname:</label>
                    </div>
                    <input autocomplete="off" type="text" id="user_fullname" class="frm_input" maxlength="24" name="user_fullname" value="<?php echo stripslashes($user_fullname); ?>">
                    <div class="frm_header">
                        <label class="noselect" for="user_password">Password:</label>
                    </div>
                    <input autocomplete="off" type="password" id="user_password" class="frm_input" maxlength="20" name="user_password" value="">
                    <div class="">
                        <button type="submit" class="frm_btn primary_btn big_btn">Install</button>
                    </div>
                </form>
            </div>

        </div>

        <?php

            include_once($_SERVER['DOCUMENT_ROOT']."/common/admin_panel_footer.inc.php");
        ?>

    </div>
</div>

</body>
</html>