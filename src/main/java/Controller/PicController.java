package Controller;

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
    @CrossOrigin
    @PostMapping("/hej")
    public ResponseEntity gg(){
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value = "/addPicture", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addPicture(@RequestParam("picture") MultipartFile picture) throws IOException {
        System.out.println("Vi kom in");
        File newPicture = new File("/Användare/carl-bernhardhallberg/Dokument/Skola/Serverutveckling/" + "pictureName.png");
        newPicture.createNewFile();
        FileOutputStream fout = new FileOutputStream(newPicture);
        fout.write(picture.getBytes());
        fout.close();
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value = "/getPicture", method = RequestMethod.GET)
    public ResponseEntity getPicture() {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
