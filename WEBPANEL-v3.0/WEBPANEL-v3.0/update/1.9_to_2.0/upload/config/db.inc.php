<?php

    /*!
	 * POCKET v1.8
	 *
	 * http://droidoxy.oxywebs.com
	 * droid@oxywebs.com, yash@oxywebs.com
	 *
	 * Copyright 2017 DroidOXY ( http://droidoxy.oxywebs.com/ )
 */

include_once($_SERVER['DOCUMENT_ROOT']."/config/config.php");

$C = array();
$B = array();

$B['APP_DEMO'] = false;                                     

$B['APP_PATH'] = "app";
$B['APP_VERSION'] = "1.1";
$B['APP_NAME'] = $APP_NAME;
$B['APP_TITLE'] = $APP_TITLE;
$B['APP_VENDOR'] = "DroidOXY ";
$B['APP_YEAR'] = "2016";
$B['APP_AUTHOR'] = "DroidOXY";
$B['APP_HOST'] = $APP_HOST;                       //edit to your domain example: yourdomain.com
$B['APP_URL'] = $Server_URL;                 //edit to your domain url
$B['GOOGLE_PLAY_LINK'] = $Google_Play_Link;

$B['CLIENT_ID'] = 1;                                        //Android App Client ID (only for android application)

$C['COMPANY_URL'] = $Company_URL;

// SMTP Settings | For password recovery

$B['SMTP_AUTH'] = $SMTP_AUTH;                                     //SMTP auth (Enable SMTP authentication)
$B['SMTP_SECURE'] = $SMTP_SECURE;                                  //SMTP secure (Enable TLS encryption, `ssl` also accepted)
$B['SMTP_PORT'] = $SMTP_PORT;                                      //SMTP port (TCP port to connect to)
$B['SMTP_EMAIL'] = $SMTP_EMAIL;                     //SMTP email
$B['SMTP_USERNAME'] = $SMTP_USERNAME;                  //SMTP username
$B['SMTP_PASSWORD'] = $SMTP_PASSWORD;                      //SMTP password

//Please edit database data

$C['DB_HOST'] = $host;                                //localhost or your db host
$C['DB_USER'] = $user;                             //your db user
$C['DB_PASS'] = $pass;                         //your db password
$C['DB_NAME'] = $database;                             //your db name


// Please Do Not Edit Below
$C['ERROR_SUCCESS'] = 0;

$C['ERROR_UNKNOWN'] = 100;
$C['ERROR_ACCESS_TOKEN'] = 101;

$C['ERROR_LOGIN_TAKEN'] = 300;
$C['ERROR_EMAIL_TAKEN'] = 301;
$C['ERROR_IP_TAKEN'] = 302;

$C['ERROR_ACCOUNT_ID'] = 400;

$C['GCM_NOTIFY_CONFIG'] = 0;
$C['GCM_NOTIFY_SYSTEM'] = 1;
$C['GCM_NOTIFY_CUSTOM'] = 2;
$C['GCM_NOTIFY_LIKE'] = 3;
$C['GCM_NOTIFY_ANSWER'] = 4;
$C['GCM_NOTIFY_QUESTION'] = 5;
$C['GCM_NOTIFY_COMMENT'] = 6;
$C['GCM_NOTIFY_FOLLOWER'] = 7;

$C['ACCOUNT_STATE_ENABLED'] = 0;
$C['ACCOUNT_STATE_DISABLED'] = 1;
$C['ACCOUNT_STATE_BLOCKED'] = 2;
$C['ACCOUNT_STATE_DEACTIVATED'] = 3;

