package br.com.sw2.gac.exception;

import br.com.sw2.gac.util.LoggerUtils;

/**
 * <b>Descri��o: Exception gen�rica para regras de neg�cio n�o atendidas.</b> <br>
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
     * Construtor Padrao Instancia um novo objeto BusinessException.
     * @param message the message
     */
    public BusinessException(String message) {
        super(message);
        this.exceptionCode = 0;
        logger.error(message);

    }

    /**
     * Construtor Padrao Instancia um novo objeto BusinessException.
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

    public int getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

}
