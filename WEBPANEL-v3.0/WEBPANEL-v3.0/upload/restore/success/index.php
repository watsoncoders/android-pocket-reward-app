<?php

    /*!
     * ifsoft engine v1.0
     *
     * http://ifsoft.com.ua, http://ifsoft.co.uk
     * qascript@ifsoft.co.uk, qascript@mail.ru
     *
     * Copyright 2012-2015 Demyanchuk Dmitry (https://vk.com/dmitry.demyanchuk)
     */

    include_once($_SERVER['DOCUMENT_ROOT']."/core/init.inc.php");

    $page_id = "restore_success";

    $css_files = array("admin.css");
    $page_title = APP_TITLE;

    include_once($_SERVER['DOCUMENT_ROOT']."/common/header.inc.php");
?>

<body class="main_page">

<div id="page_wrap">

    <!-- BEGIN TOP BAR -->
    <?php
        include_once($_SERVER['DOCUMENT_ROOT']."/common/topbar.inc.php");
    ?>
    <!-- END TOP BAR -->

    <div id="page_layout" class="no_footer_border">
        <div id="page_body">
            <div id="wrap3">
                <div id="wrap2">
                    <div id="wrap1">
                        <div id="content">
                            <div class="note orange">
                                <div class="title">Success!</div>
                                A new password has been successfully installed!
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

            <!-- BEGIN FOOTER -->
            <?php
                include_once($_SERVER['DOCUMENT_ROOT']."/common/footer.inc.php");
            ?>
            <!-- END FOOTER -->

        </div>
    </div>

</body>
</html>