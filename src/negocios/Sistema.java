package negocios;

import java.util.ArrayList;

import dados.Atividade;
import dados.Projeto;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.SelectException;
import exceptions.UpdateException;
import persistencia.AtividadeDAO;
import persistencia.ProjetoDAO;

public class Sistema {
private ArrayList<Atividade> atividades = null;
private ProjetoDAO projetoDAO;
private AtividadeDAO atividadeDAO;

public void cadastrarProjeto (Projeto projeto) {
	projetoDAO = ProjetoDAO.getInstance();
	
	try {
		projetoDAO.insert(projeto);
	}catch (InsertException e) {
		System.out.println(e.getMessage());
	}
}

public void cadastrarAtividade (Atividade atividade) {
	atividadeDAO = AtividadeDAO.getInstance();
	
	try {
		atividadeDAO.insert(atividade);
	}catch (InsertException e) {
		System.out.println(e.getMessage());
	}
}

public void removerProjeto (int id) {
	projetoDAO = ProjetoDAO.getInstance();
		
		try {
			projetoDAO.delete(id);
		}catch (DeleteException e) {
			System.out.println(e.getMessage());
		}
}

public void removerAtividade (int id) {
	atividadeDAO = AtividadeDAO.getInstance();
		
		try {
			atividadeDAO.delete(id);
		}catch (DeleteException e) {
			System.out.println(e.getMessage());
		}
}


public ArrayList<Atividade> buscarAtividades(int id){
	atividadeDAO = AtividadeDAO.getInstance();
	ArrayList<Atividade> lista = new ArrayList<Atividade>();	
		try {
			atividades = atividadeDAO.selectAll();
			
		}catch (SelectException e) {
			System.out.println(e.getMessage());
		}
		
		for (Atividade atividade: atividades) {
			if (atividade.getIdProj() == id)
				lista.add(atividade);
		}	
		return lista;
}

public void alterarProjeto(Projeto projeto) {
	projetoDAO = ProjetoDAO.getInstance();
	
	try {
		projetoDAO.update(projeto);
	}catch (UpdateException e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
}

public void alterarAtividade(Atividade atividade) {
	atividadeDAO = AtividadeDAO.getInstance();
	
	try {
		atividadeDAO.update(atividade);
	}catch (UpdateException e) {
		System.out.println(e.getMessage());
	}
}

}
