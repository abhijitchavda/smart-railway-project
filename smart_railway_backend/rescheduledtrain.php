<?php
include('config.php');
$email=$_POST['email'];
date_default_timezone_set('Asia/Kolkata');
$date1= date('d/m/Y');
$date=str_replace("/","-",$date1);
$service_url = 'http://api.railwayapi.com/rescheduled/date/'.$date.'/apikey/'.$apikey.'/';
get_response_from_url($service_url);



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
		$result=curl_exec($ch);
		$res=json_decode($result,true);
	
	if($res['response_code']==200)
	{
		echo $result;	
	}
	else
	{
		echo '{"trains": [{"rescheduled_date": "17-Apr", "from": {"code": "PLNI", "name": "PALANI"}, "to": {"code": "PGTN", "name": "PALGHAT TOWN"}, "time_diff": "01:30", "number": "06770", "name": "PLNI-PGTN PASSR SPL", "rescheduled_time": "20:55"}, {"rescheduled_date": "17-Apr", "from": {"code": "TCN", "name": "TIRUCHENDUR"}, "to": {"code": "PLNI", "name": "PALANI"}, "time_diff": "01:30", "number": "56770", "name": "TCN-PLNI PASSR", "rescheduled_time": "12:40"}, {"rescheduled_date": "17-Apr", "from": {"code": "QLN", "name": "KOLLAM JN"}, "to": {"code": "ERS", "name": "ERNAKULAM JN"}, "time_diff": "01:30", "number": "66308", "name": "QLN-ERS MEMU", "rescheduled_time": "12:40"}], "response_code": 200}';
	}
}
?>