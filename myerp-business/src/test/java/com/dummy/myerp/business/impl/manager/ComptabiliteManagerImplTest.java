package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoTesting;
import com.dummy.myerp.consumer.dao.impl.db.dao.DaoProxyTesting;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class ComptabiliteManagerImplTest {


    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    @Test
    public void checkEcritureComptableUnit() throws Exception {
        EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {

        // GIVEN
        EcritureComptable vEcritureComptable = new EcritureComptable();

        // WHEN
        manager.checkEcritureComptableUnit(vEcritureComptable);

        // THEN
        // expected FunctionalException (voir conf du test)
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws Exception {

        // GIVEN
        EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(1234)));
        // WHEN
        manager.checkEcritureComptableUnit(vEcritureComptable);

        // THEN
        // expected FunctionalException (voir conf du test)

    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws Exception {
        EcritureComptable vEcritureComptable = new EcritureComptable();

        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5() throws Exception {
        EcritureComptable vEcritureComptable = new EcritureComptable();

        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(10),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(10)));


        vEcritureComptable.setReference("AC-2016/00001");
        manager.RG_Compta_5(vEcritureComptable);
    }

    @Test
    public void CheckRG_Compta_5() throws FunctionalException {

        // GIVEN
        EcritureComptable vEcritureComptable = new EcritureComptable();

        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));
        vEcritureComptable.setReference("AC-2020/00001");

        //WHEN
        manager.RG_Compta_5(vEcritureComptable);

        //THEN
        //J'attends aucune exception.

    }

    //TODO  A FINIR !!
    @Test
    public void addReferenceSansEcritureCompatable() {
        ComptabiliteDaoTesting comptabiliteDaoTesting = new ComptabiliteDaoTesting();
        comptabiliteDaoTesting.setSequenceEcritureComptable(null);
        manager.configure(null, new DaoProxyTesting(comptabiliteDaoTesting), null);

        // GIVEN
        EcritureComptable vEcriture = new EcritureComptable();
        vEcriture.setJournal(new JournalComptable("AC", "Achat"));
        vEcriture.setLibelle("Libelle");
        vEcriture.setDate(new Date());

        //WHEN
        manager.addReference(vEcriture);

        //THEN
        assertThat(vEcriture.getReference()).isEqualTo("AC-2020/00001");

    }

    @Test
    public void addReferenceAvecEcritureCompatable() {
        // GIVEN
        EcritureComptable vEcriture = new EcritureComptable();
        JournalComptable journalComptable = new JournalComptable("AC", "Achat");
        vEcriture.setJournal(journalComptable);
        vEcriture.setLibelle("Libelle");
        vEcriture.setDate(new Date());

        SequenceEcritureComptable sequenceEcritureCompatbleIntial = new SequenceEcritureComptable();
        sequenceEcritureCompatbleIntial.setJournalComptable(journalComptable);
        sequenceEcritureCompatbleIntial.setAnnee(2020);
        sequenceEcritureCompatbleIntial.setDerniereValeur(999);

        ComptabiliteDaoTesting comptabiliteDaoTesting = new ComptabiliteDaoTesting();
        comptabiliteDaoTesting.setSequenceEcritureComptable(sequenceEcritureCompatbleIntial);
        manager.configure(null, new DaoProxyTesting(comptabiliteDaoTesting), null);

        //WHEN
        manager.addReference(vEcriture);

        //THEN
        assertThat(vEcriture.getReference()).isEqualTo("AC-2020/01000");

    }

//
//    @Test
//    public void setReference() {
//
//        //GIVEN
//        //WHEN
//        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(new JournalComptable("AC", "Achat"), 2020, 1);
//
//
//        //THEN
//        Assert.assertEquals("AC-2020/00001", manager.computeReference(sequenceEcritureComptable));
//        Assert.assertNotEquals("AL-2020/0001", manager.computeReference(sequenceEcritureComptable));
//
//
//        sequenceEcritureComptable = new SequenceEcritureComptable(new JournalComptable("AC", "Achat"), 2016, 1);
//
//        Assert.assertEquals("AC-2016/00001", manager.computeReference(sequenceEcritureComptable));
//        Assert.assertNotEquals("AV-2016/00001", manager.computeReference(sequenceEcritureComptable));
//    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptable() {


    }
}
