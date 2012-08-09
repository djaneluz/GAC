/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sw2.gac.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rogerio
 */
@Entity
@Table(name = "tblparametro")
@NamedQueries({
    @NamedQuery(name = "Parametro.findAll", query = "SELECT p FROM Parametro p")})
public class Parametro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdParametro")
    private Integer idParametro;
    @Basic(optional = false)
    @Column(name = "DiasDados")
    private int diasDados;
    @Basic(optional = false)
    @Column(name = "DiasBemEstar")
    private int diasBemEstar;
    @Column(name = "ToleraRotinaCliente")
    private Integer toleraRotinaCliente;

    public Parametro() {
    }

    public Parametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public Parametro(Integer idParametro, int diasDados, int diasBemEstar) {
        this.idParametro = idParametro;
        this.diasDados = diasDados;
        this.diasBemEstar = diasBemEstar;
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public int getDiasDados() {
        return diasDados;
    }

    public void setDiasDados(int diasDados) {
        this.diasDados = diasDados;
    }

    public int getDiasBemEstar() {
        return diasBemEstar;
    }

    public void setDiasBemEstar(int diasBemEstar) {
        this.diasBemEstar = diasBemEstar;
    }

    public Integer getToleraRotinaCliente() {
        return toleraRotinaCliente;
    }

    public void setToleraRotinaCliente(Integer toleraRotinaCliente) {
        this.toleraRotinaCliente = toleraRotinaCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParametro != null ? idParametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametro)) {
            return false;
        }
        Parametro other = (Parametro) object;
        if ((this.idParametro == null && other.idParametro != null) || (this.idParametro != null && !this.idParametro.equals(other.idParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.sw2.gac.modelo.Parametro[ idParametro=" + idParametro + " ]";
    }
    
}
