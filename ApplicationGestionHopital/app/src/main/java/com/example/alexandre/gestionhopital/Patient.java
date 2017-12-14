package com.example.alexandre.gestionhopital;

import java.util.Date;

/**
 * Created by Alexandre on 14/12/2017.
 */

public class Patient {

    int id;
    String numsecu;
    String nom;
    String prenom;
    Date datenaiss;
    int codepostal;
    String mail;
    int assurer;

    public Patient(int Pid,String Pnumsecu,String Pnom,String Pprenom,Date Pdatenaiss,int Pcodepostal,String Pmail,int Passurer)
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

}