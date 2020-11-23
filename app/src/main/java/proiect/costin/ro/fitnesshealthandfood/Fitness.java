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
 * se va ocupa de crearea obiectelor fitness care cuprind atributele cerute in formularul ActivitateFitnesAdd
 * al doilea constructor (cel cu id este folosit pentru transmiterea id-ului
 * din baza de date in conditiile editarii (ActivitateFitnessEdit) respectivei intrari)
 *
 * */

public class Fitness implements Parcelable {

    private String denumireSport;
    private Integer nrCalorii;
    private Integer nrMinute;
    private Date dataActivitatii;

    private Long idFitness;

    public Fitness(String denumireSport, Integer nrCalorii, Integer nrMinute,
                   Date dataActivitatii) {
        this.denumireSport = denumireSport;
        this.nrCalorii = nrCalorii;
        this.nrMinute = nrMinute;
        this.dataActivitatii = dataActivitatii;
    }

    public Fitness(String denumireSport, Integer nrCalorii, Integer nrMinute,
                   Date dataActivitatii, Long idFitness) {
        this.denumireSport = denumireSport;
        this.nrCalorii = nrCalorii;
        this.nrMinute = nrMinute;
        this.dataActivitatii = dataActivitatii;
        this.idFitness = idFitness;
    }

    protected Fitness(Parcel in) {
        denumireSport = in.readString();
        if (in.readByte() == 0) {
            nrCalorii = null;
        } else {
            nrCalorii = in.readInt();
        }
        if (in.readByte() == 0) {
            nrMinute = null;
        } else {
            nrMinute = in.readInt();
        }
        if (in.readByte() == 0) {
            idFitness = null;
        } else {
            idFitness = in.readLong();
        }
        try {
            this.dataActivitatii = new SimpleDateFormat
                    (ActivitateFitnessAdd.DATE_FORMAT, Locale.US).parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Fitness> CREATOR = new Creator<Fitness>() {
        @Override
        public Fitness createFromParcel(Parcel in) { return new Fitness(in); }

        @Override
        public Fitness[] newArray(int size) { return new Fitness[size]; }
    };

    public String getDenumireSport() { return denumireSport; }
    public void setDenumireSport(String denumireSport) { this.denumireSport = denumireSport; }
    public Integer getNrCalorii() { return nrCalorii; }
    public void setNrCalorii(Integer nrCalorii) { this.nrCalorii = nrCalorii; }
    public Integer getNrMinute() { return nrMinute; }
    public void setNrMinute(Integer nrMinute) { this.nrMinute = nrMinute; }
    public Date getDataActivitatii() { return dataActivitatii; }
    public void setDataActivitatii(Date dataActivitatii) { this.dataActivitatii = dataActivitatii; }
    public Long getIdFitness() { return idFitness; }
    public void setIdFitness(Long idFitness) { this.idFitness = idFitness; }

    @Override
    public String toString() {
        return "Fitness{" +
                "denumireSport='" + denumireSport + '\'' +
                ", nrCalorii=" + nrCalorii +
                ", nrMinute=" + nrMinute +
                ", dataActivitatii=" + dataActivitatii +
                '}';
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(denumireSport);
        if (nrCalorii == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(nrCalorii);
        }
        if (nrMinute == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(nrMinute);
        }
        if (idFitness == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(idFitness);
        }
        String dateStr = this.dataActivitatii != null ?
                new SimpleDateFormat(ActivitateFitnessAdd.DATE_FORMAT, Locale.US)
                        .format(this.dataActivitatii) :
                null;
        dest.writeString(dateStr);
    }
}
