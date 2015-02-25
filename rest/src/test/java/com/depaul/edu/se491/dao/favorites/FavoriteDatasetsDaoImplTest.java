package com.depaul.edu.se491.dao.favorites;

import com.depaul.edu.se491.dao.user.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Tom Mitic on 2/15/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/applicationContext-Test.xml")
public class FavoriteDatasetsDaoImplTest {

    @Autowired
    protected FavoriteDatasetsDao dao;

    @Autowired
    protected UserDao u_dao;

    @Test
    public void getFavoriteDatasetsByUserIdTest() {
        List<FavoriteDatasetsEntity> f_s = dao.getFavoriteDatasetsByUserId(1L);
        assertNotNull(f_s);
        assertEquals("The length of the list is 2", 2, f_s.size());
        assertNotEquals("The ordering of the datasets is by created date asc", "{j: {$ne: 4}, k: {$gt: 15} }", f_s.get(0).getQuery());
    }

    @Test
    public void getFavoriteDatasetsByUserUuidTest() {
        List<FavoriteDatasetsEntity> f_s = dao.getFavoriteDatasetsByUserUuid("067e6162-3b6f-4ae2-a171-2470b63dff00");
        assertNotNull(f_s);
        assertEquals("The length of the list is 2", 2, f_s.size());
        assertNotEquals("The ordering of the datasets is by created date asc", "{j: {$ne: 4}, k: {$gt: 15} }", f_s.get(0).getQuery());
    }

    @Test
    public void getFavoriteDatasetsByIdTest() {
        FavoriteDatasetsEntity f = dao.getFavoriteDatasetsById(3L);
        assertNotNull(f);
        assertEquals("The id of the favorite data set is 3", new Long(3), f.getId());
        assertNotEquals("The owner of this favorite data set is not O'Mally", "O'Mally", f.getUser().getLastName());

        f = dao.getFavoriteDatasetsById(4L);
        assertNotNull(f);
        assertEquals("The id is null", null, f.getId());
    }

    @Test
    public void updateFavoriteDatasetsTest() {
        FavoriteDatasetsEntity f = dao.getFavoriteDatasetsById(3L);
        f.setQuery("{j: {$ne: 2}, k: {$lt: 100} }");
        dao.updateFavoriteDatasets(f);
        f = dao.getFavoriteDatasetsById(3L);
        assertNotEquals("The query is not different", "{j: {$ne: 2}, k: {$gt: 100} }", f.getQuery());
    }

    @Test
    public void createFavoriteDatasetsTest() {
        FavoriteDatasetsEntity f = new FavoriteDatasetsEntity();
        f.setQuery("{j: {$ne: 2}, k: {$lt: 100} }");
        f.setCreatedDate(new Date());
        f.setUser(u_dao.getUserById(2L));
        f.setNotes("Creating a favorite datasets for Lisa George");
        Long id = dao.createFavoriteDatasets(f);
        assertNotNull("An id for the new favorite dataset was created", id);
        assertEquals("The id is equal to 4", new Long(4), id);
    }
}
