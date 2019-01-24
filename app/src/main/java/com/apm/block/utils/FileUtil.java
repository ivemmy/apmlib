package com.apm.block.utils;

import com.apm.block.constants.BlockConstant;
import com.apm.block.model.BlockInfo;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyman on 2018/5/5.
 */
public class FileUtil {

    static {
        File lagDir = new File(BlockConstant.BLOCK_DIR);
        File leakDir = new File(BlockConstant.LEAK_DIR);
        if (!lagDir.exists()) {
            lagDir.mkdirs();
        }
        if (!leakDir.exists()) {
            leakDir.mkdirs();
        }
    }

    public static List<BlockInfo> readBlockInfos() {
        List<BlockInfo> blockInfos = new ArrayList<BlockInfo>();
        File file = new File(BlockConstant.BLOCK_DIR);
        File[] files = file.listFiles();
        for (File f : files) {
            String lag = readFromFile(f);
            BlockInfo blockInfo = new Gson().fromJson(lag, BlockInfo.class);
            blockInfos.add(blockInfo);
        }
        return blockInfos;
    }


    public static String readFromFile(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            // 读取文件，并且以utf-8的形式写出去
            BufferedReader bufread;
            String read;
            bufread = new BufferedReader(new FileReader(file));
            while ((read = bufread.readLine()) != null) {
                sb.append(read);
            }
            bufread.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    public static void writeToFile(String json) {
        FileOutputStream fop = null;
        File file;
        String content = json;
        try {

            file = new File(BlockConstant.BLOCK_DIR + "/" + System.currentTimeMillis() + ".txt");
            fop = new FileOutputStream(file);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            // get the content in bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
