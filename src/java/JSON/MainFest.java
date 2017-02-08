package JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainFest {

@SerializedName("Festival")
@Expose
private Fest festival;

public Fest getFestival() {
return festival;
}

public void setFestival(Fest festival) {
this.festival = festival;
}

}