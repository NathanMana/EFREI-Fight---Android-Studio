package fr.android.project_vyas_manaranche.services;

import android.util.Log;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import fr.android.project_vyas_manaranche.models.Fight;
import fr.android.project_vyas_manaranche.models.Fighter;
import fr.android.project_vyas_manaranche.models.PlayerRoundStatistics;

public class FighterService {

    public static int addFighter(Fighter fighter) {
        try {

            DatabaseService dbService = new DatabaseService();
            String generatedColumns[] = { "fighter_id" };

            PreparedStatement statement = dbService.connection.prepareStatement(
                    "INSERT INTO fighter(fullname, fight_id) VALUES (?, ?)", generatedColumns);
            statement.setString(1, fighter.getFullname());
            statement.setInt(2, fighter.getFightId());

            int affectedRow = statement.executeUpdate();
            if (affectedRow < 1) return 0;

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) return 0;
            int fighterId = generatedKeys.getInt(1);

            dbService.connection.close();
            return fighterId;
        }
        catch (Exception e) {
            System.out.println("ERREUR : " + e);
        }

        return 0;
    }

    public static int updateFighter(Fighter fighter) {
        try {

            for (PlayerRoundStatistics playerRoundStatistics: fighter.getStatistics()) {
                PlayerRoundStatisticsService.updatePlayerRoundStatistics(playerRoundStatistics);
            }
        }
        catch (Exception e) {
            System.out.println("ERREUR : " + e);
        }

        return 0;
    }

    public static Fighter GetFighter(int fighterId) {

        try {

            DatabaseService dbService = new DatabaseService();
            PreparedStatement statement = dbService.connection.prepareStatement(
                    "SELECT * FROM fighter WHERE fighter_id = ?");
            statement.setInt(1, fighterId);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return null;

            // récupérer les statistiques des rounds
            List listStats = PlayerRoundStatisticsService.GetListPlayerRoundStatistics(fighterId);

            dbService.connection.close();

            return new Fighter(
                    resultSet.getInt("fighter_id"),
                    resultSet.getString("fullname"),
                    listStats
            );
        }
        catch (Exception e) {
            System.out.println("Erreur  : "  + e);
        }

        return null;
    }

    public static List<Fighter> GetListFighters(int fightId) {

        try {

            DatabaseService dbService = new DatabaseService();
            PreparedStatement statement = dbService.connection.prepareStatement(
                    "SELECT * FROM fighter WHERE fight_id = ?");
            statement.setInt(1, fightId);

            ResultSet resultSet = statement.executeQuery();

            List listFighters = new LinkedList<Fighter>();

            while(resultSet.next()) {

                int fighterId = resultSet.getInt("fighter_id");

                List listStats = PlayerRoundStatisticsService.GetListPlayerRoundStatistics(fighterId);

                listFighters.add(new Fighter(
                        fighterId,
                        resultSet.getString("fullname"),
                        listStats));
            }

            dbService.connection.close();

            return listFighters;
        }
        catch (Exception e) {
            System.out.println("Erreur  : "  + e);
        }

        return null;
    }

}