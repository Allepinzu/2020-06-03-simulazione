package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Arco;
import it.polito.tdp.PremierLeague.model.Player;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(double d){
		String sql = "SELECT distinct  p.PlayerID, p.Name  , SUM(a.Goals), COUNT(*) "
				+ "FROM actions a , players p "
				+ "WHERE a.PlayerID = p.PlayerID "
				+ "GROUP BY a.PlayerID "
				+ "HAVING SUM(a.Goals)/COUNT(*) > ?";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, d);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Arco archi(Player p1, Player p2){
		String sql = "SELECT DISTINCT  a1.PlayerID, a2.PlayerID , SUM(a1.TimePlayed) as somma1, SUM(a2.TimePlayed) as somma2 "
				+ "FROM actions a1, actions a2 "
				+ "WHERE a1.PlayerID = ? AND a2.PlayerID = ? AND a1.starts=1 and a2.starts=1 and a1.PlayerID <> a2.PlayerID and a1.TeamID <> a2.TeamID  AND a1.MatchID=a2.MatchID "
				+ "GROUP BY a1.PlayerID, a2.PlayerID "
				+ "HAVING COUNT(*) > 0";
	
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,p1.getPlayerID() );
			st.setInt(2,p2.getPlayerID() );
			ResultSet res = st.executeQuery();
			Arco action = null;
			while (res.next()) {

				action = new Arco(p1, p2, res.getInt("somma1"), res.getInt("somma2") );
				
				
			}
			conn.close();
			return action;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
