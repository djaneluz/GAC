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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rogerio
 */
@Entity
@Table(name = "tblmonitoramento")
@NamedQueries({
    @NamedQuery(name = "Monitoramento.findAll", query = "SELECT m FROM Monitoramento m")})
public class Monitoramento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dtaInicioMonitora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtaInicioMonitora;
    @Column(name = "tpMonitora")
    private Character tpMonitora;
    @Column(name = "Acontecimento")
    private Character acontecimento;
    @JoinColumn(name = "NmCPFCliente", referencedColumnName = "NmCPFCliente")
    @ManyToOne(optional = false)
    private Cliente nmCPFCliente;

    public Monitoramento() {
    }

    public Monitoramento(Date dtaInicioMonitora) {
        this.dtaInicioMonitora = dtaInicioMonitora;
    }

    public Date getDtaInicioMonitora() {
        return dtaInicioMonitora;
    }

    public void setDtaInicioMonitora(Date dtaInicioMonitora) {
        this.dtaInicioMonitora = dtaInicioMonitora;
    }

    public Character getTpMonitora() {
        return tpMonitora;
    }

    public void setTpMonitora(Character tpMonitora) {
        this.tpMonitora = tpMonitora;
    }

    public Character getAcontecimento() {
        return acontecimento;
    }

    public void setAcontecimento(Character acontecimento) {
        this.acontecimento = acontecimento;
    }

    public Cliente getNmCPFCliente() {
        return nmCPFCliente;
    }

    public void setNmCPFCliente(Cliente nmCPFCliente) {
        this.nmCPFCliente = nmCPFCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dtaInicioMonitora != null ? dtaInicioMonitora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Monitoramento)) {
            return false;
        }
        Monitoramento other = (Monitoramento) object;
        if ((this.dtaInicioMonitora == null && other.dtaInicioMonitora != null) || (this.dtaInicioMonitora != null && !this.dtaInicioMonitora.equals(other.dtaInicioMonitora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.sw2.gac.modelo.Monitoramento[ dtaInicioMonitora=" + dtaInicioMonitora + " ]";
    }
    
}
