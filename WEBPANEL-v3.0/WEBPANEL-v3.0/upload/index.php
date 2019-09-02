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

    if (admin::isSession()) {

        header("Location: admin.php");
    }

    $css_files = array();
    $page_title = "POCKET - Android Rewards App";

    include_once($_SERVER['DOCUMENT_ROOT']."/config/config.php");
    include_once($_SERVER['DOCUMENT_ROOT']."/common/header.inc.php");
?>

<body style="color: #525252; font-family: 'Source Sans Pro', sans-serif; font-size: 16px; -webkit-font-smoothing: antialiased; margin: 0">

    <div style="text-align: center;font-size: 56px; font-weight: 200; margin: 140px 0;">
        <?php echo $APP_TITLE; ?>
        <div style="margin-top: 50px;">
            <a href="<?php echo GOOGLE_PLAY_LINK; ?>" rel="nofollow">
                <img src="/img/googleplay.png">
            </a>
        </div>
        <div style="margin-top: 40px; font-size: 22px;">
            <a style="color: #3A6DA5" href="/admin/login.php">Go to admin panel -></a>
        </div>
    </div>
</body>
</html>

