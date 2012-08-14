package br.com.sw2.gac.exception;

import br.com.sw2.gac.util.LoggerUtils;

/**
 * <b>Descrição: Objeto que representa uma exception lancada por um serviço.</b> <br>
 * .
 * @author: SW2
 * @version 1.0 Copyright 2012 SmartAngel.
 */
public class BusinessException extends RuntimeException {

    /** Constante serialVersionUID. */
    public static final long serialVersionUID = -6353287459086929559L;

    /** Atributo logger. */
    private LoggerUtils logger = LoggerUtils.getInstance(BusinessException.class);

    /** Atributo exception code. */
    private int exceptionCode;

    /**
     * Construtor Padrao Instancia um novo objeto BusinessException.
     */
    public BusinessException() {
        super();
    }

    /**
     * Construtor Padrão Instancia um novo objeto BusinessException.
     * @param message the message
     */
    public BusinessException(String message) {
        super(message);
        this.exceptionCode = 0;
        logger.error(message);

    }

    /**
     * Construtor Padrão Instancia um novo objeto BusinessException.
     * @param message the message
     */
    public BusinessException(BusinessExceptionMessages message) {
        super(message.toString());
        this.exceptionCode = message.getValue();
        logger.error(message.getValue() + " - " + message.getLabel());
    }

    /**
     * Construtor Padrao Instancia um novo objeto BusinessException.
     * @param code the code
     * @param message the message
     */
    public BusinessException(int code, String message) {
        super(message);
        this.exceptionCode = code;
        logger.error(code + " - " + message);
    }

    /**
     * Nome: getExceptionCode Recupera o valor do atributo 'exceptionCode'.
     * @return valor do atributo 'exceptionCode'
     * @see
     */
    public int getExceptionCode() {
        return exceptionCode;
    }

    /**
     * Nome: setExceptionCode Registra o valor do atributo 'exceptionCode'.
     * @param exceptionCode valor do atributo exception code
     * @see
     */
    public void setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    /**
     * Método que retorna a mensagem de erro do enum BusinessExceptionMessages.
     * @param mensagem mensagem
     * @return BusinessExceptionMessages
     */
    public BusinessExceptionMessages getBusinessMessage(String mensagem) {
    	return BusinessExceptionMessages.valueOf(mensagem);
    }

}
