package fr.isen.java2.db.daos;

import fr.isen.java2.db.entities.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDao {

    public List<Genre> listGenres() {
        List<Genre> genres = new ArrayList<>();
        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM genre");
             ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("idgenre");
                String name = resultSet.getString("name");
                Genre genre = new Genre(id, name);
                genres.add(genre);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return genres;
    }

    public Genre getGenre(String name) {
        Genre genre = null;
        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM genre WHERE name = ?")) {
            stmt.setString(1, name);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("idgenre");
                    String genreName = resultSet.getString("name");
                    genre = new Genre(id, genreName);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return genre;
    }

    public void addGenre(String name) {
        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement("INSERT INTO genre(name) VALUES(?)")) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace(); 
    }
}


//	public List<Genre> listGenres() {
//		throw new RuntimeException("Method is not yet implemented");
//	}

//	public Genre getGenre(String name) {
//		throw new RuntimeException("Method is not yet implemented");
//	}

//	public void addGenre(String name) {
//		throw new RuntimeException("Method is not yet implemented");
//	}
//}
