<?php

$database="railway";
$username="root";
$server="localhost";
$password="";
date_default_timezone_set('Asia/Kolkata');
$date= date('y/m/d');

$email=$_POST['email'];

$conn=@mysql_connect($server,$username,$password,$database);
//if(!$conn)
//	echo "not connected";
//else
//	echo "connected";

$select=@mysql_select_db("railway",$conn);
//if(!$select)
//	echo "db not selected".mysql_error();
//else
//	echo "db selected";
$query1="select email from profile where email='$email';";
$result1=@mysql_query($query1);
$row=@mysql_fetch_array($result1);
if($row)
{
$pass1=randomPassword(9,1,"lower_case,upper_case,numbers,special_symbols");
$pass=$pass1['0'];
//echo $pass;
$emailBody = "Respected sir,

Your New password for Rail Beat Account is ".$pass.". Please login using this password and change your password after login.

regards
Rail Beat";
//echo $emailBody;


$to=$email;
if(!class_exists('PHPMailer')) {
    require('C:\wamp64\www\beacons\PHPMailer-master\class.phpmailer.php');
	require('C:\wamp64\www\beacons\PHPMailer-master\class.smtp.php');
	require('C:\wamp64\www\beacons\PHPMailer-master\PHPMailerAutoload.php');
}

require_once("mail_configuration.php");
$mail = new PHPMailer();





$mail->IsSMTP();
$mail->SMTPDebug = 0;
$mail->SMTPAuth = TRUE;
$mail->SMTPSecure = "tls";
$mail->Port     = PORT;  
$mail->Username = MAIL_USERNAME;
$mail->Password = MAIL_PASSWORD;
$mail->Host     = MAIL_HOST;
$mail->Mailer   = MAILER;

$mail->SetFrom(SERDER_EMAIL, SENDER_NAME);
$mail->AddReplyTo(SERDER_EMAIL, SENDER_NAME);
$mail->ReturnPath=SERDER_EMAIL;	
$mail->AddAddress($to);
$mail->Subject = "Rail Beat : Forgot Password Recovery";		
$mail->Body = $emailBody; 
$mail->AltBody = 'This is a plain-text message body';
$mail->IsHTML(true);
//echo "<pre>";
//print_r($mail);
//try {
  if($mail->Send())
  {

$query="UPDATE profile SET password_='$pass',mod_date='$date' where email='$email';";
$result=@mysql_query($query);
if(@mysql_affected_rows()>0)
{
	header('Content-type: application/json');
  echo json_encode(array('result'=>true,'code'=>200,'msg'=>'success'));
  }
else
{

header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'msg'=>'error:database'));
}
}
else
{
    header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'msg'=>'error:mail'));
}
}
else
{
    header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'msg'=>'invalid email'));
}
// catch (phpmailerException $e) {
  //echo $e->errorMessage(); 
//} catch (Exception $e) {
  //echo $e->getMessage();
//}

function randomPassword($length,$count, $characters) {
 
    $symbols = array();
    $passwords = array();
    $used_symbols = '';
    $pass = '';
     
    $symbols["lower_case"] = 'abcdefghijklmnopqrstuvwxyz';
    $symbols["upper_case"] = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $symbols["numbers"] = '1234567890';
    $symbols["special_symbols"] = '!?~@#-_+<>[]{}';
 
    $characters = explode(",", $characters); 
    foreach ($characters as $key=>$value) {
        $used_symbols .= $symbols[$value];
    }
    $symbols_length = strlen($used_symbols) - 1; 
     
    for ($p = 0; $p < $count; $p++) {
        $pass = '';
        for ($i = 0; $i < $length; $i++) {
            $n = rand(0, $symbols_length); 
            $pass .= $used_symbols[$n]; 
        }
        $passwords[] = $pass;
    }
     
    return $passwords; 
}
?>