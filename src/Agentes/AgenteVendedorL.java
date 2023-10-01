/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Agentes;
import java.util.Hashtable;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import gui.InterfazVendedor;
import Behaviours.ServidorOferta;
import Behaviours.servidorCompra;
/**
 *
 * @author kaize
 */
public class AgenteVendedorL extends Agent{
    //variables dentro de clase
    private Hashtable catalogue; // almacenamos lista catalogo libros
    private InterfazVendedor gui; // creamos interfaz grafica para el agente vendedor
    
    //creamos metodo del agente
    protected void setup(){
        catalogue = new Hashtable(); //se crea tabla has para catalogo
        
        gui = new InterfazVendedor(this);
        gui.showGui(); //muestra interfaz grafica de interfaz vendedor
        
        // Confugura la descripciòn del agente para el registro en el servicio de directorio
        
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        
        ServiceDescription sd = new ServiceDescription();
        //definiciòn de servicios
        sd.setType("book-selling");
        sd.setType("book-trading");
        dfd.addServices(sd);
        
        try{
            DFService.register(this,dfd); // Registra el agente en el servicio de directorio
        } catch(FIPAException fe){
            fe.printStackTrace();
        }
        
        //agregamos solititudes y ordenes compra
        
        //falta crear metodos comportamiento por eso da error
        addBehaviour(new ServidorOferta (this));
        addBehaviour(new servidorCompra (this));
    }
    
    //finalizando agente
    protected void takeDown(){
        try{
            DFService.deregister(this);//baja de agente en el directorio
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        gui.dispose();// cierre interfaz grafica
        System.out.println("El agente vendedor " + getAID().getName() + " ah finalizado");
    }
    
    //actualizaciòn de catalogo
    public void updateCatalogue(final String title, final int price){
        addBehaviour(new OneShotBehaviour(){
            public void action(){
                catalogue.put(title, price);//se agregan vartiables a catalogo
                System.out.println(title + " agregado con precio de "+ price);
            }
            
        });
    }
    
    //se obtiene catalogo de libros
    public Hashtable obtenerCatalogo(){
        return catalogue;
    }
    
    
    
}
