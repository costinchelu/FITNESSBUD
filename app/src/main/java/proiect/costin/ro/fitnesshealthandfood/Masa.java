package proiect.costin.ro.fitnesshealthandfood;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*
*
* clasa cu implementare Parcellable pentru a putea transfera informatiile intre activitati
* se va ocupa de crearea obiectelor masa care cuprind atributele cerute in formularul ActivitateMeseAdd
* al doilea constructor (cel cu id este folosit pentru transmiterea id-ului
* din baza de date in conditiile editarii respectivei intrari)
*
* */

public class Masa implements Parcelable {

    private String numeMancare;
    private String perioadaMesei;
    private Integer nrCalorii;
    private Float nrPortii;
    private Date dataMesei;

    private Long idMasa;

    public Masa(String numeMancare, String perioadaMesei, Integer nrCalorii,
                Float nrPortii, Date dataMesei) {
        this.numeMancare = numeMancare;
        this.perioadaMesei = perioadaMesei;
        this.nrCalorii = nrCalorii;
        this.nrPortii = nrPortii;
        this.dataMesei = dataMesei;
    }

    public Masa(String numeMancare, String perioadaMesei, Integer nrCalorii,
                Float nrPortii, Date dataMesei, Long idMasa) {
        this.numeMancare = numeMancare;
        this.perioadaMesei = perioadaMesei;
        this.nrCalorii = nrCalorii;
        this.nrPortii = nrPortii;
        this.dataMesei = dataMesei;
        this.idMasa = idMasa;
    }

    protected Masa(Parcel in) {
        numeMancare = in.readString();
        perioadaMesei = in.readString();
        if (in.readByte() == 0) {
            nrCalorii = null;
        } else {
            nrCalorii = in.readInt();
        }
        if (in.readByte() == 0) {
            nrPortii = null;
        } else {
            nrPortii = in.readFloat();
        }
        if (in.readByte() == 0) {
            idMasa = null;
        } else {
            idMasa = in.readLong();
        }
        try {
            this.dataMesei = new SimpleDateFormat
                    (ActivitateMeseAdd.DATE_FORMAT, Locale.US)
                    .parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Masa> CREATOR = new Creator<Masa>() {
        @Override
        public Masa createFromParcel(Parcel in) { return new Masa(in); }

        @Override
        public Masa[] newArray(int size) { return new Masa[size]; }
    };

    public String getNumeMancare() { return numeMancare; }
    public void setNumeMancare(String numeMancare) { this.numeMancare = numeMancare; }
    public String getPerioadaMesei() { return perioadaMesei; }
    public void setPerioadaMesei(String perioadaMesei) { this.perioadaMesei = perioadaMesei; }
    public Integer getNrCalorii() { return nrCalorii; }
    public void setNrCalorii(Integer nrCalorii) { this.nrCalorii = nrCalorii; }
    public Float getNrPortii() { return nrPortii; }
    public void setNrPortii(Float nrPortii) { this.nrPortii = nrPortii; }
    public Date getDataMesei() { return dataMesei; }
    public void setDataMesei(Date dataMesei) { this.dataMesei = dataMesei; }
    public Long getIdMasa() { return idMasa; }
    public void setIdMasa(Long idMasa) { this.idMasa = idMasa; }

    @Override
    public String toString() {
        return "Masa{" +
                "numeMancare='" + numeMancare + '\'' +
                ", perioadaMesei='" + perioadaMesei + '\'' +
                ", nrCalorii=" + nrCalorii +
                ", nrPortii=" + nrPortii +
                ", dataMesei=" + dataMesei +
                '}';
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(numeMancare);
        dest.writeString(perioadaMesei);
        if (nrCalorii == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(nrCalorii);
        }
        if (nrPortii == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(nrPortii);
        }
        if (idMasa == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(idMasa);
        }
        String dateStr = this.dataMesei != null ?
                new SimpleDateFormat
                        (ActivitateMeseAdd.DATE_FORMAT, Locale.US)
                        .format(this.dataMesei) :
                null;
        dest.writeString(dateStr);
    }
}
