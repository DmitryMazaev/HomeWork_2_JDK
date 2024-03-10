package db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileStorage implements Repository {
    public static final String LOG_PATH = "src/db/log.txt";


    /**
     * Запись в файл
     */
    @Override
    public void whriteInLogFile(String string)
    {
        try {
            FileWriter writer = new FileWriter(LOG_PATH, true);
            writer.write(string +"\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    /**
     * Чтение из файла, метод возвращает чат
     */
    @Override
    public String readFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(LOG_PATH));
        String line;
        String chart = "";
        while ((line = br.readLine()) != null) {
            chart = chart + line + "\n";
        }
        return chart;
    }
}
