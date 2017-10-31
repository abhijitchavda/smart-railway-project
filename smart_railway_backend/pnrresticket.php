<?php
include('config.php');
date_default_timezone_set('Asia/Kolkata');
$date1= date('y/m/d');
$email=$_POST['email'];
$pnr=$_POST['pnr'];


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

$service_url = 'http://api.railwayapi.com/pnr_status/pnr/'.$pnr.'/apikey/'.$apikey.'/';
$respnse=get_response_from_url($service_url);
$res=json_decode($respnse,true);


if($res['response_code']==200)
{
$trainno=$res['train_num'];
$src=$res['boarding_point']['name'];
$srccode=$res['boarding_point']['code'];
$des=$res['reservation_upto']['name'];
$descode=$res['reservation_upto']['code'];
$date=$res['train_start_date']['year'].'-'.$res['train_start_date']['month'].'-'.$res['train_start_date']['day'];

$query="Insert into resticket values ('$email','$date','$trainno','$src','$srccode','$des','$descode',null,'$date1',null,'active','RESERVATION');";
$result1=@mysql_query($query);
if(@mysql_affected_rows()>0)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>true,'code'=>200,'msg'=>'success'));


}
else
{
	header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'msg'=>'error:server failure'));

}

}
else if($res['response_code']==410)
{
	header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'msg'=>'pnr no not generated, try again after some time.'));
}
else if($res['response_code']==204)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'msg'=>'no response data from API'));

//not running on this date;
}
else if($res['response_code']==403)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'msg'=>'hits finish'));

//not running on this date;
}

else if($res['response_code']==404)
{
	header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'msg'=>'source not responding, try again after a while or enter manually.'));
}
else
{
	header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'msg'=>'failure,try again'));
}


function get_response_from_url($url, $curl = true)
{
	$responseString = '';
		$ch = curl_init( $url );
		$options = array(
				CURLOPT_RETURNTRANSFER => true,
				CURLOPT_HTTPHEADER => array('Content-type: application/json') ,
		);

		curl_setopt_array( $ch, $options );

	 
$responseString=curl_exec($ch);
	
return $responseString;
}

?>