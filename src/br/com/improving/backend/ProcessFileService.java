package br.com.improving.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/upload")
public class ProcessFileService implements Webservice {
	private static final String ROOT_DIR = "/Users/conradmarquesperes/Documents/tests/Ninja/improvingFileReader/src/main/resources";
	private static final String FILE_NAME = "/clients.csv";
	private static int HTTP_COD_SUCESSO = 200;

	@Override
	public void processed(String fileName) throws IOException{
		URL url;
		try {
			url = new URL("http://localhost:8080/improving/");
			HttpURLConnection con = null;
			con = (HttpURLConnection) url.openConnection();

			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				throw new RuntimeException("HTTP error code : " + con.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			
		} catch (IOException e) {
			throw new IOException(e);
		}

	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream in,
			@FormDataParam("file") FormDataContentDisposition info) {
		try {
			java.nio.file.Path path = Paths.get(ROOT_DIR);
			File file = new File(ROOT_DIR + "/" + info.getFileName());
			if (!file.exists()) {
				Files.copy(in, file.toPath());
				return Response.status(Status.OK).build();
			} else
				return Response.status(Status.NOT_ACCEPTABLE).entity("Arquivo já existe, favor escolher outro arquivo!")
						.build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro no servidor:" + e.getMessage()).build();
		}
	}

	public static void main(String[] args) throws IOException {
		java.nio.file.Path path = Paths.get(ROOT_DIR+FILE_NAME);
		System.out.println(path.getFileName());
		new ProcessFileService().processed(null);
	}
}
