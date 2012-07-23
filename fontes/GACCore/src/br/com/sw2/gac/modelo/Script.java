package br.com.sw2.gac.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the TblScript database table.
 * 
 */
@Entity
@Table(name="TblScript")
public class Script implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="IdScript", unique=true, nullable=false)
	private int idScript;

	@Column(length=100)
	private String dsDescricao;

	@Lob
	private String dsProcesso;

	@Temporal(TemporalType.DATE)
	private Date dtFinalValidade;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date dtInicioValidade;

	@Column(nullable=false, length=60)
	private String nmTitulo;

	//bi-directional many-to-one association to TblOcorrencia
	@OneToMany(mappedBy="tblScript")
	private List<Ocorrencia> tblOcorrencias;

	public Script() {
	}

	public int getIdScript() {
		return this.idScript;
	}

	public void setIdScript(int idScript) {
		this.idScript = idScript;
	}

	public String getDsDescricao() {
		return this.dsDescricao;
	}

	public void setDsDescricao(String dsDescricao) {
		this.dsDescricao = dsDescricao;
	}

	public String getDsProcesso() {
		return this.dsProcesso;
	}

	public void setDsProcesso(String dsProcesso) {
		this.dsProcesso = dsProcesso;
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

	public String getNmTitulo() {
		return this.nmTitulo;
	}

	public void setNmTitulo(String nmTitulo) {
		this.nmTitulo = nmTitulo;
	}

	public List<Ocorrencia> getTblOcorrencias() {
		return this.tblOcorrencias;
	}

	public void setTblOcorrencias(List<Ocorrencia> tblOcorrencias) {
		this.tblOcorrencias = tblOcorrencias;
	}

}