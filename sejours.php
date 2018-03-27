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
    if(isset($_PUT['validerentree']) && isset($_PUT['idpatient']))
    {
      ModifentreePatient($_PUT['id'],$_PUT['entree']);
    }

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

  $pdoStatement = $dbh->prepare("SELECT sejours.id,sejours.datedebut,sejours.datefin,patient.nom,patient.prenom,chambre.id,numlit FROM sejours join patient on sejours.patient_id = patient.id JOIN chambre ON sejours.leschambres_id = chambre.id WHERE datedebut >= date(NOW()) GROUP BY datedebut");

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

function ModifentreePatient($idPatient,$idSejours)
{
  $dbh = connexion();

  $pdoStatement = $dbh->prepare("UPDATE sejours set ValiderEntree=:validerentree WHERE patient_id=:id");
  $PdoStatement->bindvalue("id",$idPatient);
  $PdoStatement->bindvalue("validerentree",$idSejours);

  if($PdoStatement->execute()){
    $PdoStatement->closeCursor();
    $dbh=null;
  }
  else{
      throw new Exception("Erreur modification du sejours");
    }


}

 ?>
