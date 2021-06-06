<?php
	include("db_connect.php");
	$db=new DB_connect();
	$con=$db->connect();
	$response = array();
	if (isset($_REQUEST['Username'])) {
		$v_Username = $_REQUEST['Username'];
		
		$qryDateDiff="SELECT datediff(curdate(),max(startdate)) as DATEDIFF, max(startdate) as MAX FROM pt_log_history where mobileno='".$v_Username."'";
		$run = mysqli_query($con,$qryDateDiff);
		$row=mysqli_fetch_array($run);
		$noofdays = $row["DATEDIFF"];
		
		if ($noofdays>0 && $noofdays<=28) {
			
			$response["success"] = 1;
        	$response["message"] = $row["MAX"];
			echo json_encode($response);
		}
		else if($noofdays>28)
		{
			$response["success"] = 0;
        	$response["message"] = "Historical data too old to calculate next period date";
			echo json_encode($response);
		}
		else {
			$response["success"] = 0;
        	$response["message"] = "No historical data available to calculate next period date";
			echo json_encode($response);
		}
	}
	else {
		$response["success"] = 0;
		$response["message"] = "Required field(s) is missing.";
		echo json_encode($response);
	}
?>