<?php
include('config.php');
$email=$_POST['email'];
date_default_timezone_set('Asia/Kolkata');
$date1= date('d/m/Y');
$date=str_replace("/","-",$date1);
$service_url = 'http://api.railwayapi.com/cancelled/date/'.$date.'/apikey/'.$apikey.'/';
get_response_from_url($service_url);


/*function get_response_from_url($url, $curl = true)
{
	$responseString = '';
		$ch = curl_init( $url );
		$options = array(
				CURLOPT_RETURNTRANSFER => true,
				CURLOPT_HTTPHEADER => array('Content-type: application/json') ,
		);

		curl_setopt_array( $ch, $options );

	echo curl_exec($ch);
}
*/
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
		echo '{"last_updated": {"time": "10:31:36", "date": "2017-04-17"}, "trains": [{"train": {"start_time": "09:10", "name": "AJMER INTERCITY", "type": "MEX", "number": "19411"}, "source": {"code": "ADI", "name": "AHMEDABAD JN"}, "dest": {"code": "AII", "name": "AJMER"}}, {"train": {"start_time": "06:25", "name": "INTERCITY EXP", "type": "MEX", "number": "19412"}, "source": {"code": "AII", "name": "AJMER"}, "dest": {"code": "ADI", "name": "AHMEDABAD JN"}}, {"train": {"start_time": "05:15", "name": "NBD-KTW EXPRESS", "type": "MEX", "number": "24041"}, "source": {"code": "NBD", "name": "NAJIBABAD JN"}, "dest": {"code": "KTW", "name": "KOTDWARA"}}, {"train": {"start_time": "22:05", "name": "KTW-NBD", "type": "MEX", "number": "24042"}, "source": {"code": "KTW", "name": "KOTDWARA"}, "dest": {"code": "NBD", "name": "NAJIBABAD JN"}}, {"train": {"start_time": "04:10", "name": "FN-DLI PASSENGER", "type": "PAS", "number": "51916"}, "source": {"code": "FN", "name": "FARUKHNAGAR"}, "dest": {"code": "DLI", "name": "DELHI"}}, {"train": {"start_time": "14:55", "name": "KODR - VRLF MG PASS", "type": "PAS", "number": "52953"}, "source": {"code": "KODR", "name": "KODINAR"}, "dest": {"code": "VRLF", "name": "KJV-VRL"}}, {"train": {"start_time": "11:55", "name": "VRLF - KODR MG PASS", "type": "PAS", "number": "52954"}, "source": {"code": "VRLF", "name": "KJV-VRL"}, "dest": {"code": "KODR", "name": "KODINAR"}}, {"train": {"start_time": "15:00", "name": "MLDT-BWN PASS", "type": "PAS", "number": "53418"}, "source": {"code": "MLDT", "name": "MALDA TOWN"}, "dest": {"code": "BWN", "name": "BARDDHAMAN"}}, {"train": {"start_time": "06:15", "name": "RBL-CNB PASS", "type": "PAS", "number": "54211"}, "source": {"code": "RBL", "name": "RAE BARELI JN"}, "dest": {"code": "CNB", "name": "KANPUR CENTRAL"}}, {"train": {"start_time": "08:15", "name": "SRE - LKO PASS.", "type": "PAS", "number": "54252"}, "source": {"code": "SRE", "name": "SAHARANPUR"}, "dest": {"code": "LKO", "name": "LUCKNOW"}}, {"train": {"start_time": "03:15", "name": "JNU - BSB PASSENGER", "type": "PAS", "number": "54262"}, "source": {"code": "JNU", "name": "JAUNPUR JN"}, "dest": {"code": "BSB", "name": "VARANASI JN"}}, {"train": {"start_time": "07:40", "name": "BSB-MGS PASS", "type": "PAS", "number": "54268"}, "source": {"code": "BSB", "name": "VARANASI JN"}, "dest": {"code": "MGS", "name": "MUGHAL SARAI JN"}}, {"train": {"start_time": "18:25", "name": "BSB-MGSPASSENGER", "type": "PAS", "number": "54270"}, "source": {"code": "BSB", "name": "VARANASI JN"}, "dest": {"code": "MGS", "name": "MUGHAL SARAI JN"}}, {"train": {"start_time": "05:30", "name": "SLN-LKO PASS", "type": "PAS", "number": "54281"}, "source": {"code": "SLN", "name": "SULTANPUR"}, "dest": {"code": "LKO", "name": "LUCKNOW"}}, {"train": {"start_time": "18:55", "name": "LKO-SLN", "type": "PAS", "number": "54284"}, "source": {"code": "LKO", "name": "LUCKNOW"}, "dest": {"code": "SLN", "name": "SULTANPUR"}}, {"train": {"start_time": "18:55", "name": "CHANDAUSI MORADABAD PASSE", "type": "PAS", "number": "54313"}, "source": {"code": "CH", "name": "CHANDAUSI JN"}, "dest": {"code": "MB", "name": "MORADABAD"}}, {"train": {"start_time": "16:30", "name": "MORADABAD CHANDAUSI PASSE", "type": "PAS", "number": "54314"}, "source": {"code": "MB", "name": "MORADABAD"}, "dest": {"code": "CH", "name": "CHANDAUSI JN"}}, {"train": {"start_time": "19:10", "name": "MB-NBD PASSENGER", "type": "PAS", "number": "54395"}, "source": {"code": "MB", "name": "MORADABAD"}, "dest": {"code": "NBD", "name": "NAJIBABAD JN"}}, {"train": {"start_time": "03:35", "name": "NBD-MB PASSENGER", "type": "PAS", "number": "54396"}, "source": {"code": "NBD", "name": "NAJIBABAD JN"}, "dest": {"code": "MB", "name": "MORADABAD"}}, {"train": {"start_time": "16:05", "name": "SHTS-MB PASSENGER", "type": "PAS", "number": "54397"}, "source": {"code": "SHTS", "name": "SAMBHAL HTM SAR"}, "dest": {"code": "MB", "name": "MORADABAD"}}, {"train": {"start_time": "13:20", "name": "MB-SHTS PASSENGER", "type": "PAS", "number": "54398"}, "source": {"code": "MB", "name": "MORADABAD"}, "dest": {"code": "SHTS", "name": "SAMBHAL HTM SAR"}}, {"train": {"start_time": "05:00", "name": "HW - RKSH PASS", "type": "PAS", "number": "54481"}, "source": {"code": "HW", "name": "HARIDWAR JN"}, "dest": {"code": "RKSH", "name": "RISHIKESH"}}, {"train": {"start_time": "15:10", "name": "RKSH-HW PASSENGER", "type": "PAS", "number": "54484"}, "source": {"code": "RKSH", "name": "RISHIKESH"}, "dest": {"code": "HW", "name": "HARIDWAR JN"}}, {"train": {"start_time": "17:40", "name": "HW-RKSH PASSENGER", "type": "PAS", "number": "54485"}, "source": {"code": "HW", "name": "HARIDWAR JN"}, "dest": {"code": "RKSH", "name": "RISHIKESH"}}, {"train": {"start_time": "14:15", "name": "SPJ-MFP PASSENGER", "type": "PAS", "number": "55239"}, "source": {"code": "SPJ", "name": "SAMASTIPUR JN"}, "dest": {"code": "MFP", "name": "MUZAFFARPUR JN"}}, {"train": {"start_time": "17:55", "name": "MFP-SPJ PASS", "type": "PAS", "number": "55240"}, "source": {"code": "MFP", "name": "MUZAFFARPUR JN"}, "dest": {"code": "SPJ", "name": "SAMASTIPUR JN"}}, {"train": {"start_time": "17:25", "name": "DLCR - SCL PASSENGER", "type": "PAS", "number": "55687"}, "source": {"code": "DLCR", "name": "DULLABCHERRA"}, "dest": {"code": "SCL", "name": "SILCHAR"}}, {"train": {"start_time": "05:00", "name": "SCL - DLCR PASSENGER", "type": "PAS", "number": "55688"}, "source": {"code": "SCL", "name": "SILCHAR"}, "dest": {"code": "DLCR", "name": "DULLABCHERRA"}}, {"train": {"start_time": "09:10", "name": "DLCR - BPB PASSENGER", "type": "PAS", "number": "55689"}, "source": {"code": "DLCR", "name": "DULLABCHERRA"}, "dest": {"code": "BPB", "name": "BADARPUR JN"}}, {"train": {"start_time": "13:45", "name": "BPB - DLCR PASSENGER", "type": "PAS", "number": "55690"}, "source": {"code": "BPB", "name": "BADARPUR JN"}, "dest": {"code": "DLCR", "name": "DULLABCHERRA"}}, {"train": {"start_time": "08:30", "name": "SQB-OMLF PASSENGER", "type": "PAS", "number": "55709"}, "source": {"code": "SQB", "name": "SINGHABAD"}, "dest": {"code": "OMLF", "name": "OLD MALDA"}}, {"train": {"start_time": "05:00", "name": "BER-AII PASS.", "type": "PAS", "number": "59601"}, "source": {"code": "MJ", "name": "MARWAR JN"}, "dest": {"code": "AII", "name": "AJMER"}}, {"train": {"start_time": "18:20", "name": "AII-BER PASS.", "type": "PAS", "number": "59602"}, "source": {"code": "AII", "name": "AJMER"}, "dest": {"code": "MJ", "name": "MARWAR JN"}}, {"train": {"start_time": "09:35", "name": "LKO-CNB MEMU", "type": "MEMU", "number": "64205"}, "source": {"code": "LKO", "name": "LUCKNOW"}, "dest": {"code": "CNB", "name": "KANPUR CENTRAL"}}, {"train": {"start_time": "11:20", "name": "LKO-CNB MEMU", "type": "MEMU", "number": "64207"}, "source": {"code": "LKO", "name": "LUCKNOW"}, "dest": {"code": "CNB", "name": "KANPUR CENTRAL"}}, {"train": {"start_time": "22:55", "name": "KKDE-UMB MEMU", "type": "MEMU", "number": "64483"}, "source": {"code": "KKDE", "name": "KURUKSHETRA JN"}, "dest": {"code": "UMB", "name": "AMBALA CANT JN"}}, {"train": {"start_time": "05:00", "name": "PBH-LKO DMU", "type": "DMU", "number": "74201"}, "source": {"code": "PBH", "name": "PARTAPGARH JN"}, "dest": {"code": "LKO", "name": "LUCKNOW"}}, {"train": {"start_time": "16:30", "name": "LKO/PBH DMU", "type": "DMU", "number": "74202"}, "source": {"code": "LKO", "name": "LUCKNOW"}, "dest": {"code": "PBH", "name": "PARTAPGARH JN"}}, {"train": {"start_time": "08:05", "name": "SHTS-MB DMU", "type": "DMU", "number": "74301"}, "source": {"code": "SHTS", "name": "SAMBHAL HTM SAR"}, "dest": {"code": "MB", "name": "MORADABAD"}}, {"train": {"start_time": "05:15", "name": "MB-SHTS DMU", "type": "DMU", "number": "74302"}, "source": {"code": "MB", "name": "MORADABAD"}, "dest": {"code": "SHTS", "name": "SAMBHAL HTM SAR"}}, {"train": {"start_time": "08:20", "name": "RB 79481", "type": "RBUS", "number": "79481"}, "source": {"code": "VJF", "name": "VIJAPUR"}, "dest": {"code": "AJM", "name": "ADRAJ MOTI"}}, {"train": {"start_time": "10:25", "name": "RB 79482", "type": "RBUS", "number": "79482"}, "source": {"code": "AJM", "name": "ADRAJ MOTI"}, "dest": {"code": "VJF", "name": "VIJAPUR"}}, {"train": {"start_time": "16:20", "name": "RB 79483", "type": "RBUS", "number": "79483"}, "source": {"code": "VJF", "name": "VIJAPUR"}, "dest": {"code": "AJM", "name": "ADRAJ MOTI"}}, {"train": {"start_time": "18:10", "name": "RB 79484", "type": "RBUS", "number": "79484"}, "source": {"code": "AJM", "name": "ADRAJ MOTI"}, "dest": {"code": "VJF", "name": "VIJAPUR"}}], "response_code": 200}';
		
	}
}
?>