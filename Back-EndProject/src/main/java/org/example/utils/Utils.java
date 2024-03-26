package org.example.utils;

import org.example.mvc.repository.UploadFileRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Utils {
    @Autowired
    static UploadFileRepository uploadFileRepository;

    public static String encodingUrl(String url){
        String encodedString;
        try {
            encodedString = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding userName", e);
        }

        return encodedString;
    }

    public static String checkDuplicateFileName(String fileName, int includeDir) {
        List<String> fileNames = uploadFileRepository.checkFileName(includeDir);

        int idxOfDot = fileName.lastIndexOf(".");
        String prefix = fileName.substring(idxOfDot + 1, fileName.length());

        String result = fileName;

        if (fileNames.contains(fileName)) {
            Pattern p = Pattern.compile("(.*)\\s\\(([0-9]+)\\)[.].*");
            Matcher m = p.matcher(fileName);

            String originalFileName = fileName.substring(0, fileName.lastIndexOf("."));

            if (m.find()) {
                originalFileName = m.group(1);
            }

            String dest = "";
            for (int i = 1; i < Integer.MAX_VALUE; i++) {
                dest = String.format("%s (%d).%s", originalFileName, i, prefix);
                if (!fileNames.contains(dest)) break;
            }
            result = dest;
        }

        return result;
    }

    public static void compressDirectory(Path path,String fileName,ZipOutputStream zipOut) throws IOException {
        File dir = path.toFile();
        for(File file : dir.listFiles()){
            if(file.isDirectory()){
                compressDirectory(file.toPath(),fileName + "/" + file.getName(),zipOut);
            }else{
                ZipEntry zipEntry = new ZipEntry(fileName + "/" + file.getName());
                zipOut.putNextEntry(zipEntry);
                Files.copy(file.toPath(),zipOut);
                zipOut.closeEntry();
            }
        }
    }
}

