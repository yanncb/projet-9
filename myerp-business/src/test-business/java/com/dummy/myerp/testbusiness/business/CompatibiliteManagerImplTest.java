package com.dummy.myerp.testbusiness.business;

import org.junit.Before;

import static org.junit.Assert.assertNotNull;


/**
 * Classe de test de l'initialisation du contexte Spring
 */
public class CompatibiliteManagerImplTest extends BusinessTestCase {

    /**
     * Constructeur.
     */
    public CompatibiliteManagerImplTest() {
        super();
    }


    /**
     * Teste l'initialisation du contexte Spring
     */
    @Before
    public void testInit() {
        SpringRegistry.init();
        assertNotNull(SpringRegistry.getBusinessProxy());
        assertNotNull(SpringRegistry.getTransactionManager());
    }



}
