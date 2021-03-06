package common.messages;

public interface AbstractMessage {

	
	public enum MessageType {
		CLIENT_MESSAGE, SERVER_MESSAGE, ECS_MESSAGE, REPLICA_MESSAGE, HEARTBEAT_MESSAGE, FAILURE_DETECTION, RECOVERY_MESSAGE
		, SUBSCRIBE_MESSAGE,UNSUBSCRIBE_MESSAGE, NOTIFICATION_MESSAGE
	}
	
	/**
	 * 
	 * @return <code>MessageType</code> representing the message type
	 */
	public abstract MessageType getMessageType ();
}
