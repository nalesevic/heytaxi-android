<?php
  $conn = mysqli_connect("localhost", "id9864931_admin", "admin", "id9864931_heytaxi");
  if ($conn->connect_error) {
      die("Connection failed: " . $conn->connect_error);
  }
   // set parameters and execute

  $stmt = $conn->prepare("INSERT INTO review (driverID, review, rating) VALUES (?, ?, ?)");
  $stmt->bind_param("sss", $driverID, $review, $rating);

  $driverID = (int)$_POST["driverID"];
  $review = $_POST["review"];
  $rating = (float) $_POST["rating"];

  $stmt->execute();
    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
