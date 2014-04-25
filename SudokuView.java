import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.event.*;

class SudokuView implements ActionListener {

    private Kontroll kontroll;
    private Brett brett;
    private JFrame vindu;
    private int[][] sudoku;
    private int stoerrelse;
    private JPanel visningsPanel;
    private JPanel tallPanel;
    private JPanel[] boksPaneler;
    private JPanel kontrollPanel;
    private JButton nesteLoesningKnapp;
    private JButton forrigeLoesningKnapp;
    private JButton velgFilKnapp;
    private int bokserPerRad;
    private int antLoesninger;
    private int teller = 0;
    private JLabel tellerVindu;
    
    SudokuView (int[][] sudoku, int boksHoeyde, int boksLengde, Kontroll k) {
	kontroll = k;
	this.sudoku = sudoku;
	stoerrelse = sudoku[0].length;
	brett = new Brett(sudoku, stoerrelse, boksLengde, boksHoeyde);
	bokserPerRad = stoerrelse/boksLengde;
	antLoesninger = k.getAntLoesninger();

	vindu = new JFrame("sudoku");
	visningsPanel = new JPanel();
	visningsPanel.setLayout(new GridLayout(stoerrelse/boksHoeyde, stoerrelse/boksLengde ));
	visningsPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

	/*lager paneler med grid layout for hver boks, putter riktige verdier inn i dem,
	  og legger dem inn i visningspanelet*/
	boksPaneler = new JPanel[stoerrelse];
	int[][] boksArray = brett.boksTabell(sudoku);
	for (int i = 0; i < stoerrelse; i++) {
	    boksPaneler[i] = new JPanel();
	    boksPaneler[i].setLayout(new GridLayout(boksHoeyde, boksLengde));
	    boksPaneler[i].setBorder(BorderFactory.createLineBorder(Color.black, 2));
	    
	    //lager knapper til hvert bokspanel
	    for (int j = 0; j < stoerrelse; j++) {
		//JButton knapp = new JButton(String.valueOf(Character.forDigit(sudoku[i][j], Character.MAX_RADIX)).toUpperCase());
		JButton knapp = new JButton(String.valueOf(boksArray[i][j]));
		knapp.setFont(new Font("Helvetica", 100, 10));
		knapp.setEnabled(false);
		boksPaneler[i].add(knapp);
	    }
	    visningsPanel.add(boksPaneler[i]);
	}

	//Lager knapper med actionlisteners, og legger dem inn i et kontrollpanel
	kontrollPanel = new JPanel(new FlowLayout());
	forrigeLoesningKnapp = new JButton("<<");
	forrigeLoesningKnapp.addActionListener(this);
	velgFilKnapp = new JButton("Velg fil");
	velgFilKnapp.addActionListener(this);
	nesteLoesningKnapp = new JButton(">>");
	nesteLoesningKnapp.addActionListener(this);
	kontrollPanel.add(forrigeLoesningKnapp);
	kontrollPanel.add(velgFilKnapp);
	kontrollPanel.add(nesteLoesningKnapp);

	//viser hvor mange loesninger som finnes om flere enn 1
	tallPanel = new JPanel();
	if (antLoesninger > 1) {
	    tallPanel.add(new JLabel("Loesning:"));
	    tellerVindu = new JLabel(String.valueOf(teller));
	    tallPanel.add(tellerVindu);
	    tallPanel.add(new JLabel("/" + String.valueOf(antLoesninger)));
	    vindu.add(tallPanel, BorderLayout.NORTH);
	}
	
	vindu.add(visningsPanel, BorderLayout.CENTER);
	vindu.add(kontrollPanel, BorderLayout.SOUTH);
	vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	vindu.setSize(50000, 50000);
	vindu.setVisible(true);
	malBokser();

    }

    public void nySudoku(int[][] sudoku) {
	for (int i = 0; i < stoerrelse; i++) {
	    for (int j = 0; j < stoerrelse; j++) {
		JButton knapp = (JButton) boksPaneler[i].getComponent(j);
		knapp.setText(String.valueOf(Character.forDigit(sudoku[i][j], Character.MAX_RADIX)).toUpperCase());
	    }
	}
	if (tellerVindu != null) {
	    tellerVindu.setText(String.valueOf(++teller));
	}
    }

    public static File velgFil() {
	JFileChooser filVelger = new JFileChooser();
	int retur = filVelger.showOpenDialog(null);
	return filVelger.getSelectedFile();
    }

    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == nesteLoesningKnapp) {
	    kontroll.visNesteLoesning();
	}
	else if (e.getSource() == forrigeLoesningKnapp) {
	    kontroll.visForrigeLoesning();
	}
	else if (e.getSource() == velgFilKnapp) {
	    vindu.setVisible(false);
	    new Kontroll(new String[0]);
	}
    }

    private void malBokser() {
	Color[] farger = {Color.white, Color.lightGray};
	int teller = 0;

	for (int i = 0; i < boksPaneler.length; i++) {
	    JPanel panel = boksPaneler[i];
	    panel.setBackground(Color.black);

	    for (Component knapp : panel.getComponents()) {
		knapp.setBackground(farger[teller % 2]);		
	    }
	    if (bokserPerRad % 2 == 0 && (i+1) % bokserPerRad == 0) {
		teller++;
	    }
	    teller++;
	}
    }
}