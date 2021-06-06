<?php
include("db_connect.php");
	$db=new DB_connect();
	$con=$db->connect();
	
	// array for JSON response
$response = array();
 
// check for required fields
if (isset($_REQUEST['Username']) && isset($_REQUEST['StartDate']) && isset($_REQUEST['EndDate']) && isset($_REQUEST['A1']) && isset($_REQUEST['A2'])) {
 
    
    $v_Mobile = $_REQUEST['Username'];
	$v_StartDate = $_REQUEST['StartDate'];
	$v_EndDate = $_REQUEST['EndDate'];
	$v_A1 = $_REQUEST['A1'];
	$v_A2 = $_REQUEST['A2'];
	$v_Status = $_REQUEST['Status'];
	$count = 0;
	
	$qry="select count(*) as Cnt from pt_log_history where mobileno='".$v_Mobile."' and startdate='".$v_StartDate."'";
	//echo $qry;
	$run=mysqli_query($con,$qry);
	$row=mysqli_fetch_array($run);
	if($row["Cnt"]==0){
		$qryInsert="insert into pt_log_history(mobileno, startdate, enddate, normal_dates, any_abnormality, status) values('".$v_Mobile."','".$v_StartDate."','".$v_EndDate."','".$v_A1."','".$v_A2."','".$v_Status."')";
		$res = mysqli_query($con,$qryInsert);
		if ($res) {
			$response["success"] = 1;
        	$response["message"] = "Log saved successfully";
			echo json_encode($response);
		}
		else {
			$response["success"] = 0;
        	$response["message"] = "Sorry, enable to save your log";
			echo json_encode($response);
		}	
	}
	else{
		// required field is missing
			$response["success"] = 0;
			$response["message"] = "Data already entered for the selected date.";
			echo json_encode($response);
	}
	
	
	
		
	}
	else {
		// required field is missing
		$response["success"] = 0;
		$response["message"] = "Required field(s) is missing.";
		// echoing JSON response
		echo json_encode($response);
	} 
		

 
?>