/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.File;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.store.ContentFeatureCollection;
import org.geotools.data.store.ContentFeatureSource;

/**
 *
 * @author C. Levallois
 */
public class Main {

    static String sourceFilePath;
    static String sourceFileName;
    static boolean testOneState = false;
    static double precision= 0.05;
    // the higher the number, the LESSER the precision

    public static void main(String[] args) throws Exception {

        if (args.length == 0)
            throw new Exception("This application expects the shapefile-path as argument.");

        File sourceFile = new File(args[0]);

        sourceFilePath = sourceFile.getAbsolutePath();
        sourceFilePath = sourceFilePath.substring(0, sourceFilePath.lastIndexOf(File.separator));

        sourceFileName = sourceFile.getName();

        if (!sourceFile.exists()) {
            throw new Exception("The specified path doesn't exist: " + sourceFilePath);
        }

        if (!sourceFileName.endsWith(".shp")) {
            throw new Exception("The shapefile's extension must be '.shp'. (" + sourceFilePath + ")");
        }

        ShapefileDataStore dataStore = new ShapefileDataStore(sourceFile.toURL());
        ContentFeatureSource featureSource = dataStore.getFeatureSource();
        ContentFeatureCollection featureCollection = featureSource.getFeatures();
        
        CoordinatesExtractor extractor = new CoordinatesExtractor(featureCollection);
        extractor.extract(testOneState);
        
        CoordinatesWriter writer = new CoordinatesWriter();
        writer.write(precision);
        dataStore.dispose();

    }
}
