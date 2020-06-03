package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JournalComptableTest {


    @Test
    public void getByCode() {
        List<JournalComptable> journalComptableList = new ArrayList<JournalComptable>();
        JournalComptable journalComptable = new JournalComptable();
        journalComptable.setCode("AC");
        journalComptable.setLibelle("Achat");

        journalComptableList.add(journalComptable);
        Assert.assertNotNull(JournalComptable.getByCode(journalComptableList, "AC"));
        Assert.assertEquals(journalComptable.getLibelle(), JournalComptable.getByCode(journalComptableList, "AC").getLibelle());
        Assert.assertNull(JournalComptable.getByCode(journalComptableList, "ACC"));
    }


}

