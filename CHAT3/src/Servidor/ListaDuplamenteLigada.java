package Servidor;
import java.lang.reflect.*;

import Servidor.ListaDesordenada.No;
public class ListaDuplamenteLigada<X>
{
    protected class No
    {
        protected X  info;
        protected No prox;
        protected No ant;

        public X getInfo ()
        {
            return this.info;
        }

        public No getProx ()
        {
            return this.prox;
        } 

        public No getAnt()
        {
        	return this.ant;
        }

        public void setInfo (X x)
        {
            this.info=x;
        }

        public void setProx (No n)
        {
            this.prox=n;
        }

        public void setAnt(No n)
        {
        	this.ant = n;
        }

        public No (X x, No n, No a)
        {
            this.info=x;
            this.prox=n;
            this.ant=a;
        }

        public No(X x, No n)
        {
        	this(x,n,null);
        }

        public No (X x)
        {
            this (x,null,null);
        }

        public int hashCode()
        {
        	int ret = 23;
        	ret = ret * 13 + this.info.hashCode();

        	return ret;
        }

        public boolean equals(Object obj)
        {
        	if(obj == null)
        		return false;

        	if(this == obj)
        		return true;
        	
        	/*if(!(obj instanceof No))
        		return false;*/

        	No objeto = (No)obj;

			if(!(this.info == objeto.info))
				return false;

			/*if(!(this.ant.equals(objeto.ant)))
				return false;

			if(!(this.prox.equals(objeto.prox)))
				return false;*/

			return true;
        }
	}

	protected No prim;
	protected No ultimo;

	public ListaDuplamenteLigada()
	{
		this.prim = null;
		this.ultimo = null;
	}
	
	 public X getInfo(int i)
	 {
		No aux = this.prim;
		
		for(int j = 0; j <= i; j++)
		{
			aux = aux.getProx();
		}
		
		return aux.getInfo();    	    	
	}
	 
	
	public void jogueForaPrimeiro() throws Exception
	{ 
		if(this.prim == null)
			throw new Exception("Nao ha o que jogar fora.");

		No proxNoPrim = this.prim.getProx();
		this.prim = proxNoPrim;
		this.prim.setAnt(null);
	}
    
	public void jogueForaUltimo() throws Exception
	{
		if(this.prim == null)
			throw new Exception("Nao ha o que jogar fora.");
		
		No penultimo = this.ultimo.getAnt();
		this.ultimo = penultimo;
		this.ultimo.setProx(null);		
	}

	public void insiraNoInicio(X x) throws Exception
	{
		if(x == null)
			throw new Exception("Coloque um valor valido.");

		X info;
		if(x instanceof Cloneable)
			info = this.meuCloneDeX(x);
		else
			info = x;

		No novoNo = new No(info);

		if(this.prim == null){
			this.prim = novoNo;
			this.ultimo = novoNo;
		}
		else{
			this.prim.setAnt(novoNo);
			novoNo.setProx(this.prim);
			this.prim = novoNo;
		}
		System.out.println("Inseriu");
	}

	public void insiraNoFim(X x) throws Exception
	{
		if(x == null)
			throw new Exception("Valor invalido para insercao");

		X info;
		if(x instanceof Cloneable)
			info = this.meuCloneDeX(x);
		else
			info = x;

		No novoNo = new No(info);
		if(this.prim == null)
		{
			this.ultimo = novoNo;
			this.prim = novoNo;
		}
		else
		{
			novoNo.setAnt(this.ultimo);
			this.ultimo.setProx(novoNo);
			this.ultimo = novoNo;			
		}
	}
    //Insira no fim com recursao  
  /*  public void insiraNoFim(X x) throws Exception
    {
        if(x==null)
            throw new Exception("Valor invalido.");
            
        X info;
        if(x instanceof Cloneable)
            info = meuCloneDeX(x);
        else
            info = x;
        
        this.prim = insiraNoFim(this.prim, info);
    }*/

    private X meuCloneDeX(X x){
        X ret = null;
        try{
            Class<?> classe = x.getClass();
            Class<?>[] tipoParametroFormal = null; //porque clone tem 0 parametros
            Method metodo = classe.getMethod("Clone", tipoParametroFormal);
            Object[] tipoParametroReal = null; //porque clone tem 0 parametros
            ret = ((X)metodo.invoke(x, tipoParametroReal));
        }
        catch(Exception erro)
        {}  

        return ret;
    }

	//Metodos obrigatorios

	public String toString()
	{
		String ret = "<";
		No atual = this.prim;

		while(atual != null){
			ret += atual.getInfo() + (atual.getProx()!=null?",":"");			
			atual = atual.getProx();
		}

		ret += ">";

		return ret;
	}

	public int hashCode()
	{
		int ret = 23;
		ret = ret * 13 + this.prim.hashCode();
		ret = ret * 13 + this.ultimo.hashCode();
		No atual = this.prim;
		while(atual != null){
			ret = ret * 13 + atual.hashCode();
			atual = atual.getProx();			
		}
		
		return ret;
	}
	
	public boolean equals(Object obj)
	{
		if(obj == null)
			return false;
		
		if(this == obj)
			return true;
		
		if(!(obj instanceof ListaDuplamenteLigada))
			return false;
		
		ListaDuplamenteLigada lista = (ListaDuplamenteLigada)obj;

		if(!(lista.prim.equals(this.prim)))
			return false;

		if(!(lista.ultimo.equals(this.ultimo)))
			return false;

		//Comparar os conteudos da lista
		No aux = this.prim;
		No auxObj = lista.prim;

		while(aux!= null){
			if(!aux.equals(auxObj))
				return false;

			aux = aux.getProx();
			auxObj = auxObj.getProx();
		}

		return true;
	}

	/*public X oMaior(){
		No primeiro = this.prim;
		No segundo  = 
	}*/

	/*public ListaDuplamenteLigada<X> concatenacao(ListaDuplamenteLigada<X> lis)
	{
		ListaDuplamenteLigada<X> nova;
		No auxThis  = this.prim;
		No auxOutro = lis.prim;
		No list = new No(this.prim);

		while(auxThis != null)
		{
			nova.list.setProx(auxThis.getProx());
			auxThis = auxThis.getProx();
			list = list.getProx();
		}

		while(auxOutro != null)
		{
			nova.list.setProx(auxOutro);
			auxOutro = auxOutro.getProx();
			list = list.getProx();
		}

		return nova;
	}

	public ListaDuplamenteLigada(ListaDuplamenteLigada modelo) throws Exception
	{
		if(modelo == null)
			throw new Exception("Modelo invalido.");

		if(modelo.prim == null){
			this.prim = modelo.prim;
			this.ultimo = modelo.ultimo;
		}
		else{
			this.prim = modelo.prim;
			this.ultimo = modelo.ultimo;
			No aux = modelo.prim;
			No auxT = this.prim;
			while(aux!=null){
				auxT.setProx(aux.getProx());
				aux = aux.getProx();
				auxT = auxT.getProx();
			}
		}
	}

	public Object clone()
	{
		ListaDuplamenteLigada ret = null;
		try{
			ret = new ListaDuplamenteLigada(this);
		}
		catch(Exception erro)
		{}
		return ret;
	}*/
}