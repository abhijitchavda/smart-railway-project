<?php
include('config.php');
$hrs=$_POST['hrs'];
$station=$_POST['station'];
$constrain=$gentrain;
$service_url = 'http://api.railwayapi.com/arrivals/station/'.$station.'/hours/'.$hrs.'/apikey/'.$apikey.'/';
get_response_from_url($service_url,$constrain);


function get_response_from_url($url,$constrain,$curl = true)
{
	$responseString = '';
		$ch = curl_init( $url );
		$options = array(
				CURLOPT_RETURNTRANSFER => true,
				CURLOPT_HTTPHEADER => array('Content-type: application/json') ,
				CURLOPT_TIMEOUT => 8
		);

		curl_setopt_array( $ch, $options );

	$result=curl_exec($ch);
	$res=json_decode($result,true);
	

	if($res['response_code']==200)
	{
		echo $result;
	}
	else
	{
		echo $constrain;
		
	}
}

?>