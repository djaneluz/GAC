/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sw2.gac.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <b>Descrição:</b> <br>
 * .
 * @author: SW2
 * @version 1.0 Copyright 2012 SmartAngel.
 */
@Entity
@Table(name = "tblcliente")
@NamedQueries({ @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c") })
public class Cliente implements Serializable {

    /** Constante serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** Atributo nm cpf cliente. */
    @Id
    @Basic(optional = false)
    @Column(name = "nmCPFCliente")
    private String nmCPFCliente;

    /** Atributo nm cliente. */
    @Basic(optional = false)
    @Column(name = "nmCliente")
    private String nmCliente;

    /** Atributo ds endereco. */
    @Basic(optional = false)
    @Column(name = "dsEndereco")
    private String dsEndereco;

    /** Atributo ds bairro. */
    @Basic(optional = false)
    @Column(name = "dsBairro")
    private String dsBairro;

    /** Atributo ds cidade. */
    @Basic(optional = false)
    @Column(name = "dsCidade")
    private String dsCidade;

    /** Atributo ds estado. */
    @Basic(optional = false)
    @Column(name = "dsEstado")
    private String dsEstado;

    /** Atributo ds cep. */
    @Basic(optional = false)
    @Column(name = "dsCEP")
    private String dsCEP;

    /** Atributo nr rg. */
    @Basic(optional = false)
    @Column(name = "nrRG")
    private String nrRG;

    /** Atributo tp sexo. */
    @Column(name = "tpSexo")
    private Integer tpSexo;

    /** Atributo dt nascimento. */
    @Basic(optional = false)
    @Column(name = "dtNascimento")
    @Temporal(TemporalType.DATE)
    private Date dtNascimento;

    /** Atributo nm necessidade especial. */
    @Lob
    @Column(name = "nmNecessidadeEspecial")
    private String nmNecessidadeEspecial;

    /** Atributo nm plano saude. */
    @Column(name = "nmPlanoSaude")
    private String nmPlanoSaude;

    /** Atributo ds cobertura. */
    @Lob
    @Column(name = "dsCobertura")
    private String dsCobertura;

    /** Atributo dta prox bem estar. */
    @Column(name = "dtaProxBemEstar")
    @Temporal(TemporalType.DATE)
    private Date dtaProxBemEstar;

    /** Atributo login. */
    @JoinColumn(name = "login", referencedColumnName = "login")
    @ManyToOne(optional = false)
    private Usuario login;

    /** Atributo nm contrato. */
    @JoinColumn(name = "nmContrato", referencedColumnName = "nmContrato")
    @ManyToOne
    private Contrato nmContrato;

    /**
     * Construtor Padrao Instancia um novo objeto Cliente.
     */
    public Cliente() {
    }

    /**
     * Construtor Padrao Instancia um novo objeto Cliente.
     * @param nmCPFCliente the nm cpf cliente
     */
    public Cliente(String nmCPFCliente) {
        this.nmCPFCliente = nmCPFCliente;
    }

    /**
     * Nome: getNmCPFCliente Recupera o valor do atributo 'nmCPFCliente'.
     * @return valor do atributo 'nmCPFCliente'
     * @see
     */
    public String getNmCPFCliente() {
        return nmCPFCliente;
    }

    /**
     * Nome: setNmCPFCliente Registra o valor do atributo 'nmCPFCliente'.
     * @param nmCPFCliente valor do atributo nm cpf cliente
     * @see
     */
    public void setNmCPFCliente(String nmCPFCliente) {
        this.nmCPFCliente = nmCPFCliente;
    }

    /**
     * Nome: getNmCliente Recupera o valor do atributo 'nmCliente'.
     * @return valor do atributo 'nmCliente'
     * @see
     */
    public String getNmCliente() {
        return nmCliente;
    }

    /**
     * Nome: setNmCliente Registra o valor do atributo 'nmCliente'.
     * @param nmCliente valor do atributo nm cliente
     * @see
     */
    public void setNmCliente(String nmCliente) {
        this.nmCliente = nmCliente;
    }

    /**
     * Nome: getDsEndereco Recupera o valor do atributo 'dsEndereco'.
     * @return valor do atributo 'dsEndereco'
     * @see
     */
    public String getDsEndereco() {
        return dsEndereco;
    }

    /**
     * Nome: setDsEndereco Registra o valor do atributo 'dsEndereco'.
     * @param dsEndereco valor do atributo ds endereco
     * @see
     */
    public void setDsEndereco(String dsEndereco) {
        this.dsEndereco = dsEndereco;
    }

    /**
     * Nome: getDsBairro Recupera o valor do atributo 'dsBairro'.
     * @return valor do atributo 'dsBairro'
     * @see
     */
    public String getDsBairro() {
        return dsBairro;
    }

    /**
     * Nome: setDsBairro Registra o valor do atributo 'dsBairro'.
     * @param dsBairro valor do atributo ds bairro
     * @see
     */
    public void setDsBairro(String dsBairro) {
        this.dsBairro = dsBairro;
    }

    /**
     * Nome: getDsCidade Recupera o valor do atributo 'dsCidade'.
     * @return valor do atributo 'dsCidade'
     * @see
     */
    public String getDsCidade() {
        return dsCidade;
    }

    /**
     * Nome: setDsCidade Registra o valor do atributo 'dsCidade'.
     * @param dsCidade valor do atributo ds cidade
     * @see
     */
    public void setDsCidade(String dsCidade) {
        this.dsCidade = dsCidade;
    }

    /**
     * Nome: getDsEstado Recupera o valor do atributo 'dsEstado'.
     * @return valor do atributo 'dsEstado'
     * @see
     */
    public String getDsEstado() {
        return dsEstado;
    }

    /**
     * Nome: setDsEstado Registra o valor do atributo 'dsEstado'.
     * @param dsEstado valor do atributo ds estado
     * @see
     */
    public void setDsEstado(String dsEstado) {
        this.dsEstado = dsEstado;
    }

    /**
     * Nome: getDsCEP Recupera o valor do atributo 'dsCEP'.
     * @return valor do atributo 'dsCEP'
     * @see
     */
    public String getDsCEP() {
        return dsCEP;
    }

    /**
     * Nome: setDsCEP Registra o valor do atributo 'dsCEP'.
     * @param dsCEP valor do atributo ds cep
     * @see
     */
    public void setDsCEP(String dsCEP) {
        this.dsCEP = dsCEP;
    }

    /**
     * Nome: getNrRG Recupera o valor do atributo 'nrRG'.
     * @return valor do atributo 'nrRG'
     * @see
     */
    public String getNrRG() {
        return nrRG;
    }

    /**
     * Nome: setNrRG Registra o valor do atributo 'nrRG'.
     * @param nrRG valor do atributo nr rg
     * @see
     */
    public void setNrRG(String nrRG) {
        this.nrRG = nrRG;
    }

    /**
     * Nome: getTpSexo Recupera o valor do atributo 'tpSexo'.
     * @return valor do atributo 'tpSexo'
     * @see
     */
    public Integer getTpSexo() {
        return tpSexo;
    }

    /**
     * Nome: setTpSexo Registra o valor do atributo 'tpSexo'.
     * @param tpSexo valor do atributo tp sexo
     * @see
     */
    public void setTpSexo(Integer tpSexo) {
        this.tpSexo = tpSexo;
    }

    /**
     * Nome: getDtNascimento Recupera o valor do atributo 'dtNascimento'.
     * @return valor do atributo 'dtNascimento'
     * @see
     */
    public Date getDtNascimento() {
        return dtNascimento;
    }

    /**
     * Nome: setDtNascimento Registra o valor do atributo 'dtNascimento'.
     * @param dtNascimento valor do atributo dt nascimento
     * @see
     */
    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    /**
     * Nome: getNmNecessidadeEspecial Recupera o valor do atributo 'nmNecessidadeEspecial'.
     * @return valor do atributo 'nmNecessidadeEspecial'
     * @see
     */
    public String getNmNecessidadeEspecial() {
        return nmNecessidadeEspecial;
    }

    /**
     * Nome: setNmNecessidadeEspecial Registra o valor do atributo 'nmNecessidadeEspecial'.
     * @param nmNecessidadeEspecial valor do atributo nm necessidade especial
     * @see
     */
    public void setNmNecessidadeEspecial(String nmNecessidadeEspecial) {
        this.nmNecessidadeEspecial = nmNecessidadeEspecial;
    }

    /**
     * Nome: getNmPlanoSaude Recupera o valor do atributo 'nmPlanoSaude'.
     * @return valor do atributo 'nmPlanoSaude'
     * @see
     */
    public String getNmPlanoSaude() {
        return nmPlanoSaude;
    }

    /**
     * Nome: setNmPlanoSaude Registra o valor do atributo 'nmPlanoSaude'.
     * @param nmPlanoSaude valor do atributo nm plano saude
     * @see
     */
    public void setNmPlanoSaude(String nmPlanoSaude) {
        this.nmPlanoSaude = nmPlanoSaude;
    }

    /**
     * Nome: getDsCobertura Recupera o valor do atributo 'dsCobertura'.
     * @return valor do atributo 'dsCobertura'
     * @see
     */
    public String getDsCobertura() {
        return dsCobertura;
    }

    /**
     * Nome: setDsCobertura Registra o valor do atributo 'dsCobertura'.
     * @param dsCobertura valor do atributo ds cobertura
     * @see
     */
    public void setDsCobertura(String dsCobertura) {
        this.dsCobertura = dsCobertura;
    }

    /**
     * Nome: getDtaProxBemEstar Recupera o valor do atributo 'dtaProxBemEstar'.
     * @return valor do atributo 'dtaProxBemEstar'
     * @see
     */
    public Date getDtaProxBemEstar() {
        return dtaProxBemEstar;
    }

    /**
     * Nome: setDtaProxBemEstar Registra o valor do atributo 'dtaProxBemEstar'.
     * @param dtaProxBemEstar valor do atributo dta prox bem estar
     * @see
     */
    public void setDtaProxBemEstar(Date dtaProxBemEstar) {
        this.dtaProxBemEstar = dtaProxBemEstar;
    }

    /**
     * Nome: getLogin Recupera o valor do atributo 'login'.
     * @return valor do atributo 'login'
     * @see
     */
    public Usuario getLogin() {
        return login;
    }

    /**
     * Nome: setLogin Registra o valor do atributo 'login'.
     * @param login valor do atributo login
     * @see
     */
    public void setLogin(Usuario login) {
        this.login = login;
    }

    /**
     * Nome: getNmContrato Recupera o valor do atributo 'nmContrato'.
     * @return valor do atributo 'nmContrato'
     * @see
     */
    public Contrato getNmContrato() {
        return nmContrato;
    }

    /**
     * Nome: setNmContrato Registra o valor do atributo 'nmContrato'.
     * @param nmContrato valor do atributo nm contrato
     * @see
     */
    public void setNmContrato(Contrato nmContrato) {
        this.nmContrato = nmContrato;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nmCPFCliente != null ? nmCPFCliente.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.nmCPFCliente == null && other.nmCPFCliente != null)
                || (this.nmCPFCliente != null && !this.nmCPFCliente.equals(other.nmCPFCliente))) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "br.com.sw2.gac.modelo.Cliente[ nmCPFCliente=" + nmCPFCliente + " ]";
    }

}
