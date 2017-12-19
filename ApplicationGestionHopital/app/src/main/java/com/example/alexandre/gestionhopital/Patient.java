package com.example.alexandre.gestionhopital;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Alexandre on 14/12/2017.
 */

public class Patient implements Serializable {


    int id;
    int numsecu;
    String nom;
    String prenom;
    Date datenaiss;
    int codepostal;
    String mail;
    int assurer;

    public Patient(int Pid,int Pnumsecu,String Pnom,String Pprenom,Date Pdatenaiss,int Pcodepostal,String Pmail,int Passurer)
    {
        id = Pid;
        numsecu = Pnumsecu;
        nom = Pnom;
        prenom = Pprenom;
        datenaiss = Pdatenaiss;
        codepostal = Pcodepostal;
        mail = Pmail;
        assurer = Passurer;
    }

    public String toString()
    {
        return "nom : "+nom+" prenom : "+prenom;
    }

    public String getPrenom() {
        return prenom;
    }
    public int getId() {
        return id;
    }

    public int getNumsecu() {
        return numsecu;
    }

    public String getNom() {
        return nom;
    }

    public Date getDatenaiss() {
        return datenaiss;
    }

    public int getCodepostal() {
        return codepostal;
    }

    public String getMail() {
        return mail;
    }

    public int getAssurer() {
        return assurer;
    }

}
