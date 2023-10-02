/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import Agentes.AgenteComprador;


/**
 *
 * @author kaize
 */

/*
public class InterfazComprador extends JFrame {
    private AgenteComprador myAgent; // se asocia comprador 
    private JTextField titleField; // campo de texto para ingresar el titulo
    
    //se construye interfaz grafica
    public InterfazComprador(AgenteComprador a){
        super(a.getLocalName());
        
        myAgent = a;
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel("Titulo de libro a buscar: "),BorderLayout.WEST);
        titleField = new JTextField(15);
        p.add(titleField, BorderLayout.CENTER);
        getContentPane().add(p,BorderLayout.CENTER);
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        
    }
    
    public void showGui(){
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) screenSize.getWidth()/2;
        int centerY = (int) screenSize.getHeight()/2;
        
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }
    
    // metodo para obtener el titulo del libro ingresado en interfaz
    public String getBookTitle(){
        return titleField.getText().trim();
    }
            
}*/
