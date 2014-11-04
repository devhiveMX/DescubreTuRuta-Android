package mx.com.sct.checkyourrute.data;

import java.util.List;

public class Section {
private String section;
private String source;
private String hint;
private String country;
private String countryCode;
private String destination;
private List<Location> locationList;
//#TODO rest of values

public String getSection() {
	return section;
}
public void setSection(String section) {
	this.section = section;
}
public String getSource() {
	return source;
}
public void setSource(String source) {
	this.source = source;
}
public String getHint() {
	return hint;
}
public void setHint(String hint) {
	this.hint = hint;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getCountryCode() {
	return countryCode;
}
public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
}
public String getDestination() {
	return destination;
}
public void setDestination(String destination) {
	this.destination = destination;
}
public List<Location> getLocationList() {
	return locationList;
}
public void setLocationList(List<Location> locationList) {
	this.locationList = locationList;
}

}
