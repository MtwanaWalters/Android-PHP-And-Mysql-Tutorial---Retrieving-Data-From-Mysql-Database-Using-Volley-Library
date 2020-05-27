<?php

	include('db_connect.php');

	if($_SERVER['REQUEST_METHOD'] == 'POST')
	{
		// posting variables to..
		$email=$_POST['email'];
		$password=$_POST['password'];

		$que=mysqli_query($conn,"select * from users where email ='$email' and password='$password'");
		$row=mysqli_num_rows($que);

		if($row)
		{

	           $result["success"] = "1";
		       $result["message"] = "Successful";

		       // Encoding result into Json
		       echo json_encode($result);
	        	
	        }

		else
			{

		$result["success"] = "0";
		$result["message"] = "Incorrect email or password";

		//Encoding the result to json
		 echo json_encode($result);
				
			}
	}

?>
