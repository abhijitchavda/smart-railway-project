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
$query1="UPDATE profile SET status='inactive' WHERE email='$email' AND status='active';";
$result2=@mysql_query($query1);
if(@mysql_affected_rows()>0)
{
	header('Content-type: application/json');
  	echo json_encode(array('result'=>true,'code'=>200,'msg'=>'logged out'));
}
else
{
	header('Content-type: application/json');
  	echo json_encode(array('result'=>false,'code'=>201,'msg'=>'error:failure'));
}


?>