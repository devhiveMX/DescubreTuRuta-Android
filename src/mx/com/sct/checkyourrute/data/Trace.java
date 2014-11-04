package mx.com.sct.checkyourrute.data;

import java.util.List;

public class Trace {
	private Double total;
	private Double totalGas;
	private String title;
	private List<Section> sectionList;
	
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getTotalGas() {
		return totalGas;
	}
	public void setTotalGas(Double totalGas) {
		this.totalGas = totalGas;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Section> getSectionList() {
		return sectionList;
	}
	public void setSectionList(List<Section> sectionList) {
		this.sectionList = sectionList;
	}
}
