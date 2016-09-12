
<?php

$user = $_POST['username'];

$pass = $_POST['password'];

$phone = $_POST['phone'];

$email = $_POST['email'];

$gender = $_POST['gender'];


/*echo $user;
echo "\n".$pass;
echo "\n".$phone;
echo "\n".$email;
echo "\n".$gender;
*/
include "config.php";


$_name = mysqli_real_escape_string($conn,$user);
$_pass = mysqli_real_escape_string($conn,$pass);
$_phone = mysqli_real_escape_string($conn,$phone);
$_email = mysqli_real_escape_string($conn,$email);
$_gender = mysqli_real_escape_string($conn,$gender);

$sql = "INSERT INTO `UserDetails` (`username`, `password`, `name`, `gender`, `email_addr`, `mobile_num`) VALUES ('$_phone','$_pass','$_name','$_gender','$_email','$_phone')";

$query=mysqli_query($conn,$sql);

//echo mysqli_error($conn);

if ($query) 
{
	echo "Successful";
}

else
{
	echo "Unsuccesful";
}

mysqli_close($conn);

?>
