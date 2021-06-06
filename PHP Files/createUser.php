<?php
include("db_connect.php");
	$db=new DB_connect();
	$con=$db->connect();
	
	
$response = array();
 

if (isset($_REQUEST['Name']) && isset($_REQUEST['MobileNo']) && isset($_REQUEST['Password'])) {
 
    
    $v_Name = $_REQUEST['Name'];
    $v_Mobile = $_REQUEST['MobileNo'];
	$v_Password = $_REQUEST['Password'];
	$count = 0;
	
	$qry="select count(*) as Cnt from pt_user where mobileno='".$v_Mobile."'";
	
	$run=mysqli_query($con,$qry);
	$row=mysqli_fetch_array($run);
	if($row["Cnt"]==0){
		$qryInsert="insert into pt_user(name,mobileno, password, status) values('".$v_Name."','".$v_Mobile."','".$v_Password."','on')";
		$res = mysqli_query($con,$qryInsert);
		if ($res) {
			$response["success"] = 1;
        	$response["message"] = "Dear ".$v_Name.",your account has created successfully";
			echo json_encode($response);
		}
		else {
			$response["success"] = 0;
        	$response["message"] = "Sorry ".$v_Name.",failed to create your account";
			echo json_encode($response);
		}	
	}
	else{
		
			$response["success"] = 0;
			$response["message"] = "Mobile Number already registered. Please try with a different Mobile Number.";
			echo json_encode($response);
	}
	
	
	
		
	}
	else {
		// required field is missing
		$response["success"] = 0;
		$response["message"] = "Required field(s) is missing.";
		$response["name"] = "";
		$response["uid"] = "";
		// echoing JSON response
		echo json_encode($response);
	} 
		

 
?>