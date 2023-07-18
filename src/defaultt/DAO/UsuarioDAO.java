package defaultt.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import defaultt.DTO.UsuarioDTO;

public class UsuarioDAO {
    Connection conexao;

    public ResultSet autenticacaoUsuario(UsuarioDTO usuariodto){
        conexao = new ConexaoDAO().conectaBD(); //declarou variavel do tipo conexão

        try {
            String sql = "select * from usuario where nome = ? and senha = ?"; //trazer resultado do banco
            PreparedStatement pstm = conexao.prepareStatement(sql); //preparando a conexão
            pstm.setString(1, usuariodto.getNome());
            pstm.setString(2, usuariodto.getSenha());

            ResultSet rs = pstm .executeQuery(); //executa comando sql na conexão
            return rs; //retornar o resultado

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ConexaoDAO "+ erro.getMessage());
            return null;
        }

        
    }
}
