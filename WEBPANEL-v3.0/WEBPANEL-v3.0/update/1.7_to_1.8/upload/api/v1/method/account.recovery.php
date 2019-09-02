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
    $email = isset($_POST['email']) ? $_POST['email'] : '';

    $clientId = helper::clearInt($clientId);
    $email = helper::escapeText($email);

    if ($clientId != CLIENT_ID) {

        api::printError(ERROR_UNKNOWN, "Error client Id.");
    }

    $result = array("error" => true,
                    "error_code" => ERROR_UNKNOWN);

    if (helper::isCorrectEmail($email)) {

        $accountId = $helper->getUserIdByEmail($email);

        if ($accountId != 0) {

            $account = new account($dbo, $accountId);

            $accountInfo = $account->get();

            if ($accountInfo['error'] === false && $accountInfo['state'] != ACCOUNT_STATE_BLOCKED) {

                $restorePointInfo = $account->restorePointCreate($email, $clientId);

                ob_start();

                ?>

                    <html>
                        <body>
                            This is link <a href="<?php echo APP_URL;  ?>/restore/?hash=<?php echo $restorePointInfo['hash']; ?>"><?php echo APP_URL;  ?>/restore/?hash=<?php echo $restorePointInfo['hash']; ?></a> to reset your password.
                        </body>
                    </html>

                <?php

                $from = SMTP_EMAIL;

                $to = $email;

                $html_text = ob_get_clean();

                $subject = APP_TITLE." | Password reset";

                $mail = new phpmailer();

                $mail->isSMTP();                                      // Set mailer to use SMTP
                $mail->Host = APP_HOST;                               // Specify main and backup SMTP servers
                $mail->SMTPAuth = SMTP_AUTH;                               // Enable SMTP authentication
                $mail->Username = SMTP_USERNAME;                      // SMTP username
                $mail->Password = SMTP_PASSWORD;                      // SMTP password
                $mail->SMTPSecure = SMTP_SECURE;                            // Enable TLS encryption, `ssl` also accepted
                $mail->Port = SMTP_PORT;                                    // TCP port to connect to

                $mail->From = $from;
                $mail->FromName = APP_TITLE;
                $mail->addAddress($to);               // Name is optional

                $mail->isHTML(true);                                  // Set email format to HTML

                $mail->Subject = $subject;
                $mail->Body    = $html_text;

                $mail->send();

                $result = array("error" => false,
                                "error_code" => ERROR_SUCCESS);
            }
        }
    }

    echo json_encode($result);
    exit;
}
