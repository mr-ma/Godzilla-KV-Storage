

/**
 * 
 * This class is a representation of the server node,
 * containing all required information about this node.
 * 
 */

package common;


public class ServerInfo {

	private String address;
	private int port;
	private String serverName;
	private boolean isServerLaunched;
	private String fromIndex;
	private String toIndex;
	private ServerInfo firstReplicaInfo;
	private ServerInfo secondReplicaInfo;

	public ServerInfo () {
	}

	public ServerInfo ( String address , int port ) {
		this.address = address;
		this.port = port;
	}

	public ServerInfo ( String address , int port , boolean isServerLaunched ) {
		this ( address , port );
		this.isServerLaunched = isServerLaunched;
	}

	public ServerInfo ( String serverInfoString ) {
		fromString ( serverInfoString );
	}

	public ServerInfo ( String address , int port , String fromIndex ,
			String toIndex ) {
		this ( address , port );
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	private void fromString ( String serverInfoString ) {
		/* should be something like nodeName host port */
		if ( serverInfoString != null && ! serverInfoString.isEmpty () ) {
			String [] tokens = serverInfoString.split ( " " );
			if ( tokens.length == 3 ) {
				setServerName ( tokens [ 0 ] );
				setAddress ( tokens [ 1 ] );
				setPort ( Integer.parseInt ( tokens [ 2 ] ) );
			} else
				throw new IllegalArgumentException (
						"Config file is not formatted as expected. near "
								+ serverInfoString );

		}

	}

	public String getAddress () {
		return address;
	}

	public void setAddress ( String address ) {
		this.address = address;
	}

	public int getPort () {
		return port;
	}

	public void setPort ( int port ) {
		this.port = port;
	}

	public boolean isServerLaunched () {
		return isServerLaunched;
	}

	public void setServerLaunched ( boolean isServerLaunched ) {
		this.isServerLaunched = isServerLaunched;
	}

	public String getServerName () {
		return serverName;
	}

	public void setServerName ( String serverName ) {
		this.serverName = serverName;
	}

	public String getFromIndex () {
		return fromIndex;
	}

	public void setFromIndex ( String fromIndex ) {
		this.fromIndex = fromIndex;
	}

	public String getToIndex () {
		return toIndex;
	}

	public void setToIndex ( String toIndex ) {
		this.toIndex = toIndex;
	}
	
	public ServerInfo getFirstReplicaInfo(){
		return this.firstReplicaInfo;
	}

	public ServerInfo getSecondReplicaInfo(){
		return this.secondReplicaInfo;
	}
	
	public void setFirstReplicaInfo(ServerInfo serverInfo){
		this.firstReplicaInfo = serverInfo;
	}
	
	public void setSecondReplicaInfo(ServerInfo serverInfo){
		this.secondReplicaInfo = serverInfo;
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ( ( address == null ) ? 0 : address.hashCode () );
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals ( Object obj ) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass () != obj.getClass () )
			return false;
		ServerInfo other = ( ServerInfo ) obj;
		if ( address == null ) {
			if ( other.address != null )
				return false;
		} else if ( ! address.equals ( other.address ) )
			return false;
		if ( port != other.port )
			return false;
		return true;
	}

	@Override
	public String toString () {
		return "Server address : " + this.getAddress () + " on port : "
				+ this.getPort ();
	}
	
	
	
	public boolean isIndexInMyRange(String index) {
		// the last node in the ring
		if(this.getFromIndex().compareTo( 
				this.getToIndex()) > 0){
			if(index.compareTo(this.getFromIndex()) >=0 )
					return true;
			else{
				return (index.compareTo(this.getToIndex()) <= 0);
			}
	}
		//for the other nodes
		else
			return (index.compareTo(this.getFromIndex()) >= 0 &&
					index.compareTo(this.getToIndex()) <= 0);
			
	}

}
