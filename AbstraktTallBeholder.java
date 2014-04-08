//-1 for tomme plasser
abstract class AbstraktTallBeholder {

    int[] tallSamling;
    Rute[] ruter;
    public static int a = 0;
    public int nr = 0;

    AbstraktTallBeholder(int[] tall) {
	nr = a++;
	ruter = new Rute[tall.length];
	tallSamling = tall;
	
// 	for (int i = 0; i < tall.length; i++) {
// 	    try {
// 		tallSamling[tall[i]] = tall[i];
// 	    }
// 	    catch (ArrayIndexOutOfBoundsException e) {
// 		tallSamling[i] = 0;
// 	    }
		
// 	}
// 	tallSamling = tall;
    }

    public void settInn(int tall) {
	for (int i = 0; i < tallSamling.length; i++) {
	    if (tallSamling[i] == -1) {
		tallSamling[i] = tall;
		return;
	    }
	}
// 	if (tallSamling[tall] != 0) {
// 	    System.out.println("au da");
// 	    //System.exit(0);
// 	}
// 	tallSamling[tall] = tall;
    }

    public void taUt(int tall) {
	for (int i = 0; i < tallSamling.length; i++) {
	    if (tallSamling[i] == tall) {
		tallSamling[i] = -1;
		return;
	    }
	}
    }
    
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

    public void leggTilTall(int tall) {
	settInn(tall);
	for (Rute r : ruter) {
	    r.ulovligTall(tall);	    
	}
    }
    
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

    public void fjern(Rute r) {
	for (int i = 0; i < ruter.length; i++) {
	    if (ruter[i] == r) {
		ruter[i] = null;
		break;
	    }
	}
    }

    public boolean loes() {
	int[] verdier = new int[tallSamling.length+1];
	for (int i = 0; i < tallSamling.length; i++) {
	    if (tallSamling[i] != -1) {
		verdier[tallSamling[i]] = tallSamling[i];
	    }
	}
	    
	boolean fremgang = false;

	for (int i = 1; i < verdier.length; i++) {
	    if (verdier[i] == 0) {
		Rute[] retur = proev(i);
		//fremgang = sjekkTall(i); //ny greie
		if (retur.length == 1) {
		    retur[0].fyllUtRute(i);
		    fremgang = true;
		}
	    }
	}

	return fremgang;
    }

    //returnerer den eneste ruten som tallet passer i, null om flere
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

    public boolean sjekkTall(int tall) {
	System.out.println("her har det skjedd noe gitt");
	return false;
    }

    //true om fremgang false om ikke, fjerner tallet som alternativ i alle ruter bortsett
    //fra de i tallbeholderen som sendes med
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

    public void lovligTall(int tall) {
	for (Rute r : ruter) {
	    r.lovligTall(tall);
	}
    }

    public boolean test() {
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