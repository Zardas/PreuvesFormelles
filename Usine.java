/*@predicate tache(Tache tache; int temps_necessaire, int gain) =
	tache.temps_necessaire |-> temps_necessaire &*&
	tache.gain |-> gain &*&
	temps_necessaire >= 0 &*& gain >= 0;
@*/
class Tache {
	private int temps_necessaire;
	private int gain;
	
	public Tache(int temps_necessaire, int gain)
	//@requires temps_necessaire >= 0 && gain >= 0;
	//@ensures tache(this, temps_necessaire, gain);
	{
		this.temps_necessaire = temps_necessaire;
		this.gain = gain;
	}
	
	public int get_temps_necessaire()
	//@requires tache(this, ?tn, ?gain);
	//@ensures tache(this, tn, gain) &*& result == tn;
	{
		return this.temps_necessaire;
	}
	
	public int get_gain()
	//@requires tache(this, ?tn, ?g);
	//@ensures tache(this, tn, g) &*& result == g;
	{
		return this.gain;
	}
}

/*@predicate travailleur(Travailleur travailleur; int temps_dispo, int salaire_horaire, int salaire_percu) = 
	travailleur.temps_dispo |-> temps_dispo &*&
	travailleur.salaire_horaire |-> salaire_horaire &*&
	travailleur.salaire_percu |-> salaire_percu;
@*/
class Travailleur {
	private int temps_dispo;
	private int salaire_horaire;
	private int salaire_percu;
	
	//private int temps_travail; //TODO : question 6
	
	public Travailleur(int temps_dispo, int salaire_horaire)
	//@requires salaire_horaire >= 0;
	//@ensures travailleur(this, temps_dispo, salaire_horaire, 0);
	{
		this.temps_dispo = temps_dispo;
		this.salaire_horaire = salaire_horaire;
		this.salaire_percu = 0;
	}
	
	public int get_temps_dispo()
	//@requires travailleur(this, ?td, ?sh, ?sp);
	//@ensures travailleur(this, td, sh, sp) &*& result == td;
	{
		return this.temps_dispo;
	}
	
	public int get_salaire_horaire()
	//@requires travailleur(this, ?td, ?sh, ?sp);
	//@ensures travailleur(this, td, sh, sp) &*& result == sh;
	{
		return this.salaire_horaire;
	}
	
	public int get_salaire_percu()
	//@requires travailleur(this, ?td, ?sh, ?sp);
	//@ensures travailleur(this, td, sh, sp) &*& result == sp;
	{
		return this.salaire_percu;
	}
	
	public int travailler(int t)
	//@requires travailleur(this, ?td, ?sh, ?sp) &*& t >= 0;
	//@ensures travailleur(this, td, sh, sp+sh*t) &*& result == sh*t;
	{
		this.salaire_percu = this.salaire_percu + (this.salaire_horaire*t);
		return (this.salaire_horaire*t);
	}
	
	public void setTempsDispo(int t)
	//@requires travailleur(this, ?td, ?sh, ?sp);
	//@ensures travailleur(this, t, sh, sp);
	{
		this.temps_dispo = t;
	}
	  
}



/*@predicate usine(Usine usine; int balance) = usine.balance |-> balance;
@*/
class Usine {
    private int balance;
    
    public Usine(int depot_initial)
    //@requires depot_initial >= 0;
    //@ensures usine(this, depot_initial);
    {
        this.balance = depot_initial;
    }
    
    public int get_balance()
    //@requires usine(this, ?di);
    //@ensures usine(this, di) &*& result == di;
    {
        return this.balance;
    }
    
    public void depose_argent(int argent)
    //@requires usine(this, ?di);
    //@ensures usine(this, di+argent);
    {
        this.balance += argent;
    }
    
    public boolean effectueTache(Tache tache, Travailleur travailleur)
    //@requires tache(tache, ?n, ?g) &*& travailleur(travailleur, ?td, ?sh, ?sp) &*& usine(this, ?di);
    //@ensures true;
    {   
    	//@open tache(tache, n, g);
    	//@open travailleur(travailleur, td, sh, sp); 
        int temps_dispo = travailleur.get_temps_dispo();
        int temps_nece = tache.get_temps_necessaire();
        
        if(temps_dispo-temps_nece >=0) //Si il reste suffisamment de temps disponible pour que le travailleur travaille sur sa tache
        {
            depose_argent(tache.get_gain());
            depose_argent(-(travailleur.travailler(tache.get_temps_necessaire())));
            
            travailleur.setTempsDispo(temps_dispo - temps_nece);
            return true;
            
        } else {
            return false;
        }
    }
    
}


class UsineTest {

	public static void main(String[] args)
	//@requires true;
	//@ensures true;
	{
		Tache tache = new Tache(2,50);
		Travailleur travailleur  = new Travailleur(35,15);
		Usine usine = new Usine(50);
		
		//Test de tache
		int temps_necessaire = tache.get_temps_necessaire();
		assert temps_necessaire == 2;
		int gain = tache.get_gain();
		assert gain == 50;
		
		//Test de travailleur
		int temps_dispo = travailleur.get_temps_dispo();
		assert temps_dispo == 35;
		int salaire_horaire = travailleur.get_salaire_horaire();
		assert salaire_horaire == 15;
		int salaire_percu = travailleur.get_salaire_percu();
		assert salaire_percu == 0;
		
		int travail = travailleur.travailler(10);
		assert travail == 150;
		salaire_percu = travailleur.get_salaire_percu();
		assert salaire_percu == 150;
		
		
		
		
	}
}