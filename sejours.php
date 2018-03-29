<?php
$methode=$_SERVER["REQUEST_METHOD"];

switch($methode)
{
  case "GET":
    if(!isset($_GET['id']))
    {
      $lessejours = getLessejours();
      echo json_encode($lessejours);
    }
    else
    {


    }
    break;

  case "PUT":

    parse_str(file_get_contents('php://input'),$_PUT);
    //if(isset($_PUT['Validerentree']) && isset($_PUT['idsejours']))
    //{
    ModifentreePatient($_PUT['Validerentree'],$_PUT['idsejours']);
    //}

    break;

}

function connexion(){
    $dsn='mysql:dbname=symfonysprint;host=localhost';
    $username='root';
    $passwd='';

    try{
        $dbh=new PDO($dsn,$username,$passwd);
    } catch (Exception $e) {
        echo 'Connexion echoue : '.$e->getMessage();
    }
    return $dbh;
}

function getLessejours(){

  $dbh = connexion();

  $pdoStatement = $dbh->prepare("SELECT sejours.id,sejours.datedebut,sejours.datefin,patient.nom,patient.prenom,chambre.id as numchambre,numlit,Validerentree,Validersortie FROM sejours join patient on sejours.patient_id = patient.id JOIN chambre ON sejours.leschambres_id = chambre.id WHERE datefin >= date(NOW()) GROUP BY datedebut");

    if($pdoStatement->execute()){

        $resultat = $pdoStatement->fetchAll();
        $pdoStatement->closeCursor();
        $dbh=null;
      }
      else{
        throw new Exception("Erreur recupération sejours");
      }
  return $resultat;
}

function ModifentreePatient($Validerentree,$idsejours)
{
  $dbh = connexion();

  $pdoStatement = $dbh->prepare("UPDATE sejours SET Validerentree=:Validerentree WHERE id=:idsejours");

  $PdoStatement->bindvalue("Validerentree",$Validerentree);
  $PdoStatement->bindvalue("idsejours",$idsejours);


  if($PdoStatement->execute()){
    $PdoStatement->closeCursor();
    $dbh=null;
  }
  else{
      throw new Exception("Erreur modification du sejours");
    }


}

 ?>
