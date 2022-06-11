package fr.android.project_vyas_manaranche.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import fr.android.project_vyas_manaranche.models.Fighter;
import fr.android.project_vyas_manaranche.models.PlayerRoundStatistics;

public class PlayerRoundStatisticsService {

    public static List<PlayerRoundStatistics> GetListPlayerRoundStatistics(int refFighter) {

        try {

            DatabaseService dbService = new DatabaseService();
            PreparedStatement statement = dbService.connection.prepareStatement(
                    "SELECT * FROM fighter WHERE fighter_id = ?");
            statement.setInt(1, refFighter);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return null;

            List<PlayerRoundStatistics> listStatistics = new LinkedList<PlayerRoundStatistics>();

            while(resultSet.next()) {
                listStatistics.add(
                        new PlayerRoundStatistics(
                                resultSet.getInt("hook"),
                                resultSet.getInt("overcute"),
                                resultSet.getInt("direct"),
                                resultSet.getInt("kick")
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
