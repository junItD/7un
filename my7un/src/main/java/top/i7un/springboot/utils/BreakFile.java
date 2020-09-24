package top.i7un.springboot.utils;

import java.io.*;

public class BreakFile {

    public static void main(String args[]) {
            String fileName = "/Users/zhaojun/Desktop/0.csv";
            String pathName = "/Users/zhaojun/Desktop/x";
        splitFile(fileName, pathName);
    }

    private static void splitFile(String fileName, String pathName) {
        try {
            FileReader read = new FileReader(fileName);
            BufferedReader br = new BufferedReader(read);
            String row;

            int rownum = 1;

            int fileNo = 1;

            FileWriter fw = new FileWriter(pathName + fileNo + ".csv");
            while ((row = br.readLine()) != null) {
                rownum++;
                fw.append(row + "\r\n");

                if ((rownum / 40000) > (fileNo - 1)) {
                    fw.close();
                    fileNo++;
                    fw = new FileWriter(pathName + fileNo + ".csv");
                }
            }
            fw.close();
            System.out.println("rownum=" + rownum + ";fileNo=" + fileNo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}