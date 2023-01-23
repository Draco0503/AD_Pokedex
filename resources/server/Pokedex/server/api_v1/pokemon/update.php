<?php

require "db.php";
require "jsonEsperado.php";

$arrMensaje = array();

$parameters = file_get_contents("php://input");

if(isset($parameters)){

    $mensajeRecibido = json_decode($parameters, true);

    if(JSONCorrecto($arrPokemonCreateUpdate, $mensajeRecibido)){

        $num_pokedex = $mensajeRecibido["num_pokedex"];
        $nombre = $mensajeRecibido["nombre"];
        $tipo1 = $mensajeRecibido["tipo1"];
        $tipo2 = $mensajeRecibido["tipo2"];
        $shiny = $mensajeRecibido["shiny"];
        $url = $mensajeRecibido["url"];

        $query = "UPDATE pokemon 
                   SET nombre= '$nombre', tipo1= $tipo1, tipo2= $tipo2, shiny= '$shiny', url= '$url'  
                   WHERE num_pokedex= $num_pokedex";
                  
        $result = $conn->query($query);

        if(isset($result) && $result){
            $arrMensaje["estado"] = "ok";
            $arrMensaje["status"] = http_response_code();
            $arrMensaje["mensaje"] = "Pokemon actualizado correctamente";
        }else{
            $arrMensaje["estado"] = "error";
            $arrMensaje["status"] = http_response_code();
            $arrMensaje["mensaje"] = "Se ha producido un error al acceder a la base de datos";
        }

    }else{

        $arrMensaje["estado"] = "error";
        $arrMensaje["status"] = http_response_code();
        $arrMensaje["mensaje"] = "El JSON no contiene los campos esperados";
    }

}else{

    $arrMensaje["estado"] = "error";
    $arrMensaje["status"] = http_response_code();
    $arrMensaje["mensaje"] = "El JSON no se ha enviado correctamente";
}

$mensajeJSON = json_encode($arrMensaje,JSON_PRETTY_PRINT);

echo $mensajeJSON;

$conn->close();
die();


?>