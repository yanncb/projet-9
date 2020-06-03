package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoTesting;
import com.dummy.myerp.consumer.dao.impl.db.dao.DaoProxyTesting;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ComptabiliteManagerImplTest {

    /**
     * Logger
     **/

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Test
    public void checkEcritureComptableUnit() throws Exception {
        EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(SIMPLE_DATE_FORMAT.parse("01/01/2020"));
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2020/00001");
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
        vEcritureComptable.setDate(SIMPLE_DATE_FORMAT.parse("01/01/2020"));
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(1234)));
        // WHEN
        try {

            manager.checkEcritureComptableUnit(vEcritureComptable);
        } catch (FunctionalException ex) {
            // THEN
            // expected FunctionalException (voir conf du test)

            assertThat(ex.getMessage()).isEqualTo("L'écriture comptable n'est pas équilibrée.RG2");
            throw ex;
        }


    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws Exception {
        //GIVEN
        EcritureComptable vEcritureComptable = new EcritureComptable();

        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(SIMPLE_DATE_FORMAT.parse("01/01/2020"));
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                null));

        try {
            //WHEN
            manager.checkEcritureComptableUnit(vEcritureComptable);
        } catch (FunctionalException ex) {
            // THEN
            // expected FunctionalException (voir conf du test)

            assertThat(ex.getMessage()).isEqualTo("L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.RG3");
            throw ex;
        }
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5() throws Exception {
        EcritureComptable vEcritureComptable = new EcritureComptable();

        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(SIMPLE_DATE_FORMAT.parse("01/01/2017"));
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(10),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(10)));


        vEcritureComptable.setReference("XX-2012/00001");

        try {

            manager.checkEcritureComptableUnit(vEcritureComptable);
        } catch (FunctionalException ex) {
            // THEN
            // expected FunctionalException (voir conf du test)

            assertThat(ex.getMessage()).isEqualTo("La référence de l'écriture comptable n'est pas coherente avec l'année de l'écriture et/ou le code du journal.RG5");
            throw ex;

        }
    }

    @Test
    public void CheckRG_Compta_5() throws Exception {

        // GIVEN
        EcritureComptable vEcritureComptable = new EcritureComptable();

        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(SIMPLE_DATE_FORMAT.parse("01/01/2020"));
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));
        vEcritureComptable.setReference("AC-2020/00001");

        //WHEN
        manager.checkEcritureComptableUnit(vEcritureComptable);

        //THEN
        //J'attends aucune exception.

    }

    @Test
    public void addReferenceSansEcritureCompatable() throws Exception {
        ComptabiliteDaoTesting comptabiliteDaoTesting = new ComptabiliteDaoTesting();
        comptabiliteDaoTesting.setSequenceEcritureComptable(null);
        manager.configure(null, new DaoProxyTesting(comptabiliteDaoTesting), null);

        // GIVEN
        EcritureComptable vEcriture = new EcritureComptable();
        vEcriture.setJournal(new JournalComptable("AC", "Achat"));
        vEcriture.setLibelle("Libelle");
        vEcriture.setDate(SIMPLE_DATE_FORMAT.parse("01/01/2020"));

        //WHEN
        manager.addReference(vEcriture);

        //THEN
        assertThat(vEcriture.getReference()).isEqualTo("AC-2020/00001");
    }

    @Test
    public void addReferenceAvecEcritureCompatable() throws Exception {
        // GIVEN
        EcritureComptable vEcriture = new EcritureComptable();
        JournalComptable journalComptable = new JournalComptable("AC", "Achat");
        vEcriture.setJournal(journalComptable);
        vEcriture.setLibelle("Libelle");
        vEcriture.setDate(SIMPLE_DATE_FORMAT.parse("01/01/2020"));

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

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableContext() throws Exception {

        //GIVEN
        EcritureComptable vEcriture = new EcritureComptable();
        JournalComptable journalComptable = new JournalComptable("AC", "Achat");
        vEcriture.setJournal(journalComptable);
        vEcriture.setLibelle("Libelle");
        vEcriture.setDate(SIMPLE_DATE_FORMAT.parse("01/01/2020"));
        vEcriture.setReference("AC-2020/00001");

        //WHEN
        try {

            manager.checkEcritureComptableContext(vEcriture);
        } catch (FunctionalException ex) {
            // THEN
            // expected FunctionalException (voir conf du test)

            assertThat(ex.getMessage()).isEqualTo("Une autre écriture comptable existe déjà avec la même référence.RG6");
            throw ex;
        }


    }

    @Mock
    private DaoProxy daoProxyMock;

    @Mock
    private ComptabiliteDaoImpl comptabiliteDaoMock;

    @InjectMocks
    public ComptabiliteManagerImpl mockedManager;

    @Before
    public void setUp2() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockedManager.setDaoProxy(daoProxyMock);
        when(daoProxyMock.getComptabiliteDao()).thenReturn(comptabiliteDaoMock);

    }

    @Test
    public void checkEcritureComptable() throws Exception {

        //GIVEN
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setId(1);
        when(comptabiliteDaoMock.getEcritureComptableByRef("AC-2020/00001")).thenReturn(ecritureComptable);

        EcritureComptable vEcriture = new EcritureComptable();
        JournalComptable journalComptable = new JournalComptable("AC", "Achat");
        vEcriture.setJournal(journalComptable);
        vEcriture.setLibelle("Libelle");
        vEcriture.setDate(SIMPLE_DATE_FORMAT.parse("01/01/2020"));
        vEcriture.setReference("AC-2020/00001");
        vEcriture.setId(1);
        vEcriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        //WHEN
        mockedManager.checkEcritureComptable(vEcriture);

        //THEN
        //j'attends pas d'erreur.

    }

    @Test
    public void checkEcritureComptable2() throws Exception {

        //GIVEN
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setId(1);
        when(comptabiliteDaoMock.getEcritureComptableByRef(any())).thenThrow(new NotFoundException());

        EcritureComptable vEcriture = new EcritureComptable();
        JournalComptable journalComptable = new JournalComptable("AX", "Achat");
        vEcriture.setJournal(journalComptable);
        vEcriture.setLibelle("Libelle");
        vEcriture.setDate(SIMPLE_DATE_FORMAT.parse("01/01/2020"));
        vEcriture.setReference("AX-2020/00001");
        vEcriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        //WHEN
        mockedManager.checkEcritureComptable(vEcriture);

        //THEN
        //j'attends pas d'erreur.

    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptable3() throws Exception {

        //GIVEN
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setId(1);
        when(comptabiliteDaoMock.getEcritureComptableByRef("AC-2020/00002")).thenReturn(ecritureComptable);

        EcritureComptable vEcriture = new EcritureComptable();
        JournalComptable journalComptable = new JournalComptable("AC", "Achat");
        vEcriture.setJournal(journalComptable);
        vEcriture.setLibelle("Libelle");
        vEcriture.setDate(SIMPLE_DATE_FORMAT.parse("01/01/2020"));
        vEcriture.setReference("AC-2020/00002");
        vEcriture.setId(2);
        vEcriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        //WHEN
        try {
            mockedManager.checkEcritureComptable(vEcriture);
        } catch (FunctionalException ex) {
            assertThat(ex.getMessage()).isEqualTo("Une autre écriture comptable existe déjà avec la même référence.RG6");
            throw ex;
        }

    }


}
