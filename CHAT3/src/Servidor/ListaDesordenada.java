package Servidor;

import java.lang.reflect.*;

public class ListaDesordenada <X>
{
    protected class No
    {
        protected X  info;
        protected No prox;

        public X getInfo ()
        {
            return this.info;
        }

        public No getProx ()
        {
            return this.prox;
        }

        public void setInfo (X x)
        {
            this.info=x;
        }

        public void setProx (No n)
        {
            this.prox=n;
        }

        public No (X x, No n)
        {
            this.info=x;
            this.prox=n;
        }

        public No (X x)
        {
            this (x,null);
        }

        public int hashCode()
        {
            int ret = 23;

            ret = ret * 13 + info.hashCode();
            
            return ret;
        }

       public String toString()
        {
            String ret = "{";

            ret += "Info:"+this.info;
            ret += "Prox:"+this.prox;
	    
            ret += "}";
	    return ret;

        }
    }

    protected No prim;
    protected No ultimo;
    protected int quantosNos;

    	
    public ListaDesordenada ()
    {
        this.prim = null;
        this.ultimo = null;
        this.quantosNos = 0;
    }  
    
    public X getInfo(int i)
    {
    	No aux = this.prim;
    	
    	for(int j = 1; j <= i; j++)
    	{
    		aux = aux.getProx();
    	}
    	
    	return aux.getInfo();    	    	
    }
    
    public void tiraNo(X x, int j) //clonar e lancar exception
    {
    	No aux = this.prim;
    	
    	for(int i=0; i <= j; i++)
    	{
    		//Se a informacao eh igual ao do parametro a exclui-o
    		if(aux.getProx().getInfo().equals(x))
    		{
    			aux.setProx(aux.getProx().getProx());
    			return;
    		}
    		else
    			aux = aux.getProx();
    	}
    }
    
    protected X meuCloneDeX (X x)
    {
        X ret = null;

        try
        {
            Class<?> classe = x.getClass();
            Class<?>[] tipoDoParametroFormal = null; // pq clone tem 0 parametros
            Method metodo = classe.getMethod ("clone", tipoDoParametroFormal);
            Object[] parametroReal = null;// pq clone tem 0 parametros
            ret = ((X)metodo.invoke (x, parametroReal));
        }
        catch (NoSuchMethodException erro)
        {}
        catch (InvocationTargetException erro)
        {}
        catch (IllegalAccessException erro)
        {}

        return ret;
    }

    public void insiraNoInicio (X x) throws Exception
    {
        if (x==null)
            throw new Exception ("Informacao ausente");

        X info;
        if (x instanceof Cloneable)
            info = meuCloneDeX(x);
        else
            info = x;
        
        No novoNo = new No(info);

        if(this.prim == null) //se esta vazia
            this.ultimo = novoNo;

        novoNo.setProx(this.prim);
        this.prim = novoNo;
        quantosNos++;
    }

   /* public X getInfoPrim() throws Exception
    {
        if(this.prim == null)
            throw new Exception("Primeiro aponta para null");

        return(X)this.prim.getInfo();
    }*/

    public void insiraNoFim (X x) throws Exception
    {
        if (x==null)
            throw new Exception ("Informacao ausente");

        X info;
        if (x instanceof Cloneable)
            info = meuCloneDeX(x);
        else
            info = x;
        No novoNo = new No(info);

        if (this.prim==null)
            this.prim = novoNo; //o prim e o ultimo apontaram para o mesmo lugar
        else{
            this.ultimo.setProx(novoNo);
        }

        this.ultimo = novoNo;
        this.ultimo.setProx(null);
        quantosNos++;
    }

    public void jogueForaPrimeiro () throws Exception
    {
        if(this.prim==null)
            throw new Exception("Nao ha o que ser jogado fora");

        this.prim = this.prim.getProx();
        quantosNos--;
    }

    public void jogueForaUltimo () throws Exception
    {
        if(this.prim == null)
            throw new Exception("Lista vazia nao ha o que jogar fora.");

          //No noAnt = new No (anterior.getInfo(),null);
          //this.ultimo = anterior;
          this.ultimo.setProx(null);
          quantosNos--;     
    }

    public String toString ()
    {
        String ret   = "{";
        No     atual = this.prim;

        while (atual!=null)
        {
            ret += atual.getInfo();

            if (atual.getProx()!=null) // se atual nao é o ultimo
                ret += ",";

            atual = atual.getProx();
        }

        ret += "}";

        return ret;
    }

    public int hashCode ()
    {
        int ret = 23;

        ret = ret * 13 + this.prim.hashCode();

        No atual = this.prim;

        while(atual!=null){
            ret = ret * 13 + atual.hashCode();             
            atual = atual.getProx();
        }
        
        return ret;
    }

	public ListaDesordenada<String> menos(ListaDesordenada<String> nick, int qtdUsuarios) {
		// TODO Auto-generated method stub
		return null;
	}

  
}