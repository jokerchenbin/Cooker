/**
 * 
 */
package com.mastercooker.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.mastercooker.R;


/** 
 * @author long_xia@loongjoy.com
 * @version 创建时间：2015年12月2日 上午10:52:15 
 * 
 */

/**
 * @author long_xia@loongjoy.com
 *
 */
public class AppLoadingDialog extends Dialog{

	/**
	 * @param context
	 * @param theme
	 */
	public AppLoadingDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see android.app.Dialog#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_dialog);
		setCanceledOnTouchOutside(false);
		
	}
	
}
