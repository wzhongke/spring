package com.wang.action.mediator;

/**
 * 控制了几个组件之间的通信
 * @author wangzhongke
 */
public class FontDialogDirectator extends DialogDirector {

	private Button ok;
	private Button cancel;
	private ListBox fontList;
	private EntryField fontName;
	@Override
	public void showDialog() {

	}

	@Override
	public void widgetChanged(Widget widget) {
		if (widget == ok) {
			// apply something change
		} else if (widget == cancel) {
			// apply cancel widget
		} else if (widget == fontList) {

		} else if (widget == fontName) {

		}

	}

	@Override
	protected void createWidgets() {
		ok = new Button(this);
		cancel = new Button(this);
		fontList = new ListBox(this);
		fontName = new EntryField(this);
	}
}
