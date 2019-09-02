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
include_once($_SERVER['DOCUMENT_ROOT']."/config/api.inc.php");

if (!empty($_POST)) {

    $clientId = isset($_POST['clientId']) ? $_POST['clientId'] : 0;

    $username = isset($_POST['username']) ? $_POST['username'] : '';
    $password = isset($_POST['password']) ? $_POST['password'] : '';

    $clientId = helper::clearInt($clientId);

    $username = helper::clearText($username);
    $username = helper::escapeText($username);

    $password = helper::clearText($password);
    $password = helper::escapeText($password);

    if ($clientId != CLIENT_ID) {

        api::printError(ERROR_UNKNOWN, "Error client Id.");
    }

    $access_data = array();

    $account = new account($dbo);
    $access_data = $account->signin($username, $password);

    unset($account);

    if ($access_data["error"] === false) {

        $auth = new auth($dbo);
        $access_data = $auth->create($access_data['accountId'], $clientId);

        if ($access_data['error'] === false) {

            $account = new account($dbo, $access_data['accountId']);
            $access_data['account'] = array();

            array_push($access_data['account'], $account->get());
        }
    }

    echo json_encode($access_data);
    exit;
}
