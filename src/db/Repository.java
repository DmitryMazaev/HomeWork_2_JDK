package db;

import java.io.IOException;

public interface Repository {
    public void whriteInLogFile(String string);
    public String readFile() throws IOException;
}
