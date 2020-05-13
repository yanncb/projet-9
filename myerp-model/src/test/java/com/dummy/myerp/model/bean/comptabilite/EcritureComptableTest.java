package com.dummy.myerp.model.bean.comptabilite;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;


public class EcritureComptableTest {

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                vLibelle,
                vDebit, vCredit);
        return vRetour;
    }

    @Test
    public void isEquilibree() {

        EcritureComptable vEcriture = new EcritureComptable();
        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(3, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(4, "40", "7"));

        Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());

    }

    @Test
    public void isNotEquilibree() {

        // GIVEN
        EcritureComptable vEcriture = new EcritureComptable();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(3, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(4, "1", "2"));

        // WHEN
        boolean isEquilibree = vEcriture.isEquilibree();

        // THEN
        Assert.assertFalse(vEcriture.toString(), isEquilibree);
    }

    @Test
    public void getTotalDebit() {

        EcritureComptable vEcriture = new EcritureComptable();
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "12.5"));
        vEcriture.getListLigneEcriture().add(this.createLigne(3, "1.5", null));

        Assert.assertEquals(BigDecimal.valueOf(51.5), vEcriture.getTotalDebit());

    }

    @Test
    public void getTotalCredit() {

        EcritureComptable vEcriture = new EcritureComptable();
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "1", "10"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "2.5", "10"));
        vEcriture.getListLigneEcriture().add(this.createLigne(3, null, "30.5"));

        Assert.assertEquals(BigDecimal.valueOf(50.5), vEcriture.getTotalCredit());

    }
}
