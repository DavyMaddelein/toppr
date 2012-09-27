/*
* This class defines an Amino Acid in terms of its single letter identifier
* and its monoisotopic and average mass delta values.
* This is used to allow the peptide viewer to check mass delta values in
* comparison with the spectrum itself.
*/


// Constructor
function Molecule(oneLetterCode, threeLetterCode, name, monoMass, averageMass, chargeState) {
	this.mono = monoMass;
	this.average = averageMass;
	this.oneLetterCode = oneLetterCode;
	this.threeLetterCode = threeLetterCode;
	this.name = name;
    this.z = chargeState;
}

// Initiate a molecule and discard it.
new Molecule (0, 0, 0, 0, 0, 0);

/* Class variable that holds the absolute error (Daltons) that defines the range
* of acceptable mass values that this AminoAcid will be identified by.
 */
Molecule.MASS_ERROR_DALTONS = 0.5;

Molecule.MAX_CHARGE_INCLUDED = 1;

/*
* method that returns a value between 0 and 3 indicating if this molecule matches the massDelta
* given the RELATIVE_ERROR set above.  If 0, no match.  If 1 - 3, this value indicates
* the potential charge state of the molecule.
*/
Molecule.prototype.matches = function (mzDelta) {
	// For efficiency.
	if (Molecule.MAX_CHARGE_INCLUDED == 1){
		return (this.mono <= ((mzDelta) + Molecule.MASS_ERROR_DALTONS) &&
			this.mono >= ((mzDelta) - Molecule.MASS_ERROR_DALTONS))
			? 1
			: 0;
	}

	for (var charge = 1; charge <= Molecule.MAX_CHARGE_INCLUDED; charge++) {
		if (this.mono <= ((mzDelta * charge) + Molecule.MASS_ERROR_DALTONS) &&
			this.mono >= ((mzDelta * charge) - Molecule.MASS_ERROR_DALTONS))
			return charge;
	}
	return 0;
};

/*
  This method creates a clone of the Molecule that includes the charge
  state from the prediction.
*/
Molecule.cloneWithCharge = function (toClone, chargeState){
    return new Molecule (
            toClone.oneLetterCode,
            toClone.threeLetterCode,
            toClone.name,
            toClone.mono,
            toClone.average,
            chargeState
    );
}

var deltas = new Array();
// Add all of the amino acids to the array
deltas[0] = new Molecule ("A", "Ala", "Alanine", 71.03711, 71.0788);
deltas[1] = new Molecule ("R", "Arg", "Arginine", 156.10111, 156.1875);
deltas[2] = new Molecule ("N", "Asn", "Asparagine", 114.04293, 114.1038);
deltas[3] = new Molecule ("D", "Asp", "Aspartic Acid", 115.02694, 115.0886);
deltas[4] = new Molecule ("C", "Cys", "Cysteine", 103.00919, 103.1388);
deltas[5] = new Molecule ("E", "Glu", "Glutamine", 129.04259, 129.1155);
deltas[6] = new Molecule ("Q", "Gln", "Glutamic Acid", 128.05858, 128.1307);
deltas[7] = new Molecule ("G", "Gly", "Glycine", 57.02146, 57.0519);
deltas[8] = new Molecule ("H", "His", "Histidine", 137.05891, 137.1411);
deltas[9] = new Molecule ("I", "Ile", "Isoleucine", 113.08406, 113.1594);
deltas[10] = new Molecule ("L", "Leu", "Leucine", 113.08406, 113.1594);
deltas[11] = new Molecule ("K", "Lys", "Lysine", 128.09496, 128.1741);
deltas[12] = new Molecule ("M", "Met", "Methionine", 131.04049, 131.1926);
deltas[13] = new Molecule ("F", "Phe", "Phenylalanine", 147.06841, 147.1766);
deltas[14] = new Molecule ("P", "Pro", "Proline", 97.05276, 97.1167);
deltas[15] = new Molecule ("S", "Ser", "Serine", 87.03203, 87.0782);
deltas[16] = new Molecule ("T", "Thr", "Threonine", 101.04768, 101.1051);
deltas[17] = new Molecule ("W", "Trp", "Tryptophan", 186.07931, 186.2132);
deltas[18] = new Molecule ("Y", "Tyr", "Tyrosine", 163.06333, 163.1760);
deltas[19] = new Molecule ("V", "Val", "Valine", 99.06841, 99.1326);

// Add modifications and small molecules to the array
deltas[20] = new Molecule ("S(P)", "Ser(P)", "O-phospho-L-serine", 166.998359);
deltas[21] = new Molecule ("T(P)", "Thr(P)", "O-phospho-L-threonine", 181.014009);
deltas[22] = new Molecule ("Y(P)", "Tyr(P))", "O4'-phospho-L-tyrosine", 243.029659);
deltas[23] = new Molecule ("Hydrogen", "Hydrogen", "Hydrogen", 1.007825035, 1.00794);
deltas[24] = new Molecule ("A(N-Ace)", "Ala(N-Ace)", "Acetylated alanine (N-term)",114.0555);
deltas[25] = new Molecule ("R(N-Ace)", "Arg(N-Ace)", "Acetylated Arginine (N-term)",199.1195);
deltas[26] = new Molecule ("N(N-Ace)", "Asn(N-Ace)", "Acetylated Asparagine (N-term)",157.0613);
deltas[27] = new Molecule ("D(N-Ace)", "Asp(N-Ace)", "Acetylated Aspartic Acid (N-term)",158.0453);
deltas[28] = new Molecule ("C(N-Ace)", "Cys(N-Ace)", "Acetylated Cysteine (N-term)",146.0276);
deltas[29] = new Molecule ("Q(N-Ace)", "Gln(N-Ace)", "Acetylated Glutamic Acid (N-term)",171.077);
deltas[30] = new Molecule ("E(N-Ace)", "Glu(N-Ace)", "Acetylated Glutamine (N-term)",172.061);
deltas[31] = new Molecule ("G(N-Ace)", "Gly(N-Ace)", "Acetylated Glycine (N-term)",100.0399);
deltas[32] = new Molecule ("H(N-Ace)", "His(N-Ace)", "Acetylated Histidine (N-term)",180.0773);
deltas[33] = new Molecule ("I(N-Ace)", "Ile(N-Ace)", "Acetylated Isoleucine (N-term)",156.1025);
deltas[34] = new Molecule ("L(N-Ace)", "Leu(N-Ace)", "Acetylated Leucine (N-term)",156.1025);
deltas[35] = new Molecule ("K(N-Ace)", "Lys(N-Ace)", "Acetylated lysine (N-term)",171.1134);
deltas[36] = new Molecule ("M(N-Ace)", "Met(N-Ace)", "Acetylated Methionine (N-term)",174.0589);
deltas[37] = new Molecule ("F(N-Ace)", "Phe(N-Ace)", "Acetylated Phenylalanine (N-term)",190.0868);
deltas[38] = new Molecule ("P(N-Ace)", "Pro(N-Ace)", "Acetylated Proline (N-term)",140.0712);
deltas[39] = new Molecule ("S(N-Ace)", "Ser(N-Ace)", "Acetylated Serine (N-term)",130.0504);
deltas[40] = new Molecule ("T(N-Ace)", "Thr(N-Ace)", "Acetylated Threonine (N-term)",144.0661);
deltas[41] = new Molecule ("W(N-Ace)", "Trp(N-Ace)", "Acetylated Tryptophan (N-term)",229.0977);
deltas[42] = new Molecule ("Y(N-Ace)", "Tyr(N-Ace)", "Acetylated Tyrosine (N-term)",206.0817);
deltas[43] = new Molecule ("V(N-Ace)", "Val(N-Ace)", "Acetylated Valine (N-term)",142.0868);
deltas[44] = new Molecule ("M(Ox)", "Met(Ox)", "Oxidized methionine",147.035400);
deltas[45] = new Molecule ("K(Ace)", "Lys(Ace)", "Acetylated Lysine",170.105528);
deltas[46] = new Molecule ("Q(Pyr)", "Gln(Pyr)", "Pyro-glutamine",112.039856);
deltas[47] = new Molecule ("C(Cmm)", "Cys(Cmm)", "Carbamidomethyl cysteine",160.030649);
deltas[48] = new Molecule ("C(Pyc)", "Cys(Pyc)", "Pyro-cmm cysteine",144.011925);
deltas[49] = new Molecule ("N(Dam)", "Asn(Dam)", "Deamidated Asparagine",115.026943);
deltas[50] = new Molecule ("Q(Dam)", "Gln(Dam)", "Deamidated Glutamine",129.042594);

/*
* Returns a formatted String listing all of the hits for the particular
* m/z delta value passed in as argument.
*/
function createMatchHTML(mzDelta, type) {
	var result = mzDelta.toFixed(3);
	result += "<br/>";
	for (var molIndex = 0; molIndex < deltas.length; molIndex++) {
		var match = deltas[molIndex].matches(mzDelta);
		if (match > 0) {
            if (type=="Y"){
                result += "<span style='color:red;'>";
            }
            else if (type=="B"){
                result += "<span style='color:blue;'>";
            }
            else {
                result += "<span>";
            }
            result += deltas[molIndex].oneLetterCode;
			if (match > 1) {
				result += "[z=" + match + "]";
			}
            result +="</span>";
            result += "<br/>";
		}
	}
	return result;
}

/*
  This function is used to return an array of the identifications that
  match a particular m/z delta value.  This is used for the reporting
  functions of the spectrum viewer.
*/
function returnMatchArray (massDelta){
    matches = new Array();
    indexPos = 0;
    for (var molIndex = 0; molIndex < deltas.length; molIndex++) {
		var match = deltas[molIndex].matches(massDelta);
		if (match > 0) {
			matches[indexPos++] = Molecule.cloneWithCharge(deltas[molIndex], match);
		}
	}
	return matches;
}





