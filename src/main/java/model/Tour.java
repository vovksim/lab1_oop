package model;

public class Tour {
    private int id;
    private String name;
    private String description;
    private String location;
    private int duration; // in days
    private double price;
    private int slots;
    private String imageUrl;

    public Tour() {}

    public Tour(int id, String name, String description, String location,
                int duration, double price, int slots, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.duration = duration;
        this.price = price;
        this.slots = slots;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public int getDuration() { return duration; }

    public void setDuration(int duration) { this.duration = duration; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public int getSlots() { return slots; }

    public void setSlots(int slots) { this.slots = slots; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
