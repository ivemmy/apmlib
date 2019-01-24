package com.apm.leak;

import com.apm.log.ApmLogger;
import com.apm.service.MemoryLeakTask;
import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.HeapDump;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hyman on 2018/6/5.
 */

public class LeakUploadService extends DisplayLeakService {

    @Override
    protected void afterDefaultHandling(HeapDump heapDump, AnalysisResult result, String leakInfo) {
        if (!result.leakFound || result.excludedLeak) {
            return;
        }

        ApmLogger.info("dumping memory leak info...");

        JSONObject obj = new JSONObject();
        try {
            obj.put("location", result.className);
            obj.put("leakTrace", result.leakTrace);
            obj.put("leakSize", result.retainedHeapSize);  //TODO 单位换算
            MemoryLeakTask.send(obj);
        } catch (JSONException e) {
            //e.printStackTrace();
            ApmLogger.info("error occurs while dumping memory leak info...");
        }

        //写入操作
        /*String newLeakInfo = leakInfo + "****************";
        String fileName = "/sdcard/leak/leak.txt";
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(file));
                bos.write(newLeakInfo.getBytes());
                bos.close();
            } else {

                file.delete();
                file.createNewFile();

                FileWriter fw = new FileWriter(fileName, true);
                fw.write(newLeakInfo);
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

}
