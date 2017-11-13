package swimmingpool.co.uk.jesmondswimmingpool.entity;

public class Course {
    private Long id;

    private String note;

    private String name;

    private Long tutorid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getTutorid() {
        return tutorid;
    }

    public void setTutorid(Long tutorid) {
        this.tutorid = tutorid;
    }
}