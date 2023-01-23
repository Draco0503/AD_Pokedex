<?php

$arrPokemonCreateUpdate = array();

$arrPokemonCreateUpdate["num_pokedex"] = 1;
$arrPokemonCreateUpdate["nombre"] = "ditto";
$arrPokemonCreateUpdate["tipo1"] = 1;
$arrPokemonCreateUpdate["tipo2"] = 1;
$arrPokemonCreateUpdate["shiny"] = true;
$arrPokemonCreateUpdate["url"] = "URL";


$arrPokemonDelete = array();

$arrPokemonDelete["num_pokedex"] = 1;



/* Funcion para comprobar si el recibido es igual al esperado */

function JSONCorrecto($esperado, $recibido){

    //depurando("Array esperado", $esperado, __FILE__, __LINE__, false);
    //depurando("Array recibido", $recibido, __FILE__, __LINE__, false);
    
    $auxCorrecto = true;
    
    foreach ($esperado as $clave => $valor){
           
        //Comprobamos que cada clave de esperado exista en recibido 
        if(isset($recibido[$clave])){
            
            //Comprobamos tipos (los valores nos dan igual)
            
            $tipoEsperado = gettype($esperado[$clave]);
            $tipoRecibido = gettype($recibido[$clave]);
            
            if($tipoEsperado == $tipoRecibido  ){
                //Si el valor es un array hacemos una llamada recursiva
                if(is_array($esperado[$clave])){
                    if(is_array($recibido[$clave])){
                        $auxCorrecto = JSONCorrecto($esperado[$clave], $recibido[$clave]);
                    }else{ // Hay un array en esperado y no en recibido
                        $auxCorrecto = false;
                        //depurando("MENSAJE", "Hay un array en esperado y no en recibido", __FILE__, __LINE__, false);
                        break;     
                    }
                }
            }else{ // Son distinto tipo
                $auxCorrecto = false;
                //depurando("MENSAJE", "Las variables de nombre '$clave' son de distinto tipo\n $tipoEsperado\n $tipoRecibido\n", __FILE__, __LINE__, false);
                break;
            }
        } else{  // No existe la clave de esperado en recibido
            
            $auxCorrecto = false;

           // depurando("MENSAJE", "No existe clave '$clave' en el array recibido", __FILE__, __LINE__, false);
            
            break;
            
        }

        
    }  

   // depurando("TODO CORRECTO", $auxCorrecto, __FILE__, __LINE__, false);
    
    return $auxCorrecto;
    
}


?>