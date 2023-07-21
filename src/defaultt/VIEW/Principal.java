package defaultt.VIEW;

import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;




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

        label = new JLabel("Tela Principal");

        painel.add(label);

        add(painel);
        //setVisible(true);

        
    }

    
}
