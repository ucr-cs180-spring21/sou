package group1.cs180_proj;

import java.io.*;
import java.util.ArrayList;

public class SerializeIO extends FileIO {


    private class FileTest implements FileFilter {

        @Override
        public boolean accept(File pathname) {

            if (pathname.getName().toLowerCase().endsWith(".ser")) {
                return true;
            }
            return false;
        }

    }

    private ArrayList<Uber> data;
    private final String projDir = System.getProperty("user.dir");

    SerializeIO(ArrayList<Uber> d) {
        data = d;
    }

    SerializeIO() {
    }

    public void backupData(String name) {
        try {
            FileOutputStream fo = new FileOutputStream(name + ".ser");
            ObjectOutputStream oo = new ObjectOutputStream(fo);

            oo.writeObject(data);
            oo.flush();
            oo.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<String> getFiles() {
        ArrayList<String> ret = new ArrayList<>();
        File dir = new File(projDir);

        File[] files = dir.listFiles(new SerializeIO.FileTest());

        for (File f : files) {
            ret.add(f.toString());
        }


        return ret;
    }

    public ArrayList<Uber> importData(String file){
        ArrayList<Uber> ret = new ArrayList();

        try{
            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(fi);

            ret = (ArrayList<Uber>) oi.readObject();
            oi.close();

        }catch (Exception e) {
            e.printStackTrace();
        }


        return ret;

    }
}