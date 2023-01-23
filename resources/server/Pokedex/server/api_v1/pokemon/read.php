<?php

require "db.php";
require "jsonEsperado.php";

$arrMensaje = array();

$query = "SELECT num_pokedex, nombre, tipo1, tipo2, shiny, url 
          FROM pokemon";
if(isset($_GET["num_pokedex"]) && is_numeric($_GET["num_pokedex"])){
    $query .= " WHERE num_pokedex=".$_GET["num_pokedex"];
}elseif(isset($_GET["nombre"])){
    $query .= " WHERE nombre LIKE ('" .$_GET["nombre"]."%')";
}elseif(isset($_GET["tipo1"]) && is_numeric($_GET["tipo1"])){
    $query .= " WHERE tipo1=".$_GET["tipo1"];
}elseif(isset($_GET["tipo2"]) && is_numeric($_GET["tipo2"])){
    $query .= " WHERE tipo2=".$_GET["tipo2"];
}elseif(isset($_GET["shiny"])){
    $query .= " WHERE shiny=".$_GET["shiny"];
}

$result = $conn->query($query);

if(isset($result)&& $result){

    if($result->num_rows >0){
        $arrPokedex = array();

        while($row = $result->fetch_assoc()){
            $arrPokemon = array();

            $arrPokemon["num_pokedex"] = $row["num_pokedex"];
            $arrPokemon["nombre"] = $row["nombre"];
            $arrPokemon["tipo1"] = $row["tipo1"];
            $arrPokemon["tipo2"] = $row["tipo2"];
            $arrPokemon["shiny"] = $row["shiny"];
            $arrPokemon["url"] = $row["url"];

            $arrPokedex[] = $arrPokemon;
            

        }

        $arrMensaje["estado"] = "ok";
        $arrMensaje["status"] = http_response_code();
        $arrMensaje["pokedex"] = $arrPokedex;
    }else{

        $arrMensaje["estado"] = "ok";
        $arrMensaje["status"] = http_response_code();
        $arrMensaje["pokedex"] = [];
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