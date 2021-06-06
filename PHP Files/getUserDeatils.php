<?php
include("db_connect.php");
	$db=new DB_connect();
	$con=$db->connect();
	
	
$response = array();
 

if (isset($_REQUEST['MobileNo'])) {
 
    
    $vMobileNo = $_REQUEST['MobileNo'];

	$qry="select count(*) as Cnt from pt_user where mobileno='".$vMobileNo."'";
	
	$run=mysqli_query($con,$qry);
	$row=mysqli_fetch_array($run);
	if($row["Cnt"]==1){
		$qry_o="select * from pt_user where mobileno='".$vMobileNo."'";

		$result_o = mysqli_query($con,$qry_o);
		$row_o = mysqli_fetch_array($result_o);

		$response["success"] = 1;
        $response["name"] = $row_o[1];
        $response["Age"] = $row_o[3];
        $response["Weight"] = $row_o[4];
        $response["Height"] = $row_o[5];
        $response["BMI"] = $row_o[6];
        $response["MarriageStatus"] = $row_o[7];
        $response["NoOfChildren"] = $row_o[8];
		echo json_encode($response);
	}
	else{
		
			$response["success"] = 0;
			$response["name"] = "";
			$response["Age"] = "";
			$response["Weight"] = "";
			$response["Height"] = "";
			$response["BMI"] = "";
			$response["MarriageStatus"] = "";
			$response["NoOfChildren"] = "";
			echo json_encode($response);
	}
	
	
	
		
	}
	else {
		
		$response["success"] = 0;
		$response["name"] = "";
		$response["Age"] = "";
		$response["Weight"] = "";
		$response["Height"] = "";
		$response["BMI"] = "";
		$response["MarriageStatus"] = "";
		$response["NoOfChildren"] = "";
		echo json_encode($response);
	} 
		

 
?>