package dados;

import java.util.Calendar;

public class Atividade {
private int idProj;
private String nomeAtv;
private Calendar dataInicialA;
private Calendar dataFinalA;
private boolean finalizada;
private String descricao;
private int id;

public Atividade() {
	dataFinalA =  Calendar.getInstance();
	dataInicialA = Calendar.getInstance();
}
		
public int getIdProj() {
	return idProj;
}
public void setIdProj(int idProj) {
	this.idProj = idProj;
}
public String getNomeAtv() {
	return nomeAtv;
}
public void setNomeAtv(String nomeAtv) {
	this.nomeAtv = nomeAtv;
}
public Calendar getDataInicialA() {
	return dataInicialA;
}
public void setDataInicialA(Calendar dataInicialA) {
	this.dataInicialA = dataInicialA;
}
public Calendar getDataFinalA() {
	return dataFinalA;
}
public void setDataFinalA(Calendar dataFinalA) {
	this.dataFinalA = dataFinalA;
}
public boolean isFinalizada() {
	return finalizada;
}
public void setFinalizada(boolean finalizada) {
	this.finalizada = finalizada;
}
public String getDescricao() {
	return descricao;
}
public void setDescricao(String descricao) {
	this.descricao = descricao;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

}
