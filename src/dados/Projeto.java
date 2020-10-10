package dados;

import java.util.Calendar;

public class Projeto {
private String nomeProj;
private Calendar dataInicial;
private Calendar dataFinal;
private int id;
private String completo;
private String atrasado;

public Projeto() {
dataFinal =  Calendar.getInstance();
dataInicial = Calendar.getInstance();
}

public String getNomeProj() {
	return nomeProj;
}
public void setNomeProj(String nomeProj) {
	this.nomeProj = nomeProj;
}
public Calendar getDataInicial() {
	return dataInicial;
}
public void setDataInicial(Calendar dataInicial) {
	this.dataInicial = dataInicial;
}
public Calendar getDataFinal() {
	return dataFinal;
}
public void setDataFinal(Calendar dataFinal) {
	this.dataFinal = dataFinal;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCompleto() {
	return completo;
}
public void setCompleto(String completo) {
	this.completo = completo;
}
public String getAtrasado() {
	return atrasado;
}
public void setAtrasado(String atrasado) {
	this.atrasado = atrasado;
}

}
