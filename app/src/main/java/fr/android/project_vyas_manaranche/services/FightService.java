package fr.android.project_vyas_manaranche.services;

import android.util.Log;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

import fr.android.project_vyas_manaranche.models.Fight;
import fr.android.project_vyas_manaranche.models.Fighter;

public class FightService {

    public static boolean addFight(Fight fight) {
        try {

            DatabaseService dbService = new DatabaseService();
            String generatedColumns[] = { "fight_id" };

            PreparedStatement statement = dbService.connection.prepareStatement("INSERT INTO fight(date, streetname, city) VALUES (?, ?, ?)", generatedColumns);
            statement.setDate(1, (Date) fight.getDate());
            statement.setString(2, fight.getStreetName());
            statement.setString(3, fight.getCity());

            int affectedRow = statement.executeUpdate();
            if (affectedRow < 1) return false;

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) return false;

            int fightId = generatedKeys.getInt(1);
            for (Fighter fighter: fight.getListFighter()) {

                fighter.setFightId(fightId);
                FighterService.addFighter(fighter);
            }

            dbService.connection.close();
        }
        catch (Exception e) {
            System.out.println("ERREUR : " + e);
        }

        return true;
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

            dbService.connection.close();

            return new Fight(
                    fightId,
                    resultSet.getDate("date"),
                    resultSet.getInt("duration"),
                    resultSet.getString("streetname"),
                    resultSet.getString("city"),
                    listPlayers,
                    listRounds);
        }
        catch (Exception e) {
            System.out.println("Erreur  : "  + e);
        }

        return null;
    }

}
