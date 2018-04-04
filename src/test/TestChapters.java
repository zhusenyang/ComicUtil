package test;

import java.util.TreeSet;

import entity.Chapter;

public class TestChapters {
	public static void main(String[] args) {
		TreeSet<Chapter> chapters = new TreeSet<>();
		for (int i = 1; i <= 30; i++) {
			if(i<10){
				Chapter cha = new Chapter("第0"+i, "");
				chapters.add(cha);
			}else if(i>9&&i<20){
				Chapter cha = new Chapter("第"+i, "");
				chapters.add(cha);
			}else{
				Chapter cha = new Chapter("第1"+i, "");
				chapters.add(cha);
			}
		}
		System.out.println(chapters);
	}
}
