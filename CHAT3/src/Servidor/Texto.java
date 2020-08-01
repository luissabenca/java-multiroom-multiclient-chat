package Servidor;

import java.io.Serializable;

//O servidor ou o cliente pode mandar algo versatil, para identificar a quais 
//valores se identificam o comp1,comp2 e comp3 verificaremos o tipo, exemplo,
//caso seja tipo=="Lista_de_Salas", isso diz que os comps correspondem ao remetente
//destinatario e nick do usuario

public class Texto implements Serializable{
	
	private String tipo; //Mensagem
	private Serializable comp1, comp2, comp3, comp4;
	
	public Texto( String t, Serializable c1, Serializable c2, Serializable c3, Serializable c4) throws Exception
	{
		if( t.equals(""))
			throw new Exception("Valores invalidos(class Texto)");
		
		this.tipo = t;
		this.comp1 = c1;
		this.comp2 = c2;
		this.comp3 = c3;
		this.comp4 = c4;
	}
	
	public Serializable getComp4() {
		return comp4;
	}

	public void setComp4(Serializable comp4) {
		this.comp4 = comp4;
	}

	public Texto(String t, Serializable d) throws Exception 
	{		
		if(t.equals(""))
			throw new Exception("tipo invalido");
		
		this.tipo = t;
		this.comp1 = d;
		this.comp2 = null;
		this.comp3 = null;
	}
	public Serializable getComp1()
	{
		return this.comp1;
	}
	//getters and setters
	
	public boolean equals(Object obj)
	{
		if(obj == null)
			return false;
		
		if(this == obj)
			return true;
		
		if(!(obj instanceof Texto))
			return false;
		
		Texto txt = (Texto)obj;	
		
		if(!(this.tipo == txt.tipo))
			return false;
		
		if(!(this.comp1 == txt.comp1))
			return false;
		
		if(!(this.comp2 == txt.comp2))
			return false;
		
		if(!(this.comp3 == txt.comp3))
			return false;
		
		return true;		
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Serializable getComplemento1() {
		return comp1;
	}

	public void setComplemento1(Serializable complemento1)throws Exception{
		if(complemento1==null)
			throw new Exception("Comp 1 errado.");
		
		this.comp1 = complemento1;
	}

	public Serializable getComplemento2() {
		return comp2;
	}

	public void setComplemento2(Serializable complemento2)throws Exception{
		if(complemento2==null)
			throw new Exception("Comp 2 invalido");
		
		this.comp2 = complemento2;
	}

	public Serializable getComplemento3() {
		return comp3;
	}

	public void setComplemento3(Serializable complemento3)throws Exception{
		if(complemento3 == null)
			throw new Exception("Comp 3 invalido");
		
		this.comp3 = complemento3;
	}
	
}

