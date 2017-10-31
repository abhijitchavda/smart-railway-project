<?php
include('config.php');
$server="localhost";
$database="railway";
$password="";
$username="root";

//$beacon_id=$_POST['beaconid'];//beacon id from application
$email=$_POST['email'];//email from application


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
$query="select train_date,train_no,src,src_code,type from resticket where email='$email' AND status_='active';";
$result=@mysql_query($query);
$rows=@mysql_fetch_array($result);


if(!$rows)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'type'=>'ON_COMPARTMENT','msg'=>'no active saved tickets'));

}
else
{
$y=$rows['train_date'];//ticket date stored in database
$train_no=$rows['train_no'];//train no
$station_ticket=$rows['src_code'];// station from in ticket
$type=$rows['type'];


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
//---constant
if($res2['response_code']==200)
{

}
else
{
	$respnse2=$trainnc;
	$res2=json_decode($respnse2,true);
}

//-----
if($res2['response_code']==200)
{
$train_name=$res2['train']['name'];
}
else
{
$train_name="cannot fetch";
}
//-----------------------//


$arr = explode("-", $y);
$year = $arr[0]; 
$month= $arr[1];
$day= $arr[2];
$date2=$arr[2]."-".$arr[1]."-".$arr[0]; 
$service_url1 = 'http://api.railwayapi.com/cancelled/date/'.$date2.'/apikey/'.$apikey.'/';
$respnse1=get_response_from_url($service_url1);
$res1=json_decode($respnse1,true);
//-----constant----
if($res1['response_code']==200)
{

}
else
{
  $respnse1=$canceltr;
  $res1=json_decode($respnse1,true);
}

//-----------

$cancelled=0;
if($res1['response_code']==200)
{
foreach ($res1['trains'] as $user) {
	if($user['train']['number']==$train_no)
	{
		//train cancelled
		$cancelled=1;
		header('Content-type: application/json');
  echo json_encode(array('result'=>false,'type'=>'ON_COMPARTMENT','code'=>501,'msg'=>'your train has been cancelled'));
  break;
	}
}
}

if($cancelled==0)
{
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
		if ($schtime=="Source")
           {
           	$acctime=$user['actdep'];//if source take actdep as actarrival
           }
           
   		$count=1;
   		break;
	}
}
if($count==1)
{
	if($type=="RESERVATION")
	{
	header('Content-type: application/json');
  echo json_encode(array('result'=>true,'code'=>200,'type'=>'ON_COMPARTMENT','ticket'=>'RESERVATION','current_position'=>$status,'late'=>$late,'platform_no'=>$platform_no,'sch_arr_time'=>$schtime,'act_arr_time'=>$acctime,'train_route'=>$station_list,'train_no'=>$train_no,'train_name'=>$train_name,'msg'=>'you have reached your compartment position'));
}
if($type=="GENERAL")
{
$query1="SELECT * FROM gen";
$result1=@mysql_query($query1);
$rows=@mysql_num_rows($result1);
	header('Content-type: application/json');
  echo json_encode(array('result'=>true,'code'=>200,'type'=>'ON_COMPARTMENT','gen_people'=>$rows,'ticket'=>'GENERAL','current_position'=>$status,'late'=>$late,'platform_no'=>$platform_no,'sch_arr_time'=>$schtime,'act_arr_time'=>$acctime,'train_route'=>$station_list,'train_no'=>$train_no,'train_name'=>$train_name,'msg'=>'you have reached your compartment position'));

}
//send all details
}
else
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'type'=>'ON_COMPARTMENT','msg'=>'Train does not stop on this station','train_route'=>$station_list));
	
//not arrive on this station send station names
}
}

else if($res['response_code']==204)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'type'=>'ON_COMPARTMENT','msg'=>'no response data from API'));

//not running on this date;
}
else if($res['response_code']==403)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'type'=>'ON_COMPARTMENT','msg'=>'hits finish'));

//not running on this date;
}

else if($res['response_code']==510)
{
header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'type'=>'ON_COMPARTMENT','msg'=>'Train not running on this date'));

//not running on this date;
}
else 
{
	header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'type'=>'ON_COMPARTMENT','msg'=>'error:server failure'));

	//error.. not able to fetch data for some reason;
}

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