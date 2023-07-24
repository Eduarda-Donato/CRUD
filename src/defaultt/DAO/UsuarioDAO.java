package defaultt.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import defaultt.DTO.UsuarioDTO;

public class UsuarioDAO {
    Connection conexao;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<UsuarioDTO> lista = new ArrayList<>();

    public ResultSet autenticacaoUsuario(UsuarioDTO usuariodto){
        conexao = new ConexaoDAO().conectaBD(); //declarou variavel do tipo conexão

        try {
            String sql = "select * from usuario where nome = ? and senha = ?"; //trazer resultado do banco
            pstm = conexao.prepareStatement(sql); //preparando a conexão
            pstm.setString(1, usuariodto.getNome()); //passa o nome para o primeiro campo com interrogação
            pstm.setString(2, usuariodto.getSenha());

            rs = pstm.executeQuery(); //executa comando sql na conexão
            return rs; //retornar o resultado

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "UsuarioDAO Autenticar"+ erro.getMessage());
            return null;
        }

        
    }

    public void cadastrarUsuario(UsuarioDTO usuariodto){
        String sql = "insert into usuario (id, nome, senha) values (?,?,?)";

        conexao = new ConexaoDAO().conectaBD();
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, usuariodto.getId());
            pstm.setString(2, usuariodto.getNome()); 
            pstm.setString(3, usuariodto.getSenha());
            

            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "UsuarioDAO Cadastrar " + erro.getMessage());
        }
    }

    public ArrayList<UsuarioDTO> pesquisarUsuario(){
        String sql = "select * from usuario";
        conexao = new ConexaoDAO().conectaBD();

        try {
            pstm = conexao.prepareStatement(sql);
            rs = pstm.executeQuery();

            while(rs.next()){ //o banco pode ter mais de uma linha //enquanto tiver linha
                UsuarioDTO usuariodto = new UsuarioDTO();
                usuariodto.setId(rs.getInt("id")); //acessa o objeto para setar o id com o valor do banco
                usuariodto.setNome(rs.getString("nome"));
                usuariodto.setSenha(rs.getString("senha"));
                /*
                 * CADASTRO: armazena na a info do usuario na dto e depois joga para o banco
                 * PESQUISA: pega info do banco armazena na dto e depois pega da dto e mostra para o usuario
                 */
                lista.add(usuariodto); //armazena todas as linhas do banco
            }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "UsuarioDAO Pesquisar " + erro.getMessage());
            return null;
        }
        return lista;
    }

    public void alterarUsuario(UsuarioDTO usuariodto){
        String sql = "update usuario set nome = ?, senha = ? where id = ?";
        conexao = new ConexaoDAO().conectaBD();

        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(3, usuariodto.getId());
            pstm.setString(1, usuariodto.getNome());
            pstm.setString(2, usuariodto.getSenha());
            

            pstm.execute();
            pstm.close();

        } catch (SQLException erro) {
           JOptionPane.showMessageDialog(null, "UsuarioDAO Alterar "+ erro.getMessage());
        }
    }
}
