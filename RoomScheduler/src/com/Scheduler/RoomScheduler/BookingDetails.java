package com.Scheduler.RoomScheduler;

//imports
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdGeneratorStrategy;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class BookingDetails {
	// the room num of this object
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key room_num;

	@Persistent
	private int roomNum;

	@Persistent
	private String personName;

	// the time he booked
	@Persistent
	private String time;

	@Persistent
	private UserRoom parent;

	// getter for the roomNum
	public Key room_num() { return room_num; }

	public void setRoomNum(final int roomNum) { this.roomNum = roomNum; }
	public int roomNum() { return roomNum; }

	public void setName(final String name) { this.personName = name; }
	public String name() { return personName; }

	// setter and getter for the time
	public void setTime(final String time) { this.time = time; }
	public String time() { return time; }

	public void setParent(final UserRoom parent){
		this.parent=parent;
	}

}
