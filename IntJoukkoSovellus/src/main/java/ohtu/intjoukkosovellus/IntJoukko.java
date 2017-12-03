package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] taulukko;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int koko;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        this(KAPASITEETTI);
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0 || kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Et voi syöttää negatiivisia arvoja!");
        }
        taulukko = new int[kapasiteetti];
        koko = 0;
        this.kasvatuskoko = kasvatuskoko;

    }

    public boolean lisaa(int luku) {
        if (!kuuluu(luku)) {
            taulukko[koko] = luku;
            koko++;
            if (koko % taulukko.length == 0) {
                int[] taulukkoOld = taulukko;
                taulukko = new int[koko + kasvatuskoko];
                kopioiTaulukko(taulukkoOld, taulukko);
            }
            return true;
        }
        return false;
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < koko; i++) {
            if (luku == taulukko[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        int indeksi = etsi(luku);
        if (indeksi != -1) {
            poistaIndeksi(indeksi);
            return true;
        }

        return false;
    }

    private void poistaIndeksi(int indeksi) {
        for (int j = indeksi; j < koko - 1; j++) {
            int apu = taulukko[j];
            taulukko[j] = taulukko[j + 1];
            taulukko[j + 1] = apu;
        }
        koko--;
    }

    private int etsi(int luku) {
        int indeksi = -1;
        for (int i = 0; i < koko; i++) {
            if (luku == taulukko[i]) {
                indeksi = i;
                taulukko[indeksi] = 0;
                break;
            }
        }
        return indeksi;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

    }

    public int koko() {
        return koko;
    }

    @Override
    public String toString() {
        if (koko == 0) {
            return "{}";
        } else if (koko == 1) {
            return "{" + taulukko[0] + "}";
        } else {
            String palautettava = "{";
            for (int i = 0; i < koko - 1; i++) {
                palautettava += taulukko[i];
                palautettava += ", ";
            }
            palautettava += taulukko[koko - 1];
            palautettava += "}";
            return palautettava;
        }
    }

    public int[] toIntArray() {
        int[] taulu = new int[koko];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = taulukko[i];
        }
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            x.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            x.lisaa(bTaulu[i]);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    y.lisaa(bTaulu[j]);
                }
            }
        }
        return y;

    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            z.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            z.poista(i);
        }

        return z;
    }

}
