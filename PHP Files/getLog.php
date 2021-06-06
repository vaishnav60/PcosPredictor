<?php
	include("db_connect.php");
	$db=new DB_connect();
	$con=$db->connect();
	
	$username=$_REQUEST['username'];
	
	$Mobile_No=$username;
	
	$response=array();
	
	//Query For Selecting Details OF journey
	$query = "Select * from pt_log_history where mobileno='".$Mobile_No."' order by startdate DESC";
	$result = mysqli_query($con,$query);
	while($row = mysqli_fetch_array($result)){
		$dt = $row['startdate']." to ".$row['enddate'];
		array_push($response,array("Date"=>$dt,"A1"=>$row['normal_dates'],"A2"=>$row['any_abnormality'],"status"=>$row['status']));
	}
	
	echo json_encode(array("response"=>$response));
?>