package Backend.Controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

@RestController
public class FileController {

    private static final String pathName = "/data/";
    @CrossOrigin
    @RequestMapping(value = "/addFile", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addPicture(@RequestParam String name, @RequestParam("file") MultipartFile file) throws IOException {
        FileOutputStream fout = null;
        try{
            createFolderIfNotExists(name);
            File newPicture = new File(pathName + name + "/" + file.getOriginalFilename());
            newPicture.createNewFile();
            fout = new FileOutputStream(newPicture);
            fout.write(file.getBytes());
        }catch(IOException e){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }finally{
            if(fout != null){ fout.close(); }
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @CrossOrigin
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM)
    @RequestMapping(value = "/getFile", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPicture(@RequestParam String user, @RequestParam String fileName) throws IOException {
        File downloadedPicture = new File(pathName + user  + "/" + fileName);
        if(downloadedPicture.isFile()){
            byte[] img = FileUtils.readFileToByteArray(downloadedPicture);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(img);
        }
        return null;
    }

    @CrossOrigin
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<String> getAll(@RequestParam String currentUser) {
        File dir = new File(pathName.substring(0, pathName.length()-1));
        ArrayList<Files> result = new ArrayList<>();
        for(File f : dir.listFiles()){
            if(!f.getName().equals(currentUser)) {
                if (f.isDirectory()) {
                    Files newF = new Files(f.getName());
                    for (File x : f.listFiles()) {
                        newF.addFile(x.getName());
                    }
                    result.add(newF);
                }
            }
        }
        for(Files f : result){
            System.out.println(f.user);
            for(String s : f.files){
                System.out.println(s);
            }
        }
        ObjectMapper om = new ObjectMapper();
        String output = "";
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            output = om.writeValueAsString(result);
        }catch(JsonProcessingException e){
            output = "Error";
            return ResponseEntity.ok(output);
        }
        return ResponseEntity.ok(output);
    }

    public class Files{
        private String user;
        private ArrayList<String> files;

        public Files(String u){
            user = u;
            files = new ArrayList<>();
        }

        public void addFile(String filename){
            files.add(filename);
        }

        @Override
        public String toString(){
            String result = "";
            result = user;
            result += "\n";
            for(String s : files){
                result += s + ", ";
            }
            result = result.substring(0, result.length()-2);
            return result;
        }
    }

    @CrossOrigin
    @GetMapping("/doesUserHavePicture")
    public ResponseEntity<Boolean> getUserInfoFiles(@RequestParam String user){
        File file = new File(pathName + user.toLowerCase());
        if(file.exists()){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

    /**
     * Taken from https://javatutorial.net/java-file-upload-rest-service
     * creates a new directory with the users username if it does not exist
     * @param dirName
     * @throws SecurityException
     */
    private void createFolderIfNotExists(String dirName)
            throws SecurityException {
        File theDir = new File(pathName + dirName.toLowerCase());
        if (!theDir.exists()) {
            theDir.mkdir();
        }
    }
}
