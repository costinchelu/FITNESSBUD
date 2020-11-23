package proiect.costin.ro.fitnesshealthandfood;

/*
*
* Clasa folosita pentru crearea unui obiect dintr-un nod JSON
*
* */
public class JsonItem {

    private String imagine;
    private String nume;
    private String portie;
    private String calorii;
    private JsonCompozitie compozitie;

    public JsonItem(String imagine, String nume, String portie,
                    String calorii, JsonCompozitie compozitie) {
        this.imagine = imagine;
        this.nume = nume;
        this.portie = portie;
        this.calorii = calorii;
        this.compozitie = compozitie;
    }

    public String getImagine() { return imagine; }

    public String getNume() { return nume; }

    public String getPortie() { return portie; }

    public String getCalorii() { return calorii; }

    public JsonCompozitie getCompozitie() { return compozitie; }

    @Override
    public String toString() {
        return "JsonItem{" +
                "imagine='" + imagine + '\'' +
                ", nume='" + nume + '\'' +
                ", portie='" + portie + '\'' +
                ", calorii='" + calorii + '\'' +
                ", compozitie=" + compozitie +
                '}';
    }
}
