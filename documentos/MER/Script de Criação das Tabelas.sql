--
-- ER/Studio 8.0 SQL Code Generation
-- Company :      SW2
-- Project :      PULSEIRAS.DM1
-- Author :       Marcelo Santos
--
-- Date Created : Sunday, September 23, 2012 20:56:17
-- Target DBMS : MySQL 5.x
--

-- 
-- TABLE: TblAcionamento 
--

CREATE TABLE TblAcionamento(
    IdAciona         INT             AUTO_INCREMENT,
    idOcorrencia     INT             NOT NULL,
    idContato        INT             NOT NULL,
    dtaHoraAciona    DATETIME,
    acaoPedida       TEXT,
    dtaHoraInicio    DATETIME,
    dtaHoraFinal     DATETIME,
    idSMS            INT,
    textoLivreSMS    VARCHAR(100),
    sucesso          CHAR(1),
    PRIMARY KEY (IdAciona)
)ENGINE=INNODB
;



-- 
-- TABLE: TblAplicaMedico 
--

CREATE TABLE TblAplicaMedico(
    hrAplicacao     TIMESTAMP    NOT NULL,
    idTratamento    INT          NOT NULL,
    nmCPFCliente    CHAR(14)     NOT NULL,
    PRIMARY KEY (hrAplicacao, idTratamento, nmCPFCliente)
)ENGINE=INNODB
;



-- 
-- TABLE: TblCID 
--

CREATE TABLE TblCID(
    cdCID           CHAR(10)       NOT NULL,
    cdTipoDoenca    CHAR(10)       NOT NULL,
    nmDoenca        VARCHAR(60),
    PRIMARY KEY (cdCID)
)ENGINE=INNODB
;



-- 
-- TABLE: TblCliente 
--

CREATE TABLE TblCliente(
    nmCPFCliente             CHAR(14)       NOT NULL,
    nmContrato               INT,
    nmCliente                VARCHAR(60)    NOT NULL,
    dsEndereco               VARCHAR(60)    NOT NULL,
    dsBairro                 VARCHAR(60)    NOT NULL,
    dsCidade                 VARCHAR(60)    NOT NULL,
    dsEstado                 CHAR(2)        NOT NULL,
    dsCEP                    CHAR(10)       NOT NULL,
    nrRG                     CHAR(14)       NOT NULL,
    tpSexo                   INT,
    dtNascimento             DATE           NOT NULL,
    nmNecessidadeEspecial    TEXT,
    nmPlanoSaude             VARCHAR(60),
    dsCobertura              TEXT,
    dtaProxBemEstar          DATE,
    login                    CHAR(10)       NOT NULL,
    PRIMARY KEY (nmCPFCliente)
)ENGINE=INNODB
;



-- 
-- TABLE: TblClientexDispositivo 
--

CREATE TABLE TblClientexDispositivo(
    nmCPFCliente      CHAR(14)    NOT NULL,
    idDispositivo     CHAR(13)    NOT NULL,
    NumDispositivo    INT,
    PRIMARY KEY (nmCPFCliente, idDispositivo)
)ENGINE=INNODB
;



-- 
-- TABLE: TblContato 
--

CREATE TABLE TblContato(
    idContato         INT            AUTO_INCREMENT,
    nomeContato       VARCHAR(60),
    grauParentesco    CHAR(1),
    endContato        VARCHAR(60),
    baiContato        VARCHAR(60),
    cidContato        VARCHAR(60),
    cepContato        CHAR(10),
    estadoContato     CHAR(2),
    dtaNascimento     DATE,
    sqaChamada        INT,
    contratante       CHAR(1),
    login             CHAR(10)       NOT NULL,
    nmCPFCliente      CHAR(14)       NOT NULL,
    PRIMARY KEY (idContato)
)ENGINE=INNODB
;



-- 
-- TABLE: TblContrato 
--

CREATE TABLE TblContrato(
    nmContrato           INT            AUTO_INCREMENT,
    dtInicioValidade     DATE           NOT NULL,
    dtFinalValidade      DATE,
    dtSuspensao          DATE,
    login                CHAR(10)       NOT NULL,
    nmCPFContratante     CHAR(14)       NOT NULL,
    nmNomeContratante    VARCHAR(60)    NOT NULL,
    dtNascContratante    DATE,
    nmRGContratante      CHAR(14),
    dtProxAtual          DATE           NOT NULL,
    idServico            INT            NOT NULL,
    PRIMARY KEY (nmContrato)
)ENGINE=INNODB
;



-- 
-- TABLE: TblDispositivo 
--

CREATE TABLE TblDispositivo(
    idDispositivo      CHAR(13)    NOT NULL,
    tpDispositivo      INT,
    dtaFabrica         DATE,
    dtaEntrada         DATE,
    tpEstado           INT         NOT NULL,
    dtaProximaManut    DATE,
    dtaSucata          DATE,
    local              INT,
    login              CHAR(10)    NOT NULL,
    PRIMARY KEY (idDispositivo)
)ENGINE=INNODB
;



-- 
-- TABLE: TblFormaComunica 
--

CREATE TABLE TblFormaComunica(
    idFormaComunica    INT             AUTO_INCREMENT,
    idContato          INT,
    tpContato          CHAR(14),
    foneContato        CHAR(12),
    mailContato        VARCHAR(100),
    nmCPFCliente       CHAR(14),
    PRIMARY KEY (idFormaComunica)
)ENGINE=INNODB
;



-- 
-- TABLE: TblHistDispositivo 
--

CREATE TABLE TblHistDispositivo(
    dthrMudaEstado      TIMESTAMP    NOT NULL,
    idDispositivo       CHAR(13)     NOT NULL,
    cdEstadoAnterior    INT,
    login               CHAR(10)     NOT NULL,
    PRIMARY KEY (dthrMudaEstado, idDispositivo)
)ENGINE=INNODB
;



-- 
-- TABLE: TblMonitoramento 
--

CREATE TABLE TblMonitoramento(
    dtaInicioMonitora    DATETIME    NOT NULL,
    tpMonitora           INT,
    acontecimento        INT,
    nmCPFCliente         CHAR(14)    NOT NULL,
    PRIMARY KEY (dtaInicioMonitora)
)ENGINE=INNODB
;



-- 
-- TABLE: TblOcorrencia 
--

CREATE TABLE TblOcorrencia(
    idOcorrencia         INT         AUTO_INCREMENT,
    tpOcorrencia         INT,
    statusOcorre         INT,
    login                CHAR(10)    NOT NULL,
    dtaAtend             DATETIME,
    acaoOcorrencia       TEXT,
    reclOcorrencia       TEXT,
    dtaHoraAbertura      DATETIME,
    dtaHoraFechamento    DATETIME,
    dtaHoraInicio        DATETIME,
    dtaHoraTermino       DATETIME,
    conclusao            TEXT,
    nmCPFCliente         CHAR(14)    NOT NULL,
    idScript             INT         NOT NULL,
    PRIMARY KEY (idOcorrencia)
)ENGINE=INNODB
;



-- 
-- TABLE: TblPacoteServico 
--

CREATE TABLE TblPacoteServico(
    idServico           INT               AUTO_INCREMENT,
    dsTitulo            VARCHAR(60)       NOT NULL,
    dsServico           VARCHAR(100),
    dtInicioValidade    DATE              NOT NULL,
    dtFinalValidade     DATE,
    prcMensal           DECIMAL(10, 2),
    PRIMARY KEY (idServico)
)ENGINE=INNODB
;



-- 
-- TABLE: TblPacXDoenca 
--

CREATE TABLE TblPacXDoenca(
    nmCPFCliente    CHAR(14)    NOT NULL,
    cdCID           CHAR(10)    NOT NULL,
    PRIMARY KEY (nmCPFCliente, cdCID)
)ENGINE=INNODB
;



-- 
-- TABLE: TblParametro 
--

CREATE TABLE TblParametro(
    idParametro            INT    AUTO_INCREMENT,
    diasDados              INT    DEFAULT 90 NOT NULL,
    diasBemEstar           INT    DEFAULT 90 NOT NULL,
    toleraRotinaCliente    INT,
    PRIMARY KEY (idParametro)
)ENGINE=INNODB
;



-- 
-- TABLE: TblScript 
--

CREATE TABLE TblScript(
    idScript            INT             AUTO_INCREMENT,
    nmTitulo            VARCHAR(60)     NOT NULL,
    dsProcesso          TEXT,
    dsDescricao         VARCHAR(100),
    dtInicioValidade    DATE            NOT NULL,
    dtFinalValidade     DATE,
    PRIMARY KEY (idScript)
)ENGINE=INNODB
;



-- 
-- TABLE: TblSMS 
--

CREATE TABLE TblSMS(
    idSMS                INT             AUTO_INCREMENT,
    tpMensagem           VARCHAR(20)     NOT NULL,
    dsMensagem           VARCHAR(100)    NOT NULL,
    idMomento            INT,
    dtInicioValidade     DATE            NOT NULL,
    dtTerminoValidade    DATE,
    PRIMARY KEY (idSMS)
)ENGINE=INNODB
;



-- 
-- TABLE: TblTipoDoenca 
--

CREATE TABLE TblTipoDoenca(
    cdTipoDoenca    CHAR(10)       NOT NULL,
    dsTipoDoenca    VARCHAR(60),
    PRIMARY KEY (cdTipoDoenca)
)ENGINE=INNODB
;



-- 
-- TABLE: TblTratamento 
--

CREATE TABLE TblTratamento(
    idTratamento    INT            AUTO_INCREMENT,
    nmCPFCliente    CHAR(14)       NOT NULL,
    nomeTrata       VARCHAR(60),
    descrTrata      VARCHAR(60),
    horaInicial     TIMESTAMP,
    tpFrequencia    INT,
    PRIMARY KEY (idTratamento, nmCPFCliente)
)ENGINE=INNODB
;



-- 
-- TABLE: TblUsuario 
--

CREATE TABLE TblUsuario(
    login           CHAR(10)       NOT NULL,
    nmUsuario       VARCHAR(60)    NOT NULL,
    senha           CHAR(70)       NOT NULL,
    nmTelFixo       CHAR(12),
    nmTelCelular    CHAR(12),
    nmFuncao        INT,
    cdPerfil        INT,
    PRIMARY KEY (login)
)ENGINE=INNODB
;



-- 
-- INDEX: ListaAcionamento 
--

CREATE INDEX ListaAcionamento ON TblAcionamento(idContato, dtaHoraAciona)
;
-- 
-- INDEX: Ref1922 
--

CREATE INDEX Ref1922 ON TblAcionamento(idOcorrencia)
;
-- 
-- INDEX: Ref1323 
--

CREATE INDEX Ref1323 ON TblAcionamento(idContato)
;
-- 
-- INDEX: Ref1624 
--

CREATE INDEX Ref1624 ON TblAcionamento(idSMS)
;
-- 
-- INDEX: Ref840 
--

CREATE INDEX Ref840 ON TblAplicaMedico(idTratamento, nmCPFCliente)
;
-- 
-- INDEX: NomeDoenca 
--

CREATE UNIQUE INDEX NomeDoenca ON TblCID(nmDoenca)
;
-- 
-- INDEX: Ref41 
--

CREATE INDEX Ref41 ON TblCID(cdTipoDoenca)
;
-- 
-- INDEX: NomePaciente 
--

CREATE INDEX NomePaciente ON TblCliente(nmCliente)
;
-- 
-- INDEX: Ref1026 
--

CREATE INDEX Ref1026 ON TblCliente(login)
;
-- 
-- INDEX: Ref332 
--

CREATE INDEX Ref332 ON TblCliente(nmContrato)
;
-- 
-- INDEX: Ref238 
--

CREATE INDEX Ref238 ON TblClientexDispositivo(nmCPFCliente)
;
-- 
-- INDEX: Ref1439 
--

CREATE INDEX Ref1439 ON TblClientexDispositivo(idDispositivo)
;
-- 
-- INDEX: NomeContato 
--

CREATE INDEX NomeContato ON TblContato(nomeContato)
;
-- 
-- INDEX: NomeContatoParentesco 
--

CREATE INDEX NomeContatoParentesco ON TblContato(nomeContato, grauParentesco)
;
-- 
-- INDEX: Ref1027 
--

CREATE INDEX Ref1027 ON TblContato(login)
;
-- 
-- INDEX: Ref233 
--

CREATE INDEX Ref233 ON TblContato(nmCPFCliente)
;
-- 
-- INDEX: NomeContratante 
--

CREATE INDEX NomeContratante ON TblContrato(nmNomeContratante)
;
-- 
-- INDEX: Ref1029 
--

CREATE INDEX Ref1029 ON TblContrato(login)
;
-- 
-- INDEX: Ref1135 
--

CREATE INDEX Ref1135 ON TblContrato(idServico)
;
-- 
-- INDEX: Ref1030 
--

CREATE INDEX Ref1030 ON TblDispositivo(login)
;
-- 
-- INDEX: Ref1337 
--

CREATE INDEX Ref1337 ON TblFormaComunica(idContato)
;
-- 
-- INDEX: Ref241 
--

CREATE INDEX Ref241 ON TblFormaComunica(nmCPFCliente)
;
-- 
-- INDEX: Ref1442 
--

CREATE INDEX Ref1442 ON TblHistDispositivo(idDispositivo)
;
-- 
-- INDEX: Ref1043 
--

CREATE INDEX Ref1043 ON TblHistDispositivo(login)
;
-- 
-- INDEX: Ref234 
--

CREATE INDEX Ref234 ON TblMonitoramento(nmCPFCliente)
;
-- 
-- INDEX: DataOcorrencia 
--

CREATE INDEX DataOcorrencia ON TblOcorrencia(dtaHoraAbertura)
;
-- 
-- INDEX: Atendimento 
--

CREATE INDEX Atendimento ON TblOcorrencia(dtaAtend)
;
-- 
-- INDEX: Ref220 
--

CREATE INDEX Ref220 ON TblOcorrencia(nmCPFCliente)
;
-- 
-- INDEX: Ref1821 
--

CREATE INDEX Ref1821 ON TblOcorrencia(idScript)
;
-- 
-- INDEX: Ref1025 
--

CREATE INDEX Ref1025 ON TblOcorrencia(login)
;
-- 
-- INDEX: idxTituloServico 
--

CREATE UNIQUE INDEX idxTituloServico ON TblPacoteServico(dsTitulo)
;
-- 
-- INDEX: Ref23 
--

CREATE INDEX Ref23 ON TblPacXDoenca(nmCPFCliente)
;
-- 
-- INDEX: Ref54 
--

CREATE INDEX Ref54 ON TblPacXDoenca(cdCID)
;
-- 
-- INDEX: idxTituloScript 
--

CREATE UNIQUE INDEX idxTituloScript ON TblScript(nmTitulo)
;
-- 
-- INDEX: Ref215 
--

CREATE INDEX Ref215 ON TblTratamento(nmCPFCliente)
;
-- 
-- INDEX: NomeUsuario 
--

CREATE INDEX NomeUsuario ON TblUsuario(nmUsuario)
;
-- 
-- TABLE: TblAcionamento 
--

ALTER TABLE TblAcionamento ADD CONSTRAINT RefTblOcorrencia22 
    FOREIGN KEY (idOcorrencia)
    REFERENCES TblOcorrencia(idOcorrencia)
;

ALTER TABLE TblAcionamento ADD CONSTRAINT RefTblContato23 
    FOREIGN KEY (idContato)
    REFERENCES TblContato(idContato)
;

ALTER TABLE TblAcionamento ADD CONSTRAINT RefTblSMS24 
    FOREIGN KEY (idSMS)
    REFERENCES TblSMS(idSMS)
;


-- 
-- TABLE: TblAplicaMedico 
--

ALTER TABLE TblAplicaMedico ADD CONSTRAINT RefTblTratamento40 
    FOREIGN KEY (idTratamento, nmCPFCliente)
    REFERENCES TblTratamento(idTratamento, nmCPFCliente)
;


-- 
-- TABLE: TblCID 
--

ALTER TABLE TblCID ADD CONSTRAINT RefTblTipoDoenca1 
    FOREIGN KEY (cdTipoDoenca)
    REFERENCES TblTipoDoenca(cdTipoDoenca)
;


-- 
-- TABLE: TblCliente 
--

ALTER TABLE TblCliente ADD CONSTRAINT RefTblUsuario26 
    FOREIGN KEY (login)
    REFERENCES TblUsuario(login)
;

ALTER TABLE TblCliente ADD CONSTRAINT RefTblContrato32 
    FOREIGN KEY (nmContrato)
    REFERENCES TblContrato(nmContrato)
;


-- 
-- TABLE: TblClientexDispositivo 
--

ALTER TABLE TblClientexDispositivo ADD CONSTRAINT RefTblCliente38 
    FOREIGN KEY (nmCPFCliente)
    REFERENCES TblCliente(nmCPFCliente)
;

ALTER TABLE TblClientexDispositivo ADD CONSTRAINT RefTblDispositivo39 
    FOREIGN KEY (idDispositivo)
    REFERENCES TblDispositivo(idDispositivo)
;


-- 
-- TABLE: TblContato 
--

ALTER TABLE TblContato ADD CONSTRAINT RefTblUsuario27 
    FOREIGN KEY (login)
    REFERENCES TblUsuario(login)
;

ALTER TABLE TblContato ADD CONSTRAINT RefTblCliente33 
    FOREIGN KEY (nmCPFCliente)
    REFERENCES TblCliente(nmCPFCliente)
;


-- 
-- TABLE: TblContrato 
--

ALTER TABLE TblContrato ADD CONSTRAINT RefTblUsuario29 
    FOREIGN KEY (login)
    REFERENCES TblUsuario(login)
;

ALTER TABLE TblContrato ADD CONSTRAINT RefTblPacoteServico35 
    FOREIGN KEY (idServico)
    REFERENCES TblPacoteServico(idServico)
;


-- 
-- TABLE: TblDispositivo 
--

ALTER TABLE TblDispositivo ADD CONSTRAINT RefTblUsuario30 
    FOREIGN KEY (login)
    REFERENCES TblUsuario(login)
;


-- 
-- TABLE: TblFormaComunica 
--

ALTER TABLE TblFormaComunica ADD CONSTRAINT RefTblContato37 
    FOREIGN KEY (idContato)
    REFERENCES TblContato(idContato)
;

ALTER TABLE TblFormaComunica ADD CONSTRAINT RefTblCliente41 
    FOREIGN KEY (nmCPFCliente)
    REFERENCES TblCliente(nmCPFCliente)
;


-- 
-- TABLE: TblHistDispositivo 
--

ALTER TABLE TblHistDispositivo ADD CONSTRAINT RefTblDispositivo42 
    FOREIGN KEY (idDispositivo)
    REFERENCES TblDispositivo(idDispositivo)
;

ALTER TABLE TblHistDispositivo ADD CONSTRAINT RefTblUsuario43 
    FOREIGN KEY (login)
    REFERENCES TblUsuario(login)
;


-- 
-- TABLE: TblMonitoramento 
--

ALTER TABLE TblMonitoramento ADD CONSTRAINT RefTblCliente34 
    FOREIGN KEY (nmCPFCliente)
    REFERENCES TblCliente(nmCPFCliente)
;


-- 
-- TABLE: TblOcorrencia 
--

ALTER TABLE TblOcorrencia ADD CONSTRAINT RefTblCliente20 
    FOREIGN KEY (nmCPFCliente)
    REFERENCES TblCliente(nmCPFCliente)
;

ALTER TABLE TblOcorrencia ADD CONSTRAINT RefTblScript21 
    FOREIGN KEY (idScript)
    REFERENCES TblScript(idScript)
;

ALTER TABLE TblOcorrencia ADD CONSTRAINT RefTblUsuario25 
    FOREIGN KEY (login)
    REFERENCES TblUsuario(login)
;


-- 
-- TABLE: TblPacXDoenca 
--

ALTER TABLE TblPacXDoenca ADD CONSTRAINT RefTblCliente3 
    FOREIGN KEY (nmCPFCliente)
    REFERENCES TblCliente(nmCPFCliente)
;

ALTER TABLE TblPacXDoenca ADD CONSTRAINT RefTblCID4 
    FOREIGN KEY (cdCID)
    REFERENCES TblCID(cdCID)
;


-- 
-- TABLE: TblTratamento 
--

ALTER TABLE TblTratamento ADD CONSTRAINT RefTblCliente15 
    FOREIGN KEY (nmCPFCliente)
    REFERENCES TblCliente(nmCPFCliente)
;

INSERT INTO `TblUsuario`(`login`, `nmUsuario`, `senha`, `nmTelFixo`, `nmTelCelular`, `nmFuncao`, `cdPerfil`)    VALUES ('admin', 'admin', '8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918', '', '', 0, 1);
