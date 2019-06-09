<?php
    $con = mysqli_connect("ns01.000webhost.com", "id9864931_heytaxi", "localhost123", "id9864931_heytaxi");

    $firstName = $_POST["firstName"];
    $lastName = $_POST["lastName"];
    $username = $_POST["email"];
    $password = $_POST["password"];

    $statement = mysqli_prepare($con, "INSERT INTO user (firstname, lastname, email, password) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "siss", $firstName, $lastName, $email, $password);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
