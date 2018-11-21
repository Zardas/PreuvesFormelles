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
	//@requires tache(this, ?tn, _);
	//@ensures tache(this, tn, _) &*& result == tn;
	{
		return this.temps_necessaire;
	}
	
	public int get_gain()
	//@requires tache(this, _, ?g);
	//@ensures tache(this, _, g) &*& result == g;
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
	
	public Travailleur(int temps_dispo, int salaire_horaire)
	//@requires salaire_horaire >= 0;
	//@ensures travailleur(this, temps_dispo, salaire_horaire, 0);
	{
		this.temps_dispo = temps_dispo;
		this.salaire_horaire = salaire_horaire;
		this.salaire_percu = 0;
	}
	
	public int get_temps_dispo()
	//@requires travailleur(this, ?td, _, _);
	//@ensures travailleur(this, td, _, _) &*& result == td;
	{
		return this.temps_dispo;
	}
	
	public int get_salaire_horaire()
	//@requires travailleur(this, _, ?sh, _);
	//@ensures travailleur(this, _, sh, _) &*& result == sh;
	{
		return this.salaire_horaire;
	}
	
	public int get_salaire_percu()
	//@requires travailleur(this, _, _, ?sp);
	//@ensures travailleur(this, _, _, sp) &*& result == sp;
	{
		return this.salaire_percu;
	}
	
	public int travailler(int t)
	//@requires travailleur(this, _, ?sh, ?sp) &*& t >= 0;
	//@ensures travailleur(this, _, sh, sp+sh*t);
	{
		this.salaire_percu += this.salaire_horaire*t;
		return this.salaire_horaire*t;
	}
}