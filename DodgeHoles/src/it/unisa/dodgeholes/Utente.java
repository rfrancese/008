package it.unisa.dodgeholes;

public class Utente
{
	private String nickname;
	private double punteggio;
	private String livello;
	
	public Utente()
	{
	}
	
	public Utente(String nickname,String livello,double punteggio)
	{
		this.nickname=nickname;
		this.punteggio=punteggio;
		this.livello=livello;
	}

	
	public String getNickname()
	{
		return this.nickname;
	}
	
	public String getLivello()
	{
		return this.livello;
	}
	
	public double getPunteggio()
	{
			return this.punteggio;
	}
	
	public void setPunteggio(double punteggio)
	{
		this.punteggio=punteggio;
	}
}
