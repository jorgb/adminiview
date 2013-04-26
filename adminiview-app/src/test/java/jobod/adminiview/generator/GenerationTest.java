package jobod.adminiview.generator;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.Test;

public class GenerationTest {

	@Test
	public void generateSimpleTemplateFile() {
		//Velocity.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM, this);
				
		Properties p = new Properties(); 
		p.setProperty("file.resource.loader.path", "src/test/resources");
		
		Velocity.init(p);

		VelocityContext context = new VelocityContext();
		
		Template t = Velocity.getTemplate("simplefile.vm");
		
		StringWriter w = new StringWriter();

		t.merge(context, w);
     
        assertEquals("<html>\n<body>\nHello Velocity World!\n</body>\n<html>", w.toString());
	}	
}
