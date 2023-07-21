package defaultt.VIEW;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.*;
import javax.swing.JScrollPane;

import defaultt.DAO.UsuarioDAO;
import defaultt.DTO.UsuarioDTO;

public class Login extends JFrame implements ActionListener {
    private JPanel painel;
    private JLabel nomeLabel;
    private JTextField nomeTexto;
    private JLabel senhaLabel;
    private JTextField senhaTexto;
    private JButton loginButton;
    private JButton singUpButton;
    private JButton pesquisaButton;
    private JTable tabela;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private JButton carregarButton;
    //private JButton alterarButton;
    //private JButton deletarButton;

    private UsuarioDTO usuariodto;
    private UsuarioDAO usuariodao;




    public Login() {
        setTitle("Cadastro de usuário");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        painel = new JPanel(new GridBagLayout());// centralizar os componentes de JPanel
        painel.setSize(500, 300);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 

        nomeLabel = new JLabel("Nome: ");
        nomeTexto = new JTextField(35);
        senhaLabel = new JLabel("Senha: ");
        senhaTexto = new JTextField(35);
        loginButton = new JButton("Login");
        singUpButton = new JButton("Sing Up");
        pesquisaButton = new JButton("Search");
        carregarButton = new JButton("Load");
        //alterarButton = new JButton("Update");
        //deletarButton = new JButton("Delete")

        usuariodto = new UsuarioDTO();
        usuariodao = new UsuarioDAO();


        loginButton.addActionListener(this);
        singUpButton.addActionListener(this);
        pesquisaButton.addActionListener(this);
        carregarButton.addActionListener(this);

        //tabela
        tabela = new JTable();
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //model da tabela
        model = (DefaultTableModel) tabela.getModel();
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Senha");


        painel.add(nomeLabel);
        painel.add(nomeTexto);
        painel.add(senhaLabel);
        painel.add(senhaTexto);
        painel.add(loginButton);
        painel.add(singUpButton);
        painel.add(pesquisaButton);
        painel.add(tabela);
        listarValores();

        /*
         * Parte Bonita do GUI
         */

        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(nomeLabel, gbc);

        gbc.gridx = 1;
        painel.add(nomeTexto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painel.add(senhaLabel, gbc);

        gbc.gridx = 1;
        painel.add(senhaTexto, gbc);

        JPanel painelButtonSuperior = new JPanel(new FlowLayout());
        painelButtonSuperior.add(loginButton);
        painelButtonSuperior.add(singUpButton);
        painelButtonSuperior.add(pesquisaButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(painelButtonSuperior, gbc);


        JPanel painelButtonInferior = new JPanel(new FlowLayout());
        painelButtonInferior.add(carregarButton);

        gbc.gridy = 4; 
        painel.add(painelButtonInferior, gbc);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        scrollPane = new JScrollPane(tabela);
        painel.add(scrollPane, gbc);

        add(painel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton){
            try {
                logar();
            }  catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Login "+ erro.getMessage());
            }
        }else if (e.getSource() == singUpButton){
            try {
                cadastrar();
            } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, "Cadastrar " + erro.getMessage());
            }
        }
        else if(e.getSource() == pesquisaButton){
            try {
                listarValores();
            } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, "Listar Valores Tabela " + erro.getMessage());
            }
        }
        else if(e.getSource() == carregarButton){
            try {
                carregarCampos();
            } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, "Carregar Campo" + erro.getMessage());
            }
        }
    }

    private void logar() throws SQLException{
        usuariodto.setNome(nomeTexto.getText());
        usuariodto.setSenha(senhaTexto.getText());

        // Fazer algo com o objeto UsuarioDTO, como enviar para o backend ou processar localmente
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
    }

    private void cadastrar(){
        usuariodto.setNome(nomeTexto.getText());
        usuariodto.setSenha(senhaTexto.getText());

        if("".equals(nomeTexto.getText()) || "".equals(senhaTexto.getText())){
        JOptionPane.showMessageDialog(null, "ERRO. PREENCHA OS CAMPOS");
        }else{
            usuariodao.cadastrarUsuario(usuariodto);
            JOptionPane.showMessageDialog(null, "Usuário Cadastrado");
            listarValores();
            limparCampos();
        }
    }


    private void listarValores(){
        ArrayList<UsuarioDTO> lista = usuariodao.pesquisarUsuario();
        model.setRowCount(0);
        //model.setNumRows(0);
        //tabela.clearSelection();
        for (UsuarioDTO usuario : lista) {
            Object[] rowData = {usuario.getId(), usuario.getNome(), usuario.getSenha()};
            model.addRow(rowData);
        }
        model.setRowCount(0);
    }
    
    private void carregarCampos(){
        int linhaSetada = tabela.getSelectedRow();

        nomeTexto.setText(tabela.getModel().getValueAt(linhaSetada, 1).toString());
        senhaTexto.setText(tabela.getModel().getValueAt(linhaSetada, 2).toString());
    }

    private void limparCampos(){
        nomeTexto.setText("");
        senhaTexto.setText("");
        nomeTexto.requestFocus();
    }
}