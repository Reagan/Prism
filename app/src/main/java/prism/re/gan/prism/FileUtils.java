package prism.re.gan.prism;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by rmbitiru on 3/28/15.
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getName() ;

    public static boolean fileExists (String fileName) {
        return new File(fileName).exists() ;
    }

    public static String readFile (Context context, String fileName) {
       String fileContents = "" ;
       try {
           BufferedReader inputReader = new BufferedReader(new InputStreamReader(context.openFileInput(fileName))) ;
           String inputString ;
           StringBuffer stringBuffer = new StringBuffer() ;
           while ((inputString = inputReader.readLine()) != null) {
               stringBuffer.append(inputString) ;
           }
           fileContents = inputString ;
       } catch (IOException ex) {
           Log.e(TAG, ex.toString()) ;
       }
        return fileContents ;
    }

    public static void writeToFile (Context context, String fileName, String content)
        throws JSONException, FileNotFoundException, IOException {

        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_ENABLE_WRITE_AHEAD_LOGGING) ;
        fos.write(content.getBytes());
        fos.close();
    }
}
