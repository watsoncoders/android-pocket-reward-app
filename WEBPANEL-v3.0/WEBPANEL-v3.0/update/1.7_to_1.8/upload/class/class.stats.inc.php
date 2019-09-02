<?php

    /*!
	 * POCKET v1.8
	 *
	 * http://droidoxy.oxywebs.com
	 * droid@oxywebs.com, yash@oxywebs.com
	 *
	 * Copyright 2016 DroidOXY ( http://droidoxy.oxywebs.com/ )
 */

class stats extends db_connect
{
    private $requestFrom = 0;

    public function __construct($dbo = NULL)
    {
        parent::__construct($dbo);

    }

    public function getUsersCount($accountState)
    {
        $stmt = $this->db->prepare("SELECT count(*) FROM users WHERE state = (:state)");
        $stmt->bindParam(":state", $accountState, PDO::PARAM_INT);
        $stmt->execute();

        return $number_of_rows = $stmt->fetchColumn();
    }

    private function getMaxAccountId()
    {
        $stmt = $this->db->prepare("SELECT MAX(id) FROM users");
        $stmt->execute();

        return $number_of_rows = $stmt->fetchColumn();
    }

    private function getMaxAuthId()
    {
        $stmt = $this->db->prepare("SELECT MAX(id) FROM access_data");
        $stmt->execute();

        return $number_of_rows = $stmt->fetchColumn();
    }

    public function getAccounts($userId = 0)
    {
        if ($userId == 0) {

            $userId = $this->getMaxAccountId();
            $userId++;
        }

        $users = array("error" => false,
                        "error_code" => ERROR_SUCCESS,
                        "userId" => $userId,
                        "users" => array());

        $stmt = $this->db->prepare("SELECT id FROM users WHERE id < (:userId) ORDER BY id");
        $stmt->bindParam(':userId', $userId, PDO::PARAM_INT);

        if ($stmt->execute()) {

            while ($row = $stmt->fetch()) {

                $account = new account($this->db, $row['id']);

                $accountInfo = $account->get();

                array_push($users['users'], $accountInfo);

                $users['userId'] = $accountInfo['id'];

                unset($accountInfo);
            }
        }

        return $users;
    }

    public function getAuthData($accountId, $authId = 0)
    {
        if ($authId == 0) {

            $authId = $this->getMaxAuthId();
            $authId++;
        }

        $result = array("error" => false,
                        "error_code" => ERROR_SUCCESS,
                        "authId" => $authId,
                        "data" => array());

        $stmt = $this->db->prepare("SELECT * FROM access_data WHERE accountId = (:accountId) AND id < (:authId) ORDER BY id");
        $stmt->bindParam(':authId', $authId, PDO::PARAM_INT);
        $stmt->bindParam(':accountId', $accountId, PDO::PARAM_INT);

        if ($stmt->execute()) {

            while ($row = $stmt->fetch()) {;

                $dataInfo = array("id" => $row['id'],
                                  "accountId" => $row['accountId'],
                                  "accessToken" => $row['accessToken'],
                                  "clientId" => $row['clientId'],
                                  "createAt" => $row['createAt'],
                                  "removeAt" => $row['removeAt'],
                                  "u_agent" => $row['u_agent'],
                                  "ip_addr" => $row['ip_addr']);

                array_push($result['data'], $dataInfo);

                $result['authId'] = $row['id'];

                unset($dataInfo);
            }
        }

        return $result;
    }

    public function setRequestFrom($requestFrom)
    {
        $this->requestFrom = $requestFrom;
    }

    public function getRequestFrom()
    {
        return $this->requestFrom;
    }
}

