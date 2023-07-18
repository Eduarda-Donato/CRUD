package defaultt.VIEW;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import defaultt.DAO.UsuarioDAO;
import defaultt.DTO.UsuarioDTO;

public class Login extends JFrame implements ActionListener {
    private JPanel painel;
    private JLabel nomeLabel;
    private JTextField nomeTexto;
    private JLabel senhaLabel;
    private JTextField senhaTexto;
    private JButton cadastroButton;

    public Login() {
        setTitle("Cadastro de usuário");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        painel = new JPanel();
        painel.setSize(300, 300);
        painel.setLayout(new GridBagLayout()); // centralizar os componentes de JPanel

        nomeLabel = new JLabel("Nome: ");
        nomeTexto = new JTextField(20);
        senhaLabel = new JLabel("Senha: ");
        senhaTexto = new JTextField(20);
        cadastroButton = new JButton("Cadastrar");
        cadastroButton.addActionListener(this);

        painel.add(nomeLabel);
        painel.add(nomeTexto);
        painel.add(senhaLabel);
        painel.add(senhaTexto);
        painel.add(cadastroButton);

        add(painel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cadastroButton) {
            try {
                UsuarioDTO usuariodto = new UsuarioDTO();
                usuariodto.setNome(nomeTexto.getText());
                usuariodto.setSenha(senhaTexto.getText());

                // Fazer algo com o objeto UsuarioDTO, como enviar para o backend ou processar localmente
                UsuarioDAO usuariodao = new UsuarioDAO();
                ResultSet rsusuariodao = usuariodao.autenticacaoUsuario(usuariodto); //resultado do banco é trabalhado com ResultSet

                if(rsusuariodao.next()){ //se tiver prox é pq trouxe algum valor (next() -> pelo menos uma linha)
                    //chamar tela desejada
                    Principal janelaPrincipal = new Principal();
                    janelaPrincipal.setVisible(true);

                    dispose(); //fecha a tela de login
                }else {
                    //mesagem de incorreto (não existe no banco)
                    JOptionPane.showMessageDialog(null, "Usuário ou Senha inválida");
                }

            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Login "+ erro.getMessage());
            }
            
        }
    }
}