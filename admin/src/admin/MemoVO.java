package admin;

public class MemoVO {
	
	private String memo_id;
	private String memo_name;
	private String Notepad;
	
	public MemoVO() { }
	
	public MemoVO(String memo_id, String memo_name, String notepad) {
		super();
		this.memo_id = memo_id;
		this.memo_name = memo_name;
		this.Notepad = notepad;
	}

	public String getMemo_id() {
		return memo_id;
	}

	public void setMemo_id(String memo_id) {
		this.memo_id = memo_id;
	}

	public String getMemo_name() {
		return memo_name;
	}

	public void setMemo_name(String memo_name) {
		this.memo_name = memo_name;
	}

	public String getNotepad() {
		return Notepad;
	}

	public void setNotepad(String notepad) {
		this.Notepad = notepad;
	}
	
}
