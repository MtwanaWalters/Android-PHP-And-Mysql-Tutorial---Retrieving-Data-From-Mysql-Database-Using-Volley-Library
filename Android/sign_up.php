<?php

include "db_connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST') {
	
$fname = mysqli_real_escape_string($conn, $_POST['fname']);
$lname = mysqli_real_escape_string($conn,$_POST['lname']);
$email= mysqli_real_escape_string($conn,$_POST['email']);
$id= mysqli_real_escape_string($conn, $_POST['id']);
$phone = mysqli_real_escape_string($conn,$_POST['phone']);
$password = mysqli_real_escape_string($conn,$_POST['password']);

$check_query = "SELECT * FROM users WHERE email='$email' AND id_number='$id' AND phone='$phone'";
$check_result_query =mysqli_fetch_array(mysqli_query($conn,$check_query));

if (isset($check_result_query)) {
	// User already exists
	  $result["success"] = "0";
	  $result["message"] = "Account already exits!";

		echo json_encode($result);
		echo $result[message];
}

else{

	// Create user
$sql=mysqli_query($conn, 'INSERT INTO users(fname,lname,email,id_number,phone,password)
         VALUES("'.$_POST['fname'].'","'.$_POST['lname'].'","'.$_POST['email'].'","'.$_POST['id'].'","'.$_POST['phone'].'","'.$_POST['password'].'")');


		 if (!$sql) {
		 die (mysqli_error($conn));
		 }

else {

	    $result["success"] = "1";
		$result["message"] = "Account created successfully!";

		echo json_encode($result);
		echo $result;
}

}}

else {

	        $result["success"] = "0";
		$result["message"] = "Registration failed, an error occurred";

		echo json_encode($result);
		echo $result;
}


?>
