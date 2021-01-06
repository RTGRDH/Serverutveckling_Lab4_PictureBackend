package Backend.Controller;

import org.springframework.core.io.Resource;
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
    @Produces("image/png")
    @RequestMapping(value = "/getPicture", method = RequestMethod.GET)
    public File getPicture(@RequestParam("user") String user, @RequestParam("fileName") String fileName) {
        File downloadedPicture = new File(pathName + "/" + user  + "/" + fileName);
        //ResponseBuilder response = Response.ok((Object) downloadedPicture);
        //response.header("Content-Disposition", "attachment;filename=" + fileName);
        return downloadedPicture;
    }

    /**
     * Taken from https://javatutorial.net/java-file-upload-rest-service
     * creates a new directory with the users username if it does not exist
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
