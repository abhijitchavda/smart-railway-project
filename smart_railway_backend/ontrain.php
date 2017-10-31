<?php
include('config.php');
$server="localhost";
$database="railway";
$password="";
$username="root";

//$beacon_id=$_POST['beaconid'];//beacon id from application
$email=$_POST['email'];//email from application
$lat1=$_POST['lat'];
$lon1=$_POST['lon'];
$flag="N";

//----------demo--
//$lat1=23.026748;
//$lon1=72.600875;
//--------------
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
$query="select train_date,train_no,des,des_code,compartment from resticket where email='$email' AND status_='active';";
$result=@mysql_query($query);
$rows=@mysql_fetch_array($result);


if(!$rows)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'type'=>'ON_TRAIN','msg'=>'no active saved tickets'));

}
else
{
$y=$rows['train_date'];//ticket date stored in database
$train_no=$rows['train_no'];//train no
$station_ticket=$rows['des_code'];// station to in ticket
$compartment=$rows['compartment'];//compartment currently present

//$query1="select * from beacon where beaconid='$beacon_id';";
//$result1=@mysql_query($query1);
//$rows1=@mysql_fetch_array($result1);

//$station_on=$rows['station_name'];//station standing

$date=str_replace('-','',$y);
$service_url = 'http://api.railwayapi.com/live/train/'.$train_no.'/doj/'.$date.'/apikey/'.$apikey.'/';
$respnse=get_response_from_url($service_url);
$res=json_decode($respnse,true);
//var_dump($res);
//-------constant-----
if($res['response_code']==200)
{

}
else
{
  $respnse=$livetrain;
  $res=json_decode($respnse,true);
}

//-------------------

$station_list="start-->"; 

//------------------------//
$service_url2 = 'http://api.railwayapi.com/name_number/train/'.$train_no.'/apikey/'.$apikey.'/';
$respnse2=get_response_from_url($service_url2);
$res2=json_decode($respnse2,true);
//----constant--
if($res2['response_code']==200)
{

}
else
{
	$respnse2=$trainnc;
	$res2=json_decode($respnse2,true);
}

//----------
if($res2['response_code']==200)
{
$train_name=$res2['train']['name'];
}
else
{
$train_name="cannot fetch";
}
//-----------------------//
//cancelled train
//-----------------------
if($res['response_code']==200)
{

foreach ($res['route'] as $user) {
		$station_list=$station_list.$user['station_']['name'];
		$station_list=$station_list."-->";
	}
	$station_list=$station_list."end";  //station list intermediate 

$status=$res['position']; 
$arr = explode("and", $status, 2);
$status = $arr[0]; //current position
$late= $arr[1]; //late minutes
$query4="select platform_no from adiplatform where train_no='$train_no';";
$result4=@mysql_query($query4);
$rows4=@mysql_fetch_array($result4);
if(!$rows4)
{
$platform_no=3; //platform no	
}
else
{
	$platform_no=$rows4['platform_no'];
}

$count=0;
foreach($res['route'] as $user)
{
	if($user['station']==$station_ticket) //-------------------------change if beacon station is done------------>
	{
		$schtime=$user['scharr']; //users station scheduled arrival time
   		$acctime=$user['actarr']; //users station actual arrival time estimated
           
   		$count=1;
   		break;
	}
}

//-------get distance
$service_url = 'http://api.railwayapi.com/route/train/'.$train_no.'/apikey/'.$apikey.'/';
$respnse=get_response_from_url($service_url);
$res=json_decode($respnse,true);
//----constant--
if($res['response_code']==200)
{

}
else
{
	$respnse=$routes;
	$res=json_decode($respnse,true);
}

//----------

if($res['response_code']==200)
{
foreach ($res['route'] as $user) {
    if($user['code']==$station_ticket)
    {
        $lat2=$user['lat'];
        $lon2=$user['lng'];
  break;
    }
}


$R = 6371; // km
$dLat = deg2rad($lat2-$lat1);
$dLon = deg2rad($lon2-$lon1);
$lat1 = deg2rad($lat1);
$lat2 = deg2rad($lat2);

$a = sin($dLat/2) * sin($dLat/2) +
     sin($dLon/2) * sin($dLon/2) * cos($lat1) * cos($lat2); 

$c = 2 * atan2(sqrt($a), sqrt(1-$a)); 
$d = $R * $c;
$distance=intval ( $d );
if($distance<=4 && $distance>0)
{
	$flag="Y";
}
}
else
{

	$distance="Not available";
}
//--------
if($count==1)
{
	
	header('Content-type: application/json');
  echo json_encode(array('result'=>true,'code'=>200,'type'=>'ON_TRAIN','current_position'=>$status,'late'=>$late,'sch_arr_time'=>$schtime,'act_arr_time'=>$acctime,'train_route'=>$station_list,'train_no'=>$train_no,'train_name'=>$train_name,'distance'=>$distance,'flag'=>$flag));
	
//send all details
}
else
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'type'=>'ON_TRAIN','msg'=>'Train does not stop on this station','train_route'=>$station_list));
	
//not arrive on this station send station names
}
}
else if($res['response_code']==204)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'type'=>'ON_TRAIN','msg'=>'no response data from API'));

//not running on this date;
}
else if($res['response_code']==403)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'type'=>'ON_TRAIN','msg'=>'hits finish'));

//not running on this date;
}

else if($res['response_code']==510)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'type'=>'ON_TRAIN','msg'=>'Train not running on this date'));

//not running on this date;
}
else 
{
	header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'type'=>'ON_TRAIN','msg'=>'error:server failure'));

	//error.. not able to fetch data for some reason;
}


}

function get_response_from_url($url, $curl = true)
{
	$responseString = '';
		$ch = curl_init( $url );
		$options = array(
				CURLOPT_RETURNTRANSFER => true,
				CURLOPT_HTTPHEADER => array('Content-type: application/json') ,
				CURLOPT_TIMEOUT => 8
		);

		curl_setopt_array( $ch, $options );

	 
$responseString=curl_exec($ch);
	
return $responseString;
}


?>