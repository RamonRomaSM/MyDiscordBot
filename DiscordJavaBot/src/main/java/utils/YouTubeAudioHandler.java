package utils;

import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class YouTubeAudioHandler implements AudioSendHandler {
    private final InputStream inputStream;
    private final byte[] buffer = new byte[4250]; // 20ms of PCM data
    //3840
    //4300
    public YouTubeAudioHandler(String youtubeUrl) throws Exception {
        // Get the direct audio URL using yt-dlp
        Process ytDlpProcess = new ProcessBuilder(
            "C:\\Users\\win10\\AppData\\Local\\Microsoft\\WinGet\\Links\\yt-dlp.exe", "-f", "bestaudio", "-g", youtubeUrl
        ).start();

        // Read the direct URL
        String directAudioUrl;
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(ytDlpProcess.getInputStream()))) {
            directAudioUrl = reader.readLine();
        }
        
        // Start FFmpeg to stream the YouTube audio
        
        Process ffmpegProcess = new ProcessBuilder(
            "C:\\Users\\win10\\AppData\\Local\\Microsoft\\WinGet\\Links\\ffmpeg.exe", "-i", directAudioUrl,
            "-f", "s16le", "-ar", "48000", "-ac", "2", "pipe:1"
        ).redirectErrorStream(true).start();
        
        
        this.inputStream = ffmpegProcess.getInputStream();
        
    }

    @Override
    public boolean canProvide() {
        try {
            return inputStream.available() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    /*
    @Override
    public ByteBuffer provide20MsAudio() {
        try {
            int bytesRead = inputStream.read(buffer);
            if (bytesRead == -1) return null;
            return ByteBuffer.wrap(buffer);
        } catch (Exception e) {
            return null;
        }
    }
    */
    @Override
    public ByteBuffer provide20MsAudio() {
    	
    	
        try {
            int bytesRead = inputStream.read(buffer); // Leer datos
            // Si no hay más datos, devolver null para detener el audio
            if (bytesRead == -1) {
                System.out.println("Fin del stream de audio.");
                return null;
            }
            
            return ByteBuffer.wrap(buffer); // Asegurar que solo se envían los bytes leídos
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

    @Override
    public boolean isOpus() {
        return false;
    }
}

