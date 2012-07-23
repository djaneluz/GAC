package br.com.sw2.gac.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the TblContrato database table.
 * 
 */
@Entity
@Table(name="TblContrato")
public class Contrato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(unique=true, nullable=false, length=10)
	private String nmContrato;

	@Column(nullable=false, length=60)
	private String dsBairroContratante;

	@Column(nullable=false, length=60)
	private String dsCidadeContratante;

	@Column(length=100)
	private String dsEMailContratante;

	@Column(nullable=false, length=60)
	private String dsEnderecoContratante;

	@Column(nullable=false, length=2)
	private String dsUFContratante;

	@Temporal(TemporalType.DATE)
	private Date dtFinalValidade;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date dtInicioValidade;

	@Temporal(TemporalType.DATE)
	private Date dtNascContratante;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date dtProxAtual;

	@Temporal(TemporalType.DATE)
	private Date dtSuspensao;

	@Column(nullable=false, length=10)
	private String nmCEPContratante;

	@Column(nullable=false, length=14)
	private String nmCPFContratante;

	@Column(nullable=false, length=60)
	private String nmNomeContratante;

	@Column(length=14)
	private String nmRGContratante;

	//bi-directional many-to-one association to TblPacoteServico
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="IdServico", nullable=false)
	private PacoteServico tblPacoteServico;

	//bi-directional many-to-one association to TbUsuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Login", nullable=false)
	private Usuario tbUsuario;

	//bi-directional many-to-one association to TblPaciente
	@OneToMany(mappedBy="tblContrato")
	private List<Paciente> tblPacientes;

	public Contrato() {
	}

	public String getNmContrato() {
		return this.nmContrato;
	}

	public void setNmContrato(String nmContrato) {
		this.nmContrato = nmContrato;
	}

	public String getDsBairroContratante() {
		return this.dsBairroContratante;
	}

	public void setDsBairroContratante(String dsBairroContratante) {
		this.dsBairroContratante = dsBairroContratante;
	}

	public String getDsCidadeContratante() {
		return this.dsCidadeContratante;
	}

	public void setDsCidadeContratante(String dsCidadeContratante) {
		this.dsCidadeContratante = dsCidadeContratante;
	}

	public String getDsEMailContratante() {
		return this.dsEMailContratante;
	}

	public void setDsEMailContratante(String dsEMailContratante) {
		this.dsEMailContratante = dsEMailContratante;
	}

	public String getDsEnderecoContratante() {
		return this.dsEnderecoContratante;
	}

	public void setDsEnderecoContratante(String dsEnderecoContratante) {
		this.dsEnderecoContratante = dsEnderecoContratante;
	}

	public String getDsUFContratante() {
		return this.dsUFContratante;
	}

	public void setDsUFContratante(String dsUFContratante) {
		this.dsUFContratante = dsUFContratante;
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

	public Date getDtNascContratante() {
		return this.dtNascContratante;
	}

	public void setDtNascContratante(Date dtNascContratante) {
		this.dtNascContratante = dtNascContratante;
	}

	public Date getDtProxAtual() {
		return this.dtProxAtual;
	}

	public void setDtProxAtual(Date dtProxAtual) {
		this.dtProxAtual = dtProxAtual;
	}

	public Date getDtSuspensao() {
		return this.dtSuspensao;
	}

	public void setDtSuspensao(Date dtSuspensao) {
		this.dtSuspensao = dtSuspensao;
	}

	public String getNmCEPContratante() {
		return this.nmCEPContratante;
	}

	public void setNmCEPContratante(String nmCEPContratante) {
		this.nmCEPContratante = nmCEPContratante;
	}

	public String getNmCPFContratante() {
		return this.nmCPFContratante;
	}

	public void setNmCPFContratante(String nmCPFContratante) {
		this.nmCPFContratante = nmCPFContratante;
	}

	public String getNmNomeContratante() {
		return this.nmNomeContratante;
	}

	public void setNmNomeContratante(String nmNomeContratante) {
		this.nmNomeContratante = nmNomeContratante;
	}

	public String getNmRGContratante() {
		return this.nmRGContratante;
	}

	public void setNmRGContratante(String nmRGContratante) {
		this.nmRGContratante = nmRGContratante;
	}

	public PacoteServico getTblPacoteServico() {
		return this.tblPacoteServico;
	}

	public void setTblPacoteServico(PacoteServico tblPacoteServico) {
		this.tblPacoteServico = tblPacoteServico;
	}

	public Usuario getTbUsuario() {
		return this.tbUsuario;
	}

	public void setTbUsuario(Usuario tbUsuario) {
		this.tbUsuario = tbUsuario;
	}

	public List<Paciente> getTblPacientes() {
		return this.tblPacientes;
	}

	public void setTblPacientes(List<Paciente> tblPacientes) {
		this.tblPacientes = tblPacientes;
	}

}