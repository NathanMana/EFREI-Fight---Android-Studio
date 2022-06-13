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
                                resultSet.getInt("round")
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
