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

public class Principal extends JFrame{
    private JPanel painel;
    private JLabel label;
    public Principal() {
        setTitle("Cadastro de usuário");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        painel = new JPanel();
        painel.setSize(300, 300);
        painel.setLayout(new GridBagLayout()); // centralizar os componentes de JPanel

        label = new JLabel("TELA PRINCIPAL");

        painel.add(label);

        add(painel);
        //setVisible(true);
    }
}
