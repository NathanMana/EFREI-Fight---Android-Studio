package fr.android.project_vyas_manaranche.services;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import fr.android.project_vyas_manaranche.models.Fighter;
import fr.android.project_vyas_manaranche.models.PlayerRoundStatistics;

public class PlayerRoundStatisticsService {

    public static boolean addPlayerRoundStatistics(PlayerRoundStatistics playerRoundStatistics) {

        try {
            DatabaseService dbService = new DatabaseService();

            PreparedStatement statement = dbService.connection.prepareStatement(
                    "INSERT INTO player_round_statistics(nbHook, nbOvercute, nbDirect, nbKick, round, fighter_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, playerRoundStatistics.getNbHook());
            statement.setInt(2, playerRoundStatistics.getNbOvercute());
            statement.setInt(3, playerRoundStatistics.getNbDirect());
            statement.setInt(4, playerRoundStatistics.getNbKick());
            statement.setInt(5, playerRoundStatistics.getRound());
            statement.setInt(6, playerRoundStatistics.getFighterId());

            int affectedRow = statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePlayerRoundStatistics(PlayerRoundStatistics playerRoundStatistics) {

        try {
            DatabaseService dbService = new DatabaseService();

            PreparedStatement statement = dbService.connection.prepareStatement(
                    "UPDATE player_round_statistics SET nbHook = ?, nbOvercute = ? " +
                            ", nbDirect = ?, nbKick = ? " +
                            "WHERE round = ? AND fighter_id = ?");
            statement.setInt(1, playerRoundStatistics.getNbHook());
            statement.setInt(2, playerRoundStatistics.getNbOvercute());
            statement.setInt(3, playerRoundStatistics.getNbDirect());
            statement.setInt(4, playerRoundStatistics.getNbKick());
            statement.setInt(5, playerRoundStatistics.getRound());
            statement.setInt(6, playerRoundStatistics.getFighterId());

            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<PlayerRoundStatistics> GetListPlayerRoundStatistics(int refFighter) {

        try {

            DatabaseService dbService = new DatabaseService();
            PreparedStatement statement = dbService.connection.prepareStatement(
                    "SELECT * FROM player_round_statistics WHERE fighter_id = ?");
            statement.setInt(1, refFighter);

            ResultSet resultSet = statement.executeQuery();

            List<PlayerRoundStatistics> listStatistics = new LinkedList<PlayerRoundStatistics>();

            while(resultSet.next()) {
                listStatistics.add(
                        new PlayerRoundStatistics(
                                resultSet.getInt("nbHook"),
                                resultSet.getInt("nbOvercute"),
                                resultSet.getInt("nbDirect"),
                                resultSet.getInt("nbKick"),
                                resultSet.getInt("round"),
                                resultSet.getInt("fighter_id")
                        )
                );
            }

            dbService.connection.close();

            return listStatistics;
        }
        catch (Exception e) {
            System.out.println("Erreur  : "  + e);
        }

        return null;
    }
}
