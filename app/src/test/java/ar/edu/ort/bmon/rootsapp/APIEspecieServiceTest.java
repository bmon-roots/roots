package ar.edu.ort.bmon.rootsapp;

import android.content.res.Resources;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import ar.edu.ort.bmon.rootsapp.model.Especie;

public class APIEspecieServiceTest {


    APIEspecieService service = APIEspecieClient.getInstance().create(APIEspecieService.class);
    @Test
    public void getAllEspecies_userkeyValidated_returnEspecies() {
        String userKey = Resources.getSystem().getString(R.string.USER_KEY);
        List<Especie> especies = (List<Especie>) service.getAllEspecies(userKey);
        Assert.assertFalse(especies.isEmpty());
//        Assert.assertTrue(especies.get(0).getNombre().equals("ACER"));
    }
}
