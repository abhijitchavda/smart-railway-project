	<?php
	date_default_timezone_set('Asia/Kolkata');
	$date1= date('y/m/d');
	$email=$_POST['email'];
	$date=$_POST['date'];
	$trainno=$_POST['trainno'];
	$src=$_POST['src'];
	$srccode=$_POST['srccode'];
	$des=$_POST['des'];
	$descode=$_POST['descode'];
	$compartment=$_POST['compartment'];
	$type=$_POST['type'];
	
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
	$query1="select * from resticket where email='$email';";
	$result2=@mysql_query($query1);
	$rows=@mysql_fetch_array($result2);

	if(!$rows || $rows['status_']!="active")
	{
$query="Insert into resticket values ('$email','$date','$trainno','$src','$srccode','$des','$descode','$compartment','$date1',null,'active','$type');";
$result1=@mysql_query($query);
if(@mysql_affected_rows()==0)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'msg'=>'error:server failure'));}
else
{
header('Content-type: application/json');
  echo json_encode(array('result'=>true,'code'=>200,'msg'=>'success'));
}
}
else
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'msg'=>'you already have an active ticket'));
}
?>