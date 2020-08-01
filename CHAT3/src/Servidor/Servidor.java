package Servidor;

// pensar em como derrubar o servidor, caso desejado,
// exigindo confirma«’ES
import java.net.*;
import java.io.*;
public class Servidor
{
    public static void main (String[] args)
    {
    	try{
	        Salas        salas  = new Salas ();
	        salas.novaSala("Dinalva");
	        salas.novaSala("Claudio");
	        salas.novaSala("Lapa");
	        ServerSocket pedido = new ServerSocket (12345);
	
	        System.out.println ("Servidor operante...");
	        System.out.println ("De o comando 'shutdown' para derrubar o servidor");
	
	        for(;;)
	        {
	            Socket conexao = pedido.accept();
	            System.out.println("Conectado...");
	            new TratadorDeConexao(salas,conexao).start();
	        }
    	}
    	catch(Exception e)
    	{
    		System.out.println("Erro"+e.getMessage());
    		e.printStackTrace();
    	}
    }
}