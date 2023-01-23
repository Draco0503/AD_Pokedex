<?php

require "db.php";
require "jsonEsperado.php";

$arrMensaje = array();

$query = "SELECT id, nombre  
          FROM tipo";
if(isset($_GET["id"]) && is_numeric($_GET["id"])){
    $query .= " WHERE id=".$_GET["id"];
}elseif(isset($_GET["nombre"])){
    $query .= " WHERE nombre LIKE ('" .$_GET["nombre"]."%')";
}

$result = $conn->query($query);

if(isset($result)&& $result){

    if($result->num_rows >0){
        $arrTipos = array();

        while($row = $result->fetch_assoc()){
            $arrTipo = array();

            $arrTipo["id"] = $row["id"];
            $arrTipo["nombre"] = $row["nombre"];

            $arrTipos[] = $arrTipo;
            

        }

        $arrMensaje["estado"] = "ok";
        $arrMensaje["status"] = http_response_code();
        $arrMensaje["tipos"] = $arrTipos;
    }else{

        $arrMensaje["estado"] = "ok";
        $arrMensaje["status"] = http_response_code();
        $arrMensaje["tipos"] = [];
    }
}else{
    $arrMensaje["estado"] = "error";
    $arrMensaje["status"] = http_response_code();
    $arrMensaje["mensaje"] = "Se ha producido un error al leer en la bbdd, query incorrecta";
}

$mensajeJSON = json_encode($arrMensaje,JSON_PRETTY_PRINT);

echo $mensajeJSON;

$conn->close ();

?>