
/**    
 * Copyright (C),Kingmed
 * @FileName: HBaseInsert.java  
 * @Package: storm.starter  
 * @Description: //模块目的、功能描述  
 * @Author blacklau  
 * @Date 2014年12月22日 下午5:56:07  
 * @History: //修改记录
 * 〈author〉      〈time〉      〈version〉       〈desc〉
 * 修改人姓名            修改时间            版本号              描述   
*/  

package storm.starter;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;


/**  
 *〈一句话功能简述〉
 * 功能详细描述
 * @author liuyong
 * @create 2014年12月22日 下午5:56:07 
 * @version 1.0.0
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */

public class HBaseInsert {
	private static Configuration conf = HBaseConfiguration.create();
	private static Map<String,HTable> tables = new HashMap<String,HTable>(); 
	private static HTable getTableInstance(String table){
		if(table == null || table.equals("")) return null;
		if(tables.containsKey(table)) 
			return tables.get(table);
		synchronized(tables){
			if(!tables.containsKey(table)){
				try {
					HTable hTable = new HTable(conf,table);
					tables.put(table, hTable);
					return hTable;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static boolean insert(String table,String cf,String qu,String key,byte[] value){
		HTable hTable = getTableInstance(table);
		if(hTable == null) return false;
		hTable.setAutoFlushTo(true);
		Put put = new Put(key.getBytes());
		put.add(cf.getBytes(),qu.getBytes(),value);
		try {
			hTable.put(put);
			return false;
		} catch (RetriesExhaustedWithDetailsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
