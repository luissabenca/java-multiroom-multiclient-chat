package Servidor;

import java.io.Serializable;
import java.net.Socket;
import Servidor.Sala;
import Servidor.Usuario;
import Servidor.Salas;

public class TratadorDeConexao extends Thread
{
    protected Sala                     sala;
    protected String nick;  //E pra ser uma lista ordenada
    protected Usuario                  usuario;
    protected boolean              	   fim=false;
    protected Texto                    texto;

    public void pare()
    {
        this.fim=true;
    }

    public void run ()
    {
        while (this.fim == false)
        {
          try {
        	  
        	/*  String[] nicksAtuais =  this.sala.getNicks(); 			//pega os nicks das pessoas daquela determinada sala
      	   	  String[] entrou      =  new String[nicksAtuais.length-1]; //tera um a menos que nicksAtuais, pois tirarei 1 do vetor
      	   																//Que no caso e o this.nick que sera retirado   
                 
           for(int i = 0; i < nicksAtuais.length-1; i++)
      	   {
      		   if(nicksAtuais[i] != this.nick)
      		   {
      			   entrou[i] = nicksAtuais[i];
      		   }
      	   }
           //FALTA A PARTE DO SAIUUUUUUU
      	   
           // para cada string s da lista entrou
           // this.usuario.envia("+") e this.usuario.envia(s)
           // para cada string s da lista saiu
           // this.usuario.envia("-") e this.usuario.envia(s)
      	   
      	  for(int i = 0; i <= entrou.length-1; i++) {      	   
      		  this.usuario.envia(new Texto("entrou",entrou[i]));      		     
      	   }*/
     
      	   // receber de this.usuario 1 comando do protocolo
           // com complementos (pode ser "SAI", se usuario quer
           // sair, "MSG"+"DESTINATARIO"+"TEXTO" se quer mandar
           // mensagem, ou "MSG"+"REMENTENTE"+"TEXTO"      	   	
           // tratar o "SAI" (parar a thread e tirar da sala) ou
           // (destinatario pode ser 1 especifico ou TODOS) e      	 
      		  
	      	   texto =(Texto)this.usuario.receptor.readObject(); //ler o que veio do usuario
	      	   
	      	   //if(texto.getTipo().equals("entrou"))
	      	   
	      	  
	      	   if(texto.getTipo().equals("msg"))
	      	   {
		           if (texto.getComplemento2().equals("Todos")) 
		           { //comp3 e o destinatario
		               // loop para mandar para cada nick i em nicksAtuais
		        	   String[] nicksAtuais = this.sala.getNicks();
		        	   for(int i = 0; i <= nicksAtuais.length-1; i++){
			               Usuario usr = this.sala.getUsuario (nicksAtuais[i]);
			               usr.envia(new Texto("msg",texto.getComplemento1(),texto.getComplemento3(),null,null));
		        	   }
		           }		           
		           else 
		           {		        	
		               Usuario usr = this.sala.getUsuario((String)texto.getComplemento2());		        
		               usr.envia(new Texto("msg",texto.getComplemento1(),texto.getComplemento3(),null,null)); // as letras M, S e G        		              
		           }
		      }
	      	  else
      		  if(texto.getTipo().equals("sair"))
      		  {
      			  //ele mandou sai, ai paramos a thread e tiramos da sala
      			  this.sala.excluirUsuario(this.usuario);
      			  pare();
      		  }
    
         }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
      	   
      }
    }

    public TratadorDeConexao (Salas salas, Socket conexao) throws Exception
    {
    	try {
	        String[] nomesSalas = salas.getNomesSalas();
	        //no constructor do usuario mando a lista de salas e ele a escolhee
	        this.usuario = new Usuario (nomesSalas, conexao); 	 
	        String nomeSala = this.usuario.getNomeSala();
	        this.sala       = salas.getSala(nomeSala);
	        this.sala.entra(this.usuario);    
	        this.nick = this.usuario.getNick();	
	        
	        //mandar para todos clientes os nicks dos usuarios da sala;
	        
	        String[] nicksAtuais = this.sala.getNicks();
     	    for(int i = 0; i <= nicksAtuais.length-1; i++)
     	    {
	               Usuario usr = this.sala.getUsuario (nicksAtuais[i]);
	               usr.envia(new Texto("nicks",this.sala.getNicks(),this.nick,null,null));
     	    }
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
}