package cn.yujian95.imusic.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;

import cn.yujian95.imusic.model.Music;

/**
 * @file: MusicUtil
 * @author: <a href="https://yujian95.cn/about/">YuJian</a>
 * @description: 音乐相关工具方法
 * @date: Created in 1/1/20  1:05 AM
 * @version: 1.0
 */

class MusicUtil {

    private static final String SONG_NAME_KEY = "TIT2";
    private static final String ARTIST_KEY = "TPE1";
    private static final String ALBUM_KEY = "TALB";

    /**
     * 通过歌曲文件地址, 获取歌曲信息
     *
     * @param filePath 歌曲文件地址
     * @return 歌曲信息
     * @throws Exception 可能抛出空指针异常
     */
    static Music getMusicInfo(String filePath) throws Exception {
    	return getMusicInfo(new File(filePath));
    }
    static Music getMusicInfo(File file) throws Exception {

        Music music = null;

        try {

            MP3File mp3File = (MP3File) AudioFileIO.read(file);
            
            MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();

            // 歌曲名称
            String songName = getInfoFromFrameMap(mp3File, SONG_NAME_KEY);
            // 歌手名称
            String artist = getInfoFromFrameMap(mp3File, ARTIST_KEY);
            // 歌曲专辑
            String album = getInfoFromFrameMap(mp3File, ALBUM_KEY);
            // 播放时长
            int duration = audioHeader.getTrackLength();
            //精确音轨播放时长
            double preciseTrackLength = audioHeader.getPreciseTrackLength();

            // 封装到music对象
            music = new Music(songName, artist, album, duration, preciseTrackLength, file.getPath());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件读取失败！" + e);
        }

        return music;
    }
    
    static Map<String, FieldKey> fk = new HashMap<String, FieldKey>(){{
    	put(SONG_NAME_KEY, FieldKey.TITLE);
    	put(ARTIST_KEY, FieldKey.ARTIST);
    	put(ALBUM_KEY, FieldKey.ALBUM);
    }};
    
    /**
     * 通过键值,获取歌曲中对应的字段信息
     *
     * @param mp3File mp3音乐文件
     * @param key     键值
     * @return 歌曲信息
     * @throws Exception 可能抛出空指针异常
     */
    private static String getInfoFromFrameMap(MP3File mp3File, String key) throws Exception {
    	String r = getFromV2(mp3File, key);
    	if(StringUtils.isBlank(r)){
    		r = getFromV1(mp3File, fk.get(key));
    	}
    	return r;
    }
	private static String getFromV2(MP3File mp3File, String key) throws Exception {
    	if(mp3File.getID3v2Tag() == null){
    		return null;
    	}
    	Map map =  mp3File.getID3v2Tag().frameMap;
    	if(map == null){
    		return null;
    	}
    	AbstractID3v2Frame frame = (AbstractID3v2Frame)map.get(key);
        return frame == null ? null :frame.getContent();
    }
    
    private static String getFromV1(MP3File mp3File, FieldKey key){
    	if(mp3File.getID3v1Tag() == null){
    		return null;
    	}
    	String str = mp3File.getID3v1Tag().getFirst(key);
    	return StringUtils.isBlank(str)?null:str;
    }
    
}
