<?php
$database="railway";
$username="root";
$server="localhost";
$password="";
date_default_timezone_set('Asia/Kolkata');
$date= date('y/m/d');

$email=$_POST['email'];
$newpass=$_POST['newpassword'];
$oldpass=$_POST['oldpassword'];

$conn=@mysql_connect($server,$username,$password,$database);
//if(!$conn)
//	echo "not connected";
//else
//	echo "connected";

$select=@mysql_select_db("railway",$conn);
//if(!$select)
	//echo "db not selected".mysql_error();
//else
//	echo "db selected";

$query1="select password_,status from profile where email='$email';";
$result1=@mysql_query($query1);
$row=@mysql_fetch_array($result1);
if($row['status']=='active')
{
if($row['password_']==$oldpass)
{
$query="UPDATE profile SET password_='$newpass', mod_date='$date' WHERE email='$email';";
$result=@mysql_query($query);
if(@mysql_affected_rows()>0)
{
	
    //echo "Your password has been changed.";

header('Content-type: application/json');
  echo json_encode(array('result'=>true,'code'=>200,'msg'=>'success'));
}
else
{
	//echo "error:try again";
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'msg'=>'error'));
}

}
else
{

header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'msg'=>'wrong old password'));
	//echo "wrong old password";
}
}
else
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'msg'=>'not loged in'));

}
?>