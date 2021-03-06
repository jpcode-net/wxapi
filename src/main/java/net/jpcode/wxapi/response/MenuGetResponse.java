/*******************************************************
 * Copyright 2018 jpcode
 * contact http://www.jpcode.net/
 * 
 * --- wxapi
 * 
 ********************************************************/

package net.jpcode.wxapi.response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.jpcode.wxapi.WxApiResponse;
import net.jpcode.wxapi.model.ConditionalMenu;
import net.jpcode.wxapi.model.CustomMenu;
import net.jpcode.wxapi.model.MenuItem;

public class MenuGetResponse extends WxApiResponse {

	private CustomMenu customMenu;
	private List<ConditionalMenu> conditionalMenus;
	
	public CustomMenu getCustomMenu() {
		return customMenu;
	}
	
	@Override
	public void parse() throws IOException {
		super.parse();
		
		// custommenu
		Map map = getValue( "menu" );
		
		customMenu = new CustomMenu();
		
		List<Map<String, Object>> list = (List<Map<String, Object>>)map.get("button");
		for (Map<String, Object> item : list) {
			customMenu.addItem(parseMenuItem(item));
		}
		
		// "conditionalmenu"
		
	}
	
	private MenuItem parseMenuItem(Map<String, Object> item) {
		MenuItem mi = new MenuItem();
		mi.setName( (String)item.get("name") );
		mi.setType( (String)item.get("type") );
		mi.setKey( (String)item.get("key") );
		mi.setUrl( (String)item.get("url") );
		mi.setMediaId( (String)item.get("media_id") );
		mi.setAppId( (String)item.get("appid") );
		mi.setPagePath( (String)item.get("pagepath") );
		
		if (item.containsKey("sub_button")) {
			List<Map<String, Object>> list = (List<Map<String, Object>>)item.get("sub_button");
			for (Map<String, Object> subitem : list) {
				mi.addItem(parseMenuItem(subitem));
			}
		}
	
		return mi;
	}
}
