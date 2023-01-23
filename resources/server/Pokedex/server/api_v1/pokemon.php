<?php

$request_method = $_SERVER["REQUEST_METHOD"];

switch($request_method) {

    case "POST":
        require "pokemon/create.php";
        break;
    case "PUT":
        require "pokemon/update.php";
        break;
    case "DELETE":
        require "pokemon/delete.php";
        break;
    default:
        require "pokemon/read.php";
        break;
}

?>