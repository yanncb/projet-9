package com.dummy.myerp.model.bean.comptabilite;

import org.apache.commons.lang.ObjectUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SequenceEcritureComptableTest {
    private List<JournalComptable > journalComptableList= new ArrayList<JournalComptable>();

    @Before
    public void init(){
        journalComptableList.add(new JournalComptable("AC","Achat") );
        journalComptableList.add(new JournalComptable("VE","Vente") );
        journalComptableList.add(new JournalComptable("BQ","Banque") );
        journalComptableList.add(new JournalComptable("OD","Opérations Diverses") );
    }

    @Test
    public void getByCodeAndYear(){
        List<SequenceEcritureComptable> sequenceEcritureComptableList = new ArrayList<SequenceEcritureComptable>();
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();

        JournalComptable journal = (JournalComptable) ObjectUtils.defaultIfNull(
                JournalComptable.getByCode( journalComptableList, "AC" ),
                new JournalComptable( "AC","Achat" ) );

        sequenceEcritureComptable.setJournalComptable( journal );
        sequenceEcritureComptable.setAnnee(2016);
        sequenceEcritureComptable.setDerniereValeur(1);

        sequenceEcritureComptableList.add( sequenceEcritureComptable );
        Assert.assertNotNull(SequenceEcritureComptable.getByCodeAndYear(sequenceEcritureComptableList,"AC",2016) );
        Assert.assertEquals(sequenceEcritureComptable.getDerniereValeur(),SequenceEcritureComptable.getByCodeAndYear(sequenceEcritureComptableList,"AC",2016).getDerniereValeur());
        Assert.assertNull(SequenceEcritureComptable.getByCodeAndYear(sequenceEcritureComptableList,"AC",2000) );

    }


}
