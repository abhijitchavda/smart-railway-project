<?php
include('config.php');
$server="localhost";
$database="railway";
$password="";
$username="root";
$email=$_POST['email'];
$phone_no=$_POST['phone_no'];
$phone_no=str_replace(" ","",$phone_no);
$lat=$_POST['lat'];
$lon=$_POST['lon'];
$alpha=randomPassword(12,1,"lower_case,upper_case,numbers");
$alphakey=$alpha['0'];
$constroute=$routes;

$conn=@mysql_connect($server,$username,$password,$database);
$result=@mysql_select_db("railway",$conn);

$query3="SELECT * from sharelocation WHERE email='$email';";
$result3=@mysql_query($query3);
$rows3=@mysql_fetch_array($result3);

if(!$rows3)
{
$query="SELECT name FROM profile WHERE email='$email'";
$result=@mysql_query($query);
$rows=@mysql_fetch_array($result);
$name=$rows['name'];
$message="Mr.".$name." has privilaged you to track his location using link--> 192.168.42.89/share_loc_msg.php?alphakey=".$alphakey;

$query2="SELECT train_no,src_code,des_code FROM resticket WHERE email='$email'";
$result2=@mysql_query($query2);
$rows2=@mysql_fetch_array($result2);

if($rows2)
{
    $train_no=$rows2['train_no'];
    $src_code=$rows2['src_code'];
    $des_code=$rows2['des_code'];

//-------------------get lat lon for src and des----------


$service_url = 'http://api.railwayapi.com/route/train/'.$train_no.'/apikey/'.$apikey.'/';
$respnse=get_response_from_url($service_url,$constroute);
$res=json_decode($respnse,true);
if($res['response_code']==200)
{
foreach ($res['route'] as $user) {
    if($user['code']==$src_code)
    {
        $srclat=$user['lat'];
        $srclon=$user['lng'];
  break;
    }
}
foreach ($res['route'] as $user) {
    if($user['code']==$des_code)
    {
        $deslat=$user['lat'];
        $deslon=$user['lng'];
  break;
    }
}


//------------------------------------------------------
$query1="INSERT into sharelocation values ('$email','$alphakey','$lat','$lon','$srclat','$srclon','$deslat','$deslon')";
$result1=@mysql_query($query1);
if(@mysql_affected_rows()==0)
{
	header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>202,'msg'=>'server error:data store'));
}
else
{
    
$url = "http://sms.hspsms.com:/sendSMS";
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POSTFIELDS, 'username=hspdemo&message='.$message.'&sendername=HSPSMS&smstype=TRANS&numbers='.$phone_no.'&apikey='.$apisms.'');
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_TIMEOUT, 8);
        $result10 = curl_exec($ch);
       $res=json_decode($result10,true);
        if($res[0]["responseCode"]=="Message SuccessFully Submitted")
        {
            header('Content-type: application/json');
echo json_encode(array('result'=>true,'code'=>200,'msg'=>$message));
        }
        else
        {
            header('Content-type: application/json');
echo json_encode(array('result'=>false,'code'=>200,'msg'=>$message));
}
//curl_close($ch);
}
}
else
{
header('Content-type: application/json');
echo json_encode(array('result'=>false,'code'=>201,'msg'=>'not able to get source and destination lon lat from API'));

}

}
else
{
header('Content-type: application/json');
echo json_encode(array('result'=>false,'code'=>201,'msg'=>'no ticket details found'));

}
}
else
{
    $query4="UPDATE sharelocation SET lat='$lat',lon='$lon' WHERE email='$email'";
    $result4=@mysql_query($query4);
    if(@mysql_affected_rows()==0)
    {

    header('Content-type: application/json');
    echo json_encode(array('result'=>false,'code'=>208,'msg'=>'server error:unable to update'));

    }
else
    {
    header('Content-type: application/json');
    echo json_encode(array('result'=>true,'code'=>200,'msg'=>'updated'));
    }

}
function randomPassword($length,$count, $characters) {
 
    $symbols = array();
    $passwords = array();
    $used_symbols = '';
    $pass = '';
     
    $symbols["lower_case"] = 'abcdefghijklmnopqrstuvwxyz';
    $symbols["upper_case"] = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $symbols["numbers"] = '1234567890';
    $symbols["special_symbols"] = '!?~@#-_+<>[]{}';
 
    $characters = explode(",", $characters); 
    foreach ($characters as $key=>$value) {
        $used_symbols .= $symbols[$value];
    }
    $symbols_length = strlen($used_symbols) - 1; 
     
    for ($p = 0; $p < $count; $p++) {
        $pass = '';
        for ($i = 0; $i < $length; $i++) {
            $n = rand(0, $symbols_length); 
            $pass .= $used_symbols[$n]; 
        }
        $passwords[] = $pass;
    }
     
    return $passwords; 
}
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

    $result= curl_exec($ch);
   $res=json_decode($result,true);
    

    if($res['response_code']==200)
    {
        return $result;
    }
    else
    {
        return $constroute;
        
    }
}

?>