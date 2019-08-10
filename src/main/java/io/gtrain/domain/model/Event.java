package io.gtrain.domain.model;

import io.gtrain.domain.model.dto.Address;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author William Gentry
 */
@Document(collection = "glms_event")
public class Event {

	@Id
	private ObjectId id;

	private String location;

	private Address address;

	private LocalDate date;

	private LocalTime time;

	private String createdBy;

	@PersistenceConstructor
	public Event(ObjectId id, String location, Address address, LocalDate date, LocalTime time, String createdBy) {
		this.id = id;
		this.location = location;
		this.address = address;
		this.date = date;
		this.time = time;
		this.createdBy = createdBy;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Event event = (Event) o;
		return Objects.equals(id, event.id) &&
				Objects.equals(location, event.location) &&
				Objects.equals(address, event.address) &&
				Objects.equals(date, event.date) &&
				Objects.equals(time, event.time) &&
				Objects.equals(createdBy, event.createdBy);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, location, address, date, time, createdBy);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Event.class.getSimpleName() + "[", "]")
				.add("id=" + id)
				.add("location='" + location + "'")
				.add("address=" + address)
				.add("date=" + date)
				.add("time=" + time)
				.add("createdBy='" + createdBy + "'")
				.toString();
	}
}
