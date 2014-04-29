import java.io.PrintWriter;

class Brett {

    private Rute [][] ruter;
    private Boks[] bokser;
    private Kolonne[] kolonner;
    private Rad[] rader;
    private int stoerrelse; //lengde og bredde
    private int boksLengde;
    private int boksHoeyde;
    private int[][] tall;

    Brett(int[][] info, int stoerrelse, int boksLengde, int boksHoeyde) {
	tall = info;
	ruter = new Rute[stoerrelse][stoerrelse];
	bokser = new Boks[stoerrelse];
	kolonner = new Kolonne[stoerrelse];
	rader = new Rad[stoerrelse];
	this.stoerrelse = stoerrelse;
	this.boksLengde = boksLengde;
	this.boksHoeyde = boksHoeyde;

	//lager rader
	for (int i = 0; i < stoerrelse; i++) {
	    rader[i] = new Rad(info[i]);
	}
	
	//snur om paa arrayen for aa lage kolonner
	int[][] snuddInfo  = snuTabell(info);
	for (int i = 0; i < stoerrelse; i++) {
	    kolonner[i] = new Kolonne(snuddInfo[i]);
	}
	
	//lager bokser
	int[][] boksArray = boksTabell(info);
	for (int i = 0; i < stoerrelse; i++) {
	    bokser[i] = new Boks(boksArray[i]);
	}

	//lager rute objekter og lenker dem sammen
	int[] a = {1};
	Rute forrigeRute = new AapenRute(new Rad(a), new Kolonne(a), new Boks(a), this); //
	for (int i = 0; i < stoerrelse; i++) {
	    for (int j = 0; j < stoerrelse; j++) {
		if (info[i][j] == -1) {
		    ruter[i][j] = new AapenRute(rader[i], kolonner[j], finnBoks(i, j), this);	
		}
		else {
		    ruter[i][j] = new UtfyltRute(rader[i], kolonner[j], finnBoks(i, j), info[i][j], this);
		}
		//nestepekere
		forrigeRute.setNeste(ruter[i][j]);
		ruter[i][j].setForrige(forrigeRute);
		forrigeRute = ruter[i][j];
	    }
	}
	
    }
    
    //gjoer kolonner om til rader og rader om til kolonner
    public int[][] snuTabell(int[][] array) {
	int[][] nyArray = new int[stoerrelse][stoerrelse];
	for (int i = 0; i < stoerrelse; i++) {
	    for (int j = 0; j < stoerrelse; j++) {
		nyArray[i][j] = array[j][i];
	    }
	}
	return nyArray;
    }
    
    //snur tabellen paa en maate som gjoer at hver rad blir informasjonen om en boks
    public int[][] boksTabell(int[][] array) {
	int[][] nyArray = new int[stoerrelse][stoerrelse];
	int boksTellerx = 0;
	int boksTellery = 0;

	for (int a = 0; a < stoerrelse/boksHoeyde; a++) {
	    for (int b = 0; b < stoerrelse/boksLengde; b++) {
		for (int i = 0; i < boksHoeyde; i++) {
		    for (int j = 0; j < boksLengde; j++) {
			nyArray [boksTellery][boksTellerx] = array[(boksHoeyde*a)+i][(boksLengde*b)+j];
			boksTellerx++;	
		    }
		}
		boksTellery++;
		boksTellerx = 0;	
	    }
	}
	return nyArray;
    }

    //finner boks for plass i tabellen
    public Boks finnBoks(int i, int j) {
	int boksRad = i/boksHoeyde;
	int boksKolonne = j/boksLengde;
	int bokserPerRad = stoerrelse/boksLengde;
	int boksNr = boksRad * bokserPerRad + boksKolonne;
	return bokser[boksNr];
    }

    //Lager en kopi av brettet
    public Brett copy() {
	int[][] oppdatertInfo = new int[stoerrelse][stoerrelse];
	for (int i = 0; i < stoerrelse; i++) {
	    for (int j = 0; j < stoerrelse; j++) {
		oppdatertInfo[i][j] = ruter[i][j].hentTall();
	    }
	}
	return new Brett(oppdatertInfo, stoerrelse, boksLengde, boksHoeyde);
    }
		

    public void skrivTilTerminal(boolean enLinje) {
	for (int i = 0; i < stoerrelse; i++) {
	    for (int j = 0; j < stoerrelse; j++) {
		System.out.printf("%2s", ruter[i][j].getInnhold());
	    }
	    if (enLinje) {
		System.out.print("//");
	    }
	    else {
		System.out.println();
	    }
	}
    }

    public void skrivTilFil(PrintWriter skriv, boolean enLinje) {
	for (int i = 0; i < stoerrelse; i++) {
	    for (int j = 0; j < stoerrelse; j++) {
		skriv.printf("%2s", ruter[i][j].getInnhold() + "  ");
	    }
	    if (enLinje) {
		skriv.print("//");
	    }
	    else {
		skriv.println();
	    }
	}
    }

    //returnerer en Sudokubeholder med alle mulige maater aa loese dette brettet paa
    public SudokuBeholder finnLoesninger() {
	return ruter[0][0].fyllUtRestenAvBrettet(new SudokuBeholder());
    }

    public int getGrense() {
	return stoerrelse;
    }
    
    //finner ruter som bare har et alternativ, returnerer true om noen ble funnet
    public boolean enVerdi() {
	boolean retur = false;
	int[] blegh = {1, 2, 3};
	for (int i = 0; i < stoerrelse; i++) {
	    for (int j = 0; j < stoerrelse; j++) {
		if (ruter[i][j].enMulighet()) {
		    ruter[i][j] = ruter[i][j].fyllUtRute();
		    retur = true;
		}
	    }
	}
	return retur;
    }

    //erstatter gammel aapen rute med ny utfylt rute
    public void erstatt(Rute gammel, Rute ny) {
	for (int i = 0; i < stoerrelse; i++) {
	    for (int j = 0; j < stoerrelse; j++) {
		if (ruter[i][j] == gammel) {
		    ruter[i][j] = ny;
		}
	    }
	}
    }

    //finner og fyller ut ruter som maa ha en bestemt verdi, true om en/flere ruter fylles ut
    public boolean fyllUt() {
	boolean fremgang = false;

	for (int i = 0; i < stoerrelse; i++) {
	    if (bokser[i].loes() || kolonner[i].loes() || rader[i].loes()) {
		fremgang = true;
	    }
	}
	return fremgang;
    }

    //fjerner muligheter som ikke er mulige i ruter, true om en/flere muligheter fjernes
    public boolean fjernMuligheter() {
	boolean fremgang = false;

	for (int i = 0; i < stoerrelse; i++) {
	    if (bokser[i].fjernMuligheter() || kolonner[i].fjernMuligheter() || rader[i].fjernMuligheter()) {
		fremgang = true;
	    }
	}
	return fremgang;
    }

    public int[][] getTall() {
	return tall;
    }
}