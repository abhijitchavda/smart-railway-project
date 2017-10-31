<?php
$email=$_POST['email'];
$server="localhost";
$database="railway";
$password="";
$username="root";

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
$query1="SELECT * FROM resticket WHERE email='$email' AND status_='active';";
$result2=@mysql_query($query1);
$rows=@mysql_fetch_array($result2);
if($rows)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'msg'=>'show ticket details'));
}
else
{
	header('Content-type: application/json');
  echo json_encode(array('result'=>true,'code'=>200,'msg'=>'enter ticket details'));
}
?>