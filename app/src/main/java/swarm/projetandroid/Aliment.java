package swarm.projetandroid;


public class Aliment {
    String name;
    boolean checked;

    public Aliment(String name, boolean checked) {
        String fullName = name;
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
