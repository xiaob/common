package tmp.jdbc;

public class Dept{
	private String deptNo;
	private String deptName;
	
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String toString(){
		return deptNo + ", " + deptName;
	}
}