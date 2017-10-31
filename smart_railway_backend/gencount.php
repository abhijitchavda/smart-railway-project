<?php
$server="localhost";
$database="railway";
$password="";
$username="root";

//$beacon_id=$_POST['beaconid'];//beacon id from application
$email=$_POST['email'];//email from application
$type=$_POST['type'];

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
if($type=="ADD")
{
$query="INSERT INTO gen VALUES('$email')";
$result=@mysql_query($query);
if(@mysql_affected_rows()>0)
{
header('Content-type: application/json');
 echo json_encode(array('result'=>true,'code'=>200,'type'=>'gen_add','msg'=>'you are listed'));
}	
else
{
header('Content-type: application/json');
echo json_encode(array('result'=>false,'code'=>202,'type'=>'gen_add','msg'=>'you are not listed'));
}
}
if($type=="SUB")
{
$query1="SELECT EMAIL FROM gen WHERE EMAIL='$email'";
$result1=@mysql_query($query1);
if(@mysql_affected_rows()>0)
{
$query="DELETE FROM gen WHERE EMAIL='$email'";
$result=@mysql_query($query);
if(@mysql_affected_rows()>0)
{
header('Content-type: application/json');
echo json_encode(array('result'=>true,'code'=>200,'type'=>'gen_sub','msg'=>'you are deleted'));
}	
else
{
header('Content-type: application/json');
echo json_encode(array('result'=>false,'code'=>202,'type'=>'gen_sub','msg'=>'you are not deleted'));
}

}
else
{
header('Content-type: application/json');
echo json_encode(array('result'=>false,'code'=>203,'type'=>'gen_sub','msg'=>'you do not exist'));

}
}

?>