package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dados.Atividade;
import dados.Projeto;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.SelectException;
import exceptions.UpdateException;


public class ProjetoDAO {
	private static ProjetoDAO instance = null;

	private PreparedStatement sqlinsert;
	private PreparedStatement sqldelete;
	private PreparedStatement sqlupdate;
	private PreparedStatement sqlall;
	private PreparedStatement sqlselect;


	public ProjetoDAO() {
		Connection conn = Conexao.getConexao();
		try {
			sqlinsert = conn.prepareStatement("insert into projeto (id, nomeproj, datainicial, datafinal, completo, atrasado) values (default,?,?,?,?,?) ");
			sqldelete = conn.prepareStatement("delete from projeto where id = ?");
			sqlupdate = conn.prepareStatement("update projeto set nomeproj = ?, datainicial = ?, datafinal = ?, completo = ?, atrasado = ?  where id = ?");
			sqlall = conn.prepareStatement("select id from projeto");
			sqlselect = conn.prepareStatement("select * from projeto where id = ?");
			} catch (SQLException e) {
				System.out.println("Erro de Conexao");
			}
		}
	public static ProjetoDAO getInstance (){
		if(instance == null) 
			instance = new ProjetoDAO();
		
		return instance;
}
	public void insert (Projeto projeto) throws InsertException {
		try {
			sqlinsert.setString(1,projeto.getNomeProj());
			java.util.Date dataI = projeto.getDataInicial().getTime();
			java.sql.Timestamp sqlDateI = new java.sql.Timestamp(dataI.getTime());
			sqlinsert.setTimestamp(2, sqlDateI);
			java.util.Date dataF = projeto.getDataFinal().getTime();
			java.sql.Timestamp sqlDateF = new java.sql.Timestamp(dataF.getTime());
			sqlinsert.setTimestamp(3, sqlDateF);
			sqlinsert.setString(4, calculaPorcentagem(projeto));
			sqlinsert.setString(5, calculaAtraso(projeto));
			sqlinsert.executeUpdate();
		} catch (Exception e) {
			 throw new InsertException("erro de inserção na tabela projeto");
		}
}
	
	public void delete (int id) throws DeleteException {
		try {
			sqldelete.setInt(1, id);
			sqldelete.executeUpdate();
		} catch (Exception e) {
			 throw new DeleteException("erro de exclusão na tabela projeto");
		}
	}
	
	public Projeto select(int id) throws SelectException {
		ResultSet rs;
		Projeto proj = null;

		
		try {
			sqlselect.setInt(1, id);
			rs = sqlselect.executeQuery();
			if(rs.next()) {
				proj = new Projeto();   
				proj.setId(rs.getInt("id"));
				proj.setNomeProj(rs.getString("nomeproj"));
				proj.getDataInicial().setTime((Date)rs.getObject("datainicial"));
				proj.getDataFinal().setTime((Date)rs.getObject("datafinal"));
				proj.setCompleto(calculaPorcentagem(proj));	
				proj.setAtrasado(calculaAtraso(proj));
	
			} 
		} catch (Exception e) {
			throw new SelectException("erro de seleção na tabela projeto");
		}
			return proj;
	}
	
	public ArrayList<Projeto> selectAll() throws SelectException {
		ArrayList<Projeto> projeto = new ArrayList<Projeto>();
		ResultSet rs;
		try {
			rs = sqlall.executeQuery();
			while (rs.next()) {
				projeto.add(select(rs.getInt("id")));
			}
		} catch (Exception e) {
			throw new SelectException("erro de seleção na tabela projeto");
		}
			return projeto;
	}

	public void update (Projeto projeto) throws UpdateException {
		try {
			sqlupdate.setString(1,projeto.getNomeProj());
			java.util.Date dataI = projeto.getDataInicial().getTime();
			java.sql.Timestamp sqlDateI = new java.sql.Timestamp(dataI.getTime());
			sqlupdate.setTimestamp(2, sqlDateI);
			java.util.Date dataF = projeto.getDataFinal().getTime();
			java.sql.Timestamp sqlDateF = new java.sql.Timestamp(dataF.getTime());
			sqlupdate.setTimestamp(3, sqlDateF);
			sqlupdate.setString(4, calculaPorcentagem(projeto));
			sqlupdate.setString(5, calculaAtraso(projeto));
			sqlupdate.setInt(6, projeto.getId());
			sqlupdate.executeUpdate();
		} catch (Exception e) {
			 throw new UpdateException("erro de update na tabela projeto");
		}

	}
	public String calculaPorcentagem (Projeto proj) {
		ArrayList<Atividade> atividades = null;
		AtividadeDAO atividadeDAO = new AtividadeDAO();
		float contador = 0;
		float contadorConcluidas=0;
		try {
			atividades = atividadeDAO.selectAll();
		} catch (SelectException e) {
			e.printStackTrace();
		}
		for (Atividade ativ : atividades) {
			if (ativ.getIdProj()== proj.getId()) {
				contador++;

					if (ativ.isFinalizada() == true)
						contadorConcluidas++;
			}
		}
		
		if (contador != 0 )
		return (contadorConcluidas/contador)*100 + "%";
		

		return "vazio";
	}
	

	public String calculaAtraso (Projeto proj) throws SelectException {
		ArrayList<Atividade> atividades = null;
		AtividadeDAO atividadeDAO = new AtividadeDAO();
		
		atividades = atividadeDAO.selectAll();
		for (Atividade ativ : atividades) {
			System.out.println(ativ.getId() +""+ ativ.getIdProj());
			if (ativ.getDataFinalA().after(proj.getDataFinal()) == true && (ativ.isFinalizada() == false && ativ.getIdProj() == proj.getId())) {
				return "sim";
			}
		}
		return "não";
	}
	
}