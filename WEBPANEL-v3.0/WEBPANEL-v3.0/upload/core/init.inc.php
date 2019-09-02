<?php

    /*!
	 * POCKET v1.8
	 *
	 * http://droidoxy.oxywebs.com
	 * droid@oxywebs.com, yash@oxywebs.com
	 *
	 * Copyright 2016 DroidOXY ( http://droidoxy.oxywebs.com/ )
	 */

session_start();

error_reporting(E_ALL);

include_once($_SERVER['DOCUMENT_ROOT']."/config/db.inc.php");

foreach ($C as $name => $val) {

    define($name, $val);
}

foreach ($B as $name => $val) {

    define($name, $val);
}

$dsn = "mysql:host=".DB_HOST.";dbname=".DB_NAME;
$dbo = new PDO($dsn, DB_USER, DB_PASS, array(PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES utf8"));

function __autoload($class)
{
    $filename = $_SERVER['DOCUMENT_ROOT']."/class/class.".$class.".inc.php";

    if (file_exists($filename)) {

        include_once($filename);
    }
}

ini_set('session.cookie_domain', APP_HOST);
session_set_cookie_params(0, '/', APP_HOST);

$helper = new helper($dbo);
$auth = new auth($dbo);
