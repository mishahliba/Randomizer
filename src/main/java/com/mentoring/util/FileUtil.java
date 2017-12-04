package com.mentoring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Class responsible for File manipulation.
 */
public class FileUtil {

    private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

    public static File getFile(String fileName) {
        File persons = new File(fileName);
        try {
            persons.createNewFile();
        } catch (IOException e) {
            LOG.error("cannot create file: {} ", e.getCause());
        }
        return persons;
    }
}
