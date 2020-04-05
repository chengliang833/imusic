package cn.yujian95.imusic.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.yujian95.imusic.model.Music;

/**
 * @file: MusicUtilTest
 * @author: <a href="https://yujian95.cn/about/">YuJian</a>
 * @description:
 * @date: Created in 1/1/20  1:12 AM
 * @version: 1.0
 */
public class MusicUtilTest {

//    @Test
//    public void getMusicInfo() throws Exception {
//    	String path = "D:\\My_music\\R.I.O\\12_Rock This Club (R.I.O. Radio Edit).mp3";
//        System.out.println(JSONObject.toJSON(MusicUtil.getMusicInfo(path)));
//    }
    
    private static String fromPath = "D:\\Download\\电台\\";
    private static String toPath = "D:\\My_music\\酷狗EDM\\";
    
    public static void main(String[] args) throws Exception {
//    	System.out.println("Diplo & Blond:ish - Diplo & Blondish - Give Dem (feat. Kah-Lo) [Kaskade Remix].mp3".replaceAll("\\\\|/|:|\\*|\\?|\"|<|>|\\|", "_"));
//    	MP3File mp3File = (MP3File) AudioFileIO.read(new File(fromPath + "电台频道_酷狗网 (1)\\电台频道_酷狗网(114).mp3"));
//    	System.out.println(JSONObject.toJSONString(((MP3File) AudioFileIO.read(new File(toPath + "酷狗音乐EDM_2m6s_151.mp3")))));
//    	System.out.println(JSONObject.toJSONString(MusicUtil.getMusicInfo(fromPath + "电台频道_酷狗网 (1)\\电台频道_酷狗网(114).mp3")));
//    	Music m = MusicUtil.getMusicInfo(toPath + "酷狗音乐EDM_2m6s_151.mp3");
//    	System.out.println("酷狗音乐EDM_" + m.getDuration()/60 + "m" + m.getDuration()%60 + "s" + "_" + m.getPreciseTrackLength() + m.getPath().substring(m.getPath().lastIndexOf(".")));
    	copyMusic(new File(fromPath));
	}
    
    public static void copyMusic(File file) throws Exception{
//    	System.out.println(file.getPath());
    	if(file.isDirectory()){
    		for(File f:file.listFiles()){
    			copyMusic(f);
    		}
    	}else{
    		if(!file.getName().endsWith("zip")){
    			Music m = MusicUtil.getMusicInfo(file);
    			String newFile;
    			if(m.getSinger() != null && m.getName() != null){
    				newFile = m.getSinger() + " - " + m.getName() + m.getPath().substring(m.getPath().lastIndexOf("."));
    			}else{
    				newFile = "酷狗音乐EDM_" + m.getDuration()/60 + "m" + m.getDuration()%60 + "s" + "_" + m.getPreciseTrackLength() + m.getPath().substring(m.getPath().lastIndexOf("."));
    			}
    			newFile = toPath + newFile.replaceAll("\\\\|/|:|\\*|\\?|\"|<|>|\\|", "_");
    			if(!new File(newFile).exists()){
    				saveFile(new FileInputStream(file), newFile);
    			}
    		}
    	}
    }
    
    public static void saveFile(InputStream is, String outputPath) throws IOException{
    	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath));
    	byte[] temp = new byte[1024]; 
    	int len;
    	while((len = is.read(temp)) != -1){
    		bos.write(temp, 0, len);
    	}
    	bos.close();
    }
    
    
    
}