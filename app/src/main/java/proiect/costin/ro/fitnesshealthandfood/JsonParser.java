package proiect.costin.ro.fitnesshealthandfood;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
*
* Clasa folosita pentru parsarea informatiilor din sirul de caractere furnizat de
* HttpManager si obtinerea unei liste de obiecte utilizabile in aplicatie
*
* */

public class JsonParser implements Constante{

    public static List<JsonItem> parseJson(String json) {
        if(json == null) {
            return null;
        }
        try {
            JSONObject object = new JSONObject(json);
            return getItemListFromJson(object.getJSONArray("food"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // cream lista de obiecte
    private static List<JsonItem> getItemListFromJson(JSONArray jsonArray) throws JSONException {
        if(jsonArray == null) {
            return null;
        }
        List<JsonItem> lista = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            JsonItem jsonItem = getItemFromJson(jsonArray.getJSONObject(i));
            if(jsonItem != null) {
                lista.add(jsonItem);
            }
        }
        return lista;
    }

    // cream un obiect care contine informatiile dintr-un nod prin citirea valorilor din JSON
    private static JsonItem getItemFromJson(JSONObject object) throws JSONException {
        if(object == null) {
            return null;
        }
        String imagine = object.getString(HTTP_TAG_MAGINE);
        String nume = object.getString(HTTP_TAG_NUME);
        String portie = object.getString(HTTP_TAG_PORTIE);
        String calorii = object.getString(HTTP_TAG_CALORII);
        JsonCompozitie compozitie = getCompozitieFromJson(object.getJSONObject(HTTP_TAG_COMPOZITIE));
        return new JsonItem(imagine, nume, portie, calorii, compozitie);
    }

    // citim cheia compozitie si cream un obiect compozitie cu atributele carbohidrati, grasimi, proteine
    private static JsonCompozitie getCompozitieFromJson(JSONObject object) throws JSONException {
        if(object == null) {
            return null;
        }
        String carbohidrati = object.getString(HTTP_TAG_CARBO);
        String grasimi = object.getString(HTTP_TAG_GRASIMI);
        String proteine = object.getString(HTTP_TAG_PROTEINE);
        return new JsonCompozitie(carbohidrati, grasimi, proteine);
    }
}
