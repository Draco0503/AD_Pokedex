<?php

require "db.php";
require "jsonEsperado.php";

$arrMensaje = array();

$parameters = file_get_contents("php://input");

if(isset($parameters)){

    $mensajeRecibido = json_decode($parameters, true);

    if(JSONCorrecto($arrPokemonDelete, $mensajeRecibido)){

        $num_pokedex = $mensajeRecibido["num_pokedex"];

        $query = "DELETE FROM pokemon WHERE num_pokedex= $num_pokedex";
                  
        $result = $conn->query($query);

        if(isset($result) && $result){
            if(($conn->affected_rows) != 1){
                $arrMensaje["estado"] = "error";
                $arrMensaje["status"] = http_response_code();
                $arrMensaje["mensaje"] = "Pokemon no encontrado";
            }else{
                $arrMensaje["estado"] = "ok";
                $arrMensaje["status"] = http_response_code();
                $arrMensaje["mensaje"] = "Pokemon eliminado correctamente";
            }

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