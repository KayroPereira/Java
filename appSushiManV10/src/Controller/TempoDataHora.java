package Controller;

import java.util.Date;

public class TempoDataHora {
	private Date data;
	private int tempo;
	
	public TempoDataHora(Date data, int tempo) {
		this.data = data;
		this.tempo = tempo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
}
