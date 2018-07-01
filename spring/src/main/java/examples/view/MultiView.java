package examples.view;

import org.springframework.web.servlet.view.JstlView;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

public class MultiView extends JstlView {

	@Override
	public boolean checkResource(Locale locale) throws Exception {
		System.out.println("paths: " + Paths.get(this.getServletContext().getRealPath("/")+getUrl()));
		return Files.exists(Paths.get(this.getServletContext().getRealPath("/")+getUrl()));
	}
}