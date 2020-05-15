package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;

public class ComptabiliteManagerImplTest {


    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    private JournalComptable JournalComptable;

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
        manager.checkEcritureComptable(vEcritureComptable);
    }

    @Test (expected = FunctionalException.class)
    public void CheckRG_Compta_5() throws FunctionalException {

        // GIVEN
        EcritureComptable vEcriture = new EcritureComptable();
        vEcriture.setJournal(vEcriture.getJournal());
        vEcriture.setReference("AC-2020/00001");
        vEcriture.setDate( new Date() );

        //WHEN
        manager.checkEcritureComptable( vEcriture );

        //THEN
        // expected FunctionalException (voir conf du test)

    }

//TODO  A FINIR !!
    @Test
    public void addReference() {
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(JournalComptable,2020,1);


        assertThat(sequenceEcritureComptable.getAnnee()).isEqualTo(2020);
        assertThat(sequenceEcritureComptable.getDerniereValeur()).isEqualTo(1);

//        Assert.assertEquals("AC-2020/00001",manager.setReference(sequenceEcritureComptable));
//        assertThat(manager.setReference(sequenceEcritureComptable)).isEqualTo("AC-2020/00001");
    }



}
