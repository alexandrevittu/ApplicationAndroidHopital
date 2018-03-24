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
    int id;
    Date datedebut;
    Date datefin;
    String nom;
    String prenom;
    int numchambre;
    int numlit;

    public Sejours(int Pid,Date Pdatedebut, Date Pdatefin,String Pnom,String Pprenom,int Pnumchambre, int Pnumlit)
    {
        id = Pid;
        datedebut = Pdatedebut;
        datefin = Pdatefin;
        nom = Pnom;
        prenom = Pprenom;
        numchambre = Pnumchambre;
        numlit = Pnumlit;
    }

    public String toString() {

        return "Date de debut : "+sdf.format(datedebut)+ "\ndate de fin : "+sdf.format(datedebut)+"\nPatient : "+nom+" "+prenom+"\nNumero de chambre : "+numchambre;
    }

    public Date getDatedebut(){ return datedebut;}
}
