<?php
$server="localhost";
$database="railway";
$password="";
$username="root";
$email=$_POST['email'];
$lat=$_POST['lat'];
$lon=$_POST['lon'];


$conn=@mysql_connect($server,$username,$password,$database);
$result=@mysql_select_db("railway",$conn);

$query="UPDATE sharelocation SET lat='$lat',lon='$lon' WHERE email='$email'";
$result=@mysql_query($query);
if(@mysql_affected_rows()==0)
{

	//header('Content-type: application/json');
  //echo json_encode(array('result'=>false,'code'=>202,'msg'=>'server error:unable to update'));

}
else
{
	//header('Content-type: application/json');
  //echo json_encode(array('result'=>true,'code'=>200,'msg'=>'updated'));
}
?>
