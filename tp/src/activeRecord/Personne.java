package activeRecord;

import java.sql.*;
import java.util.ArrayList;

import static activeRecord.DBConnection.connect;

public class Personne {
    private int id;
    private String prenom;
    private String nom;

    public Personne(String n,String p){
        this.id = -1;
        this.prenom = p;
        this.nom = n;
    }

    public static ArrayList<Personne> findAll() throws SQLException {
        ArrayList<Personne> liste = new ArrayList<>();
        {
            Connection connect = DBConnection.getInstance();
            String SQLPrep = "SELECT * FROM personne";
            PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            if (rs.next()) {
                String prenom = rs.getString("prenom");
                String nom = rs.getString("nom");
                Personne p = new Personne(nom,prenom);
                liste.add(p);
            }

        }
        return liste;
    }

    public static Personne findById(int id) throws SQLException {
        Personne p = null;
        {
            Connection connect = DBConnection.getInstance();
            String SQLPrep = "SELECT * FROM personne WHERE id = ?";
            PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
            prep1.setInt(1, id);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            if (rs.next()) {
                String prenom = rs.getString("prenom");
                String nom = rs.getString("nom");
                p = new Personne(nom,prenom);
            }
        }
        return p;
    }

    public static ArrayList<Personne> findByName(String n) throws SQLException {
        ArrayList<Personne> liste = new ArrayList<>();
        {
            Connection connect = DBConnection.getInstance();
            String SQLPrep = "SELECT * FROM personne WHERE nom = ?";
            PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
            prep1.setString(1, n);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            if (rs.next()) {
                String prenom = rs.getString("prenom");
                String nom = rs.getString("nom");
                Personne p = new Personne(nom,prenom);
                liste.add(p);
            }

        }
        return liste;
    }

    public static void createTable() throws SQLException {
        Connection connect = DBConnection.getInstance();
        String SQLPrep = "\n" +
                "CREATE TABLE `Personne` (\n" +
                "  `id` int(11) NOT NULL,\n" +
                "  `nom` varchar(40) NOT NULL,\n" +
                "  `prenom` varchar(40) NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
    }

    public static void deleteTable() throws SQLException {
        String drop = "DROP TABLE Personne";
        Connection connect = DBConnection.getInstance();
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
