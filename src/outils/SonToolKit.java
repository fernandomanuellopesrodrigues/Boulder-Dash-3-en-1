package outils;

/**
 * Created by celso on 20/03/17.
 */
public class SonToolKit {
    private long compteur1, compteur2, compteur3, compteur4, compteur5, compteur6;
    private Sons sons1 = new Sons(), sons2 = new Sons(), sons3 = new Sons();
    private boolean boolean1,boolean2,boolean3;

    public void jouerSon1(String nom, int delaiEnCentiSecondes) {
        compteur1 = System.currentTimeMillis();
        if (compteur1 - compteur2 > delaiEnCentiSecondes * 10) {
            sons1.jouer(nom);
            compteur2 = System.currentTimeMillis();
        }
    }

    public void jouerSon2(String nom, int delaiEnCentiSecondes) {
        compteur3 = System.currentTimeMillis();
        if (compteur3 - compteur4 > delaiEnCentiSecondes * 10) {
            sons2.jouer(nom);
            compteur4 = System.currentTimeMillis();
        }
    }

    public void jouerSon3(String nom, int delaiEnCentiSecondes) {
        compteur5 = System.currentTimeMillis();
        if (compteur5 - compteur6 > delaiEnCentiSecondes * 10) {
            sons3.jouer(nom);
            compteur6 = System.currentTimeMillis();
        }
    }
    public void stopSon1(){
    	sons1.stop();
    }
    public void stopSon2(){
    	sons2.stop();
    }
    public void stopSon3(){
    	sons3.stop();
    }
    public void stopAll(){
    	stopSon1();
    	stopSon2();
    	stopSon3();
    }
    public boolean isBoolean1() {
        return boolean1;
    }

    public boolean isBoolean2() {
        return boolean2;
    }

    public boolean isBoolean3() {
        return boolean3;
    }

    public void setBoolean1(boolean boolean1) {
        this.boolean1 = boolean1;
    }

    public void setBoolean2(boolean boolean2) {
        this.boolean2 = boolean2;
    }

    public void setBoolean3(boolean boolean3) {
        this.boolean3 = boolean3;
    }
}
