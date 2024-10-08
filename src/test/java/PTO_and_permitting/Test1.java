package PTO_and_permitting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

@SuppressWarnings("deprecation")
public class Test1 {

		private static final String APPLICATION_NAME = "wattmonk projects";
		private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		private static final String TOKENS_DIRECTORY_PATH = "https://oauth2.googleapis.com/token";

		/**
		 * Global instance of the scopes required by this quickstart. If modifying these
		 * scopes, delete your previously saved tokens/ folder.
		 */
		private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
		private static final String CREDENTIALS_FILE_PATH = "C:\\Users\\Admin\\eclipse-workspace\\Pto_permitting_Flow\\src\\main\\resources\\client_secret_1097480448354-74of3gpa5i2ub8jlci2o5hmbk26rgj4o.apps.googleusercontent.com.j";

		/**
		 * Creates an authorized Credential object.
		 * 
		 * @param HTTP_TRANSPORT The network HTTP Transport.
		 * @return An authorized Credential object.
		 * @throws IOException If the credentials.json file cannot be found.
		 */
		
		private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
			// Load client secrets.
			InputStream in = GetSheetData.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
			if (in == null) {
				throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
			}
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

			// Build flow and trigger user authorization request.
			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
					clientSecrets, SCOPES)
							.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
							.setAccessType("offline").build();
			LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
			return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		}

		/**
		 * Prints the names and majors of students in a sample spreadsheet:
		 * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
		 */
		public static List<List<Object>> getData(String range) throws IOException, GeneralSecurityException {
			// Build a new authorized API client service.
			final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			final String spreadsheetId = "1rHurzyfcZsHHAqj2yK5kxLE1iLk06Q2pmvihozXRbmo";
			
			Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
					.setApplicationName(APPLICATION_NAME).build();
			ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
			List<List<Object>> values = response.getValues();
			if (values == null || values.isEmpty()) {
				System.out.println(values);
				return null;
			} else {
				return values;
			}
		}

	}
	
