package com.kralite.workflow.test;

import com.kralite.workflow.engine.RunEngine;
import com.kralite.workflow.manager.DataManager;
import com.kralite.workflow.manager.FlowManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.URLDecoder;

/**
 * Created by ChenDaLin on 2019/2/3.
 */
public class ParserTest extends BaseTest{
    @Autowired
    RunEngine runEngine;

    @Autowired
    DataManager dataManager;

    @Autowired
    FlowManager flowManager;

    @Test
    public void testRun(){
        String xml = readResourceFile("flow_file/groovy_test.xml");

        runEngine.loadFlow(xml);
        runEngine.run();
    }

    public String readResourceFile(String fileName) {
        String encoding = "UTF-8";
        File file = new File(URLDecoder.decode(this.getClass().getClassLoader().getResource(fileName).getPath()));
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
}
