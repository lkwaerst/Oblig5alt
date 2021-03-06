class Rad extends AbstraktTallBeholder {
    
    Rad(int[] tall) {
	super(tall);
    }

    //sjekker om tellet maa vaere paa en bestemt Linje/kolonne/boks/ og fjerner den fra resten
    public boolean sjekkTall(int tall) {
	Rute[] muligeRuter = proev(tall);
	if (muligeRuter.length < 2) {
	    return false;
	}

	Boks boks = muligeRuter[0].getBoks();
	AbstraktTallBeholder match = null;

	for (int i = 1; i < muligeRuter.length; i++) {
	    if (muligeRuter[i].getBoks() == boks) {
		match = muligeRuter[i].getBoks();
	    }
	    else {
		return false;
	    }
	}
	
	boolean fremgang = match.ulovligTall(tall, this);
	return fremgang;
    }
}