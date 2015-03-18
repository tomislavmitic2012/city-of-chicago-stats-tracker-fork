package com.depaul.edu.se491.service;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import com.depaul.edu.se491.dao.favorites.FavoriteDatasetsDao;
import com.depaul.edu.se491.dao.favorites.FavoriteDatasetsEntity;
import com.depaul.edu.se491.errorhandling.AppException;
import com.depaul.edu.se491.resource.favorites.FavoriteDatasets;
import com.depaul.edu.se491.service.favorites.FavoriteDatasetsService;
import com.depaul.edu.se491.service.favorites.FavoriteDatasetsServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class FavoriteDatasetsServiceTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private static final Long USER_ID = Long.valueOf(1);
	private static final String UUID = "12sdf13";
	private static final String USER_QUERY = "select";
	private static final String USER_NOTES = "search";

	FavoriteDatasetsService fdeService;

	@Mock
	FavoriteDatasetsDao mockFdeDao;

	@Before
	public void setUp() throws Exception {
		fdeService = new FavoriteDatasetsServiceImpl();
		;
		fdeService.setFavoriteDatasetsDao(mockFdeDao);
	}

	@Test
	public void testGetFavoriteDatasetsByUserId_successful()
			throws AppException {

		FavoriteDatasetsEntity fdeEntity = new FavoriteDatasetsEntity();
		List<FavoriteDatasetsEntity> fdeEntityList = new LinkedList<>();
		fdeEntityList.add(fdeEntity);

		when(mockFdeDao.getFavoriteDatasetsByUserId(USER_ID)).thenReturn(
				fdeEntityList);

		// Execution
		List<FavoriteDatasets> result = fdeService
				.getFavoriteDatasetsByUserId(USER_ID);

		// Verification
		verify(mockFdeDao, times(1)).getFavoriteDatasetsByUserId(USER_ID);

		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.size());
	}

	@Test
	public void testGetFavoriteDatasetsByUuId_successful() throws AppException {

		FavoriteDatasetsEntity fdeEntity = new FavoriteDatasetsEntity();
		List<FavoriteDatasetsEntity> fdeEntityList = new LinkedList<>();
		fdeEntityList.add(fdeEntity);

		when(mockFdeDao.getFavoriteDatasetsByUserUuid(UUID)).thenReturn(
				fdeEntityList);

		// Execution
		List<FavoriteDatasets> result = fdeService
				.getFavoriteDatasetsByUserUuid(UUID);

		// Verification
		verify(mockFdeDao, times(1)).getFavoriteDatasetsByUserUuid(UUID);

		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.size());

	}

	@Test
	public void testGetFavoriteDatasetsById_successful() throws AppException {

		FavoriteDatasetsEntity fdeEntity = new FavoriteDatasetsEntity();
		fdeEntity.setId(USER_ID);

		when(mockFdeDao.getFavoriteDatasetsById(USER_ID)).thenReturn(fdeEntity);

		// Execution
		FavoriteDatasets result = fdeService.getFavoriteDatasetsById(USER_ID);

		// Verification
		verify(mockFdeDao, times(1)).getFavoriteDatasetsById(USER_ID);

		Assert.assertNotNull(result);
		Assert.assertEquals(USER_ID, result.getId());
	}

	@Test
	public void testUpdateFavoriteDatasets_successful() throws AppException {

		FavoriteDatasetsEntity fdeEntity = new FavoriteDatasetsEntity();
		fdeEntity.setId(USER_ID);

		when(mockFdeDao.getFavoriteDatasetsById(USER_ID)).thenReturn(fdeEntity);
		doNothing().when(mockFdeDao).updateFavoriteDatasets(
				any(FavoriteDatasetsEntity.class));

		FavoriteDatasets fde = new FavoriteDatasets(fdeEntity);
		fde.setNotes(USER_NOTES);
		fde.setQuery(USER_QUERY);

		fdeService.updateFavoriteDatasets(fde);

		// Verification
		verify(mockFdeDao).getFavoriteDatasetsById(USER_ID);
		verify(mockFdeDao).updateFavoriteDatasets(
				any(FavoriteDatasetsEntity.class));

		Assert.assertTrue(fde.getQuery() == USER_QUERY);
		Assert.assertTrue(fde.getQuery() == USER_QUERY);
	}

	@Test
	public void testUpdateFavoriteDatasets_unsuccessful() {

		when(mockFdeDao.getFavoriteDatasetsById(USER_ID)).thenReturn(null);

		FavoriteDatasetsEntity fdeEntity = new FavoriteDatasetsEntity();
		FavoriteDatasets fde = new FavoriteDatasets(fdeEntity);

		fde.setId(USER_ID);
		
		try {
			fdeService.updateFavoriteDatasets(fde);
			Assert.fail("Should have thrown exception");
		} catch (AppException e) {
			verify(mockFdeDao).getFavoriteDatasetsById(USER_ID);
			Assert.assertEquals(e.getCode(), 409);
		}

	}

	@Test
	public void testCreateFavoriteDataset() throws AppException {

		when(mockFdeDao.getFavoriteDatasetsById(USER_ID)).thenReturn(null);
		when(
				mockFdeDao
						.createFavoriteDatasets(any(FavoriteDatasetsEntity.class)))
				.thenReturn(USER_ID);

		FavoriteDatasets fde = new FavoriteDatasets();
		fde.setId(Long.valueOf(1));
		fde.setNotes(USER_NOTES);
		fde.setQuery(USER_QUERY);
		
		// Execution
		Long createFavoriteDataset = fdeService.createFavoriteDatasets(fde);
		
		//Verification
		verify(mockFdeDao).getFavoriteDatasetsById(USER_ID);

		verify(mockFdeDao, times(1)).createFavoriteDatasets(
				any(FavoriteDatasetsEntity.class));

		Assert.assertSame(Long.valueOf(1), createFavoriteDataset);

	}

	@Test
	public void testCreateFavoriteDataset_missingNotes() throws AppException {

		exception.expect(AppException.class);
		exception.expectMessage("Provided data not sufficient for insertion");

		FavoriteDatasets fde = new FavoriteDatasets();
		fde.setNotes(null);
		fdeService.createFavoriteDatasets(fde);

	}

	@Test
	public void testCreateFavoriteDataset_missingQuery() throws AppException {

		exception.expect(AppException.class);
		exception.expectMessage("Provided data not sufficient for insertion");

		FavoriteDatasets fde = new FavoriteDatasets();
		fde.setQuery(null);
		fdeService.createFavoriteDatasets(fde);

	}

}