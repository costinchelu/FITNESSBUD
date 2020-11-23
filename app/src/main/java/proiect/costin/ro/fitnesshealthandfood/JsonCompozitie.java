package proiect.costin.ro.fitnesshealthandfood;

/*
*
* Clasa folosita pentru crearea unui obiect care este valoare pentru cheia compozitie din
* obiectul JsonItem
*
* */
public class JsonCompozitie {

    private String carbohidrati;
    private String grasimi;
    private String proteine;

    public JsonCompozitie(String carbohidrati, String grasimi, String proteine) {
        this.carbohidrati = carbohidrati;
        this.grasimi = grasimi;
        this.proteine = proteine;
    }

    public String getCarbohidrati() { return carbohidrati; }

    public String getGrasimi() { return grasimi; }

    public String getProteine() { return proteine; }

    @Override
    public String toString() {
        return "JsonCompozitie{" +
                "carbohidrati='" + carbohidrati + '\'' +
                ", grasimi='" + grasimi + '\'' +
                ", proteine='" + proteine + '\'' +
                '}';
    }


}
