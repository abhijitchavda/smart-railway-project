<?php
//echo "hii"; exit;

$database="railway";
$username="root";
$server="localhost";
$password="";

$email=$_POST['email'];
$pass=$_POST['password'];

$conn=@mysql_connect($server,$username,$password,$database);
//if(!$conn)
//	echo "not connected";
//else
//	echo "connected";

$select=@mysql_select_db("railway",$conn);
//if(!$select)
//	echo "db not selected".mysql_error();
//else
//	echo "db selected";

$query="select password_ from profile where email='$email';";
$result=@mysql_query($query);
$rows=@mysql_fetch_array($result);

if($rows)
{

	if($rows['password_']==$pass)
	{

$query1="UPDATE PROFILE SET status='active' where email='$email';";
$result=@mysql_query($query1);

/*$query="select email,name,city,contact_no from profile where email='$email';";
$result1=mysql_query($query,$conn);

$rows=mysql_fetch_array($result1);
echo "HELLO ".$rows['name'];

echo "<br>
<br>WELCOME";

echo "<br>
<br>your email id is : ".$rows['email'];

echo "<br>
<br>your contact no. is : ".$rows['contact_no'];

echo "<br>
<br>your city is : ".$rows['city'];
*/
header('Content-type: application/json');
    echo json_encode(array('result'=>true,'code'=>200,'msg'=>'success','email'=>$email),true);
	}

	else
	{
		
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'msg'=>'wrong password'),true);

	}
}
else
{
	header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'msg'=>'wrong email'),true);
}





?>
