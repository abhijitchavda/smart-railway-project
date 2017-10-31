<!DOCTYPE html>
<html>
<body bgcolor="black">
<?php
//$lat=23.022505;
//$lon=72.5713621;
//$name=$_GET['name'];
///*
$server="localhost";
$database="railway";
$password="";
$username="root";

//$beacon_id=$_POST['beaconid'];//beacon id from application
$alphakey=$_GET['alphakey'];

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


$query="SELECT * FROM sharelocation WHERE alphakey='$alphakey';";
$result=@mysql_query($query);
$rows=@mysql_fetch_array($result);

if($rows)
{
$lat=$rows['lat'];
$lon=$rows['lon'];
$srclat=$rows['srclat'];
$srclon=$rows['srclon'];
$deslat=$rows['deslat'];
$deslon=$rows['deslon'];

$email=$rows['email'];
$query1="SELECT * FROM profile WHERE email='$email'";
$result1=@mysql_query($query1);
$rows1=@mysql_fetch_array($result1);
$name=$rows1['name'];
$name=strtoupper($name);
}
else
{
	header('Content-type: application/json');
  echo json_encode(array('result'=>false,'code'=>201,'msg'=>'your privilages are terminated'));
}
//*/
?>
<h1 style="color:#FFFFFF"><?php echo $name; ?>'S LOCATION </h1>

<div id="map" style="width:100%;height:500px;"></div>

<script>

function myMap() {
	 var styles = [{
        "stylers": [{
            "saturation": -100
        }]
    }, {
        "featureType": "transit.line",
            "stylers": [{
            "saturation": 100
        }, {
            "color": "#ff3183"
        }]
    }];

    // Create a new StyledMapType object, passing it the array of styles, as well as the name to be displayed on the map type control.
    var styledMap = new google.maps.StyledMapType(styles, {
        name: "Styled Map"
    });
 var myCenter = new google.maps.LatLng("<?php echo $lat; ?>","<?php echo $lon; ?>");
 var mysrc=new google.maps.LatLng("<?php echo $srclat; ?>","<?php echo $srclon; ?>");
 var mydes=new google.maps.LatLng("<?php echo $deslat; ?>","<?php echo $deslon; ?>");
 
  var mapCanvas = document.getElementById("map");
  var mapOptions = {center: myCenter, zoom: 15,mapTypeControlOptions: {
            mapTypeIds: [google.maps.MapTypeId.TERRAIN, 'map_style']
        }};
  var map = new google.maps.Map(mapCanvas, mapOptions);
  map.mapTypes.set('map_style', styledMap);
    map.setMapTypeId('map_style');
  var marker = new google.maps.Marker({position:myCenter});
  marker.setMap(map);

 var marker1 = new google.maps.Marker({position:mysrc});
  marker1.setMap(map);

var marker2 = new google.maps.Marker({position:mydes});
  marker2.setMap(map);


  // Zoom to 9 when clicking on marker
  google.maps.event.addListener(marker,'click',function() {
     var infowindow = new google.maps.InfoWindow({
      content:"<?php echo $name; ?>"
    });
  infowindow.open(map,marker);
  });

  google.maps.event.addListener(marker1,'click',function() {
     var infowindow1 = new google.maps.InfoWindow({
      content:"source"
    });
  infowindow1.open(map,marker1);
  });

  google.maps.event.addListener(marker2,'click',function() {
     var infowindow2 = new google.maps.InfoWindow({
      content:"destination"
    });
  infowindow2.open(map,marker2);
  });

}
</script>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA8WFk2ShqsxZaDjdM1yd1Sk2vhVvLixLc&callback=myMap"></script>

</body>
</html>





