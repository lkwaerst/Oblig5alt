//-1 for tomme plasser
abstract class AbstraktTallBeholder {

    private int[] tallSamling;
    private Rute[] ruter;

    AbstraktTallBeholder(int[] tall) {
	ruter = new Rute[tall.length];
	tallSamling = tall;
    }

    public void settInn(int tall) {
	for (int i = 0; i < tallSamling.length; i++) {
	    if (tallSamling[i] == -1) {
		tallSamling[i] = tall;
		return;
	    }
	}
    }

    public void taUt(int tall) {
	for (int i = 0; i < tallSamling.length; i++) {
	    if (tallSamling[i] == tall) {
		tallSamling[i] = -1;
		return;
	    }
	}
    }
    
    //true om tallet ikke finnes i beholderen allerede
    public boolean tallPasser(int tall) {
	for (int i = 0; i < tallSamling.length; i++) {
	    if (tallSamling[i] == tall) {
		return false;
	    }
	}
	return true;
    }

    public void leggTilRute(Rute r) {
	for (int i = 0; i < ruter.length; i++) {
	    if (ruter[i] == null) {
		ruter[i] = r;
		return;
	    }
	}
    }

    //setter inn tallet og gjoer det ulovlig i alle rutene
    public void leggTilTall(int tall) {
	settInn(tall);
	for (Rute r : ruter) {
	    r.ulovligTall(tall);	    
	}
    }
    
    //tar ut tall og gjoer det lovlig i alle ruter som kan
    public void taUtTall(int tall) {
	taUt(tall);
	for (Rute r : ruter) {
	    if (r instanceof AapenRute) {
 		r.lovligTall(tall);
	    }
	}
    }

    public Rute[] getRuter() {
	return ruter;
    }

    //fjerner rute fra beholderen
    public void fjern(Rute r) {
	for (int i = 0; i < ruter.length; i++) {
	    if (ruter[i] == r) {
		ruter[i] = null;
		break;
	    }
	}
    }

    public boolean loes() {
	//lager array der tallene er paa indeksen til verdien sin
	int[] verdier = new int[tallSamling.length+1];
	for (int i = 0; i < tallSamling.length; i++) {
	    if (tallSamling[i] != -1) {
		verdier[tallSamling[i]] = tallSamling[i];
	    }
	}
	    
	boolean fremgang = false;

	for (int i = 1; i < verdier.length; i++) {
	    if (verdier[i] == 0) {   //om tallet ikke finnes i beholderen enda
		Rute[] retur = proev(i);
		if (retur.length == 1) {  //en mulighet
		    retur[0].fyllUtRute(i);
		    fremgang = true;
		}
	    }
	}

	return fremgang;
    }

    //returnerer en array med alle rutene som har tallet som lovlig verdi
    public Rute[] proev(int tall) {
	Rute[] lovligeRuter;
	int teller = 0;
	for (Rute r : ruter) {
	    if (r.erLovligTall(tall)) {
		teller++;
	    }
	}
	lovligeRuter = new Rute[teller];
	int i = 0;

	for (Rute r : ruter) {
	    if (r.erLovligTall(tall)) {
		lovligeRuter[i] = r;
		i++;
	    }
	}
	return lovligeRuter;
    }

    //overskrives i subklasser
    public boolean sjekkTall(int tall) {
	System.out.println("her har det skjedd noe gitt");
	return false;
    }

    /*Fjerner tallet som alternativ i alle ruter bortset fra de i tallbeholderen
      som sendes med, returnerer true om muligheter fjernes*/
    public boolean ulovligTall(int tall, AbstraktTallBeholder beholder) {
	boolean fremgang = false;
	for (Rute r : ruter) {
	    if (r.erLovligTall(tall) && !r.erI(beholder)) {
		r.ulovligTall(tall);
		fremgang = true;
	    }
	}
	return fremgang;
    }

    //setter tall som lovlig i alle ruter
    public void lovligTall(int tall) {
	for (Rute r : ruter) {
	    r.lovligTall(tall);
	}
    }

    //mye av det samme som loes(), men fjerner muligheter dersom det er mulig
    public boolean fjernMuligheter() {
	int[] verdier = new int[tallSamling.length+1];
	for (int i = 0; i < tallSamling.length; i++) {
	    if (tallSamling[i] != -1) {
		verdier[tallSamling[i]] = tallSamling[i];
	    }
	}
	    
	boolean fremgang = false;

	for (int i = 1; i < verdier.length; i++) {
	    if (verdier[i] == 0 && sjekkTall(i)) {
		fremgang = true;
	    }
	}

	return fremgang;
    }
}