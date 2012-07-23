package br.com.sw2.gac.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the TblPacoteServico database table.
 * 
 */
@Entity
@Table(name="TblPacoteServico")
public class PacoteServico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="IdServico", unique=true, nullable=false)
	private int idServico;

	@Column(length=100)
	private String dsServico;

	@Temporal(TemporalType.DATE)
	private Date dtFinalValidade;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date dtInicioValidade;

	//bi-directional many-to-one association to TblContrato
	@OneToMany(mappedBy="tblPacoteServico")
	private List<Contrato> tblContratos;

	public PacoteServico() {
	}

	public int getIdServico() {
		return this.idServico;
	}

	public void setIdServico(int idServico) {
		this.idServico = idServico;
	}

	public String getDsServico() {
		return this.dsServico;
	}

	public void setDsServico(String dsServico) {
		this.dsServico = dsServico;
	}

	public Date getDtFinalValidade() {
		return this.dtFinalValidade;
	}

	public void setDtFinalValidade(Date dtFinalValidade) {
		this.dtFinalValidade = dtFinalValidade;
	}

	public Date getDtInicioValidade() {
		return this.dtInicioValidade;
	}

	public void setDtInicioValidade(Date dtInicioValidade) {
		this.dtInicioValidade = dtInicioValidade;
	}

	public List<Contrato> getTblContratos() {
		return this.tblContratos;
	}

	public void setTblContratos(List<Contrato> tblContratos) {
		this.tblContratos = tblContratos;
	}

}