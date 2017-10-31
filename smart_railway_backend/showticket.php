<?php
$email=$_POST['email'];
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
if($type=="show")
{
$query1="select * from resticket where email='$email';";
$result2=@mysql_query($query1);
$rows=@mysql_fetch_array($result2);

if(!$rows || $rows['status_']!="active")
{
	header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'msg'=>'no ticket exists'));
}
else
{
	$trainno=$rows['train_no'];
	$date=$rows['train_date'];
	$src=$rows['src'];
	$des=$rows['des'];
	$comp=$rows['compartment'];
	$type=$rows['type'];
header('Content-type: application/json');
  echo json_encode(array('result'=>true,'code'=>200,'msg'=>'success','train_no'=>$trainno,'date'=>$date,'src'=>$src,'des'=>$des,'compartment'=>$comp,'type'=>$type));	
}
}
else if($type == "delete")
{

$query="DELETE FROM resticket WHERE email='$email';";
$result=@mysql_query($query);
if(@mysql_affected_rows()==0)
{
header('Content-type: application/json');
echo json_encode(array('result'=>false,'code'=>401,'msg'=>'no active saved tickets'));
}
else
{

$query1="SELECT * FROM sharelocation WHERE email='$email';";
$result1=@mysql_query($query1);
$rows1=@mysql_fetch_array($result1);
if(!$rows1)
{
header('Content-type: application/json');
echo json_encode(array('result'=>true,'code'=>400,'msg'=>'ticket deleted'));
}
else
{
$query2="DELETE FROM sharelocation WHERE email='$email';";
$result2=@mysql_query($query2);

header('Content-type: application/json');
echo json_encode(array('result'=>true,'code'=>400,'msg'=>'ticket deleted and share location data deleted'));
	
}


}

}

?>