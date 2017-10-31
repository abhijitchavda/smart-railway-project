<?php
$email=$_POST['email'];
$server="localhost";
$database="railway";
$password="";
$username="root";
$conn=@mysql_connect($server,$username,$password,$database);
$result=@mysql_select_db("railway",$conn);
$query1="select name,city,contact_no from profile where email='$email';";
$result2=@mysql_query($query1);
$rows=@mysql_fetch_array($result2);
if($rows)
{
	$name=$rows['name'];
	$city=$rows['city'];
	$contact_no=$rows['contact_no'];

	header('Content-type: application/json');
  echo json_encode(array('result'=>true,'code'=>200,'msg'=>'success','name'=>$name,'city'=>$city,'contact_no'=>$contact_no));	
	
}
else {
	header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'msg'=>'server error'));

}?>