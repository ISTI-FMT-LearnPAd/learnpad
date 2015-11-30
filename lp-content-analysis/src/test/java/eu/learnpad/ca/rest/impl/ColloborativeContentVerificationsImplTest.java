package eu.learnpad.ca.rest.impl;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;

import eu.learnpad.ca.gate.GateServletContextListener;
import eu.learnpad.ca.rest.data.collaborative.AnnotatedCollaborativeContentAnalysis;
import eu.learnpad.ca.rest.data.collaborative.CollaborativeContentAnalysis;






public class ColloborativeContentVerificationsImplTest extends JerseyTest{

	
	@Override
	protected TestContainerFactory getTestContainerFactory()  {
	    return new GrizzlyWebTestContainerFactory();
	}
	@Override
    protected DeploymentContext configureDeployment() {
		 forceSet(TestProperties.CONTAINER_PORT, "0");
		    return ServletDeploymentContext.forServlet(new ServletContainer(new ResourceConfig(ColloborativeContentVerificationsImpl.class)))
		                                   .addListener(GateServletContextListener.class)
		                                   .build();
         
    }

	@Override
	protected Application configure() {
		return new ResourceConfig(ColloborativeContentVerificationsImpl.class);
	}

	@Test
	public void checkCollaborativeContentAnalysis() throws JAXBException {
		
		InputStream is = ColloborativeContentVerificationsImplTest.class.getClassLoader().getResourceAsStream("CollaborativeContentXMLAll.xml");
		assertNotNull(is);
		JAXBContext jaxbContexti = JAXBContext.newInstance(CollaborativeContentAnalysis.class);

		Unmarshaller jaxbUnmarshaller1 = jaxbContexti.createUnmarshaller();
		CollaborativeContentAnalysis collaborativeContentInput = (CollaborativeContentAnalysis) jaxbUnmarshaller1.unmarshal(is);

		Entity<CollaborativeContentAnalysis> entity = Entity.entity(collaborativeContentInput,MediaType.APPLICATION_XML);
		Response response =  target("/learnpad/ca/validatecollaborativecontent").request(MediaType.APPLICATION_XML).post(entity);

		String id = response.readEntity(String.class);

		assertNotNull(response);
		assertTrue(id!="");
		String status = "IN PROGRESS";
		while(!status.equals("OK")){
			status =  target("/learnpad/ca/validatecollaborativecontent/"+id+"/status").request().get(String.class);

			assertNotNull(status);

		}

		Response annotatecontent =  target("/learnpad/ca/validatecollaborativecontent/"+id).request().get();

		ArrayList<AnnotatedCollaborativeContentAnalysis> res =	annotatecontent.readEntity(new GenericType<ArrayList<AnnotatedCollaborativeContentAnalysis>>() {});
		for (AnnotatedCollaborativeContentAnalysis annotatedCollaborativeContentAnalysis : res) {
			JAXBContext jaxbCtx;
			try {
				jaxbCtx = javax.xml.bind.JAXBContext.newInstance(AnnotatedCollaborativeContentAnalysis.class);

				Marshaller marshaller = jaxbCtx.createMarshaller();
				marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
				marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				//marshaller.marshal(annotatedCollaborativeContentAnalysis, System.out);
				String type = annotatedCollaborativeContentAnalysis.getType();
				OutputStream os = new FileOutputStream( "nosferatu"+type+".xml" );
				marshaller.marshal( annotatedCollaborativeContentAnalysis, os );
			} catch (JAXBException | FileNotFoundException  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		assertNotNull(res);
		assertNotNull(annotatecontent);
	}

	

}
