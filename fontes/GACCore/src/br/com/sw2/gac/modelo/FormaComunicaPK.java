package br.com.sw2.gac.modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TblFormaComunica database table.
 * 
 */
@Embeddable
public class FormaComunicaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(unique=true, nullable=false)
	private int idFormaComunica;

	@Column(name="IdContato", unique=true, nullable=false)
	private int idContato;

	public FormaComunicaPK() {
	}
	public int getIdFormaComunica() {
		return this.idFormaComunica;
	}
	public void setIdFormaComunica(int idFormaComunica) {
		this.idFormaComunica = idFormaComunica;
	}
	public int getIdContato() {
		return this.idContato;
	}
	public void setIdContato(int idContato) {
		this.idContato = idContato;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FormaComunicaPK)) {
			return false;
		}
		FormaComunicaPK castOther = (FormaComunicaPK)other;
		return 
			(this.idFormaComunica == castOther.idFormaComunica)
			&& (this.idContato == castOther.idContato);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idFormaComunica;
		hash = hash * prime + this.idContato;
		
		return hash;
	}
}