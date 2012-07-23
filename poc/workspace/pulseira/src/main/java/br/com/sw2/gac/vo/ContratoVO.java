package br.com.sw2.gac.vo;

import java.util.Date;
import java.util.List;

public class ContratoVO {

    private String numeroContrato;
    private Date dtInicioValidade;
    private Date dtFinalValidade;
    private String nomeContratante;
    private String cpfContratante;
    
    private List<TratamentoVO> listaTratamentos;
    private List<ContatoVO> listaContatos;
    
    public String getNumeroContrato() {
        return numeroContrato;
    }
    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }
    public Date getDtInicioValidade() {
        return dtInicioValidade;
    }
    public void setDtInicioValidade(Date dtInicioValidade) {
        this.dtInicioValidade = dtInicioValidade;
    }
    public Date getDtFinalValidade() {
        return dtFinalValidade;
    }
    public void setDtFinalValidade(Date dtFinalValidade) {
        this.dtFinalValidade = dtFinalValidade;
    }
    public String getNomeContratante() {
        return nomeContratante;
    }
    public void setNomeContratante(String nomeContratante) {
        this.nomeContratante = nomeContratante;
    }
    public String getCpfContratante() {
        return cpfContratante;
    }
    public void setCpfContratante(String cpfContratante) {
        this.cpfContratante = cpfContratante;
    }
    public List<TratamentoVO> getListaTratamentos() {
        return listaTratamentos;
    }
    public void setListaTratamentos(List<TratamentoVO> listaTratamentos) {
        this.listaTratamentos = listaTratamentos;
    }
    public List<ContatoVO> getListaContatos() {
        return listaContatos;
    }
    public void setListaContatos(List<ContatoVO> listaContatos) {
        this.listaContatos = listaContatos;
    }   
    
}
