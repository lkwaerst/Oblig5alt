class UtfyltRute extends Rute {

    boolean skrivUt = true;

    UtfyltRute(Rad r, Kolonne k, Boks b, int tall, Brett brett) {
	super(r, k, b, tall, brett);
    }

    //kaller paa nestes metode, true om det er siste rute, passivt mellomledd
    public SudokuBeholder fyllUtRestenAvBrettet(SudokuBeholder loesninger) {
	if (neste != null) {
	    return neste.fyllUtRestenAvBrettet(loesninger);
	}
	loesninger.add(brett.copy());
	return loesninger;
    }

    public boolean erLovligTall(int tall) {
	return false;
    }
}