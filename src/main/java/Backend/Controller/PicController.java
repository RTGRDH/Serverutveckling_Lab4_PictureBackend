package Backend.Controller;

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
    public File getPicture(@RequestParam String user, @RequestParam String fileName) {
        File downloadedPicture = new File(pathName + "/" + user  + "/" + fileName);
        //ResponseBuilder response = Response.ok((Object) downloadedPicture);
        //response.header("Content-Disposition", "attachment;filename=" + fileName);
        System.out.println(downloadedPicture.isFile());
        return downloadedPicture;
    }

    @CrossOrigin
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM)
    @RequestMapping(value = "/getPic", method = RequestMethod.GET)
    public Response testPic(@RequestParam String user, @RequestParam String fileName) {
        File downloadedPicture = new File(pathName + user  + "/" + fileName);
        System.out.println(downloadedPicture.isFile());
        return Response.ok(downloadedPicture, javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + downloadedPicture.getName() + ".png" + "\"")
                .build();
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
    @CrossOrigin
    @Produces("image/png")
    @GetMapping("/defaultPicture")
    public File getDefaultPic(){
        File pic = new File(pathName + "Poster.png");
        if(pic.isFile()){
            return pic;
        }
        return null;
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
