<?php

    /*!
	 * POCKET v1.8
	 *
	 * http://droidoxy.oxywebs.com
	 * droid@oxywebs.com, yash@oxywebs.com
	 *
	 * Copyright 2016 DroidOXY ( http://droidoxy.oxywebs.com/ )
 */

    include_once("../core/init.inc.php");

    if (!admin::isSession()) {

        header("Location: /admin/login.php");
    }

    $accountInfo = array();

    if (isset($_GET['id'])) {

        $accountId = isset($_GET['id']) ? $_GET['id'] : 0;
        $accessToken = isset($_GET['access_token']) ? $_GET['access_token'] : 0;
        $act = isset($_GET['act']) ? $_GET['act'] : '';

        $accountId = helper::clearInt($accountId);

        $account = new account($dbo, $accountId);
        $accountInfo = $account->get();

        if ($accessToken === admin::getAccessToken() && !APP_DEMO) {

            switch ($act) {

                case "close": {

                    $auth->removeAll($accountId);

                    header("Location: /admin/profile.php/?id=".$accountInfo['id']);
                    break;
                }

                case "block": {

                    $account->setState(ACCOUNT_STATE_BLOCKED);

                    header("Location: /admin/profile.php/?id=".$accountInfo['id']);
                    break;
                }

                case "unblock": {

                    $account->setState(ACCOUNT_STATE_ENABLED);

                    header("Location: /admin/profile.php/?id=".$accountInfo['id']);
                    break;
                }

                default: {

                    header("Location: /admin/profile.php/?id=".$accountInfo['id']);
                    exit;
                }
            }
        }

    } else {

        header("Location: /users.php");
    }

    if ($accountInfo['error'] === true) {

        header("Location: /users.php");
    }

    $stats = new stats($dbo);

    $page_id = "account";

    $error = false;
    $error_message = '';

    helper::newAuthenticityToken();

    $css_files = array("admin.css");
    $page_title = $accountInfo['username']." | Account info";

    include_once($_SERVER['DOCUMENT_ROOT']."/common/header.inc.php");

?>

<body class="bg_gray">

    <div id="page_wrap">

    <?php

        include_once($_SERVER['DOCUMENT_ROOT']."/common/admin_panel_topbar.inc.php");
    ?>

    <div id="page_layout">

        <?php

            include_once($_SERVER['DOCUMENT_ROOT']."/common/admin_panel_banner.inc.php");
        ?>

        <div id="page_body">
            <div id="wrap3">
                <div id="wrap2">
                    <div id="wrap1">

                        <div id="content">

                            <div class="header">
                                <div class="title">
                                    <span>Account info</span>
                                </div>
                            </div>

                            <form method="post" class="support_wrap">

                                <div class="ticket_email">
                                    <label class="noselect">Account Username:</label>
                                    <span><?php echo $accountInfo['username']; ?></span>
                                </div>

                                <div class="ticket_email">
                                    <label class="noselect">Account Fullname:</label>
                                    <span><?php echo $accountInfo['fullname']; ?></span>
                                </div>

                                <div class="ticket_email">
                                    <label class="noselect">Account email:</label>
                                    <span><?php echo $accountInfo['email']; ?></span>
                                </div>

                                <div class="ticket_email">
                                    <label class="noselect">SignUp Ip address:</label>
                                    <span><?php if (!APP_DEMO) {echo $accountInfo['ip_addr'];} else {echo "It is not available in the demo version";} ?></span>
                                </div>


                                <div class="ticket_email">
                                    <label class="noselect">SignUp Date:</label>
                                    <span><?php echo date("Y-m-d H:i:s", $accountInfo['regtime']); ?></span>
                                </div>

                                <div class="ticket_title">
                                    <label for="state" class="noselect">Account state:</label>
                                    <div style="margin: 0 0 10px;">
                                        <?php

                                            if ($accountInfo['state'] == ACCOUNT_STATE_ENABLED) {

                                                echo "<span>Account is active</span>";

                                            } else {

                                                echo "<span>Account is blocked</span>";
                                            }
                                        ?>

                                    </div>
                                </div>

                                <div class="ticket_controls">
                                    <?php

                                        if ($accountInfo['state'] == ACCOUNT_STATE_ENABLED) {

                                            ?>
                                                <a class="primary_btn big_btn" href="/admin/profile.php/?id=<?php echo $accountInfo['id']; ?>&access_token=<?php echo admin::getAccessToken(); ?>&act=block">Block account</a>
                                            <?php

                                        } else {

                                            ?>
                                                <a class="primary_btn big_btn" href="/admin/profile.php/?id=<?php echo $accountInfo['id']; ?>&access_token=<?php echo admin::getAccessToken(); ?>&act=unblock">Unblock account</a>
                                            <?php
                                        }
                                    ?>

                                    <a class="primary_btn big_btn" href="/admin/profile.php/?id=<?php echo $accountInfo['id']; ?>&access_token=<?php echo admin::getAccessToken(); ?>&act=close">Close all authorizations</a>
                                </div>
                            </form>

                            <div class="header" style="margin-top: 20px;">
                                <div class="title">
                                    <span>Authorizations</span>
                                </div>
                            </div>

                            <?php

                                $result = $stats->getAuthData($accountInfo['id'], 0);

                                $inbox_loaded = count($result['data']);

                                if ($inbox_loaded != 0) {

                                    ?>

                                        <table class="admin_table">
                                            <tr>
                                                <th class="text-left">Id</th>
                                                <th>Access token</th>
                                                <th>Client Id</th>
                                                <th>Create At</th>
                                                <th>Close At</th>
                                                <th>User agent</th>
                                                <th>Ip address</th>
                                            </tr>

                                    <?php

                                    foreach ($result['data'] as $key => $value) {

                                        draw($value);
                                    }

                                    ?>

                                        </table>

                                    <?php

                                } else {

                                    ?>

                                    <div class="info">
                                        List is empty.
                                    </div>

                                    <?php
                                }
                            ?>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <?php

            include_once($_SERVER['DOCUMENT_ROOT']."/common/admin_panel_footer.inc.php");
        ?>

        <script type="text/javascript">


        </script>

    </div>

</body>
</html>

<?php

    function draw($authObj)
    {
        ?>

        <tr>
            <td class="text-left"><?php echo $authObj['id']; ?></td>
            <td><?php echo $authObj['accessToken']; ?></td>
            <td><?php echo $authObj['clientId']; ?></td>
            <td><?php echo date("Y-m-d H:i:s", $authObj['createAt']); ?></td>
            <td><?php if ($authObj['removeAt'] == 0) {echo "-";} else {echo date("Y-m-d H:i:s", $authObj['removeAt']);} ?></td>
            <td><?php echo $authObj['u_agent']; ?></td>
            <td><?php if (!APP_DEMO) {echo $authObj['ip_addr'];} else {echo "It is not available in the demo version";} ?></td>
        </tr>

        <?php
    }