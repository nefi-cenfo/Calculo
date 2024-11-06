package Optimizacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.stream;

public class OptimizacionCalculo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OptimizacionCalculo optimizacion = new OptimizacionCalculo();

        System.out.println("Seleccione el problema que desea resolver:");
        System.out.println("1. Maximizar el volumen de la caja sin tapa.");
        System.out.println("2. Minimizar la superficie del papel.");

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                // Variables para el problema de la caja sin tapa
                System.out.print("Ingrese el largo de la hoja (cm): ");
                double largo = scanner.nextDouble();
                System.out.print("Ingrese el ancho de la hoja (cm): ");
                double ancho = scanner.nextDouble();
                System.out.print("Ingrese el valor mínimo de L (cm): ");
                double LMin = scanner.nextDouble();
                System.out.print("Ingrese el valor máximo de L (cm): ");
                double LMax = scanner.nextDouble();

                optimizacion.maximizarVolumenCaja(ancho, largo, LMin, LMax);
                break;

            case 2:
                // Variables para el problema de minimizar la superficie del papel
                System.out.print("Ingrese el área del texto impreso (cm²): ");
                double areaTexto = scanner.nextDouble();
                System.out.print("Ingrese el margen superior e inferior (cm): ");
                double margenVertical = scanner.nextDouble();
                System.out.print("Ingrese el margen lateral (cm): ");
                double margenLateral = scanner.nextDouble();

                optimizacion.minimizarSuperficiePapel(areaTexto, margenVertical, margenLateral);
                break;

            default:
                System.out.println("Opción no válida.");
        }

        scanner.close();
    }

    public double[] calcularSolucionCuadratica(double a, double b, double c) {
        double[] soluciones = new double[2];
        double discrimante = Math.pow(b, 2)-(4*a*c);

        soluciones[0] = (-(b)+Math.sqrt(discrimante))/(2*a);
        soluciones[1] = (-(b)-Math.sqrt(discrimante))/(2*a);

        return soluciones;
    }

    // Método para maximizar el volumen de la caja sin tapa
    public void maximizarVolumenCaja(double ancho, double largo, double LMin, double LMax) {
        double maxVolumen = 0;
        double mejorL = 0;
        boolean volumenEnRango = false;
        double volumenMax = (largo - 2 * LMin) * (ancho - 2 * LMin) * LMin;
        double volumenMin = (largo - 2 * LMax) * (ancho - 2 * LMax) * LMax;
//        int count = 0;
//
//        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        //double volumen = (largo - 2 * L) * (ancho - 2 * L) * L;
        // (20 - 2L) * (10L - 2LL)
        //200l - 40L^2 - 20LL + 4LLL
        //200L - 60LL + 4LLL
        String firstExp = "(20 - 2L)";
        String secondExp = "(10 - 2L)";
        String thirdExp = "L";
        double[] firstStep = {largo, -2, ancho, -2};
        double[] secondStep = {largo*ancho, largo*-2, -2*ancho, -2*-2};
        double[] thirdStep = {secondStep[0], secondStep[1]+secondStep[2], secondStep[3]};

        // Derivar
        double[] derivada = {thirdStep[0], thirdStep[1]*2, thirdStep[2]*3};

        // Dividir entre 4
        for (int i=0; i<derivada.length; i++) {
            derivada[i] = derivada[i]/4;
        }

        double[] soluciones = calcularSolucionCuadratica(derivada[2], derivada[1], derivada[0]);

        System.out.printf("El valor de L que maximiza el volumen dentro del rango es: %.2f cm%n", soluciones[0]);
        System.out.printf("El valor de L que maximiza el volumen dentro del rango es: %.2f cm%n", soluciones[1]);

//        Matcher addL = pattern.matcher(secondExp);
//        ArrayList<Integer> addLValues = new ArrayList<Integer>();
//
//        while (addL.find()) {
//            System.out.println(addL.group());
//            addLValues.add(Integer.valueOf(addL.group()));
//        }
//
//        String secondExp2 = "(" + addLValues.getFirst() + thirdExp + " " + addLValues.get(1) + thirdExp + thirdExp + ")";


//        for (double L = LMin; L <= LMax; L += 0.01) {
//            double volumen = (largo - 2 * L) * (ancho - 2 * L) * L;
//
////             Verificamos si el volumen está en el rango deseado
//            if (volumen >= volumenMin && volumen <= volumenMax) {
//                volumenEnRango = true;
//                if (volumen > maxVolumen) {
//                    maxVolumen = volumen;
//                    mejorL = L;
//                }
//            }
//            count++;
//        }
//
//        if (volumenEnRango) {
//            System.out.printf("El valor de L que maximiza el volumen dentro del rango es: %.2f cm%n", mejorL);
//            System.out.printf("El volumen máximo de la caja dentro del rango deseado es: %.2f cm³%n", maxVolumen);
//            System.out.println("Count " + count);
//        } else {
//            System.out.println("No se encontró ningún volumen dentro del rango especificado.");
//        }
    }

//    public void maximizarVolumenCaja(double ancho, double largo, double LMin, double LMax) {
//        double maxVolumen = 0;
//        double mejorL = 0;
//        for (double L = LMin; L <= LMax; L += 0.01) {
//            double volumen = (ancho - 2 * L) * (largo - 2 * L) * L;
//            if (volumen > maxVolumen) {
//                maxVolumen = volumen;
//                mejorL = L;
//            }
//        }


        // Método para minimizar la superficie del papel
    public void minimizarSuperficiePapel(double areaTexto, double margenVertical, double margenLateral) {
        double minSuperficie = Double.MAX_VALUE;
        double mejorX = 0;
        double mejorY = 0;

        for (double x = 2 * margenLateral + 1; x <= 100; x += 0.1) { // Limite superior arbitrario para x
            double y = (areaTexto / (x - 2 * margenLateral)) + 2 * margenVertical;

            double superficie = x * y;

            if (superficie < minSuperficie) {
                minSuperficie = superficie;
                mejorX = x;
                mejorY = y;
            }
        }

        System.out.printf("Las dimensiones que minimizan la superficie son: x = %.2f cm, y = %.2f cm%n", mejorX, mejorY);
        System.out.printf("La superficie mínima del papel es: %.2f cm²%n", minSuperficie);

    }

}
