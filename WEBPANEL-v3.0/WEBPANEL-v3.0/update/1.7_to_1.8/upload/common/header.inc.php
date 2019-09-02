<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><?php echo $page_title; ?></title>
    <meta name="google-site-verification" content="" />
    <meta name='yandex-verification' content='' />
    <meta name="msvalidate.01" content="" />
    <meta charset="utf-8">
    <meta name="description" content="">
    <link href="/img/favicon.png" rel="shortcut icon" type="image/x-icon">
    <?php

        foreach($css_files as $css): ?>
        <link rel="stylesheet" href="/css/<?php echo $css."?x=65"; ?>" type="text/css" media="screen">

    <?php

        endforeach;
    ?>
</head>