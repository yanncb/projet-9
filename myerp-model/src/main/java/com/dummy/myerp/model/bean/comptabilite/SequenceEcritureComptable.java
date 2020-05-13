package com.dummy.myerp.model.bean.comptabilite;


import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * Bean représentant une séquence pour les références d'écriture comptable
 */
public class SequenceEcritureComptable {

    // ==================== Attributs ====================
    /** L'année */
    @NotNull
    private Integer annee;
    /** La dernière valeur utilisée */
    private Integer derniereValeur;

    private JournalComptable journalComptable;

    // ==================== Constructeurs ====================
    /**
     * Constructeur
     */
    public SequenceEcritureComptable() {
    }

    /**
     *
     * @param journalComptable
     * @param annee
     * @param derniereValeur
     */
    public SequenceEcritureComptable(JournalComptable journalComptable, Integer annee, Integer derniereValeur) {
        this.journalComptable = journalComptable;
        this.annee = annee;
        this.derniereValeur = derniereValeur;
    }


    // ==================== Getters/Setters ====================
    public Integer getAnnee() {
        return annee;
    }
    public void setAnnee(Integer pAnnee) {
        annee = pAnnee;
    }
    public Integer getDerniereValeur() {
        return derniereValeur;
    }
    public void setDerniereValeur(Integer pDerniereValeur) {
        derniereValeur = pDerniereValeur;
    }

    public JournalComptable getJournalComptable() {
        return journalComptable;
    }

    public void setJournalComptable(JournalComptable journalComptable) {
        this.journalComptable = journalComptable;
    }

    // ==================== Méthodes ====================
    @Override
    public String toString() {
        final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
        final String vSEP = ", ";
        vStB.append("{")
            .append("annee=").append(annee)
            .append(vSEP).append("derniereValeur=").append(derniereValeur)
            .append("}");
        return vStB.toString();
    }
    
    
    // ==================== Méthodes STATIC ====================
    /**
     * Renvoie le {@link SequenceEcritureComptable} de code {@code pCode} s'il est présent dans la liste
     *
     * @param pList la liste où chercher le {@link SequenceEcritureComptable}
     * @param pCode le code du {@link SequenceEcritureComptable} à chercher
     * @param  pYear l'année du {@link SequenceEcritureComptable} à chercher
     * @return {@link SequenceEcritureComptable} ou {@code null}
     */
    public static SequenceEcritureComptable getByCodeAndYear(List<? extends SequenceEcritureComptable> pList, String pCode, Integer pYear) {
        SequenceEcritureComptable vRetour = null;
        for (SequenceEcritureComptable vBean : pList) {
            if (isSequenceEcritureComptableExist( vBean,  pCode, pYear )) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }

    private static boolean isSequenceEcritureComptableExist(SequenceEcritureComptable vBean, String pCode, Integer pYear) {
        return (vBean != null && Objects.equals(vBean.getJournalComptable().getCode(), pCode) && Objects.equals(vBean.getAnnee(), pYear));
    }

}
