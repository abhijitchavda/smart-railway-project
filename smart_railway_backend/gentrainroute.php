<?php
include('config.php');
$trainno=$_POST['train_no'];
$constroute=$routes;
$service_url = 'http://api.railwayapi.com/route/train/'.$trainno.'/apikey/'.$apikey.'/';
get_response_from_url($service_url,$constroute);


function get_response_from_url($url,$constroute,$curl = true)
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
		echo $constroute;
		
	}
}


?>