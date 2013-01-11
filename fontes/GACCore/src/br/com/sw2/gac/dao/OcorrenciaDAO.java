package br.com.sw2.gac.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.eclipse.persistence.exceptions.DatabaseException;

import br.com.sw2.gac.exception.DataBaseException;
import br.com.sw2.gac.modelo.Cliente;
import br.com.sw2.gac.modelo.Ocorrencia;
import br.com.sw2.gac.tools.StatusOcorrencia;

/**
 * <b>Descrição: Classe responsavel pelo acesso a dados da tabela de ocorrências.</b> <br>
 * .
 * @author: SW2
 * @version 1.0 Copyright 2012 SmartAngel.
 */
public class OcorrenciaDAO extends BaseDao<Ocorrencia> {

    /**
     * Construtor Padrao Instancia um novo objeto OcorrenciaDAO.
     */
    public OcorrenciaDAO() {
        super(Ocorrencia.class);
    }

    /**
     * Nome: filtarOcorrenciasPorDataFechamento Filtar ocorrencias por data fechamento.
     * @param date the date
     * @return list
     * @throws DataBaseException the data base exception
     * @see
     */
    public List<Ocorrencia> filtarOcorrenciasPorDataFechamento(Date date) throws DataBaseException {

        StringBuffer jpql = new StringBuffer("SELECT entity FROM Ocorrencia");
        jpql.append(" entity");
        Query query = null;
        if (null == date) {
            jpql.append(" WHERE entity.dtaHoraFechamento is null");
            query = getEntityManager().createQuery(jpql.toString());
        } else {
            jpql.append(" WHERE entity.dtaHoraFechamento = :value");
            query = getEntityManager().createQuery(jpql.toString());
            query.setParameter("value", date);
        }

        List<Ocorrencia> result;
        try {
            result = query.getResultList();
        } catch (NoResultException exception) {
            result = null;
        } catch (DatabaseException exception) {
            throw new DataBaseException(DataBaseException.FALHA_COMUNICACAO_BANCO,
                exception.getMessage());
        }
        return result;

    }

    /**
     * Método que recupera a lista de chamadas por origem, baseado no critério de busca definido
     * pelos parâmetros passados no relatório Chamadas por Origem.
     *
     * @param perInicial perInicial
     * @param perFinal perFinal
     * @return null
     * @throws DataBaseException exception
     * @see
     */
    public List<Object[]> recuperaRelChamadasOrigem(Date perInicial, Date perFinal)
        throws DataBaseException {
        List<Object[]> result;
        StringBuffer query = new StringBuffer();
        /*
         * query.append(
         * "SELECT h.dispositivo.idDispositivo, h.tblhistdispositivoPK.dthrMudaEstado, h.dispositivo.tpEstado,"
         * ); query.append(" h.cdEstadoAnterior, h.login FROM HistDispositivo h WHERE 1=1");
         */
        if (perInicial != null) {
            query.append(" AND h.dispositivo.idDispositivo = ");
        }

        if (perFinal != null) {
            query.append(" AND h.dispositivo.tpEstado = ");
        }

        /*
         * if (dataMovimentacao != null) { // Somar 1 dia na data selecionada para poder montar a
         * query Calendar calendar = Calendar.getInstance(); calendar.setTime(dataMovimentacao);
         * calendar.add(Calendar.DAY_OF_MONTH, 1); Date dataFim = calendar.getTime(); // Converter
         * as datas para Timestamp, pois está assim no banco Timestamp timeFim = new
         * Timestamp(dataFim.getTime()); Timestamp timeInicio = new
         * Timestamp(dataMovimentacao.getTime());
         * query.append(" AND h.tblhistdispositivoPK.dthrMudaEstado BETWEEN '" + timeInicio +
         * "' AND '" + timeFim + "'"); } if (login != null && !login.isEmpty()) {
         * query.append(" AND h.login = '" + login + "'"); } try { Query createQuery =
         * getEntityManager().createQuery(query.toString()); result = createQuery.getResultList(); }
         * catch (NoResultException exception) { result = null; } catch (DatabaseException
         * exception) { throw new DataBaseException(DataBaseException.FALHA_COMUNICACAO_BANCO,
         * exception.getMessage()); }
         */
        return null;
    }

    /**
     * Nome: obterOcorrenciaPendenteDoCliente
     * Obter ocorrencia pendente do cliente.
     *
     * @param cliente the cliente
     * @return ocorrencia
     * @throws DataBaseException the data base exception
     * @see
     */
    public Ocorrencia obterOcorrenciaPendenteDoCliente(Cliente cliente)
        throws DataBaseException {
        Ocorrencia retorno;
        try {

            StringBuffer statementJPA = new StringBuffer("SELECT o from Ocorrencia o ");
            statementJPA.append(" WHERE o.nmCPFCliente.nmCPFCliente = :nmCPFCliente");
            statementJPA.append(" AND o.statusOcorre = :statusOcorre");
            Query query = getEntityManager().createQuery(statementJPA.toString());
            query.setParameter("nmCPFCliente", cliente.getNmCPFCliente());
            query.setParameter("statusOcorre", StatusOcorrencia.EmEspera.getValue());

            retorno = (Ocorrencia) query.getSingleResult();
        } catch (NoResultException ex) {
            retorno = null;
        } catch (DataBaseException exception) {
            throw new DataBaseException(DataBaseException.FALHA_COMUNICACAO_BANCO,
                exception.getMessage());
        }
        return retorno;
    }


}
