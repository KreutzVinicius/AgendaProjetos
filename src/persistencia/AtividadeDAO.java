package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dados.Atividade;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.SelectException;
import exceptions.UpdateException;


public class AtividadeDAO {
	private static AtividadeDAO instance = null;

	private PreparedStatement sqlinsert;
	private PreparedStatement sqldelete;
	private PreparedStatement sqlupdate;
	private PreparedStatement sqlall;
	private PreparedStatement sqlselect;


	public AtividadeDAO() {
		Connection conn = Conexao.getConexao();
		try {
			sqlinsert = conn.prepareStatement("insert into atividades (id, idproj, nomeatv, datainiciala, datafinala, finalizada, descricao) values (default,?,?,?,?,?,?) ");
			sqldelete = conn.prepareStatement("delete from atividades where id = ?");
			sqlupdate = conn.prepareStatement("update atividades set nomeatv = ?, datainiciala = ?, datafinala = ?, finalizada = ?, descricao = ?, where id = ?");
			sqlall = conn.prepareStatement("select id from atividades");
			sqlselect = conn.prepareStatement("select * from atividades where id = ?");
			} catch (SQLException e) {
				System.out.println("Erro de Conexao");
			}
		}
	public static AtividadeDAO getInstance (){
		if(instance == null) 
			instance = new AtividadeDAO();
		
		return instance;
}
	public void insert (Atividade atividade) throws InsertException {
		try {

			sqlinsert.setInt(1,atividade.getIdProj());
			sqlinsert.setString(2,atividade.getNomeAtv());
			java.util.Date dataI = atividade.getDataInicialA().getTime();
			java.sql.Timestamp sqlDateI = new java.sql.Timestamp(dataI.getTime());
			sqlinsert.setTimestamp(3, sqlDateI);
			java.util.Date dataF = atividade.getDataFinalA().getTime();
			java.sql.Timestamp sqlDateF = new java.sql.Timestamp(dataF.getTime());
			sqlinsert.setTimestamp(4, sqlDateF);
			sqlinsert.setBoolean(5,atividade.isFinalizada());
		sqlinsert.setString(6,atividade.getDescricao());
			sqlinsert.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			 throw new InsertException("erro de inserção na tabela atividade");
		}
}
	
	public void delete (int id) throws DeleteException {
		try {
			sqldelete.setInt(1, id);
			sqldelete.executeUpdate();
		} catch (Exception e) {
			 throw new DeleteException("erro de exclusão na tabela atividade");
		}
	}
	
	public Atividade select(int id) throws SelectException {
		ResultSet rs;
		Atividade atividade = null;
		
		try {
			sqlselect.setInt(1, id);
			rs = sqlselect.executeQuery();
			if(rs.next()) {
				atividade = new Atividade();
				atividade.setId(rs.getInt("id"));
				atividade.setNomeAtv(rs.getString("nomeatv"));
				atividade.getDataInicialA().setTime((Date)rs.getObject("datainiciala"));
				atividade.getDataFinalA().setTime((Date)rs.getObject("datafinala"));
				atividade.setIdProj(rs.getInt("idProj"));
				atividade.setFinalizada(rs.getBoolean("finalizada"));
				atividade.setDescricao(rs.getString("descricao"));
	
			} 
		} catch (Exception e) {
			throw new SelectException("erro de seleção na tabela atividade");
		}
			return atividade;
	}
	
	public ArrayList<Atividade> selectAll() throws SelectException {
		ArrayList<Atividade> atividade = new ArrayList<Atividade>();
		ResultSet rs;
		try {
			rs = sqlall.executeQuery();
			while (rs.next()) {
				atividade.add(select(rs.getInt("id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SelectException("erro de seleção total na tabela atividade");
		}
			return atividade;
	}

	public void update (Atividade atividade) throws UpdateException {
		try {
			sqlupdate.setString(2,atividade.getNomeAtv());
			java.util.Date dataI = atividade.getDataInicialA().getTime();
			java.sql.Timestamp sqlDateI = new java.sql.Timestamp(dataI.getTime());
			sqlupdate.setTimestamp(2, sqlDateI);
			java.util.Date dataF = atividade.getDataFinalA().getTime();
			java.sql.Timestamp sqlDateF = new java.sql.Timestamp(dataF.getTime());
			sqlupdate.setTimestamp(3, sqlDateF);
			sqlupdate.setBoolean(5,atividade.isFinalizada());
			sqlupdate.setString(6,atividade.getDescricao());
			sqlupdate.executeUpdate();
		} catch (Exception e) {
			 throw new UpdateException("erro de update na tabela atividade");
		}

	}
	
}