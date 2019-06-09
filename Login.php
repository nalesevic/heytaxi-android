<?php
    $con = mysqli_connect("localhost", "id9864931_heytaxi", "localhost123", "id9864931_heytaxi");

    $email = $_POST["email"];
    $password = $_POST["password"];

    $statement = mysqli_prepare($con, "SELECT * FROM user WHERE email = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $email, $password);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $firstname, $lastname, $email, $password);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["firstname"] = $firstname;
        $response["lastname"] = $lastname;
        $response["email"] = $email;
        $response["password"] = $password;
    }

    echo json_encode($response);
?>
