<?php
include("db_connect.php");
	$db=new DB_connect();
	$con=$db->connect();
	
$response = array();
 

if (isset($_REQUEST['Username']) && isset($_REQUEST['Password'])) {
 
    
    $Username = $_REQUEST['Username'];
	$Password = $_REQUEST['Password'];
	$count = 0;
	
	$qry="select count(*) as Cnt from pt_user where mobileno='".$Username."' and password='".$Password."'";
	
	$run=mysqli_query($con,$qry);
	$row=mysqli_fetch_array($run);
	if($row["Cnt"]==1){
		$qry_o="select status,name,id from pt_user where mobileno='".$Username."'";
		
		$result_o = mysqli_query($con,$qry_o);
		$row_o = mysqli_fetch_array($result_o);
		$status = $row_o[0];
		$name = $row_o[1];
		$uid = $row_o[2];
		
		if ($status=="on"){	
			$response["success"] = 1;
        	$response["message"] = "Login Successful. Welcome ".$name;
        	$response["name"] = $name;
        	$response["uid"] = "".$uid;
			echo json_encode($response);
		}
		else if($status=="off"){	
			$response["success"] = 0;
        	$response["message"] = "Account blocked. Please contact Admin.";
			$response["name"] = "";
			$response["uid"] = "";
			echo json_encode($response);
        	
    		}
			
		else {
			
			$response["success"] = 0;
			$response["message"] = "Invalid user details, please contact Admin";
			$response["name"] = "";
			$response["uid"] = "";
			
			echo json_encode($response);
		}
	}
	else{
		
			$response["success"] = 0;
			$response["message"] = "Invalid Username / Password, try again";
			$response["name"] = "";
			$response["uid"] = "";
			
			echo json_encode($response);
	}
	
	
	
		
	}
	else {
		
		$response["success"] = 0;
		$response["message"] = "Required field(s) is missing.";
		$response["name"] = "";
		$response["uid"] = "";
		
		echo json_encode($response);
	} 
		

 
?>