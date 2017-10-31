<?php
include('config.php');
$server="localhost";
$database="railway";
$password="";
$username="root";

//$beacon_id=$_POST['beaconid'];//beacon id from application
$email=$_POST['email'];//email from application


$conn=@mysql_connect($server,$username,$password,$database);
//if(!$conn)
//echo "<br>not connected";
//else 
//echo "<br>connected";
$result=@mysql_select_db("railway",$conn);
//if(!$result)
//echo "<br>db not selected";
//else
//echo "<br>db selected";
$query="DELETE FROM resticket WHERE email='$email';";
$result=@mysql_query($query);
if(mysql_affected_rows()==0)
{
	header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'msg'=>'no active saved tickets'));
}
else
{

$query1="SELECT * FROM sharelocation WHERE email='$email';";
$result1=@mysql_query($query1);
$rows1=@mysql_fetch_array($result1);
if(!$rows1)
{
header('Content-type: application/json');
echo json_encode(array('result'=>true,'code'=>200,'msg'=>'ticket deleted'));
}
else
{
$query1="DELETE FROM sharelocation WHERE email='$email';";
$result1=@mysql_query($query1);

header('Content-type: application/json');
echo json_encode(array('result'=>true,'code'=>200,'msg'=>'ticket deleted and share location data deleted'));
	
}


}



?>