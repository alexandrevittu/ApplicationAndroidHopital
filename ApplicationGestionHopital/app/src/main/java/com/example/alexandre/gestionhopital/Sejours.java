package com.example.alexandre.gestionhopital;

import java.io.Serializable;
import java.security.Policy;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alexandre on 24/03/2018.
 */

public class Sejours implements Serializable {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public int getId() {
        return id;
    }

    int id;
    Date datedebut;

    public Date getDatefin() {
        return datefin;
    }

    Date datefin;

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getNumchambre() {
        return numchambre;
    }

    public int getNumlit() {
        return numlit;
    }

    String nom;
    String prenom;
    int numchambre;
    int numlit;

    public int getValideRentree() {
        return ValideRentree;
    }

    int ValideRentree;

    public int getValiderSortie() {
        return ValiderSortie;
    }

    int ValiderSortie;

    public Sejours(int Pid,Date Pdatedebut, Date Pdatefin,String Pnom,String Pprenom,int Pnumchambre, int Pnumlit,int Pvaliderentree,int Pvalidersortie)
    {
        id = Pid;
        datedebut = Pdatedebut;
        datefin = Pdatefin;
        nom = Pnom;
        prenom = Pprenom;
        numchambre = Pnumchambre;
        numlit = Pnumlit;
        ValideRentree = Pvaliderentree;
        ValiderSortie = Pvalidersortie;
    }

    public String toString() {

        return "Date de debut : "+sdf.format(datedebut)+ "\ndate de fin : "+sdf.format(datedebut)+"\nPatient : "+nom+" "+prenom+"\nNumero de chambre : "+numchambre;
    }

    public Date getDatedebut(){ return datedebut;}
}
