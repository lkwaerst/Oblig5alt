class Boks extends AbstraktTallBeholder {

    Boks(int[] tall) {
	super(tall);
    }

    /*sjekker om tallet maa vaere paa en bestemt Linje/kolonne i boksen, og fjerner tellet som
      mulighet i resten av linjen/kolonnen*/
    public boolean sjekkTall(int tall) {
	Rute[] muligeRuter = proev(tall);
	if (muligeRuter.length < 2) {   //tas av en annen metode
	    return false;
	}
	
	Kolonne kol  = muligeRuter[0].getKolonne();
	Rad rad = muligeRuter[0].getRad();
	AbstraktTallBeholder match = null;
	
	//sjekker foerst om samme kolonne
	for (int i = 1; i < muligeRuter.length; i++) {
	    if (!(muligeRuter[i].getKolonne() == kol)) {
		break;
	    }
	    if (i == muligeRuter.length-1) {
		match = kol;
	    }
	}

	//sjekker om samme rad
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
	
	//fremgang om muligheter ble fjernet
	boolean fremgang = match.ulovligTall(tall, this);
	return fremgang;
    }
}