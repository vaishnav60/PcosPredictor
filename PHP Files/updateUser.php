<?php
include("db_connect.php");
	$db=new DB_connect();
	$con=$db->connect();
	
	
$response = array();
 

if (isset($_REQUEST['Name']) && isset($_REQUEST['MobileNo'])) {
 
    
    $v_Name = $_REQUEST['Name'];
    $v_Mobile = $_REQUEST['MobileNo'];
    $v_Age = $_REQUEST['Age'];
    $v_Weight = $_REQUEST['Weight'];
    $v_Height = $_REQUEST['Height'];
    $v_BMI = $_REQUEST['BMI'];
    $v_MarriageStatus = $_REQUEST['MarriageStatus'];
    $v_NoOfChildren = $_REQUEST['NoOfChildren'];

		$qryUpdate="update pt_user set name='".$v_Name."',age='".$v_Age."',weight='".$v_Weight."',height='".$v_Height."',bmi='".$v_BMI."',marriagestatus='".$v_MarriageStatus."',noofchildren='".$v_NoOfChildren."' where mobileno='".$v_Mobile."'";
		$res = mysqli_query($con,$qryUpdate);
		if ($res) {
			$response["success"] = 1;
        	$response["message"] = "Dear ".$v_Name.",your account has updated successfully";
			echo json_encode($response);
		}
		else {
			$response["success"] = 0;
        	$response["message"] = "Sorry ".$v_Name.",failed to update your account";
			echo json_encode($response);
		}	
}
else {
	
	$response["success"] = 0;
	$response["message"] = "Required field(s) is missing.";
	
	echo json_encode($response);
} 
?>