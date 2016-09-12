<?php

/*$filedata = file_get_contents('php://input',true);
$arr = json_decode($filedata);
$user = $arr->user;
$pass = $arr->pass;*/

$user = $_POST['username'];

$pass = $_POST['password'];

//echo "Hello world!";

//echo $user;

	include "config.php";


	$name = mysqli_real_escape_string($conn,$user);
	$pass = mysqli_real_escape_string($conn,$pass);

	
	$sql = "SELECT * FROM UserDetails WHERE username = $name";

	$query=mysqli_query($conn,$sql);

	$status1  = "";

	if ($query) 
	{
	$row = mysqli_fetch_assoc($query);
	if($row['password']==$pass)
	{
	   	$status1 = "Successful";
	}
	else
	{
		$status1 = "Unsuccessful";
	}
	}

	mysqli_close($conn);

	$total = array();	
		
	$total['status']=$status1;
	
	//print(json_encode($total));

	echo $status1;

	//return json_encode($total);

?>
