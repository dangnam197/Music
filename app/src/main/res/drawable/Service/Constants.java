package com.example.dangn.music.Service;

public class Constants {
	public interface ACTION {
		public static String MAIN_ACTION = "com.example.dangn.music.Service.MediaService.action.main";
		public static String PREV_ACTION = "com.example.dangn.music.Service.MediaService.action.prev";
		public static String PLAY_ACTION = "com.example.dangn.music.Service.MediaService.action.play";
		public static String NEXT_ACTION = "com.example.dangn.music.Service.MediaService.action.next";
		public static String SEEKTO_ACTION = "com.example.dangn.music.Service.MediaService.action.seekto";
		public static String PROGRESS_ACTION = "com.example.dangn.music.Service.MediaService.action.seekto";
		public static String DURATION_ACTION = "com.example.dangn.music.Service.MediaService.action.duration";
		public static String STARTMEDIASERVER_ACTION = "com.example.dangn.music.Service.MediaService.action.startforeground";
		public static String STOPMEDIASERVER_ACTION = "com.example.dangn.music.Service.MediaService.action.stopforeground";
	}

	public interface NOTIFICATION_ID {
		public static int FOREGROUND_SERVICE = 101;
	}
}
