package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CompteComptableTest {

    @Test
    public void getByNumero(){
        List<CompteComptable> compteComptableList = new ArrayList<CompteComptable>();
        compteComptableList.add(  new CompteComptable(11223344,"Compte courant") );
        Assert.assertNotNull( CompteComptable.getByNumero(compteComptableList,11223344) );
        Assert.assertNull( CompteComptable.getByNumero(compteComptableList,11223345));
    }

    @Test
    public void getByLibelle(){
        List<CompteComptable> compteComptableList = new ArrayList<>();
        compteComptableList.add(new CompteComptable(123123, "compte courant"));
        Assert.assertNotNull(CompteComptable.getByLibelle(compteComptableList,"compte courant"));
    }


}
