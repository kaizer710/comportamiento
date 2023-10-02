/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Agentes.AgenteVendedorL; //se imprta libreria agente vendedor


/**
 *
 * @author kaize
 */


public class InterfazVendedor extends JFrame{
    private AgenteVendedorL myAgent; //se crea el agente vendedor y se asocia
    private JTextField titleFileld, priceField; //se crean variables para ingresar titulo y precio
    
    //constructor de interfas grafica
    public InterfazVendedor(AgenteVendedorL a){
        super(a.getLocalName());
        myAgent = a;
        
        JPanel p= new JPanel();
        p.setLayout(new GridLayout(2,2));
        p.add(new JLabel("Titulo del Libro:"));
        titleFileld = new JTextField(15);
        p.add(titleFileld);
        p.add(new JLabel("Precio"));
        priceField =new JTextField(15);
        p.add(priceField);
        getContentPane().add(p,BorderLayout.CENTER);
        
        JButton addButton = new JButton("Agregar");
        addButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                try{
                    String title = titleFileld.getText().trim();
                    String precie = priceField.getText().trim();
                    myAgent.updateCatalogue(title, Integer.parseInt(precie)); //actualiza catalogo en agente Vendedor
                    titleFileld.setText("");
                    priceField.setText("");
                    JOptionPane.showMessageDialog(InterfazVendedor.this, "Libro Agregado", "Confirmar",JOptionPane.INFORMATION_MESSAGE);
                } catch(Exception e){
                    JOptionPane.showMessageDialog(InterfazVendedor.this, "Valores Invalidos","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        p = new JPanel();
        p.add(addButton);
        getContentPane().add(p,BorderLayout.SOUTH);
        
        //manejo de eliminaciòn del agente
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e){
                myAgent.doDelete();
            }
        }
        );
        setResizable(false);
    }
    
    //Mostrar interfaz grafica
    public void showGui(){
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) screenSize.getWidth() /2;
        int centerY = (int) screenSize.getHeight()/2;
        
        setLocation(centerX - getWidth() /2, centerY - getHeight()/2);
        super.setVisible(true);
    }
}


