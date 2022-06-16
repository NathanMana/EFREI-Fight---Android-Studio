package fr.android.project_vyas_manaranche.services;

import android.util.Log;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import fr.android.project_vyas_manaranche.models.Fight;
import fr.android.project_vyas_manaranche.models.Fighter;

public class FightService {

    public static int addFight(Fight fight) {
        try {

            DatabaseService dbService = new DatabaseService();
            String generatedColumns[] = { "fight_id" };

            PreparedStatement statement = dbService.connection.prepareStatement("INSERT INTO fight(date, streetname, city) VALUES (?, ?, ?)", generatedColumns);
            statement.setLong(1, fight.getDate());
            statement.setString(2, fight.getStreetName());
            statement.setString(3, fight.getCity());

            int affectedRow = statement.executeUpdate();
            if (affectedRow < 1) return 0;

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) return 0;

            int fightId = generatedKeys.getInt(1);

            dbService.connection.close();
            return fightId;
        }
        catch (Exception e) {
            System.out.println("ERREUR : " + e);
        }

        return 0;
    }

    public static boolean updateFight(Fight fight) {
        try {

            DatabaseService dbService = new DatabaseService();

            PreparedStatement statement = dbService.connection.prepareStatement(
                    "UPDATE fight SET duration = ? WHERE fight_id = ?");
            statement.setLong(1, fight.getDuration());
            statement.setInt(2, fight.getFightId());

            statement.executeUpdate();

            for (Fighter fighter: fight.getListFighter()) {

                FighterService.updateFighter(fighter);
            }

            dbService.connection.close();
            return true;
        }
        catch (Exception e) {
            System.out.println("ERREUR : " + e);
        }

        return false;
    }

    public static Fight GetFight(int fightId) {

        try {

            DatabaseService dbService = new DatabaseService();
            PreparedStatement statement = dbService.connection.prepareStatement("SELECT * FROM fight WHERE fight_id = ?");
            statement.setInt(1, fightId);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return null;

            List listPlayers = FighterService.GetListFighters(fightId);
            List listRounds = RoundService.GetListRounds(fightId);

            Fight fight = new Fight(
                    fightId,
                    resultSet.getLong("date"),
                    resultSet.getInt("duration"),
                    resultSet.getString("streetname"),
                    resultSet.getString("city"),
                    listPlayers,
                    listRounds);
            dbService.connection.close();

            return fight;
        }
        catch (Exception e) {
            System.out.println("Erreur  : "  + e);
        }

        return null;
    }

    public static List<Fight> GetListFights() {

        List listFights = new LinkedList<Fight>();

        try {

            DatabaseService dbService = new DatabaseService();
            PreparedStatement statement = dbService.connection.prepareStatement("SELECT * FROM fight");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int fightId =  resultSet.getInt("fight_id");

                List listPlayers = FighterService.GetListFighters(fightId);

                List listRounds = RoundService.GetListRounds(fightId);

                listFights.add(

                        new Fight(
                                fightId,
                                resultSet.getLong("date"),
                                resultSet.getInt("duration"),
                                resultSet.getString("streetname"),
                                resultSet.getString("city"),
                                listPlayers,
                                listRounds)
                );
            }

            dbService.connection.close();

            return listFights;
        }
        catch (Exception e) {
            System.out.println("Erreur  : "  + e);
        }

        return listFights;
    }

}
