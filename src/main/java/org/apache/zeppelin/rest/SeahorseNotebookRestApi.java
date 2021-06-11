/**
 * 
 */
package org.apache.zeppelin.rest;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.zeppelin.annotation.ZeppelinApi;
import org.apache.zeppelin.conf.ZeppelinConfiguration;
import org.apache.zeppelin.notebook.AuthorizationService;
import org.apache.zeppelin.notebook.Note;
import org.apache.zeppelin.notebook.Notebook;
import org.apache.zeppelin.notebook.scheduler.SchedulerService;
import org.apache.zeppelin.search.SearchService;
import org.apache.zeppelin.server.JsonResponse;
import org.apache.zeppelin.service.AuthenticationService;
import org.apache.zeppelin.service.JobManagerService;
import org.apache.zeppelin.service.NotebookService;
import org.apache.zeppelin.socket.NotebookServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * @author subin.soman
 * @version 1.0.0.0
 * @since 10 Jun, 2021
 *
 *        Development History
 *
 *        Date Author Description
 *        ------------------------------------------------------------------------------------------
 *        10 Jun, 2021 subin.soman
 *        ------------------------------------------------------------------------------------------
 */
@Path("/seahorse/notebook")
@Produces("application/json")
@Singleton
public class SeahorseNotebookRestApi extends AbstractRestApi {

	private static final Logger		LOGGER	= LoggerFactory.getLogger(SeahorseNotebookRestApi.class);
	private static final Gson		GSON	= new Gson();

	private ZeppelinConfiguration	zConf;
	private Notebook				notebook;
	private NotebookServer			notebookServer;
	private SearchService			noteSearchService;
	private AuthorizationService	authorizationService;
	private NotebookService			notebookService;
	private JobManagerService		jobManagerService;
	private AuthenticationService	authenticationService;
	private SchedulerService		schedulerService;

	@Inject
	public SeahorseNotebookRestApi(Notebook notebook, NotebookServer notebookServer, NotebookService notebookService, SearchService search, AuthorizationService authorizationService, ZeppelinConfiguration zConf, AuthenticationService authenticationService, JobManagerService jobManagerService,
			SchedulerService schedulerService) {
		super(authenticationService);
		this.notebook = notebook;
		this.notebookServer = notebookServer;
		this.notebookService = notebookService;
		this.jobManagerService = jobManagerService;
		this.noteSearchService = search;
		this.authorizationService = authorizationService;
		this.zConf = zConf;
		this.authenticationService = authenticationService;
		this.schedulerService = schedulerService;
	}

	/**
	 * Import new note REST API. TODO(zjffdu) support to import jupyter note.
	 *
	 * @param noteJson
	 *            - note Json
	 * @return JSON with new note ID
	 * @throws IOException
	 */
	@POST
	@Path("import")
	@ZeppelinApi
	public Response importSeahorseNote(@QueryParam("notePath") String notePath, String noteJson) throws IOException {
		//write your logic here
		Note note = notebookService.importNote(notePath, noteJson, getServiceContext(), new RestServiceCallback());
		return new JsonResponse<>(Status.OK, "", note.getId()).build();
	}
	
	
}
