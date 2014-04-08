class Boks extends AbstraktTallBeholder {

    Boks(int[] tall) {
	super(tall);
    }

    //sjekker om tellet maa vaere paa en bestemt Linje/kolonne/boks/ og fjerner den fra resten
    public boolean sjekkTall(int tall) {
	Rute[] muligeRuter = proev(tall);
	if (muligeRuter.length < 2) {
	    return false;
	}
	Kolonne kol  = muligeRuter[0].getKolonne();
	Rad rad = muligeRuter[0].getRad();
	AbstraktTallBeholder match = null;
	
	for (int i = 1; i < muligeRuter.length; i++) {
	    if (!(muligeRuter[i].getKolonne() == kol)) {
		break;
	    }
	    if (i == muligeRuter.length-1) {
		match = kol;
	    }
	}

	for (int i = 1; i < muligeRuter.length; i++) {
	    if (!(muligeRuter[i].getRad() == rad)) {
		break;
	    }
	    if (i == muligeRuter.length-1) {
		match = rad;
	    }
	}
	if (match == null) {
	    return false;
	}
	
	boolean fremgang = match.ulovligTall(tall, this);
	return fremgang;
    }
}