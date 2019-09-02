<?php

    /*!
	 * POCKET v1.8
	 *
	 * http://droidoxy.oxywebs.com
	 * droid@oxywebs.com, yash@oxywebs.com
	 *
	 * Copyright 2016 DroidOXY ( http://droidoxy.oxywebs.com/ )
 */

	try {

		$sth = $dbo->prepare("CREATE TABLE IF NOT EXISTS Requests (
  rid int(11) NOT NULL AUTO_INCREMENT,
  request_from varchar(255) NOT NULL,
  dev_name varchar(255) NOT NULL,
  dev_man varchar(255) NOT NULL,
  gift_name varchar(255) NOT NULL,
  req_amount varchar(255) NOT NULL,
  points_used varchar(255) NOT NULL,
  date varchar(255) NOT NULL,
  status int(11) NOT NULL DEFAULT '1',
  username varchar(255) NOT NULL,
  PRIMARY KEY (rid)
) CHARACTER SET utf8 COLLATE utf8_unicode_ci
AUTO_INCREMENT=29");

		$sth->execute();
		
		$sth = $dbo->prepare("CREATE TABLE IF NOT EXISTS tracker (
  id int(255) NOT NULL AUTO_INCREMENT,
  username varchar(255) NOT NULL,
  points varchar(255) NOT NULL,
  type varchar(255) NOT NULL,
  date date NOT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_unicode_ci");

		$sth->execute();


		$sth = $dbo->prepare("CREATE TABLE IF NOT EXISTS referers (
  id int(255) NOT NULL AUTO_INCREMENT,
  username varchar(255) NOT NULL,
  referer varchar(255) NOT NULL,
  points varchar(255) NOT NULL,
  type varchar(255) NOT NULL,
  date date NOT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_unicode_ci");

		$sth->execute();

		
		$sth = $dbo->prepare("CREATE TABLE IF NOT EXISTS track_red (
  id int(255) NOT NULL AUTO_INCREMENT,
  username varchar(255) NOT NULL,
  points varchar(255) NOT NULL,
  type varchar(255) NOT NULL,
  date varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_unicode_ci");

		$sth->execute();
		
		$sth = $dbo->prepare("CREATE TABLE IF NOT EXISTS Completed (
  rid int(11) NOT NULL,
  request_from varchar(255) NOT NULL,
  dev_name varchar(255) NOT NULL,
  dev_man varchar(255) NOT NULL,
  gift_name varchar(255) NOT NULL,
  req_amount varchar(255) NOT NULL,
  points_used varchar(255) NOT NULL,
  date varchar(255) NOT NULL,
  status int(11) NOT NULL DEFAULT '1',
  username varchar(255) NOT NULL,
  PRIMARY KEY (rid)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_unicode_ci");

		$sth->execute();



$sth = $dbo->prepare("CREATE TABLE IF NOT EXISTS users (
								  id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
								  gcm_regid TEXT,
								  state INT(10) UNSIGNED DEFAULT 0,
								  fullname VARCHAR(150) NOT NULL DEFAULT '',
								  salt CHAR(3) NOT NULL DEFAULT '',
								  passw VARCHAR(32) NOT NULL DEFAULT '',
								  login VARCHAR(50) NOT NULL DEFAULT '',
								  email VARCHAR(64) NOT NULL DEFAULT '',
								  regtime INT(10) UNSIGNED DEFAULT 0,
								  ip_addr CHAR(32) NOT NULL DEFAULT '',
								  points VARCHAR(255) NOT NULL DEFAULT '0',
								  refer VARCHAR(255) NOT NULL DEFAULT '0',
  								PRIMARY KEY  (id), UNIQUE KEY (login)) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_unicode_ci");
		$sth->execute();

		$sth = $dbo->prepare("CREATE TABLE IF NOT EXISTS admins (
								id int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
								username VARCHAR(50) NOT NULL DEFAULT '',
                                salt CHAR(3) NOT NULL DEFAULT '',
                                password VARCHAR(32) NOT NULL DEFAULT '',
                                fullname VARCHAR(150) NOT NULL DEFAULT '',
                                createAt int(11) UNSIGNED DEFAULT 0,
								u_agent varchar(300) DEFAULT '',
								ip_addr CHAR(32) NOT NULL DEFAULT '',
								PRIMARY KEY  (id)) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_unicode_ci");
		$sth->execute();

		$sth = $dbo->prepare("CREATE TABLE IF NOT EXISTS access_data (
								id int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
								accountId int(11) UNSIGNED NOT NULL,
								accessToken varchar(32) DEFAULT '',
								clientId int(11) UNSIGNED DEFAULT 0,
								createAt int(10) UNSIGNED DEFAULT 0,
								removeAt int(10) UNSIGNED DEFAULT 0,
								u_agent varchar(300) DEFAULT '',
								ip_addr CHAR(32) NOT NULL DEFAULT '',
								PRIMARY KEY  (id)) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_unicode_ci");
		$sth->execute();

        $sth = $dbo->prepare("CREATE TABLE IF NOT EXISTS restore_data (
								id int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
								accountId int(11) UNSIGNED NOT NULL,
								hash varchar(32) DEFAULT '',
								email VARCHAR(64) NOT NULL DEFAULT '',
								clientId int(11) UNSIGNED DEFAULT 0,
								createAt int(10) UNSIGNED DEFAULT 0,
								removeAt int(10) UNSIGNED DEFAULT 0,
								u_agent varchar(300) DEFAULT '',
								ip_addr CHAR(32) NOT NULL DEFAULT '',
								PRIMARY KEY  (id)) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_unicode_ci");
        $sth->execute();

	} catch (Exception $e) {

		die ($e->getMessage());
	}
