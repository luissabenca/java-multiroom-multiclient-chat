package Servidor;

public class Salas
{
    protected int                    qtdSalas=0;
    protected ListaDesordenada<Sala> salas;

    public Salas()
    {
    	this.salas = new ListaDesordenada<Sala>();	
    }
    
    public void novaSala (String nome) throws Exception
    {
    	if(nome.equals(""))
    		throw new Exception("Parametro invalido.");
    	
    	if(nome == null)
    		throw new Exception("Insira o parametro em novaSala().");
    	
    	Sala s = new Sala(nome,0);
    	salas.insiraNoFim(s);
    	qtdSalas++;
    }
    
    public void novoUsuario (String nomSal, Usuario u) throws Exception{
    	if(nomSal.equals(""))
    		throw new Exception("Nicks invalidos.");
    	
    	if(nomSal == null || u == null)
    		throw new Exception("Sem parametros :/, por favor os coloque");
    	
    	//Achar tal sala na lista(a partir do nomSal) CHAMANDO O METODO GETSALA e quando acha-la inserir este novo usuario nela
    	 Sala s = getSala(nomSal);
    	 s.entra(u);
   }

    public Sala getSala (String nome) throws Exception{
    	if(nome == null)
    		throw new Exception("Coloque o parametro :)");
    	if(nome.equals(""))
    		throw new Exception("Parametro invalido.");
    	
    	for(int i = 0; i <= qtdSalas-1; i++) {
    		Sala s = salas.getInfo(i);
    		if(s.getNome().equals(nome))
    		{
    			return s;
    		}
    	}
    	
    	//Caso chegue aqui nao achou a sala
    	System.out.println("Não se tem sala com este nome");
    	return null;    	
    }
    
    public String [] getNomesSalas() throws Exception{
    	//Com o for percorrer a lista e ir pegando o nome das salas
    	String[] nomesSalas = new String[qtdSalas];
    	
    	if(qtdSalas > 0)
    	{
	    	for(int i = 0; i <= qtdSalas-1; i++)
	    	{
	    		nomesSalas[i] = salas.getInfo(i).getNome();	  		
	    	}
	    	return nomesSalas;
    	}
    	else
    		throw new Exception("Nao ha salas, logo sem nomes para exibicao");  
    	    	
    }
    
    public Usuario[] getUsuariosSala (String nomSal) throws Exception {
    	if(nomSal == null || nomSal.equals(""))
    		throw new Exception("Parametro invalido");
    	
    	if(getSala(nomSal) != null)
	    	{
	    	//Saber qual e a sala indicada
	    	Sala s = getSala(nomSal);
	    	
	    	//Apartir daqui pegar na tal salas todos os usuarios e colocar no vetor de 
	    	//Usuarios
	    	if(s.getQtdUsuarios() != 0) {
	    		Usuario[] u = new Usuario[s.getQtdUsuarios()];    	
	    		for(int i = 0; i <= s.qtdUsuarios-1; i++)
	    		{
	    			u[i] = s.usuarios.getInfo(i);
	    			//Usar o metodo getInfo(i) da lista, para pegar o usuario 
    			}
	    		return u;
	    	}
	    	else
	    		return null; 
    	}	
    	else
    		throw new Exception("Nao ha sala com este nome");     	
    }
    
    public Usuario getUsuario(String nomSal, String nicUsr) throws Exception {
    	//PARA AUXILIAR USAR O METODO GETSALA(NOMECLASS) DESTA CLASSE E O GETUSUARIO(NICK)
    	if(nomSal.equals("") || nicUsr.equals(""))
    		throw new Exception("Parametro invalido.");
    	
    	if(getSala(nomSal) != null)
    	{
    		//1) achar a sala
    		Sala s = this.getSala(nomSal);
	    		if(s.getUsuario(nicUsr) != null) {
	    			//2) instanciar um usuario, usando o metodo da classe sala
	    			Usuario u = s.getUsuario(nicUsr);
	    			return u;
	    		}
	    		else
	    			throw new Exception("Nick nao e valido :/");
    	}
    	else
    		throw new Exception("Nao ha sala para este parametro :/");
    	
    }
    
    public void tiraUsuario (String nomSal, String nicUsr) throws Exception {
    	if(nomSal.equals("")|| nicUsr.equals(""))
    		throw new Exception("Parametros invalidos :(");
    	
    	if(this.getSala(nomSal) != null)
    	{
    	//1) Achar a sala
    		Sala s = this.getSala(nomSal); 		
    
    		if(s.getUsuario(nicUsr) != null)
    		{
    			//2) Achar qual o usuario a partir do nick, usando o metodo GetUsuario da tal sala
        		Usuario u = s.getUsuario(nicUsr);
        		//3) Percorrer a lista para achar o tal usuario e exclui-lo da lista
    			s.excluirUsuario(u);
    		}
    	}
    	else 
    		throw new Exception("Nao ha sala com este nome :(");    	
    }
  
    public void fechaSala (String nome) throws Exception {
    	if(nome.equals("") || nome == null)
    		throw new Exception("Parametro invalido para nome da sala :/");
    	
    	if(this.getSala(nome) != null)
    	{
    		//Usar a mesma logica do retira usuario
    		Sala s = this.getSala(nome);
    		for(int i = 0; i <= this.qtdSalas-1; i++)
    		{
    			salas.tiraNo(s, i);
    		}
    	}
    	else
    		throw new Exception("Nao ha sala com este nome");
    }

    // metodos obrigatorios, claro
}
