package fr.android.project_vyas_manaranche.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import fr.android.project_vyas_manaranche.models.Fighter;
import fr.android.project_vyas_manaranche.models.Round;

public class RoundService {

    public static List<Round> GetListRounds(int fightId) {

        try {

            DatabaseService dbService = new DatabaseService();
            PreparedStatement statement = dbService.connection.prepareStatement(
                    "SELECT * FROM round WHERE fight_id = ?");
            statement.setInt(1, fightId);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return null;

            List listRounds = new LinkedList<Round>();

            while(resultSet.next()) {

                listRounds.add(new Round(
                        resultSet.getInt("round_id"),
                        resultSet.getInt("duration")
                ));
            }

            dbService.connection.close();

            return listRounds;
        }
        catch (Exception e) {
            System.out.println("Erreur  : "  + e);
        }

        return null;
    }

}
