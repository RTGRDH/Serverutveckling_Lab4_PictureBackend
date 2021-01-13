package Backend.PictureBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import Backend.Controller.*;

@SpringBootApplication
@ComponentScan(basePackageClasses = FileController.class)
public class PictureBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PictureBackendApplication.class, args);
	}

}
