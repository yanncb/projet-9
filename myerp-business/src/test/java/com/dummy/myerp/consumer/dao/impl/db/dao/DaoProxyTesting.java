package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;

public class DaoProxyTesting implements DaoProxy {
    private ComptabiliteDao comptabiliteDao;

    public DaoProxyTesting(ComptabiliteDao comptabiliteDao) {
        this.comptabiliteDao = comptabiliteDao;
    }

    @Override
    public ComptabiliteDao getComptabiliteDao() {
        return comptabiliteDao;
    }
}
