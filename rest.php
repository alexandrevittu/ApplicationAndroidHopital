
<?php

$methode=$_SERVER["REQUEST_METHOD"];

switch($methode)
{
  case "GET":
    if(!isset($_GET['id']))
    {

        $lespatients=getLespatients();
        /*echo'<table>';
        echo'<tr><th>id</th><th>numero securité social</th><th>nom</th><th>prenom</th><th>mail</th></tr>';

        foreach($lespatients as $unpatient)
        {
          echo'<tr><td>'.$unpatient['id'].'</td>';
          echo'<td>'.$unpatient['numerosecu'].'</td>';
          echo'<td>'.$unpatient['nom'].'</td>';
          echo'<td>'.$unpatient['prenom'].'</td>';
          echo'<td>'.$unpatient['mail'].'</td>';


        }
        echo'</tr></table>';*/
		echo json_encode($lespatients);

    } else {
      $unpatient=getpatient($_GET['id']);
      /*echo'<table>';
      echo'<tr><th>id</th><th>numero securité social</th><th>nom</th><th>prenom</th><th>mail</th></tr>';

      echo'<tr><td>'.$unpatient['id'].'</td>';
      echo'<td>'.$unpatient['numerosecu'].'</td>';
      echo'<td>'.$unpatient['nom'].'</td>';
      echo'<td>'.$unpatient['prenom'].'</td>';
      echo'<td>'.$unpatient['mail'].'</td>';
      echo'</tr></table>';*/

      echo json_encode($unpatient);
    }
    break;
  case "POST":

    $patient = ajouterpatient($_POST['id'],$_POST['numerosecu'],$_POST['nom'],$_POST['prenom'],$_POST['datenaiss'],$_POST['codepostal'],$_POST['mail'],$_POST['assurer']);


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

function getPatient($id){

  $dbh = connexion();

  $pdoStatement = $dbh->prepare("select * from patient where id =:id");
  $pdoStatement->bindparam('id',$id);

    if($pdoStatement->execute()){

        $resultat = $pdoStatement->fetch();
        $pdoStatement->closeCursor();
        $dbh=null;
      }
      else{
        throw new Exception("Erreur recupération patient");
      }
  return $resultat;
}

function getLespatients(){

  $dbh = connexion();

  $pdoStatement = $dbh->prepare("select * from patient");

    if($pdoStatement->execute()){

        $resultat = $pdoStatement->fetchAll();
        $pdoStatement->closeCursor();
        $dbh=null;
      }
      else{
        throw new Exception("Erreur recupération patient");
      }
  return $resultat;
}

function ajouterpatient($id,$numerosecu,$nom,$prenom,$datenaiss,$codepostal,$mail,$assurer){
  $dbh=connexion();
  $PdoStatement=$dbh->prepare("INSERT INTO patient VALUES (:id,:numerosecu,:nom,:prenom,:datenaiss,:codepostal,:mail,:assurer)");
  $PdoStatement->bindvalue("id",$id);
  $PdoStatement->bindvalue("numerosecu",$numerosecu);
  $PdoStatement->bindvalue("nom",$nom);
  $PdoStatement->bindvalue("prenom",$prenom);
  $PdoStatement->bindvalue("datenaiss",$datenaiss);
  $PdoStatement->bindvalue("codepostal",$codepostal);
  $PdoStatement->bindvalue("mail",$mail);
  $PdoStatement->bindvalue("assurer",$assurer);

  if($PdoStatement->execute()){
      $PdoStatement->closeCursor();
      $dbh=null;
  }
  else{
      throw new Exception("Erreur ajout de patient");
  }

}




?>
