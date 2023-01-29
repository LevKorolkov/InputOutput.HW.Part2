import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        List<String> paths = new ArrayList<>();
        String pathSave1 = "C:/Games/savegames/save1.data";
        String pathSave2 = "C:/Games/savegames/save2.data";
        String pathSave3 = "C:/Games/savegames/save3.data";
        paths.add(pathSave1);
        paths.add(pathSave2);
        paths.add(pathSave3);

        List<GameProgress> progresses = new ArrayList<>();
        GameProgress gameProgress1 = new GameProgress(8, 3, 14, 20.10);
        GameProgress gameProgress2 = new GameProgress(3, 1, 7, 11.24);
        GameProgress gameProgress3 = new GameProgress(10, 7, 27, 43.21);
        progresses.add(gameProgress1);
        progresses.add(gameProgress2);
        progresses.add(gameProgress3);

        for (int i = 0; i < paths.size(); i++) {
            saveGame(paths.get(i), progresses.get(i));
        }

        String pathToZip = "C://Games/savegames/saves.zip";
        zipFiles(pathToZip, paths);
        for (String path : paths) {
            File file = new File(path);
            if (file.delete()) {
                System.out.println("Файл сохранения \"" + file.getName() + '"' + " успешно удалён.");
            }
        }
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(gameProgress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(String pathToZip, List<String> gameProgressesPaths) {
        try (ZipOutputStream zOut = new ZipOutputStream(new FileOutputStream(pathToZip))) {
            for (String arr : gameProgressesPaths) {
                try (FileInputStream fileInputStream = new FileInputStream(arr)) {
                    ZipEntry entry = new ZipEntry(arr);
                    zOut.putNextEntry(entry);
                    while (fileInputStream.available() > 0) {
                        zOut.write(fileInputStream.read());
                    }
                    zOut.closeEntry();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
