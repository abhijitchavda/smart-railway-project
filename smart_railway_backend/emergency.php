<?php
include('config.php');
$server="localhost";
$database="railway";
$password="";
$username="root";

$beacon_id=$_POST['beaconid'];
$email=$_POST['email'];
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
$query="SELECT name,city,contact_no FROM profile WHERE email='$email';";
$result_=@mysql_query($query);
$rows=@mysql_fetch_array($result_);

if(!$rows)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'msg'=>'server error:profile selection'));
}
else
{
	$name=$rows['name'];
	$address=$rows['city'];
	$contact_no=$rows['contact_no'];

	$query1="SELECT sta_name,location FROM beacon WHERE beaconid='$beacon_id';";
$result1=@mysql_query($query1);
$rows1=@mysql_fetch_array($result1);


if(!$rows1)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'msg'=>'server error:beacon extraction'));
}
else
{

	$station_name=$rows1['sta_name'];
	$location=$rows1['location'];
	$query2="SELECT medic_no,police_no FROM emergency WHERE sta_name='$station_name';";
	$result2=@mysql_query($query2);
	$rows2=@mysql_fetch_array($result2);
	if(!$rows2)
	{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'msg'=>'server error:emergency contact'));
	}
	else
	{
		if($type=="medic")
		{
			$contact=$rows2['medic_no'];
			
$message = "attention required at ".$station_name." at location ".$location.".Details-> Name: ".$name.", address: ".$address.", contact no: ".$contact_no.".";
	
   
$url = "http://sms.hspsms.com:/sendSMS";
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POSTFIELDS, 'username=hspdemo&message='.$message.'&sendername=HSPSMS&smstype=TRANS&numbers='.$contact.'&apikey='.$apisms.'');
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_TIMEOUT, 8);
        $result10 = curl_exec($ch);
       $res=json_decode($result10,true);
        if($res[0]["responseCode"]=="Message SuccessFully Submitted")
        {
            header('Content-type: application/json');
  echo json_encode(array('result'=>true,'code'=>200,'msg'=>$message,'type'=>'medic'));
        }
        else
        {
           header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>200,'msg'=>$message,'type'=>'medic'));
}




/*			// Authorisation details.
	$username = "chavda.abhijit@yahoo.com";
	$hash = "f28b560c05619f478c826b0f65ab4f6ecb5279175077dd1393cc45168bfd7b7c";

	// Config variables. Consult http://api.textlocal.in/docs for more info.
	$test = "0";

	// Data for text message. This is the text message data.
	$sender = "TXTLCL"; // This is who the message appears to be from.
	$numbers = $contact; // A single number or a comma-seperated list of numbers
	$message = "attention required at ".$station_name." at location ".$location.".Details-> Name: ".$name.", address: ".$address.", contact no: ".$contact_no.".";
	// 612 chars or less
	// A single number or a comma-seperated list of numbers
	$message = urlencode($message);
	$data = "username=".$username."&hash=".$hash."&message=".$message."&sender=".$sender."&numbers=".$numbers."&test=".$test;
	$ch = curl_init('http://api.textlocal.in/send/?');
	curl_setopt($ch, CURLOPT_POST, true);
	curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$result = curl_exec($ch); // This is the result from the API
	$res=json_decode($result,true);
	if($res['status']=="success")
	{
header('Content-type: application/json');
  echo json_encode(array('result'=>true,'code'=>200,'msg'=>$message,'type'=>'medic'));
	}
	else
	{
		header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'msg'=>$message));
	}
	//curl_close($ch);
*/
	}
		else if($type=="police")
		{
			$contact=$rows2['police_no'];
$message = "attention required at ".$station_name." at location ".$location.".Details-> Name: ".$name.", address: ".$address.", contact no: ".$contact_no.".";
	
$url = "http://sms.hspsms.com:/sendSMS";
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POSTFIELDS, 'username=hspdemo&message='.$message.'&sendername=HSPSMS&smstype=TRANS&numbers='.$contact.'&apikey='.$apisms.'');
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_TIMEOUT, 8);
        $result10 = curl_exec($ch);
       $res=json_decode($result10,true);
        if($res[0]["responseCode"]=="Message SuccessFully Submitted")
        {
            header('Content-type: application/json');
  echo json_encode(array('result'=>true,'code'=>200,'msg'=>$message,'type'=>'police'));
        }
        else
        {
           header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>200,'msg'=>$message,'type'=>'police'));
}

		/*	// Authorisation details.
	$username = "chavda.abhijit@yahoo.com";
	$hash = "f28b560c05619f478c826b0f65ab4f6ecb5279175077dd1393cc45168bfd7b7c";

	// Config variables. Consult http://api.textlocal.in/docs for more info.
	$test = "0";

	// Data for text message. This is the text message data.
	$sender = "TXTLCL"; // This is who the message appears to be from.
	$numbers = $contact; // A single number or a comma-seperated list of numbers
	$message = "attention required at ".$station_name." at location ".$location.".Details-> Name: ".$name.", address: ".$address.", contact no: ".$contact_no.".";
	// 612 chars or less
	// A single number or a comma-seperated list of numbers
	$message = urlencode($message);
	$data = "username=".$username."&hash=".$hash."&message=".$message."&sender=".$sender."&numbers=".$numbers."&test=".$test;
	$ch = curl_init('http://api.textlocal.in/send/?');
	curl_setopt($ch, CURLOPT_POST, true);
	curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$result = curl_exec($ch); // This is the result from the API
	$res=json_decode($result,true);
	if($res['status']=="success")
	{
header('Content-type: application/json');
  echo json_encode(array('result'=>true,'code'=>200,'msg'=>$message,'type'=>'police'));
	}
	else
	{
		header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'msg'=>$message));
	}*/
	//curl_close($ch);

		}
	}
}

}
?>
