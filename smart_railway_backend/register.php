<?php
date_default_timezone_set('America/Los_Angeles');
$date= date('y/m/d');
$name=$_POST['name'];
$email=$_POST['email'];
$contact=$_POST['phone'];
$pass=$_POST['password'];
$city=$_POST['address'];


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
$query="select email from profile where email='$email';";
$result1=@mysql_query($query);
$rows=@mysql_fetch_array($result1);
if($rows)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'msg'=>'email exist'));
}
else{
$create="Insert into profile values ('$email','$name','$pass','$city','$contact','$date',null,null);";
$result=@mysql_query($create);
if(@mysql_affected_rows()==0)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'msg'=>'error'));}
else
{
header('Content-type: application/json');
  echo json_encode(array('result'=>true,'code'=>200,'msg'=>'success'));
}
}
?>
