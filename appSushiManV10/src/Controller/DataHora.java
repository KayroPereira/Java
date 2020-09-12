package Controller;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class DataHora {

	public Time getHoraAtual(){
		SimpleDateFormat formatarHora = new SimpleDateFormat("HH:mm:ss");
		Calendar c = Calendar.getInstance();
		
		Date date = c.getTime();
		
		try{
			date = formatarHora.parse(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Time horaFormatada = new Time(date.getTime());
		
		return horaFormatada;
	}

	public Date getDataAtual(){
		
		DateFormat formatarData = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		
		try {
			return (Date) formatarData.parse(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getDataAtualFormatada() {
		LocalDateTime dataFormatada = LocalDateTime.now();
		
		int 	dia = dataFormatada.getDayOfMonth(),
				mes = dataFormatada.getMonthValue(),
				ano = dataFormatada.getYear();
		
		Function<Integer, String> fData = d -> Math.abs(d) < 10 ? "0"+d : d.toString();
		
		return fData.apply(dia) + "/" + fData.apply(mes) + "/" + ano;
	}
	
	public String formatarPeriodo (TempoDataHora periodo) {
		String periodoAjustado = "";
		Date dataAtual = getDataAtual();
		
		UnaryOperator<Integer> ajustePeriodo = pAtual -> pAtual < 0 ? 86400+pAtual : pAtual;
		
		UnaryOperator<Integer> ajusteHora = pAjustado -> pAjustado / (60*60);
		BinaryOperator<Integer> ajusteMinuto = (pAjustado, horaAjustada) -> (pAjustado / 60) - (horaAjustada * 60);
		BinaryOperator<Integer> ajusteSegundo = (pAjustado, horaAjustada) -> pAjustado - (horaAjustada * 3600);
		
		int hora = ajustePeriodo.andThen(ajusteHora).apply(periodo.getTempo()),								//quebra o período em hora, minuto e segundos
			minuto = ajusteMinuto.apply(ajustePeriodo.apply(periodo.getTempo()), hora),
			segundo = ajusteSegundo.apply(ajustePeriodo.apply(periodo.getTempo()), hora) - (minuto * 60);

		Function<Integer, String> fPeriodo = p -> Math.abs(p) < 10 ? "0"+p : p.toString();					//adiciona um zero caso seja menor que 10
		
		periodoAjustado = fPeriodo.apply(hora)  + ":" + fPeriodo.apply(minuto) + ":" + fPeriodo.apply(segundo);
		
		int diferencaDias = (int) ((dataAtual.getTime() - periodo.getData().getTime())/(1000*86400));		//converte de milisegundos para dias
		
		BiFunction<Integer, String, String> ajusteDias = (dias, pAjustado) -> dias < 2 ? dias + " dia - " + pAjustado : dias + " dias - " + pAjustado; 
		
		if (diferencaDias > 0) {																			//formata para dia ou dias
			if (periodo.getTempo() <= 0)
				diferencaDias--;
				
			if (diferencaDias != 0)
				periodoAjustado = ajusteDias.apply(diferencaDias, periodoAjustado);
		}
		
		return periodoAjustado;
	}
}
