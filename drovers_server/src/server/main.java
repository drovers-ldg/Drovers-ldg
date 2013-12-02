package server;

import java.io.IOException;
// main server file
import java.sql.SQLException;

class Drovers_Server
{	
	public static void main(String [] args) throws IOException, InterruptedException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		new Server();
	}
}