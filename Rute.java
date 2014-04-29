
abstract class Rute {

    protected Brett brett;
    private Kolonne kolonne;
    private Boks boks;
    private Rad rad;
    private int tall;
    protected Rute neste;
    protected Rute forrige;
    private int grense;
    private boolean[] muligeVerdier;
    
    
    Rute(Rad rad, Kolonne kol, Boks boks, int verdi, Brett brett) {
	
	this.boks = boks;
	kolonne = kol;
	this.rad = rad;
	tall = verdi;
	this.brett = brett;
	grense = brett.getGrense();
	muligeVerdier = new boolean[grense+1];

	rad.leggTilRute(this);
	boks.leggTilRute(this);
	kolonne.leggTilRute(this);
	
	//finner hvilke verdier som kan vaere i ruten
	for (int i = 1; i <= grense; i++) {
	    if (boks.tallPasser(i) && rad.tallPasser(i) && kolonne.tallPasser(i)) {
		muligeVerdier[i] = true;
	    }
	}
    }

    /*metoden finner et tall som passer i ruten og kaller saa paa neste rutes
      metode. Om den siste ruten finner et tall som passer, lages en kopi av
      brettet slik det ser ut da, som saa legges i sudokubeholderen*/
    public SudokuBeholder fyllUtRestenAvBrettet(SudokuBeholder loesninger) {
	for (int i = 1; i <= grense; i++) {
	    if (muligeVerdier[i]) {		
		settInnTall(i);
		if (neste == null) {
		    loesninger.add(brett.copy());
		}
		else {
		    neste.fyllUtRestenAvBrettet(loesninger);
		}
		//proever neste tall
		taUtTall(i);
	    }
	}
	//proevd alle tall
	return loesninger;
    }
    
    public void setNeste(Rute r) {
	neste = r;
    }

    public void setForrige(Rute r) {
	forrige = r;
    }

    public void settInnTall(int tall) {
	this.tall = tall;
	boks.leggTilTall(tall);
	rad.leggTilTall(tall);
	kolonne.leggTilTall(tall);
    }

    public void taUtTall(int tall) {
	this.tall = -1;
	boks.taUtTall(tall);
	rad.taUtTall(tall);
	kolonne.taUtTall(tall);
    }

    public int hentTall() {
	return tall;
    }
    
    //returnerer streng av tallet som er i ruten, bokstav om over 9
    public String getInnhold() {
	String retur = "";
	String alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ?";
	
	if (this.tall > 9) {
	    retur = String.valueOf(alfabet.charAt(this.tall - 10));
	}
	else {
	    retur = String.valueOf(this.tall);
	}

	return retur;
    }

    public void ulovligTall(int tall) {
	muligeVerdier[tall] = false;
    }

    public void lovligTall(int tall) {
	if (boks.tallPasser(tall) && rad.tallPasser(tall) && kolonne.tallPasser(tall)) {
	    muligeVerdier[tall] = true;
	}
    }

    public boolean enMulighet() {
	int teller = 0;
	for (int i = 1; i < muligeVerdier.length; i++) {
	    if (muligeVerdier[i]) {
		teller++;
	    }
	}
	return teller == 1;
    }

    //kalles naar det bare finnes en mulighet for ruten
    public UtfyltRute fyllUtRute() {
	int tall = 0;
	boks.fjern(this);
	rad.fjern(this);
	kolonne.fjern(this);

	for (int i = 1; i <= grense; i++) {
	    if (muligeVerdier[i]) {
		tall = i;
		break;
	    }
	}

	UtfyltRute nyRute = new UtfyltRute(rad, kolonne, boks, tall, brett);
	nyRute.settInnTall(tall);
	nyRute.setNeste(neste);
	nyRute.setForrige(forrige);
	forrige.setNeste(nyRute);
	if (neste != null) {
	    neste.setForrige(nyRute);
	}
	brett.erstatt(this, nyRute);
	return nyRute;
    }

    
    public void fyllUtRute(int tall) {
	boks.fjern(this);
	rad.fjern(this);
	kolonne.fjern(this);

	UtfyltRute nyRute = new UtfyltRute(rad, kolonne, boks, tall, brett);
	nyRute.settInnTall(tall);
	nyRute.setNeste(neste);
	nyRute.setForrige(forrige);
	if (neste != null) {
	    neste.setForrige(nyRute);
	}
	forrige.setNeste(nyRute);
	brett.erstatt(this, nyRute);
    }

    public boolean erLovligTall(int tall) {
	return muligeVerdier[tall];
    }

    public Rad getRad() {
	return rad;
    }

    public Kolonne getKolonne() {
	return kolonne;
    }
    
    public Boks getBoks() {
	return boks;
    }

    public boolean erI(AbstraktTallBeholder beholder) {
	return boks == beholder || rad == beholder || kolonne == beholder;
    }
}