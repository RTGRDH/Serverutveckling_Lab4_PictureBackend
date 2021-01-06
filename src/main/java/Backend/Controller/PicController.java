package Backend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class PicController {

    private static final String pathName = "/Users/carl-bernhardhallberg/Documents/Skola/Serverutveckling/";
    @CrossOrigin
    @RequestMapping(value = "/addPicture", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addPicture(@RequestParam String name, @RequestParam("picture") MultipartFile picture) throws IOException {
        FileOutputStream fout = null;
        try{
            createFolderIfNotExists(name);
            File newPicture = new File(pathName + name + "/" + picture.getName());
            newPicture.createNewFile();
            fout = new FileOutputStream(newPicture);
            fout.write(picture.getBytes());
        }catch(IOException e){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }finally{
            if(fout != null){ fout.close(); }
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value = "/getPicture", method = RequestMethod.GET)
    public ResponseEntity getPicture() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Taken from https://javatutorial.net/java-file-upload-rest-service
     * @param dirName
     * @throws SecurityException
     */
    private void createFolderIfNotExists(String dirName)
            throws SecurityException {
        File theDir = new File(pathName + "/" + dirName);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
    }
}
