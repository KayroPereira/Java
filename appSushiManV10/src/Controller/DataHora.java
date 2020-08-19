package Controller;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class DataHora {

	@SuppressWarnings("deprecation")
	public Time GetHoraAtual()
	{
		SimpleDateFormat formatarHora = new SimpleDateFormat("HH:mm:ss");
		Date temp = new Date();
		
		try
		{
			temp = formatarHora.parse(temp.getHours() + ":" + temp.getMinutes() + ":" + temp.getSeconds());
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		Time horaFormatada = new Time(temp.getTime());
		
		return horaFormatada;
	}

	public Date GetDataAtual()
	{
		DateFormat formatarData = new SimpleDateFormat("yyyy-MM-dd");  
		LocalDateTime dataFormatada = LocalDateTime.now();
		
		String [] data = dataFormatada.toString().substring(0, dataFormatada.toString().indexOf("T")).split("-");
		
		try 
		{
			return (Date) formatarData.parse(data[0] + "-" + data[1] + "-" + data[2]);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String GetDataAtualFormatada()
	{  
		LocalDateTime dataFormatada = LocalDateTime.now();
		
		int 	dia = dataFormatada.getDayOfMonth(),
				mes = dataFormatada.getMonthValue(),
				ano = dataFormatada.getYear();
		
		String saida = "";
		
		if ((dia+"").length() < 2)
			saida = "0" + dia;
		else
			saida = dia+"";
		
		if ((mes+"").length() < 2)
			saida += "/0" + mes;
		else
			saida += "/"+mes;
		
		return saida + "/" + ano;
	}
	
	public String formatarPeriodo (TempoDataHora periodo) {
		String periodoAjustado = "";
		Date dataAtual = GetDataAtual();
		
		int hora = 0,
			minuto = 0,
			segundo = 0;
		
		if (periodo.getTempo() < 0) {
			hora = (86400+periodo.getTempo())/(60*60);
			minuto = (86400+periodo.getTempo())/(60)-(hora*60);
			segundo = (86400+periodo.getTempo())-(hora*3600)-(minuto*60);
		}
		else {
			hora = (periodo.getTempo())/(60*60);
			minuto = (periodo.getTempo())/(60)-(hora*60);
			segundo = (periodo.getTempo())-(hora*3600)-(minuto*60);
		}
		
		int diferencaDias = (int) ((dataAtual.getTime() - periodo.getData().getTime())/1000/86400); 
		
		periodoAjustado = ((hora+"").length() < 2 ? "0"+hora : hora+"") + ":" +
				  ((minuto+"").length() < 2 ? "0"+minuto : minuto+"") + ":" +
				  ((segundo+"").length() < 2 ? "0"+segundo : segundo+"");
		
		if (diferencaDias > 0) {
			if (periodo.getTempo() > 0) {
				if (diferencaDias < 2)
					periodoAjustado = diferencaDias + " dia - " + periodoAjustado;
				else
					periodoAjustado = diferencaDias + " dias - " + periodoAjustado;
			}
			else {
				if (--diferencaDias != 0)
					if (diferencaDias == 1)
						periodoAjustado = diferencaDias + " dia - " + periodoAjustado;
					else
						periodoAjustado = diferencaDias + " dias - " + periodoAjustado;
			}
		}
		
		return periodoAjustado;
	}
}
