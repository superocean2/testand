package com.samsam.Envo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.omg.CORBA.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


/**
 * Created by NghiaTrinh on 7/6/2015.
 */
public class Helpers {
    public  static boolean isTouchedInRect(Rectangle r,float xInput,float yInput)
    {
        if (r.x<=xInput&&r.x+r.width>=xInput&&r.y<=yInput&&r.y+r.height>=yInput)
        {
            return true;
        }
        return  false;
    }

    public static String toCapitalize(String s)
    {
        String first = s.substring(0,1);
        String remain = s.substring(1,s.length()-1);
        return first.toUpperCase()+remain.toLowerCase();
    }
    public static int randomInt(int min, int max)
    {
        Random r = new Random();
        return r.nextInt(max-min)+min;
    }

    public static void saveHighScore(int score)
    {
        Preferences prefs = Gdx.app.getPreferences("profiles");
        prefs.putInteger( "highscore", score );
        prefs.flush();
    }

    public static int getHighScore()
    {
        Preferences prefs = Gdx.app.getPreferences( "profiles" );
        return prefs.getInteger("highscore",0);
    }
    public static void saveIsLocalDefaultPicture(boolean isSaved)
    {
        Preferences prefs = Gdx.app.getPreferences("localDefault");
        prefs.putBoolean("isSaved",isSaved);
        prefs.flush();
    }

    public static boolean getIsLocalDefaultPicture()
    {
        Preferences prefs = Gdx.app.getPreferences( "localDefault" );
        return prefs.getBoolean("isSaved",false);
    }

    public static void saveLoadedCategory(String categories)
    {
        Preferences prefs = Gdx.app.getPreferences( "loaded" );
        prefs.putString("loadedCategories", categories);
        prefs.flush();
    }

    public static String getLoadedCategory()
    {
        Preferences prefs = Gdx.app.getPreferences( "loaded" );
        return prefs.getString("loadedCategories","0;");
    }

    public static String post(String url,String json)
    {
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setContent(json);

        GameHttpListener listener = new GameHttpListener();
        Gdx.net.sendHttpRequest (httpPost,listener);
        return listener.result;
    }

    public static String get(String url)
    {
        Net.HttpRequest httpGet = new Net.HttpRequest(Net.HttpMethods.GET);
        httpGet.setUrl(url);
        GameHttpListener listener = new GameHttpListener();
        Gdx.net.sendHttpRequest(httpGet, listener);
        return listener.result;
    }

    public static String jsonSerialize(Objects o)
    {
        Json json = new Json();
        return json.toJson(o);
    }

    public static Objects jsonDeserialize(String js)
    {
        Json json = new Json();
        return json.fromJson(Objects.class,js);
    }

    public  static boolean containString(String[] arr,String st)
    {
        for (String s:arr)
        {
            if (s.equals(st)) return  true;
        }
        return false;
    }


    private static class GameHttpListener implements Net.HttpResponseListener
    {
        public String result;
        @Override
        public void handleHttpResponse(Net.HttpResponse httpResponse) {
            result= httpResponse.getResultAsString();
        }

        @Override
        public void failed(Throwable t) {
            result = "failed";
        }

        @Override
        public void cancelled() {

        }
    }

    public static void extractFile(String filename)
    {
        try {
            File dir = new File(Gdx.files.getLocalStoragePath() + "maindata");
            if (!dir.exists()) dir.mkdir();
            File zippicture = new File(dir.getPath() +"/"+ filename);
            ZipFile zipFile = new ZipFile(zippicture);
            if (zipFile.isEncrypted())
            {
                zipFile.setPassword("MainActivity");
            }
            zipFile.extractAll(dir.getPath());
            zippicture.delete();

        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static void  compressFile(ArrayList<File> files,String path)
    {
        try {
            FileHandle[] imageFiles = Gdx.files.internal("1/pictures/").list();
            FileHandle[] soundFiles = Gdx.files.internal("1/english/").list();
            File dir = new File(Gdx.files.getLocalStoragePath() + "maindata");
            if (!dir.exists()) dir.mkdir();
            File imageFolder = new File(dir.getPath() + "/1" + "/pictures");
            File dirCategory1 = new File(dir.getPath() + "/5");
            if (!dirCategory1.exists()) dirCategory1.mkdir();
            File dirPictures = new File(dirCategory1.getPath() + "/pictures");
            if (!dirPictures.exists()) dirPictures.mkdir();
            File dirSounds = new File(dirCategory1.getPath() + "/english");
            if (!dirSounds.exists()) dirSounds.mkdir();

            File zippicture = new File(dirPictures.getPath() + "/picture.zip");
            ZipFile zipFile = new ZipFile(zippicture);

            // Build the list of files to be added in the array list
            ArrayList filesToAdd = new ArrayList();

            for (FileHandle file : imageFiles) {
               filesToAdd.add(file);
            }
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);

            parameters.setPassword("123456789");

            // Sets the folder in the zip file to which these new files will be added.
            // In this example, test2 is the folder to which these files will be added.
            // Another example: if files were to be added to a directory test2/test3, then
            // below statement should be parameters.setRootFolderInZip("test2/test3/");
           // parameters.setRootFolderInZip("pictures/");

            // Now add files to the zip file
            //zipFile.addFiles(filesToAdd, parameters);
            zipFile.addFolder(imageFolder,parameters);
        } catch (ZipException e) {
            Gdx.app.log("ee",e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getDownloadHostName()
    {
        return "https://www.dropbox.com/";
    }
}
