package activeRecord;

import java.sql.*;

public class mainTestConnec {
    public static void main(String[] args) throws SQLException {
        Connection connect = DBConnection.getInstance();

        // creation de la table Personne
        {
            String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                    + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
            Statement stmt = connect.createStatement();
            stmt.executeUpdate(createString);
            System.out.println("1) creation table Personne\n");
        }

        // ajout de personne avec requete preparee
        {
            String SQLPrep = "INSERT INTO Personne (nom, prenom) VALUES (?,?);";
            PreparedStatement prep;
            // l'option RETURN_GENERATED_KEYS permet de recuperer l'id (car
            // auto-increment)
            prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, "Spielberg");
            prep.setString(2, "Steven");
            prep.executeUpdate();
            System.out.println("2) ajout Steven Spielberg\n");
        }

        // ajout second personne
        {
            String SQLPrep = "INSERT INTO Personne (nom, prenom) VALUES (?,?);";
            PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, "Scott");
            prep.setString(2, "Ridley");
            prep.executeUpdate();
            System.out.println("3) ajout Ridley Scott");

            // recuperation de la derniere ligne ajoutee (auto increment)
            // recupere le nouvel id
            int autoInc = -1;
            ResultSet rs = prep.getGeneratedKeys();
            if (rs.next()) {
                autoInc = rs.getInt(1);
            }
            System.out.print("  ->  id utilise lors de l'ajout : ");
            System.out.println(autoInc);
            System.out.println();
        }

        // recuperation de toutes les personnes + affichage
        {
            System.out.println("4) Recupere les personnes de la table Personne");
            String SQLPrep = "SELECT * FROM Personne;";
            PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int id = rs.getInt("id");
                System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
            }
            System.out.println();
        }

        // suppression de la table personne
        {
            String drop = "DROP TABLE Personne";
            Statement stmt = connect.createStatement();
            stmt.executeUpdate(drop);
            System.out.println("9) Supprime table Personne");
        }


    }
}
