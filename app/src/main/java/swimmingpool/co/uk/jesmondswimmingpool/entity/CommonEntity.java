package swimmingpool.co.uk.jesmondswimmingpool.entity;


public class CommonEntity<Bean> {
	
	
	
	private String msg;
	private int status;
	public CommonEntity() {
		super();
	}
	private Bean bean;
	public CommonEntity(String msg, int status, Bean bean) {
		super();
		this.msg = msg;
		this.status = status;
		this.bean = bean;
	}
	public String getMsg() {
		return msg;
	}
	@Override
	public String toString() {
		return "CommonEntity [msg=" + msg + ", status=" + status + ", bean=" + bean + "]";
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Bean getBean() {
		return bean;
	}
	public void setBean(Bean bean) {
		this.bean = bean;
	}
	

}
