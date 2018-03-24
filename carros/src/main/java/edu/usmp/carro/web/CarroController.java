package edu.usmp.carro.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import edu.usmp.carro.model.Carro;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Controller
public class CarroController {


    public Object getResult(String values) {

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        Object result = null;
        try {
            result = engine.eval(values);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        return result;
    }


    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("carro", new Carro());
        return "carro";
    }

    @PostMapping("/new")
    public String submitForm(@ModelAttribute Carro carro) {

        double galones = 0;
        try {
            String modelo = carro.getVehiculo();
            int recorrido = carro.getRecorrido();
            switch (modelo) {
                case "Marca1":
                    galones = recorrido / 40.00;
                    break;
                case "Marca2":
                    galones = recorrido / 35.00;
                    break;
                case "Marca3":
                    galones = recorrido / 45.00;
                    break;

                default:
                    System.out.print("Error de modelo");
                    break;
            }

            carro.setConsumo(String.valueOf(round(galones,2)));
            System.out.print(String.valueOf(galones));


        } catch (Exception e) {
            System.out.print("Error de modelo");
        }

        return "resultado";
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}






