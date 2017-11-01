package com.Scheduler.RoomScheduler;


//imports
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import java.util.List;
import java.util.ArrayList;

import com.google.appengine.api.datastore.Key;

//class definition
@PersistenceCapable
public class UserRoom {
	//the identifier for the object as we will be using the user key for this
	//we need to use a key object here
	@PrimaryKey
	@Persistent
	private Key room_num;
	//the room number for this user
	@Persistent
	private String name;

	@Persistent
	private String time;

	@Persistent
	private int roomNum;


	@Persistent(mappedBy="parent")
	private List<BookingDetails> roomList;

	@Persistent
	private String allocate;
	//JDO
	//setter and getter for the id
	public void setRoomNum(final Key room_num) { this.room_num = room_num; }
	public Key room_num() { return room_num; }

	public void setRoom_num(final int room_num){this.roomNum = room_num;}
	public int roomNum(){return roomNum;}

	//setter and getter for the room number
	public void setName(final String name) { this.name=name; }
	public String name() { return name; }

	public void setAllocate(final String allocate) { this.allocate = allocate; }
	public String allocate() { return allocate; }

	public void setTime(final String date){this.time = date;}
	public String time(){return time;}

	public void allocateRoom(BookingDetails r){
		if(roomList == null){
			roomList = new ArrayList<BookingDetails>();
			roomList.add(r);
		}
	}
	public List<BookingDetails> getRoomList() { return roomList; }
}
