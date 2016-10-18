<?php


include "config.php";
		
		$image = $_POST['image'];
        $user = $_POST['user_id'];
        $latitude = $_POST['latitude'];
		$longitude = $_POST['longitude'];

		echo $user;
		echo $latitude;
		echo $longitude;


        $name = mysqli_real_escape_string($conn,$user);
	
		
		$sql = "SELECT * FROM UserDetails WHERE username = $name";

		$query=mysqli_query($conn,$sql);

		if ($query) 
		{
		$row = mysqli_fetch_assoc($query);
		$prob_num = $row['prob_num'];

		$prob_num = $prob_num+1;

		}

		$filename = "prob_images/".$user."_prob_".$prob_num.".jpg";
		
		$path = "/var/www/html/Click4Change/".$filename;

		$prob_id = $user."_prob_".$prob_num;

		$date = new DateTime();

		$ts = $date->getTimestamp();

		$date = new DateTime("@$ts");

		$date_time =  $date->format('Y-m-d H:i:s');
			
		
		$sql = "INSERT INTO problems(prob_id,prob_img, prob_type, location_id, latitude, longitude, user_id, date_time, num_reactions) VALUES ('$prob_id','$filename',0,0,'$latitude','$longitude','$name','$date_time',0)";

		$query=mysqli_query($conn,$sql);
		
		if($query){
			
			$imageData = base64_decode($image);
			$source = imagecreatefromstring($imageData);
			
			$imageName = $path;
			$imageSave = imagejpeg($source,$imageName,100);
			imagedestroy($source);

			$sql = "UPDATE UserDetails SET prob_num = prob_num + 1 WHERE username = $name";
			$query=mysqli_query($conn,$sql);

			echo "Successful";
		}
		
		mysqli_close($con);
?>
