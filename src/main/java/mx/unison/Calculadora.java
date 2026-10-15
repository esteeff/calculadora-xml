package mx.unison;

import java.io.File;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

public class Calculadora {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("No ser proporcionaron argumentos requeridos");
            System.exit(1);
        }

        if (args.length == 1) {
            System.out.println("Debes incluir al menos el nombre de una variable");
            System.exit(2);
        }

        if (args.length > 3) {
            System.out.println("Solo se permiten hasta dos variables");
            System.exit(3);
        }

        String archivo = args[0];
        String var1 = args[1];
        String var2 = (args.length == 3) ? args[2] : null;

        try {
            List<Double> valores1 = leerVariable(archivo, var1);

            if (valores1.isEmpty()) {
                System.out.println("La variable '" + var1 + "' no existe en el documento o no tiene valores");
                System.exit(4);
            }

            imprimirEstadisticas(var1, valores1);

            if (var2 != null) {
                List<Double> valores2 = leerVariable(archivo, var2);

                if (valores2.isEmpty()) {
                    System.out.println("La variable '" + var2 + "' no existe en el documento o no tiene valores");
                    System.exit(5);
                }

                imprimirEstadisticas(var2, valores2);

                if (valores1.size() != valores2.size()) {
                    System.out.println("No se puede calcular la correlacion: las variables tienen distinto numero de valores");
                } else {
                    double r = correlacion(valores1, valores2);
                    System.out.printf("%nCoeficiente de correlacion entre %s y %s: %.4f%n", var1, var2, r);
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(6);
        }
    }

    private static List<Double> leerVariable(String archivo, String variable) throws Exception {
        File f = new File(archivo);
        if (!f.exists()) {
            throw new Exception("No se puede abrir el documento XML: " + archivo);
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        SAXParser parser = factory.newSAXParser();

        VariablesHandler handler = new VariablesHandler(variable);
        parser.parse(f, (DefaultHandler) handler);

        return handler.getValores();
    }

    private static void imprimirEstadisticas(String nombre, List<Double> valores) {
        double media = media(valores);
        double varianza = varianza(valores, media);
        double desv = Math.sqrt(varianza);
        double max = maximo(valores);
        double min = minimo(valores);

        System.out.printf("%nEstadisticas de '%s':%n", nombre);
        System.out.printf("  Media:               %.4f%n", media);
        System.out.printf("  Desviacion estandar: %.4f%n", desv);
        System.out.printf("  Varianza:            %.4f%n", varianza);
        System.out.printf("  Maximo:              %.4f%n", max);
        System.out.printf("  Minimo:              %.4f%n", min);
    }

    private static double media(List<Double> v) {
        double suma = 0;
        for (double x : v) suma += x;
        return suma / v.size();
    }

    private static double varianza(List<Double> v, double media) {
        double suma = 0;
        for (double x : v) suma += Math.pow(x - media, 2);
        return suma / v.size();
    }

    private static double maximo(List<Double> v) {
        double max = v.get(0);
        for (double x : v) if (x > max) max = x;
        return max;
    }

    private static double minimo(List<Double> v) {
        double min = v.get(0);
        for (double x : v) if (x < min) min = x;
        return min;
    }

    private static double correlacion(List<Double> x, List<Double> y) {
        int n = x.size();
        double mx = media(x);
        double my = media(y);

        double sumaXY = 0;
        double sumaX2 = 0;
        double sumaY2 = 0;

        for (int i = 0; i < n; i++) {
            double dx = x.get(i) - mx;
            double dy = y.get(i) - my;
            sumaXY += dx * dy;
            sumaX2 += dx * dx;
            sumaY2 += dy * dy;
        }

        return sumaXY / Math.sqrt(sumaX2 * sumaY2);
    }
}