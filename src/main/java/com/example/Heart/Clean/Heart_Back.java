package com.example.Heart.Clean;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import java.util.Locale;
import java.util.Scanner;

public class Heart_Back {

    private  Instances data;

    private Classifier classifier;
    private Scanner scanner;

    public Heart_Back(){
        scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        try {
            DataSource source = new DataSource("C:\\Users\\Usuario\\Downloads\\Heart Clean.arff");
            data = source.getDataSet();
            data.setClassIndex(data.numAttributes() - 1);

            //Convertir el atributo clase a nominal para poder usar el j48
            NumericToNominal filter = new NumericToNominal();
            filter.setInputFormat(data);
            data = Filter.useFilter(data, filter);

            classifier = new J48();
            classifier.buildClassifier(data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public double predictInConsole(Data data1){
        String predictedClassName = "";
        //Obtener los valores ingresados por el usuario
        double edad = data1.getAge();
        double genero = data1.getGender();
        double chest_pain = data1.getChestPain();
        double pressure = data1.getBloodPressure();
        double serum = data1.getCholesterol();
        double sugar = data1.getBloodSugar();
        double electro = data1.getElectroResult();
        double max = data1.getMaxHeartRate();
        double exer = data1.getExerciseAngina();
        double old = data1.getOldPeakST();
        double slop = data1.getSlopeSTSegment();

        Instance newInstance = new DenseInstance(12);
        newInstance.setValue(data.attribute("Age"), edad);
        newInstance.setValue(data.attribute("Sex"), genero);
        newInstance.setValue(data.attribute("ChestPainType"), chest_pain);
        newInstance.setValue(data.attribute("RestingBP"), pressure);
        newInstance.setValue(data.attribute("Cholesterol"), serum);
        newInstance.setValue(data.attribute("FastingBS"), sugar);
        newInstance.setValue(data.attribute("Re1ingECG"), electro);
        newInstance.setValue(data.attribute("MaxHR"), max);
        newInstance.setValue(data.attribute("ExerciseA0gi0a"), exer);
        newInstance.setValue(data.attribute("Oldpeak"), old);
        newInstance.setValue(data.attribute("ST_Slope"), slop);
        newInstance.setDataset(data);

        try {
            //Realizar la predicción
            double predictedClass = classifier.classifyInstance(newInstance);
            predictedClassName = data.classAttribute().value((int) predictedClass);

            //Mostrar el resultado de la predicción en la consola
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return Double.parseDouble(predictedClassName);

    }

    private double promptDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Entrada inválida. Introduce un número válido.");
            System.out.print(prompt);
            scanner.next();
        }
        return scanner.nextDouble();
    }


}
