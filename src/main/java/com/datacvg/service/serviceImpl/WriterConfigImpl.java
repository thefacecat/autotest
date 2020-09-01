package com.datacvg.service.serviceImpl;


import com.datacvg.service.WriterConfig;

import java.io.*;

public class WriterConfigImpl implements WriterConfig {

    @Override
    public void writer(String key, String value) {
        String pathM =  this.getClass().getClassLoader().getResource("testng.xml").getPath();

        String path = pathM.substring(0,pathM.lastIndexOf("/"));
        File file = new File(path+"/auth.properties");
        try {
            if(file.exists()) {
                //如果存在文件，且包含填写key，则删除文件，重新新建文件写入
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String rl;
                while ((rl = bufferedReader.readLine()) != null) {
                    if (rl.contains(key)) {
                        bufferedReader.close();
                        file.delete();
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                        bufferedWriter.write(key + "=" + value + "\n");
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        return;
                    }
                }

                //文件存在，key值不存在时
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                bufferedWriter.write(key + "=" + value + "\n");
                bufferedWriter.flush();
                bufferedWriter.close();
                return;
                //文件不存在时
            }else {
                file.createNewFile();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
                bufferedWriter.write(key+"="+value+"\n");
                bufferedWriter.newLine();
                bufferedWriter.flush();
                bufferedWriter.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
