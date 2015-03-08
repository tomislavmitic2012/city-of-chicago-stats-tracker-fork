package com.depaul.edu.se491.resource.favorites;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.depaul.edu.se491.service.favorites.FavoriteDatasetsService;

/**
 * Created by Tu Vo on 2/26/15.
 */
@Component
@Path("/favoriteDatasets")

public class FavoriteDatasetsResource {
	
	@Autowired
	FavoriteDatasetsService favoriteDatasetsService;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<FavoriteDatasets> getFavoriteDatasetsByUserIds(
			@QueryParam("id") Long id

	) {

		List<FavoriteDatasets> fdeUserById = favoriteDatasetsService
				.getFavoriteDatasetsByUserId(id);

		return fdeUserById;

	}
	
    @GET
    @Path("/get_favoriteDatasetsList_by_Uuid")
    @Produces({MediaType.APPLICATION_JSON})
    public List<FavoriteDatasets> getFavoriteDatasetsByUserUuid(
            @QueryParam("uuid") String  uuid
           
    ) {
    	
    	List<FavoriteDatasets> fdeByUserUuid = favoriteDatasetsService.getFavoriteDatasetsByUserUuid(uuid);

        return fdeByUserUuid;       
    }
    
    @GET
	@Path("/get_favoriteDatasets_by_id")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFavoriteDatasetsByUserId(
			@QueryParam("id") Long id
	) {
		FavoriteDatasets fdeByUserId = favoriteDatasetsService.getFavoriteDatasetsById(id);
		
		return Response.status(200).entity(fdeByUserId)
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.build();
	}
	
    
    @POST
    @Path("/update_favoriteDatasets")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    public Response FavoriteDatasets(FavoriteDatasets fde){

    	favoriteDatasetsService.updateFavoriteDatasets(fde);

        return Response.status(Response.Status.OK)
            .entity("FavoriteDatasets " + fde.getId()+ " was updated.")
            .build();
    }
    
    
    @POST
    @Path("/create_favoriteDatasets")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    public Response createFavoriteDatasets(FavoriteDatasets fde) {
        Long id = favoriteDatasetsService.createFavoriteDatasets(fde);
        FavoriteDatasets c_fde = favoriteDatasetsService.getFavoriteDatasetsById(id);

        return Response.status(Response.Status.OK)
                .entity("FavoriteDatasets " + c_fde.getId() + " was created.")
                .build();
    }

	
	

}
