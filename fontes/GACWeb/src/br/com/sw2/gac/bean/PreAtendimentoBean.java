package br.com.sw2.gac.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import br.com.sw2.gac.business.OcorrenciaBusiness;
import br.com.sw2.gac.exception.BusinessException;
import br.com.sw2.gac.exception.BusinessExceptionMessages;
import br.com.sw2.gac.filtro.FiltroPesquisarPreAtendimento;
import br.com.sw2.gac.socket.PhoneCommand;
import br.com.sw2.gac.socket.SocketPhone;
import br.com.sw2.gac.socket.bean.Event;
import br.com.sw2.gac.socket.bean.Line;
import br.com.sw2.gac.socket.constants.StatusLigacao;
import br.com.sw2.gac.socket.constants.TipoLigacao;
import br.com.sw2.gac.socket.exception.SocketConnectionException;
import br.com.sw2.gac.socket.exception.SocketException;
import br.com.sw2.gac.tools.SinalDispositivo;
import br.com.sw2.gac.tools.StatusOcorrencia;
import br.com.sw2.gac.tools.TipoOcorrencia;
import br.com.sw2.gac.util.CollectionUtils;
import br.com.sw2.gac.util.StringUtil;
import br.com.sw2.gac.vo.ContratoVO;
import br.com.sw2.gac.vo.LigacaoVO;
import br.com.sw2.gac.vo.MotivoPausaVO;
import br.com.sw2.gac.vo.OcorrenciaVO;
import br.com.sw2.gac.vo.TipoOcorrenciaVO;
import br.com.sw2.gac.vo.UsuarioVO;

/**
 * <b>Descrição: Controller da tela de atendimento.</b> <br>
 * .
 * @author: SW2
 * @version 1.0 Copyright 2012 SmartAngel.
 */
@ManagedBean
@ViewScoped
public class PreAtendimentoBean extends BaseBean {

    /** Constante serialVersionUID. */
    private static final long serialVersionUID = -7387330971721094361L;

    /** Atributo filtro. */
    private FiltroPesquisarPreAtendimento filtro = new FiltroPesquisarPreAtendimento();

    /** Atributo resultado pesquisa. */
    private List<ContratoVO> resultadoPesquisa;

    /** Atributo ocorrencia business. */
    private OcorrenciaBusiness ocorrenciaBusiness = new OcorrenciaBusiness();

    /** Atributo id pgd painel de alerta rendered. */
    private boolean idPgdPainelDeAlertaRendered = false;

    /** Atributo socket client. */
    private SocketPhone socketPhone = null;

    /** Atributo id pgd painel de alerta style. */
    private String idPgdPainelDeAlertaStyle = "";

    /** Atributo btn login value. */
    private String btnLoginValue = "Login";

    /** Atributo btn login disabled. */
    private boolean btnLoginDisabled = false;

    /** Atributo btn break disabled. */
    private boolean btnBreakDisabled = false;

    /** Atributo btn disponibilidade disabled. */
    private boolean btnDisponibilidadeDisabled = false;

    /** Atributo auto run monitor socket. */
    private boolean autoRunMonitorSocket = false;

    /** Atributo lbl tipo atendimento rendered. */
    private boolean lblTipoAtendimentoRendered = false;

    /** Atributo ligacao. */
    private LigacaoVO ligacao = null;

    /** Atributo ocorrencia aberta. */
    private OcorrenciaVO ocorrenciaAberta = null;

    /** Atributo lista combo motivos pausa. */
    private List<SelectItem> listaComboMotivosPausa = new ArrayList<SelectItem>();

    /** Atributo motivo pausa selecionado. */
    private MotivoPausaVO motivoPausaSelecionado;

    /**
     * Construtor Padrao Instancia um novo objeto PreAtendimentoBean.
     */
    public PreAtendimentoBean() {
        reset();

        List<MotivoPausaVO> listaMotivosPausa = this.ocorrenciaBusiness.obterListaMotivosPausa();
        this.listaComboMotivosPausa = getSelectItems(listaMotivosPausa, "motivoPausaId", "descricaoMotivoPausa");

        //Verific se ja existe uma conexão socket ativa. Se tiver usa ela.
        SocketPhone socketPhoneSalvo = (SocketPhone) getSessionAttribute("socketPhone");
        if (null == this.socketPhone && null != socketPhoneSalvo) {
            this.socketPhone = socketPhoneSalvo;
            this.socketPhone.setEmAtendimento(false);
            if (this.socketPhone.isAtendenteAutenticado()) {
                this.btnLoginValue = "Logout";
                this.btnLoginDisabled = false;
            }

            this.debug("***** Recuperando conexão socket ja existente *****");
        }

        this.autoRunMonitorSocket = getRequestBooleanAttribute("ativarMonitorSocket");
        //True indica que a página esta sendo chamada da página de registro de ocorrência.
        if (autoRunMonitorSocket && null != this.socketPhone) {
            this.socketPhone.setAlertaChamada(null);
            //Ramal (user)
            Integer ramal = this.getUsuarioLogado().getRamal();
            for (Line linhaAEncerrar : this.socketPhone.getLinhas()) {

                //Numero da linha a encerras caso necessário
                Integer numeroDaLinha = linhaAEncerrar.getNumeroLinha();

                if (linhaAEncerrar.getStatusLinha().intValue() != StatusLigacao.LIVRE.getValue().intValue()) {
                    //Se a linha chegou com status diferente de livre nessa parte precisa ser encerrada.
                    if (linhaAEncerrar.getTipoLigacao().intValue() == TipoLigacao.LOGIN.getValue().intValue()) {
                        /*
                         * Encerra ligação para o agente.
                         * Se fizer hangup na linha que oa gente esta logado ele desloga.
                         */
                        linhaAEncerrar.setStatusLinha(2);
                        linhaAEncerrar.setAcionamento(null);
                        linhaAEncerrar.setSubNumeroDiscado(null);
                        this.socketPhone.enviarMensagem(PhoneCommand.selecionarLinha(ramal, numeroDaLinha));
                        this.socketPhone.enviarMensagem(PhoneCommand.enviarDtmf(ramal, "*"));

                    } else {

                        if (null != this.socketPhone && null != this.socketPhone.getSocket()) {
                            this.socketPhone.enviarMensagem(PhoneCommand.selecionarLinha(ramal, numeroDaLinha));
                            this.socketPhone.enviarMensagem(PhoneCommand.desligar(ramal, numeroDaLinha));
                        }

                        linhaAEncerrar.setStatusLinha(1);
                        linhaAEncerrar.setNumeroDiscado(null);
                        linhaAEncerrar.setTipoLigacao(TipoLigacao.INDEFINIDO.getValue());
                        linhaAEncerrar.setAcionamento(null);
                    }
                }
            }

            this.socketPhone.enviarMensagem(PhoneCommand.dgTimeStamp(this.getUsuarioLogado().getRamal()));
            this.socketPhone.enviarMensagem(PhoneCommand.agentPause(ramal, this.getUsuarioLogado().getRegistroAtendente(), false, 1));
            this.socketPhone.enviarMensagem(PhoneCommand.selecionarLinha(ramal, 1));
        }

        atendenteDisponivel();
    }

    /**
     * Nome: atendenteDisponivel
     * Atendente disponivel.
     *
     * @see
     */
    private void atendenteDisponivel() {
        this.motivoPausaSelecionado = new MotivoPausaVO();
        this.motivoPausaSelecionado.setMotivoPausaId(-1);
    }

    /**
     * Nome: reset
     * Reset.
     *
     * @see
     */
    public void reset() {
        this.idPgdPainelDeAlertaStyle = "";
        if (null != this.socketPhone) {
            this.socketPhone.setAlertaChamada("");
        }
        this.btnDisponibilidadeDisabled = false;
        this.btnLoginValue = "Login";
        this.btnLoginDisabled = false;
        this.btnBreakDisabled = false;
        this.socketPhone = null;
    }

    /**
     * Nome: pesquisarCliente Pesquisar cliente.
     * @param e the e
     * @see
     */
    public void pesquisarCliente(ActionEvent e) {
        this.debug("***** Iniciando método pesquisarCliente(ActionEvent e) *****");
        this.debug("***** Campos preenchidos ***** ");
        this.debug("Numero contrato:" + filtro.getNumeroContrato());
        this.debug("CPF do cliente: " + filtro.getNumeroCPFCliente());
        this.debug("Telefone do cliente: " + filtro.getTelefone());
        this.debug("Nome do cliente: " + filtro.getNomeCliente());

        try {
            this.resultadoPesquisa = this.ocorrenciaBusiness
                .pesquisarContratosPreAtendimento(filtro);
        } catch (BusinessException businessException) {
            if (businessException.getExceptionCode() == BusinessExceptionMessages.FILTRO_PESQUISA_PRE_ATENDIMENTO_NAO_INFORMADO
                .getValue().intValue()) {
                setFacesErrorMessage("message.preatendimento.filtro.empty");
            } else {
                setFacesErrorMessage("message.generic.system.unavailable");
            }
        }

        this.debug("***** Finalizando método pesquisarCliente(ActionEvent e) *****");
    }

    /**
     * Nome: resetPesquisa Método responsável por limpar o resultado e o filtro utilizado na
     * pesquisa.
     * @param e the e
     * @see
     */
    public void resetPesquisa(ActionEvent e) {
        this.resultadoPesquisa = null;
        this.filtro = new FiltroPesquisarPreAtendimento();
    }

    /**
     * Nome: iniciarAtendimento Iniciar atendimento.
     * @return string
     * @see
     */
    public String iniciarAtendimento() {

        String toViewId;
        if (this.socketPhone == null || this.getSocketPhone().getSocket() == null && !this.getSocketPhone().isAtendenteAutenticado()) {
            setFacesErrorMessage("message.socketphone.agent.login.off");
            toViewId = null;
        } else {
            Integer numeroContrato = Integer.parseInt(getRequestParameter("numeroContratoAtender"));
            ContratoVO contrato = (ContratoVO) CollectionUtils.findByAttribute(this.resultadoPesquisa,
                "numeroContrato", numeroContrato);

            gerarOcorrencia(contrato, TipoOcorrencia.AtendimentoManual, null);

            if (null != this.socketPhone) {
                this.socketPhone.saveState();
            }
            toViewId = "atendimento";
        }
        return toViewId;

    }

    /**
     * Nome: gerarOcorrencia
     * Gerar ocorrencia.
     *
     * @param contrato the contrato
     * @param tipoOcorrencia the tipo ocorrencia
     * @param codigoSinalDispositivo the codigo sinal dispositivo
     * @see
     */
    private void gerarOcorrencia(ContratoVO contrato, TipoOcorrencia tipoOcorrencia, Integer codigoSinalDispositivo) {

        if (null == this.ocorrenciaAberta && null != contrato) {
            this.ocorrenciaAberta =  this.ocorrenciaBusiness.obterOcorrenciaPendenteDoCliente(contrato.getCliente());
        }

        OcorrenciaVO ocorrencia = new OcorrenciaVO();
        if (null == this.ocorrenciaAberta) {
            ocorrencia.setTipoOcorrencia(new TipoOcorrenciaVO(tipoOcorrencia));

            if (tipoOcorrencia.equals(TipoOcorrencia.Emergencia)) {
                ocorrencia.setCodigoPrioridade(3);
            } else if (tipoOcorrencia.equals(TipoOcorrencia.Outros)) {
                ocorrencia.setCodigoPrioridade(1);
            } else {
                ocorrencia.setCodigoPrioridade(2);
            }

            if (StringUtil.isEmpty(this.socketPhone.getAlertaChamada(), true) && tipoOcorrencia.equals(TipoOcorrencia.AtendimentoManual)) {
                this.socketPhone.setAlertaChamada(tipoOcorrencia.getLabel());
            }

            ocorrencia.setSnDispositivo(codigoSinalDispositivo);
            ocorrencia.setUsuario(new UsuarioVO());
            ocorrencia.getUsuario().setLogin(getUsuarioLogado().getLogin());
            ocorrencia.setDataAbertura(new Date());
            ocorrencia.setDataHoraAberturaOcorrencia(new Date());
            ocorrencia.setStatusOcorrencia(StatusOcorrencia.EmAtendimento.getValue());

            ocorrencia.setContrato(contrato);
            if (null != this.ligacao) {
                ocorrencia.setIdLigacao(this.ligacao.getIdUniqueid());
                ocorrencia.setNumerorTelefoneLigado(this.ligacao.getNumeroTelefoneOrigem());
            }
            Integer codigoOcorrencia = this.ocorrenciaBusiness.gravarNovaOcorrencia(ocorrencia);
            ocorrencia.setIdOcorrencia(codigoOcorrencia);
            this.ocorrenciaAberta = ocorrencia;

        } else {
            ocorrencia = this.ocorrenciaAberta;
            ocorrencia.setContrato(contrato);
        }

        if (null != contrato && null != contrato.getCliente()) {
            List<OcorrenciaVO> listaOcorrenciasDoCliente = this.ocorrenciaBusiness.obterListaOcorrenciaDoCliente(contrato.getCliente());
            ocorrencia.setListaHistoricoOcorrencias(listaOcorrenciasDoCliente);
        }
        setSessionAttribute("atenderOcorrencia", ocorrencia);

    }

    /**
     * Nome: monitorSocket Monitor socket.
     * @see
     */
    public synchronized void monitorSocket() {

        int ramal = this.getUsuarioLogado().getRamal();

        this.autoRunMonitorSocket = false;

        if (null != this.socketPhone) {

            if (null == this.socketPhone.getSocket()) {
                dispatchError500(getMessageFromBundle("message.socketphone.error.connect.failed"));
            } else if (this.socketPhone.isRamalAtivo()) {
                if (this.socketPhone.isAtendenteAutenticado()) {

                    if (this.socketPhone.isAtendenteDisponivel()) {
                        atendenteDisponivel();
                    }

                    //Fica em loop ate achar um evento que necessita atualizar a tela.
                    try {
                        this.debug("MonitorSocket da tela de pre-atendimento ********************");
                        List<Event> eventos = this.socketPhone.aguardarEvento(); //Escutando socket
                        for (Event eventoRecebido : eventos) {
                            tratarEvento(ramal, eventoRecebido);
                        }
                        addCallbackParam("stopMonitorChamadas", false);
                    } catch (Exception sce) {
                        logarErro("*************** MONITOR SOCKET SERA ENCERRADO *************************");
                        logarErro(sce.getMessage());
                        logarErro("***********************************************************************");
                        reset();
                        addCallbackParam("stopMonitorChamadas", true);
                        addCallbackParam("hideDlgGacPhoneChamada", true);
                        addCallbackParam("loginSucess", false);
                        removeSessionAttribute("socketPhone");
                        if (null != this.socketPhone) {
                            Line linhaCliente = (Line) CollectionUtils.findByAttribute(this.socketPhone .getLinhas(), "numeroLinha", 1);
                            linhaCliente.setSubNumeroDiscado("");
                            this.socketPhone.fecharConexaoSocket(ramal);
                        }
                        setFacesMessage("Erro ao monitorar o socket de telefonia", false);
                    }

                } else {
                    dispatchError500(getMessageFromBundle("message.socketphone.agent.login.off"));
                }
            } else {
                dispatchError500(getMessageFromBundle("message.socketphone.user.login.off"));
            }
        } else {
            addCallbackParam("stopMonitorChamadas", true);
        }
        this.debug("Finalizando monitorSocket da tela de pre-atendimento ********************");
    }

    /**
     * Nome: tratarEvento
     * Tratar evento recebido na tela de pre atendimento.
     *
     * @param ramal the ramal
     * @param eventoRecebido the evento recebido
     * @see
     */
    private void tratarEvento(int ramal, Event eventoRecebido) {
        if (null != eventoRecebido.getStatus()) {

            this.socketPhone.tratarEventoSocket(eventoRecebido);

            if (this.socketPhone.isAbandonoNaFila()) {
                this.socketPhone.setAbandonoNaFila(false);
                addCallbackParam("hideDlgGacPhoneChamada", true);

                this.debug("Ligação perdida ************************");
            }

            if (this.socketPhone.isAgenteNaoAtende()) {
                this.btnDisponibilidadeDisabled = false;
                this.btnBreakDisabled = false;
                this.btnLoginDisabled = false;
                this.lblTipoAtendimentoRendered = false;
                this.ocorrenciaAberta = null;
                this.socketPhone.setAgenteNaoAtende(false);
                addCallbackParam("hideDlgGacPhoneChamada", true);

                this.debug("Ligação não atendida. Irá retornar a fila ************************");
            }

            if (this.socketPhone.isAgenteAtendeuLigacao()) {
                this.socketPhone.setAgenteAtendeuLigacao(false);
                this.btnDisponibilidadeDisabled = true;
                this.btnBreakDisabled = true;
                this.btnLoginDisabled = true;
                this.lblTipoAtendimentoRendered = true;
                this.ocorrenciaAberta = null;

                try {
                    ContratoVO contratoDoCliente = null;
                    if (CollectionUtils.isNotEmptyOrNull(this.resultadoPesquisa) && this.resultadoPesquisa.size() == 1) {
                        //Se existe um unico contrato utiliza ele por padrão.
                        contratoDoCliente = this.resultadoPesquisa.get(0);
                    }

                    TipoOcorrencia tipoOcorrencia = TipoOcorrencia.AtendimentoManual;
                    Integer codigoSinal = null;
                    String numeroTelefone = null;
                    if (null != this.ligacao) {
                        codigoSinal =  this.ligacao.getCodigoSinalDispositivo();
                        numeroTelefone = this.ligacao.getNumeroTelefoneOrigem();

                        if (codigoSinal.intValue() > 0 && codigoSinal.intValue() < 7) {
                            tipoOcorrencia = TipoOcorrencia.Emergencia;
                        }
                    } else {
                        tipoOcorrencia = TipoOcorrencia.Outros;
                        numeroTelefone = "0000000000";
                    }
                    gerarOcorrencia(contratoDoCliente, tipoOcorrencia, codigoSinal);
                    Line linhaCliente = (Line) CollectionUtils.findByAttribute(this.socketPhone.getLinhas(), "numeroLinha", 1);
                    linhaCliente.setSubNumeroDiscado(numeroTelefone);
                    linhaCliente.setStatusLinha(StatusLigacao.FALANDO.getValue());

                    this.socketPhone.enviarMensagem(PhoneCommand.agentPause(ramal, this.getUsuarioLogado().getRegistroAtendente(),
                        true, 1));

                } catch (SocketException ex) {
                    this.getLogger().error(ex);
                }

                addCallbackParam("hideDlgGacPhoneChamada", true);
                this.debug("Ligação atendida ************************");
            }

            if (eventoRecebido.getStatus().equalsIgnoreCase("AgentCalled")) {
                // Com o ID da ligação, recupero os dados da ligação gravados no banco de dados.
                try {
                    this.ligacao = this.ocorrenciaBusiness.obterDadosNovaLigacaoAtendente(eventoRecebido.getUniqueid());
                    if (null != this.ligacao) {
                        this.resultadoPesquisa = this.ligacao.getListaContratosCliente();
                    }
                } catch (BusinessException e) {
                    this.getLogger().error(e.getMessage());
                }
                this.determinarTipoDeAcionamento(this.ligacao);
            }
        }
    }

    /**
     * Nome: determinarTipoDeAcionamento
     * Determinar tipo de acionamento.
     *
     * @param ligacao the ligacao
     * @see
     */
    private void determinarTipoDeAcionamento(LigacaoVO ligacao) {

        if (null == ligacao) {
            this.socketPhone.setAlertaChamada("Acionamento indefinido");
            this.idPgdPainelDeAlertaStyle = "areaVerde";
        } else {
            if (ligacao.getNumeroDispositivo() < 7) {
                for (SinalDispositivo item : SinalDispositivo.values()) {
                    if (item.getValue().equals(ligacao.getCodigoSinalDispositivo())) {
                        this.socketPhone.setAlertaChamada(item.getLabel());
                    }
                }
                this.idPgdPainelDeAlertaStyle = "areaVermelha";
            } else if (null == ligacao.getCodigoEnviadoPulseira()) {
                this.socketPhone.setAlertaChamada("Recebendo ligação de " + ligacao.getNumeroTelefoneOrigem());
                this.idPgdPainelDeAlertaStyle = "areaVerde";
            } else {
                this.socketPhone.setAlertaChamada("Acionamento indefinido");
                this.idPgdPainelDeAlertaStyle = "areaVerde";
            }
        }
        addCallbackParam("avisoDeChamada", true);

    }

    /**
     * Nome: conectarSocketServer Conectar socket server.
     *
     * @throws SocketConnectionException the socket connection exception
     * @see
     */
    private void conectarSocketServer() throws SocketConnectionException {
        this.debug("***** Conectando com o servidor doscket *****");
        if (null == socketPhone) {
            try {
                this.socketPhone = new SocketPhone();

                String host = this.getGACProperty("socket.softphone.address").trim();
                int port = Integer.parseInt(this.getGACProperty("socket.softphone.port").trim());
                this.debug("Endereço de conexão: " + host + " porta: " + port);
                this.socketPhone.conectarAoSocketServer(host, port);
                this.debug("***** Conectado ao servidor socket  *****");
            } catch (Exception e) {
                reset();
                throw new SocketConnectionException(getMessageFromBundle("message.socketphone.error.connect.failed"), e);
            }
        }
    }

    /**
     * Nome: disponibilidadeAtendente
     * Disponibilidade atendente.
     *
     * @see
     */
    public void disponibilidadeAtendente() {
    	if (null == this.socketPhone || !this.socketPhone.isAtendenteAutenticado()) {
    		setFacesMessage("message.socketphone.agent.login.off");
    	} else {

    	    boolean ficarIndisponivel = true;
    	    int motivo = this.motivoPausaSelecionado.getMotivoPausaId().intValue();
    	    if (this.motivoPausaSelecionado.getMotivoPausaId().intValue() < 0) {
    	        ficarIndisponivel = false;
    	    }

            this.socketPhone.enviarMensagem(PhoneCommand.agentPause(this.getUsuarioLogado()
                .getRamal(), this.getUsuarioLogado().getRegistroAtendente(), ficarIndisponivel, motivo));

    	}
    }

    /**
     * Nome: logarAtendente
     * Logar atendente.
     *
     * @param event the event
     * @see
     */
    public void loginLogoutAtendente(ActionEvent event) {

        if (this.btnLoginValue.equals("Login")) {
            this.debug("***** Iniciando processo de login do atendente (agente): "
                + this.getUsuarioLogado().getRegistroAtendente());
            //Iniciar servidor socket.
            try {

                if (null == this.socketPhone) {
                    conectarSocketServer();
                } else {
                    this.debug("Utilizando conexão socket existente. Não foi necessário reconectar *****");
                }

                //Ativar ramal
                try {
                    this.socketPhone.login(this.getUsuarioLogado().getRamal());
                } catch (SocketException e) {
                    this.getLogger().error(e.getMessage());
                    String motivo = e.getMessage();
                    if (motivo.contains("User is not active")) {
                        String usuarioInativo = getMessageFromBundle("message.socketphone.error.user.not.active");
                        throw new SocketException(getMessageFromBundle("message.socketphone.error.user.login.failed", usuarioInativo));
                    } else {
                        throw new SocketException(getMessageFromBundle("message.socketphone.error.user.login.failed", ""));
                    }
                }

                //logar atendente
                try {
                    this.socketPhone.loginAgente(getUsuarioLogado().getRegistroAtendente(), getUsuarioLogado().getSenha());
                } catch (SocketException e) {
                    this.getLogger().error(e.getMessage());
                    throw new SocketException(getMessageFromBundle("message.socketphone.error.agent.login.failed"));
                }

                Line line = (Line) CollectionUtils.findByAttribute(this.getSocketPhone().getLinhas(), "numeroLinha", 1);
                line.setStatusLinha(StatusLigacao.PAUSA.getValue());
                line.setNumeroDiscado("*9800");
                line.setTipoLigacao(4);
                this.btnLoginValue = "Logout";
                this.socketPhone.setQtdeLigacoesFilaAtendimentoEmergencia(0);
                addCallbackParam("loginSucess", true);
                atendenteDisponivel();
            } catch (SocketConnectionException e) {
                dispatchError500(e.getMessage());
            } catch (SocketException e) {
                this.socketPhone.fecharConexaoSocket(getUsuarioLogado().getRamal());
                reset();
                addCallbackParam("loginSucess", false);
                dispatchError500(e.getMessage());
            }
        } else {
            this.socketPhone.fecharConexaoSocket(getUsuarioLogado().getRamal());
            reset();
            addCallbackParam("loginSucess", false);
            addCallbackParam("stopMonitorChamadas", true);
            removeSessionAttribute("socketPhone");
        }
    }

    /**
     * Nome: atenderChamada
     * Atender chamada.
     *
     * @param e the e
     * @see
     */
    public void atenderChamada(ActionEvent e) {
        if (null != this.ligacao) {
            this.ligacao.setDataHorarAtendimento(new Date());
        }
        this.socketPhone.atenderLigacaoParaAgente(this.getUsuarioLogado().getRamal());
    }


    /**
     * Nome: encerrarPreAtendimento
     * Encerrar pre atendimento.
     *
     * @return string
     *
     * @see
     */
    public String encerrarPreAtendimento() {
        this.encerrarSocketPhone();
        return "menuPrincipal";
    }

    /**
     * Nome: encerrarSocketPhone
     * Encerrar socket phone.
     *
     * @see
     */
    public void encerrarSocketPhone() {
        this.debug("Encerrando socketphone ***************************");
        if (null != this.socketPhone && null != this.socketPhone.getSocket()) {
            this.socketPhone.fecharConexaoSocket(this.getUsuarioLogado().getRamal());
            removeSessionAttribute("socketPhone");
            this.socketPhone = null;
        }
        this.debug("Fim do Encerrando socketphone ***************************");
    }

    /**
     * Nome: getFiltro
     * Recupera o valor do atributo 'filtro'.
     *
     * @return valor do atributo 'filtro'
     * @see
     */
    public FiltroPesquisarPreAtendimento getFiltro() {
        return filtro;
    }

    /**
     * Nome: setFiltro
     * Registra o valor do atributo 'filtro'.
     *
     * @param filtro valor do atributo filtro
     * @see
     */
    public void setFiltro(FiltroPesquisarPreAtendimento filtro) {
        this.filtro = filtro;
    }

    /**
     * Nome: getResultadoPesquisa
     * Recupera o valor do atributo 'resultadoPesquisa'.
     *
     * @return valor do atributo 'resultadoPesquisa'
     * @see
     */
    public List<ContratoVO> getResultadoPesquisa() {
        return resultadoPesquisa;
    }

    /**
     * Nome: setResultadoPesquisa
     * Registra o valor do atributo 'resultadoPesquisa'.
     *
     * @param resultadoPesquisa valor do atributo resultado pesquisa
     * @see
     */
    public void setResultadoPesquisa(List<ContratoVO> resultadoPesquisa) {
        this.resultadoPesquisa = resultadoPesquisa;
    }

    /**
     * Nome: isIdPgdPainelDeAlertaRendered
     * Verifica se e id pgd painel de alerta rendered.
     *
     * @return true, se for id pgd painel de alerta rendered senão retorna false
     * @see
     */
    public boolean isIdPgdPainelDeAlertaRendered() {
        return idPgdPainelDeAlertaRendered;
    }

    /**
     * Nome: setIdPgdPainelDeAlertaRendered
     * Registra o valor do atributo 'idPgdPainelDeAlertaRendered'.
     *
     * @param idPgdPainelDeAlertaRendered valor do atributo id pgd painel de alerta rendered
     * @see
     */
    public void setIdPgdPainelDeAlertaRendered(boolean idPgdPainelDeAlertaRendered) {
        this.idPgdPainelDeAlertaRendered = idPgdPainelDeAlertaRendered;
    }

    /**
     * Nome: getSocketPhone
     * Recupera o valor do atributo 'socketPhone'.
     *
     * @return valor do atributo 'socketPhone'
     * @see
     */
    public SocketPhone getSocketPhone() {
        return socketPhone;
    }

    /**
     * Nome: setSocketPhone
     * Registra o valor do atributo 'socketPhone'.
     *
     * @param socketPhone valor do atributo socket phone
     * @see
     */
    public void setSocketPhone(SocketPhone socketPhone) {
        this.socketPhone = socketPhone;
    }

    /**
     * Nome: getIdPgdPainelDeAlertaStyle
     * Recupera o valor do atributo 'idPgdPainelDeAlertaStyle'.
     *
     * @return valor do atributo 'idPgdPainelDeAlertaStyle'
     * @see
     */
    public String getIdPgdPainelDeAlertaStyle() {
        return idPgdPainelDeAlertaStyle;
    }

    /**
     * Nome: setIdPgdPainelDeAlertaStyle
     * Registra o valor do atributo 'idPgdPainelDeAlertaStyle'.
     *
     * @param idPgdPainelDeAlertaStyle valor do atributo id pgd painel de alerta style
     * @see
     */
    public void setIdPgdPainelDeAlertaStyle(String idPgdPainelDeAlertaStyle) {
        this.idPgdPainelDeAlertaStyle = idPgdPainelDeAlertaStyle;
    }

    /**
     * Nome: getBtnLoginValue
     * Recupera o valor do atributo 'btnLoginValue'.
     *
     * @return valor do atributo 'btnLoginValue'
     * @see
     */
    public String getBtnLoginValue() {
        return btnLoginValue;
    }

    /**
     * Nome: setBtnLoginValue
     * Registra o valor do atributo 'btnLoginValue'.
     *
     * @param btnLoginValue valor do atributo btn login value
     * @see
     */
    public void setBtnLoginValue(String btnLoginValue) {
        this.btnLoginValue = btnLoginValue;
    }

    /**
     * Nome: isBtnLoginDisabled
     * Verifica se e btn login disabled.
     *
     * @return true, se for btn login disabled senão retorna false
     * @see
     */
    public boolean isBtnLoginDisabled() {
        return btnLoginDisabled;
    }

    /**
     * Nome: setBtnLoginDisabled
     * Registra o valor do atributo 'btnLoginDisabled'.
     *
     * @param btnLoginDisabled valor do atributo btn login disabled
     * @see
     */
    public void setBtnLoginDisabled(boolean btnLoginDisabled) {
        this.btnLoginDisabled = btnLoginDisabled;
    }

    /**
     * Nome: isBtnBreakDisabled
     * Verifica se e btn break disabled.
     *
     * @return true, se for btn break disabled senão retorna false
     * @see
     */
    public boolean isBtnBreakDisabled() {
        return btnBreakDisabled;
    }

    /**
     * Nome: setBtnBreakDisabled
     * Registra o valor do atributo 'btnBreakDisabled'.
     *
     * @param btnBreakDisabled valor do atributo btn break disabled
     * @see
     */
    public void setBtnBreakDisabled(boolean btnBreakDisabled) {
        this.btnBreakDisabled = btnBreakDisabled;
    }

    /**
     * Nome: isBtnDisponibilidadeDisabled
     * Verifica se e btn disponibilidade disabled.
     *
     * @return true, se for btn disponibilidade disabled senão retorna false
     * @see
     */
    public boolean isBtnDisponibilidadeDisabled() {
        return btnDisponibilidadeDisabled;
    }

    /**
     * Nome: setBtnDisponibilidadeDisabled
     * Registra o valor do atributo 'btnDisponibilidadeDisabled'.
     *
     * @param btnDisponibilidadeDisabled valor do atributo btn disponibilidade disabled
     * @see
     */
    public void setBtnDisponibilidadeDisabled(boolean btnDisponibilidadeDisabled) {
        this.btnDisponibilidadeDisabled = btnDisponibilidadeDisabled;
    }

    /**
     * Nome: getOcorrenciaAberta
     * Recupera o valor do atributo 'ocorrenciaAberta'.
     *
     * @return valor do atributo 'ocorrenciaAberta'
     * @see
     */
    public OcorrenciaVO getOcorrenciaAberta() {
        return ocorrenciaAberta;
    }

    /**
     * Nome: setOcorrenciaAberta
     * Registra o valor do atributo 'ocorrenciaAberta'.
     *
     * @param ocorrenciaAberta valor do atributo ocorrencia aberta
     * @see
     */
    public void setOcorrenciaAberta(OcorrenciaVO ocorrenciaAberta) {
        this.ocorrenciaAberta = ocorrenciaAberta;
    }


    /**
     * Nome: simularchamada
     * Simularchamada.
     *
     * @param e the e
     * @see
     */
    public void simularchamada(ActionEvent e) {
        this.socketPhone.enviarMensagem(PhoneCommand.selecionarLinha(this.getUsuarioLogado().getRamal(), 6));
        this.socketPhone.enviarMensagem(PhoneCommand.discar("4031", this.getUsuarioLogado().getRamal(), 6));
        Line linha = (Line) CollectionUtils.findByAttribute(this.socketPhone .getLinhas(), "numeroLinha", 6);
        linha.setStatusLinha(StatusLigacao.PAUSA.getValue());
        this.debug("discagem feita para testes de AgentCalled");
    }

    /**
     * Nome: isAutoRunMonitorSocket Verifica se e auto run monitor socket.
     * @return true, se for auto run monitor socket senão retorna false
     * @see
     */
    public boolean isAutoRunMonitorSocket() {
        return autoRunMonitorSocket;
    }

    /**
     * Nome: setAutoRunMonitorSocket
     * Registra o valor do atributo 'autoRunMonitorSocket'.
     *
     * @param autoRunMonitorSocket valor do atributo auto run monitor socket
     * @see
     */
    public void setAutoRunMonitorSocket(boolean autoRunMonitorSocket) {
        this.autoRunMonitorSocket = autoRunMonitorSocket;
    }

    /**
     * Nome: isLblTipoAtendimentoRendered
     * Verifica se e lbl tipo atendimento rendered.
     *
     * @return true, se for lbl tipo atendimento rendered senão retorna false
     * @see
     */
    public boolean isLblTipoAtendimentoRendered() {
        return lblTipoAtendimentoRendered;
    }

    /**
     * Nome: setLblTipoAtendimentoRendered
     * Registra o valor do atributo 'lblTipoAtendimentoRendered'.
     *
     * @param lblTipoAtendimentoRendered valor do atributo lbl tipo atendimento rendered
     * @see
     */
    public void setLblTipoAtendimentoRendered(boolean lblTipoAtendimentoRendered) {
        this.lblTipoAtendimentoRendered = lblTipoAtendimentoRendered;
    }

    /**
     * Nome: getListaComboMotivosPausa
     * Recupera o valor do atributo 'listaComboMotivosPausa'.
     *
     * @return valor do atributo 'listaComboMotivosPausa'
     * @see
     */
    public List<SelectItem> getListaComboMotivosPausa() {
        return listaComboMotivosPausa;
    }

    /**
     * Nome: setListaComboMotivosPausa
     * Registra o valor do atributo 'listaComboMotivosPausa'.
     *
     * @param listaComboMotivosPausa valor do atributo lista combo motivos pausa
     * @see
     */
    public void setListaComboMotivosPausa(List<SelectItem> listaComboMotivosPausa) {
        this.listaComboMotivosPausa = listaComboMotivosPausa;
    }

    /**
     * Nome: getMotivoPausaSelecionado
     * Recupera o valor do atributo 'motivoPausaSelecionado'.
     *
     * @return valor do atributo 'motivoPausaSelecionado'
     * @see
     */
    public MotivoPausaVO getMotivoPausaSelecionado() {
        return motivoPausaSelecionado;
    }

    /**
     * Nome: setMotivoPausaSelecionado
     * Registra o valor do atributo 'motivoPausaSelecionado'.
     *
     * @param motivoPausaSelecionado valor do atributo motivo pausa selecionado
     * @see
     */
    public void setMotivoPausaSelecionado(MotivoPausaVO motivoPausaSelecionado) {
        this.motivoPausaSelecionado = motivoPausaSelecionado;
    }

}
