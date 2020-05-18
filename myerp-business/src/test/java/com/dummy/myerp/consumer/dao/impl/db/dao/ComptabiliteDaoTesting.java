package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;

import java.util.List;

public class ComptabiliteDaoTesting implements ComptabiliteDao {

    private SequenceEcritureComptable sequenceEcritureComptable;

    @Override
    public List<CompteComptable> getListCompteComptable() {
        return null;
    }

    @Override
    public List<JournalComptable> getListJournalComptable() {
        return null;
    }

    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return null;
    }

    @Override
    public EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException {
        return null;
    }

    @Override
    public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
        return null;
    }

    @Override
    public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {

    }

    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) {

    }

    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) {

    }

    @Override
    public void deleteEcritureComptable(Integer pId) {

    }

    @Override
    public SequenceEcritureComptable getSequenceEcritureComptable(String pJournalCode, Integer pAnnee) {
        return sequenceEcritureComptable;
    }

    @Override
    public void updateSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable) {

    }

    @Override
    public void deleteSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable) {

    }

    @Override
    public void insertSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable) {

    }

    public void setSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable) {
        this.sequenceEcritureComptable = sequenceEcritureComptable;
    }
}
