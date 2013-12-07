package cn.gaily.crm.util;

import org.apache.commons.lang.StringUtils;

public class DataType {

	/**
	 * 该方法完成的功能是转化字符串类型的数组为整形数组
	 * @return
	 */
	public static Integer[] converterStringArray2IntegerArray(String[] sids) {

		if(sids!=null&&sids.length>0){
			Integer[] ids = new Integer[sids.length];
			for(int i=0;i<sids.length;i++){
				if(StringUtils.isNotBlank(sids[i])){
					ids[i]=Integer.parseInt(sids[i]);
				}
			}
			return ids;
		}
		return null;
	}

	
	
	/**
	 * 利用给定的流水位生成第一个流水号
	 * 	流水位3    ---- 流水号 001
	 *  流水位4    ---- 流水号0001
	 * @param glideBit
	 * @return
	 */
	public static String geneFirstGlideNumber(Integer glideBit) {
		if(glideBit==null||glideBit<3){
			glideBit=3;
		}
		String glideNumber = "";
		for(int i=0;i<glideBit-1;i++){
			glideNumber = glideNumber+"0";
		}
		glideNumber = glideNumber+"1";
		return glideNumber;
	}


	/**
	 *<p> 根据当前的流水号生成下一个流水号</p>
	 * 	<p> 001  -->  002</p>
	 *  <p> 0002 -->  0003</p>
	 * @param curGlideNumber 当前的流水号
	 * @return nextGlideNumber 下一个流水号
	 */
	public static String geneNextGlideNumber(String curGlideNumber) {
		if(StringUtils.isBlank(curGlideNumber)){
			throw new RuntimeException("不能计算下一个流水号！");
		}
		
		curGlideNumber = "1"+curGlideNumber;
		Integer icurGlideNumber = Integer.parseInt(curGlideNumber);
		icurGlideNumber++;
		
		curGlideNumber=icurGlideNumber+"";
		curGlideNumber = curGlideNumber.substring(1);
		return curGlideNumber;
	}
	
	
	public static void main(String[] args) {
		System.out.println(geneNextGlideNumber("99"));
	}
}
