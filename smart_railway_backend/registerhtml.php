<!DOCTYPE html>
<html>
<head>
	<title>authentication</title>
</head>

<body>
<form action="sharelocation.php" method="post">
	<!--name : <input type="textbox" id="1" name="name">
	<br>
	<br>
	date : <input type="textbox" id="2" name="date">
	<br>
	<br>-->
	phonenumber : <input type="textbox" id="3"  name="phone_no">
	<br>
	<br>
	lat : <input type="textbox" id="4" name="lat">
	<br>
	<br>
	lon : <input type="textbox" id="5" name="lon">
	<br>
	<br>
	email: <input type="textbox" id="5" name="email">
	<br>
	<br>
	

	<input type="submit" name="create" value="create">


</form>
</body>
</html>
$email=$_POST['email'];
$date=$_POST['date'];
$trainno=$_POST['trainno'];
$src=$_POST['src'];
$srccode=$_POST['srccode'];
$des=$_POST['des'];
$descode=$_POST['descode'];
$compartment=$_POST['compartment'];
